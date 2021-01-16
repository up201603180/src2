import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Transmitter implements Runnable{

    private static final int    MULTICAST_PORT = 1234;
    private static final String MULTICAST_ADDRESS = "228.5.6.7";
    public int nodeID;
    public int value;

    public Transmitter(int nodeID, int value){
        this.nodeID = nodeID;
        this.value = value;
    }

    // Send a DatagramPacket to a specific port of a multicast address
    public static void send ( int nodeID, String message, String multicast_addr, int multicast_port ) throws IOException {

        // Join a Multicast group
        InetAddress multicast_group = InetAddress.getByName( multicast_addr );
        MulticastSocket multicast_socket = new MulticastSocket();

        // Send a DatagramPacket containing a message
        byte[] buffer = ( nodeID + ";" + message ).getBytes();
        DatagramPacket packet = new DatagramPacket( buffer, buffer.length, multicast_group, multicast_port );
        multicast_socket.send( packet );

        // Close the socket
        multicast_socket.close();

    }
    public void run(){
        try {
            send( this.nodeID, String.valueOf( this.value ), MULTICAST_ADDRESS, MULTICAST_PORT );
        }
        catch (IOException ex){
            //ex.printStackTrace();
            System.out.println("Failed to send message");
        }
    }

}