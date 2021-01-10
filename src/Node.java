import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Node implements Runnable {

    // Variables for the Multicast Communication
    private static final int    MULTICAST_PORT = 1234;
    private static final String MULTICAST_ADDR = "228.5.6.7";

    // Variables kept during election
    private Boolean in_election; // 1 if the node is election a leader
    private int id; // this node id
    private int node_parent; // id of the parent node
    private int node_leader; // id of the leader node (-1 when a node starts its execution)
    private ArrayList<Node> neighbour_nodes = new ArrayList<>();
    private ArrayList<Node> nodes_left_to_ack = new ArrayList<>();

    // Variables for node coordinates and value
    private int x_coord, y_coord, value;

    public Node ( int id, int x_coord, int y_coord, int value ) {
        this.id = id;
        this.x_coord = x_coord;
        this.y_coord = y_coord;
        this.value = value;
    }

    @Override
    public void run() {
        try {
            if(this.id == 0){
                Transmitter.send(this.id, "hi", MULTICAST_ADDR, MULTICAST_PORT);
                TimeUnit.MILLISECONDS.sleep(500);
                Transmitter.send(this.id, "Exit", MULTICAST_ADDR, MULTICAST_PORT);
            }
            else {
                Receiver.receive(this.id, MULTICAST_ADDR, MULTICAST_PORT);
            }
            //TimeUnit.MILLISECONDS.sleep(10);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}