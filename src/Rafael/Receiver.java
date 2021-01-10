package Rafael;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Receiver implements Runnable {

    // Variables for the Multicast Communication
    private static final int    MULTICAST_PORT = 1234;
    private static final String MULTICAST_ADDR = "228.5.6.7";

    // Receive a DatagramPacket from a specific port of a multicast address
    public void receive( String multicast_address, int multicast_port ) throws IOException {

        // Join a Multicast group
        MulticastSocket multicast_socket = new MulticastSocket( multicast_port );
        InetAddress multicast_group = InetAddress.getByName( multicast_address );
        multicast_socket.joinGroup( multicast_group );

        // Receive a DatagramPacket containing a message
        byte[] buffer = new byte[ 1024 ];
        while ( true ) {
            System.out.println( "[Client]: Waiting..." );

            DatagramPacket packet = new DatagramPacket( buffer, buffer.length );
            multicast_socket.receive( packet );
            String message = new String( packet.getData(), packet.getOffset(), packet.getLength() );
            System.out.println("[Server]: " + message );

            // Rejeitar as mensagens do próprio nó

            // Tratar diversas Rafael.MessageType
            if ( "Exit".equals( message ) ) break;
        }

        // Leave Multicast group and close the socket
        multicast_socket.leaveGroup( multicast_group );
        multicast_socket.close();

    }

    @Override
    public void run() {
        try {
            receive( MULTICAST_ADDR, MULTICAST_PORT );
        } catch ( IOException ex ) {
            ex.printStackTrace();
        }
    }

}