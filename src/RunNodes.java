import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class RunNodes {
    RunNodes(){

    }

    public static void main ( String[] args ) throws IOException, InterruptedException {
        System.out.println("Main");
        Node nodeA = new Node(0, 0,0, 20);

        List<Thread> threads = new ArrayList<>();
        for (int i=1;i<6;i++){
            Node node = new Node(i, i,i, 40*i);
            threads.add(new Thread(node));
        }
        for(int i=0; i<threads.size(); i++){
            threads.get(i).start();
        }
        TimeUnit.MILLISECONDS.sleep(1000);
        Thread thread = new Thread((nodeA));
        thread.start();
    }
}
