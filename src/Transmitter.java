import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Transmitter implements Runnable{

    /*
     *   Variables
     */
    public int nodeID;
    private String multicast_address;
    private int multicast_port;
    private MulticastSocket multicast_socket;
    private InetAddress multicast_group;

    /*
     *   Constructors
     */
    public Transmitter( int nodeID, String multicast_address, int multicast_port ){

        this.nodeID = nodeID;
        this.multicast_address = multicast_address;
        this.multicast_port = multicast_port;

    }

    /*
     *   Methods
     */

    // Join a Multicast group
    public void initiateSockets() throws IOException {

        this.multicast_group = InetAddress.getByName( this.multicast_address );
        this.multicast_socket = new MulticastSocket();

    }

    // Leave Multicast group and close the socket
    public void closeMulticast() throws IOException {

        this.multicast_socket.close();

    }

    // Send a DatagramPacket
    public void send ( int nodeID, String message) throws IOException {

        // Send a DatagramPacket containing a message
        byte[] buffer = ( nodeID + ";" + message ).getBytes();
        DatagramPacket packet = new DatagramPacket( buffer, buffer.length, this.multicast_group, this.multicast_port );
        this.multicast_socket.send( packet );

    }

    @Override
    public void run(){
        try {
            initiateSockets();
            send( this.nodeID, "Hello");
            closeMulticast();
        }
        catch (IOException ex){
            //ex.printStackTrace();
            System.out.println("Failed to send message");
        }
    }

}