import java.io.IOException;
import java.net.DatagramPacket;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Node implements Runnable {

    /*
     * Variables
     */

    // Variables for the Multicast Communication
    private static final int    MULTICAST_PORT = 1234;
    private static final String MULTICAST_ADDR = "228.5.6.7";
    Receiver receiver;

    // Variables kept during election
    private Boolean in_election; // 1 if the node is election a leader
    private int nodeID; // this node id
    private int node_parent; // id of the parent node
    private int node_leader; // id of the leader node (-1 when a node starts its execution)
    private ArrayList<Node> neighbour_nodes = new ArrayList<>();
    private ArrayList<Node> nodes_left_to_ack = new ArrayList<>();
    private Map<Integer,Integer> nodeInfoList = new HashMap<>();

    // Variables for node coordinates and value
    private int x_coord, y_coord, value;

    /*
     * Constructors
     */

    public Node ( int nodeID, int x_coord, int y_coord, int value ) {

        this.nodeID = nodeID;
        this.x_coord = x_coord;
        this.y_coord = y_coord;
        this.value = value;

        this.receiver = new Receiver( nodeID, MULTICAST_ADDR, MULTICAST_PORT );

    }

    /*
     * Methods
     */

    @Override
    public void run() {

        try {

            nodeInfoList.put( this.nodeID, this.value );
            this.receiver.initiateSockets();
            Transmitter.send( this.nodeID, String.valueOf( this.value ), MULTICAST_ADDR, MULTICAST_PORT );
            NodeMessage nm;
            boolean keep = true;

            while ( keep ) {

                nm = this.receiver.receive();

                if ( nm != null ) {
                    System.out.println( "Node " + nm.getNodeId() + ": " + nm.getMessage() );
                    if ( nm.getMessage().equals("EXIT") )
                        keep = false;
                }


            }

            this.receiver.closeMulticast();

        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public int getNodeID() {
        return nodeID;
    }

    public int getX_coord() {
        return x_coord;
    }

    public int getY_coord() {
        return y_coord;
    }

    public int getValue() {
        return value;
    }

    public static void main ( String[] args ) throws InterruptedException {

        if ( args.length != 4 ) {
            System.out.println( "Usage: java Node.java <nodeID> <x_coord> <y_coord> <node_value>" );
        }
        Node node = new Node( Integer.parseInt(args[0]), Integer.parseInt(args[1]),Integer.parseInt(args[2]), Integer.parseInt(args[3]) );
        System.out.println( "ID = " + node.getNodeID() + "\nx = " + node.getX_coord() + "\ny = " + node.getY_coord() + "\nvalue = " + node.getValue() );

        Thread t = new Thread( node );
        t.start();
        TimeUnit.MILLISECONDS.sleep( 10 );

    }

}