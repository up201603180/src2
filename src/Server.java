import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(1234); //creation of socket
        BufferedReader serverInput = new BufferedReader(new InputStreamReader(System.in)); //to send data
        InetAddress ipAddress = InetAddress.getByName("localhost"); //to send data
        byte outData[] = new byte[1024];
        byte inData[] = new byte[1024];
        while(true){ //to receive data from client
            DatagramPacket packet2 = new DatagramPacket(inData, inData.length);
            serverSocket.receive(packet2);
            String str = new String(packet2.getData());
            System.out.println(str);

            InetAddress clientAddress = packet2.getAddress();
            int port = packet2.getPort();
            String sendStr = serverInput.readLine();
            outData = sendStr.getBytes();
            DatagramPacket outPacket = new DatagramPacket(outData, outData.length, clientAddress, port);
            serverSocket.send(outPacket);
        }
    }
}