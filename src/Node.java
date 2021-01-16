import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Node {

    /*
     * Variables
     */

    // Variables for the Multicast Communication
    private static final int    MULTICAST_PORT = 1234;
    private static final String MULTICAST_ADDRESS_1 = "230.0.0.1";
    private static final String MULTICAST_ADDRESS_2 = "230.0.0.2";
    private static final String MULTICAST_ADDRESS_3 = "230.0.0.3";
    private static final String MULTICAST_ADDRESS_4 = "230.0.0.4";
    private static final String MULTICAST_ADDRESS_5 = "230.0.0.5";
    Receiver receiver;
    Transmitter transmitter;

    // Variables kept during election
    private int src_i; // Computation index of the diffusing computation
    private int delta_i; // 0 if node has sent its pending ACK message to its parent
    private boolean in_election; // 1 if the node is electing a leader
    private int nodeID; // this node id
    private int nodeParent; // id of the parent node
    private int nodeLeader; // id of the leader node (-1 when a node starts its execution)
    private ArrayList<NodeInfo> neighbourInfo = new ArrayList<>();
    private ArrayList<Node> nodes_left_to_ack = new ArrayList<>();

    // Variables for node coordinates and value
    private int xCoord, yCoord, value;
    private boolean hasLeader = false; //Variable to indicate if node has leader(delta minusculo)
    private int lid_i; // Current leader

    /*
     * Constructors
     */

    public Node ( int nodeID, int x_coord, int y_coord, int value ) {

        this.nodeID = nodeID;
        this.xCoord = x_coord;
        this.yCoord = y_coord;
        this.value = value;

        //this.receiver = new Receiver( nodeID, MULTICAST_ADDRESS, MULTICAST_PORT );
    }

    /*
     * Methods
     */
    public void initializeNode ( Node node ) {

        // NodeInfo Initialization
        NodeInfo node1 = new NodeInfo(1, MULTICAST_ADDRESS_1, 0, 2);
        NodeInfo node2 = new NodeInfo(2, MULTICAST_ADDRESS_2, 2, 1);
        NodeInfo node3 = new NodeInfo(3, MULTICAST_ADDRESS_3, 2, 3);
        NodeInfo node4 = new NodeInfo(4, MULTICAST_ADDRESS_4, 3, 3);
        NodeInfo node5 = new NodeInfo(5, MULTICAST_ADDRESS_5, 3, 1);

        // Tree initialization
        switch( node.nodeID ) {
            case 1:
                node.nodeParent = 1;
                node.neighbourInfo.add( node2 );
                node.neighbourInfo.add( node3 );
                // Receiver/Transmitter Instantiation
                this.receiver = new Receiver( node.nodeID, MULTICAST_ADDRESS_1, MULTICAST_PORT, neighbourInfo );
                this.transmitter = new Transmitter( node.nodeID, MULTICAST_ADDRESS_1, MULTICAST_PORT);
                break;
            case 2:
                node.nodeParent = 1;
                node.neighbourInfo.add( node1 );
                node.neighbourInfo.add( node3 );
                node.neighbourInfo.add( node5 );
                // Receiver/Transmitter Instantiation
                this.receiver = new Receiver( node.nodeID, MULTICAST_ADDRESS_2, MULTICAST_PORT, neighbourInfo );
                this.transmitter = new Transmitter( node.nodeID, MULTICAST_ADDRESS_2, MULTICAST_PORT);
                break;
            case 3:
                node.nodeParent = 1;
                node.neighbourInfo.add( node1 );
                node.neighbourInfo.add( node2 );
                node.neighbourInfo.add( node4 );
                // Receiver/Transmitter Instantiation
                this.receiver = new Receiver( node.nodeID, MULTICAST_ADDRESS_3, MULTICAST_PORT, neighbourInfo );
                this.transmitter = new Transmitter( node.nodeID, MULTICAST_ADDRESS_3, MULTICAST_PORT);
                break;
            case 4:
                node.nodeParent = 3;
                node.neighbourInfo.add( node3 );
                node.neighbourInfo.add( node5 );
                // Receiver/Transmitter Instantiation
                this.receiver = new Receiver( node.nodeID, MULTICAST_ADDRESS_4, MULTICAST_PORT, neighbourInfo );
                this.transmitter = new Transmitter( node.nodeID, MULTICAST_ADDRESS_4, MULTICAST_PORT);
                break;
            case 5:
                node.nodeParent = 2;
                node.neighbourInfo.add( node2 );
                node.neighbourInfo.add( node4 );
                // Receiver/Transmitter Instantiation
                this.receiver = new Receiver( node.nodeID, MULTICAST_ADDRESS_5, MULTICAST_PORT, neighbourInfo );
                this.transmitter = new Transmitter( node.nodeID, MULTICAST_ADDRESS_5, MULTICAST_PORT);
                break;
        }

        // Thread Initialization
        Thread threadReceiver = new Thread( this.receiver );
        threadReceiver.start();

        Thread threadTransmitter = new Thread( this.transmitter );
        threadTransmitter.start();

    }

    // Getters
    public int getNodeID() {
        return nodeID;
    }

    public int getXCoord() {
        return xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public int getValue() {
        return value;
    }

    public static void main ( String[] args ) {

        // Argument check
        if ( args.length != 4 ) System.out.println( "Usage: java Node.java <nodeID> <x_coord> <y_coord> <node_value>" );

        // Node Instantiation and Information Display
        Node node = new Node( Integer.parseInt(args[0]), Integer.parseInt(args[1]),Integer.parseInt(args[2]), Integer.parseInt(args[3]) );
        System.out.println( "ID = " + node.getNodeID() + "\nx = " + node.getXCoord() + "\ny = " + node.getYCoord() + "\nvalue = " + node.getValue() );

        node.initializeNode( node );

    }

}