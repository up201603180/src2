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
    Receiver receiver;

    // Variables kept during election
    private int src_i; // Computation index of the diffusing computation
    private int delta_i; // 0 if node has sent its pending ACK message to its parent
    private boolean in_election; // 1 if the node is electing a leader
    private int nodeID; // this node id
    private int nodeParent; // id of the parent node
    private int nodeLeader; // id of the leader node (-1 when a node starts its execution)
    private ArrayList<NodeInfo> neighbourNodes = new ArrayList<>();
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

    public void initializeNode(Node node) throws Exception{
        NodeInfo node1 = new NodeInfo(1, 3, 0, 2);
        NodeInfo node2 = new NodeInfo(2, 2, 2, 1);
        NodeInfo node3 = new NodeInfo(3, 5, 2, 3);
        NodeInfo node4 = new NodeInfo(4, 10, 3, 3);
        NodeInfo node5 = new NodeInfo(5, 7, 3, 1);

        //Tree initialization
        switch(node.nodeID) {
            case 1:
                node.nodeParent = 1;
                node.neighbourNodes.add(node2);
                node.neighbourNodes.add(node3);
                InetAddress group1 = InetAddress.getByName("230.0.0.1");
                MulticastSocket receiveSocket = new MulticastSocket(MULTICAST_PORT); //Receiver socket
                DatagramSocket transmitSocket = new DatagramSocket();
            case 2:
                node.nodeParent = 1;
                node.neighbourNodes.add(node1);
                node.neighbourNodes.add(node3);
                node.neighbourNodes.add(node5);
            case 3:
                node.nodeParent = 1;
                node.neighbourNodes.add(node1);
                node.neighbourNodes.add(node2);
                node.neighbourNodes.add(node4);
                break;
            case 4:
                node.nodeParent = 3;
                node.neighbourNodes.add(node3);
                node.neighbourNodes.add(node5);
                break;
            case 5:
                node.nodeParent = 2;
                node.neighbourNodes.add(node2);
                node.neighbourNodes.add(node4);
                break;
        }
    }


    //Getters
    public int getNodeID() {
        return nodeID;
    }

    public int getX_coord() {
        return xCoord;
    }

    public int getY_coord() {
        return yCoord;
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

        //Declaração do objecto para o thread receptor
        //Receiver receiver = new Receiver(Integer.parseInt(args[0]), MULTICAST_ADDRESS, MULTICAST_PORT);
        //Declaração do objecto para o thread transmissor
        Transmitter transmitter = new Transmitter(Integer.parseInt(args[0]), Integer.parseInt(args[3]));
        //Declaração dos threads
        //Thread threadReceiver = new Thread( receiver );
        //threadReceiver.start();
        Thread threadTransmitter = new Thread( transmitter);
        threadTransmitter.start();
        //Election algorithm

    }

}