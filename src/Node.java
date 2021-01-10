import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Node {

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

    public Node ( int id ) {
        this.id = id;
    }

    public static void main ( String[] args ) throws IOException, InterruptedException {

        // Atribuir dinamicamente id's aos nós com o mesmo pedaço de código


        // Receive
        Thread t1 = new Thread( new Receiver() );
        t1.start();

        // Delay to ensure the first send is received
        TimeUnit.MILLISECONDS.sleep(10);

        // Transmit
        Transmitter.send( "Multicast Message Test", MULTICAST_ADDR, MULTICAST_PORT );
        //Transmitter.send( "Exit", MULTICAST_ADDR, MULTICAST_PORT );

    }

}