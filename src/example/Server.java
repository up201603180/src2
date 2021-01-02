package example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {

    public static void send( String message, String ipAddress, int port ) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress group = InetAddress.getByName( ipAddress );
        byte[] msg = message.getBytes();
        DatagramPacket packet = new DatagramPacket( msg, msg.length, group, port );
        socket.send( packet );
        socket.close();
    }

    public static void main( String[] args ) throws IOException {
        send( "This is a multicast message", "230.0.0.0", 4321 );
        send( "This is the second multicast message", "230.0.0.0", 4321 );
        send( "This is the third multicast message", "230.0.0.0", 4321 );
        send( "OK", "230.0.0.0", 4321 );
    }
}