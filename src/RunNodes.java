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
        Random rand = new Random();
        for (int i=6;i>0;i--){
            Node node = new Node(i, i,i, rand.nextInt(100));
            threads.add(new Thread(node));
        }
        for(int i=0; i<threads.size(); i++){
            threads.get(i).start();
            TimeUnit.MILLISECONDS.sleep(10);
        }
    }
}
