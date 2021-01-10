import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Receiver {
    private String multicast_address;
    private int multicast_port;
    private MulticastSocket multicast_socket;
    private InetAddress multicast_group;
    Receiver(String multicast_address, int multicast_port){
        this.multicast_address = multicast_address;
        this.multicast_port = multicast_port;
    }

    public void initiateSockets() throws IOException{
        // Join a Multicast group
        this.multicast_socket = new MulticastSocket( this.multicast_port );
        multicast_group = InetAddress.getByName( this.multicast_address );
        multicast_socket.joinGroup( multicast_group );
    }

    public void closeMulticast() throws IOException{
        // Leave Multicast group and close the socket
        multicast_socket.leaveGroup(multicast_group);
        multicast_socket.close();
    }

    // Receive a DatagramPacket from a specific port of a multicast address
    public NodeMessage receive( int node ) throws IOException{
        // Receive a DatagramPacket containing a message
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        this.multicast_socket.receive(packet);
        String fullmessage = new String(packet.getData(), packet.getOffset(), packet.getLength());
        int nodeOrig = Integer.parseInt(fullmessage.split((";"))[0]);
        String message = fullmessage.split((";"))[1];
        return new NodeMessage(nodeOrig, message);
    }

}