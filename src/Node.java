import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Node implements Runnable {

    // Variables for the Multicast Communication
    private static final int    MULTICAST_PORT = 1234;
    private static final String MULTICAST_ADDR = "228.5.6.7";
    Receiver receiver;

    // Variables kept during election
    private Boolean in_election; // 1 if the node is election a leader
    private int id; // this node id
    private int node_parent; // id of the parent node
    private int node_leader; // id of the leader node (-1 when a node starts its execution)
    private ArrayList<Node> neighbour_nodes = new ArrayList<>();
    private ArrayList<Node> nodes_left_to_ack = new ArrayList<>();
    private Map<Integer,Integer> nodeInfoList = new HashMap<Integer, Integer>();

    // Variables for node coordinates and value
    private int x_coord, y_coord, value;

    public Node ( int id, int x_coord, int y_coord, int value ) {
        this.id = id;
        this.x_coord = x_coord;
        this.y_coord = y_coord;
        this.value = value;

        this.receiver = new Receiver(MULTICAST_ADDR,MULTICAST_PORT);
    }

    @Override
    public void run() {
        try {
            nodeInfoList.put(this.id, this.value);
            Transmitter.send(this.id, String.valueOf(this.value), MULTICAST_ADDR, MULTICAST_PORT);
            this.receiver.initiateSockets();
            NodeMessage nm;
            boolean keep = true;
            while(keep) {
                nm = this.receiver.receive(this.id);
                if (!nodeInfoList.containsKey(nm.nodeId))
                    nodeInfoList.put(nm.nodeId, Integer.parseInt(nm.message));
                Map.Entry<Integer, Integer> maxEntry = null;
                for (Map.Entry<Integer, Integer> entry : nodeInfoList.entrySet())
                {
                    if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) < 0)
                    {
                        maxEntry = entry;
                    }
                }
                //System.out.println("Node "+this.id +": The node "+maxEntry.getKey()+" is the best with value: "+maxEntry.getValue());
                Transmitter.send(this.id, String.valueOf(this.value), MULTICAST_ADDR, MULTICAST_PORT);
                if(nodeInfoList.size()==6)
                    keep=false;
                if(nm.message=="Exit")
                    keep=false;
            }
            this.receiver.closeMulticast();
            Map.Entry<Integer, Integer> maxEntry = null;
            for (Map.Entry<Integer, Integer> entry : nodeInfoList.entrySet())
            {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) < 0)
                {
                    maxEntry = entry;
                }
            }
            System.out.println("Node "+this.id +": The node "+maxEntry.getKey()+" is the best with value: "+maxEntry.getValue());
            for(int i=1;i<nodeInfoList.size()+1;i++){
                System.out.print(i+": "+nodeInfoList.get(i)+" || ");
            }
            System.out.println();
            //TimeUnit.MILLISECONDS.sleep(10);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}