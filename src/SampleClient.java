import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.net.*;

public class SampleClient {

    private static DatagramSocket socket;
    private static InetAddress address;

    private static byte[] buf;

    private static StreamTokenizer sc = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));

    public static void main(String args[]) throws IOException {
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName("127.0.0.1");
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }

        String s, command;
        Integer id;

        while(true) {
            command = nextString();
            if(command.equals("receive") || command.equals("Receive") || command.equals("r")) {
                receive();
            }
            else {
                id = nextInt();
                s = nextString();
                System.out.println(id + " " + s);
                send(id, s);
            }
            if (false) {
                break;
            }
        }

        socket.close();
    }

    private static byte[] concat(byte[] a, byte[] b) {
        byte[] t = new byte[a.length + b.length];
        System.arraycopy(a, 0, t, 0, a.length);
        System.arraycopy(b, 0, t, a.length, b.length);
        return t;
    }

    public static void receive() throws IOException {
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        System.out.println(packet);
    }

    public static void send(Integer id, String message) throws IOException {
        byte[] a = new byte[] {id.byteValue()};
        byte[] b = message.getBytes();
        buf = concat(a, b);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 50000);
        socket.send(packet);
    }

    private static String nextString() throws IOException {
        sc.nextToken();
        return sc.sval;
    }

    private static int nextInt() throws IOException {
        sc.nextToken();
        return (int) sc.nval;
    }
}
