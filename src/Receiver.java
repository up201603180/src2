import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

public class Receiver implements Runnable{

    /*
     *   Variables
     */
    private ArrayList<NodeInfo> neighbourInfo;
    private int nodeID;
    private String multicast_address;
    private int multicast_port;
    private MulticastSocket multicast_socket;
    private InetAddress multicast_group;

    /*
     *   Constructors
     */
    Receiver ( int nodeID, String multicast_address, int multicast_port, ArrayList<NodeInfo> neighbourInfo ) {

        this.nodeID = nodeID;
        this.multicast_address = multicast_address;
        this.multicast_port = multicast_port;
        this.neighbourInfo = neighbourInfo;

    }

    /*
     *   Methods
     */

    // Join a Multicast group
    public void initiateSockets () throws IOException {

        this.multicast_socket = new MulticastSocket( this.multicast_port );
        for ( int i = 0; i < this.neighbourInfo.size(); i++ ) {
            this.multicast_group = InetAddress.getByName( this.neighbourInfo.get(i).getMulticastAddress() );
            this.multicast_socket.joinGroup( multicast_group );
        }
        this.multicast_group = InetAddress.getByName( this.multicast_address );
        this.multicast_socket.joinGroup( this.multicast_group );

    }

    // Leave Multicast group and close the socket
    public void closeMulticast() throws IOException {

        this.multicast_socket.leaveGroup( this.multicast_group );
        this.multicast_socket.close();

    }

    // Receive a DatagramPacket
    public NodeMessage receive() throws IOException {

        byte[] buffer = new byte[ 1024 ];
        DatagramPacket packet = new DatagramPacket( buffer, buffer.length );
        this.multicast_socket.receive( packet );
        String full_message = new String( packet.getData(), packet.getOffset(), packet.getLength() );

        int nodeOrig = Integer.parseInt( full_message.split( (";") )[0] );
        String message = full_message.split( (";") )[1];

        if ( nodeOrig != this.nodeID )
            return new NodeMessage( nodeOrig, message );
        else
            return null;

    }

    public void run() {
        try {

            initiateSockets();
            NodeMessage nm;
            boolean keep = true;
            while (keep) {

                nm = receive();

                if (nm != null) {
                    System.out.println("Node " + nm.getNodeId() + ": " + nm.getMessage());
                    if (nm.getMessage().equals("EXIT")) {
                        keep = false;
                    }
                }
            }
            closeMulticast();

        }
        catch (IOException ex){
            //ex.printStackTrace();
            System.out.println("Failed to initialize sockets.");
        }
    }
}