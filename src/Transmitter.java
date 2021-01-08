import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Transmitter {

    // Send a DatagramPacket to a specific port of a multicast address
    public static void send ( String message, String multicast_addr, int multicast_port ) throws IOException {

        // Join a Multicast group
        InetAddress multicast_group = InetAddress.getByName( multicast_addr );
        MulticastSocket multicast_socket = new MulticastSocket();

        // Send a DatagramPacket containing a message
        byte[] buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket( buffer, buffer.length, multicast_group, multicast_port );
        multicast_socket.send( packet );

        // Close the socket
        multicast_socket.close();

    }

}