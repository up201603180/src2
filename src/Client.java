import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;

public class Client {

    public static void main (String[] args){
        try {
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress ipAddress = InetAddress.getByName("localhost"); //May change method
            String str = userInput.readLine();
            byte[] inData = new byte[1024];
            byte[] outData = str.getBytes();
            DatagramPacket outPacket = new DatagramPacket(outData, outData.length, ipAddress, 1234);
            DatagramPacket inPacket = new DatagramPacket(inData, inData.length);
            clientSocket.send(outPacket);
            System.out.println("Waiting");
            clientSocket.receive(inPacket);
            System.out.println("Received");
            String receiveStr = new String(inPacket.getData());
            System.out.println(receiveStr);
            clientSocket.close();
        } catch(IOException ioe) {
            System.out.println("Error:" + ioe);
        }

    }
}