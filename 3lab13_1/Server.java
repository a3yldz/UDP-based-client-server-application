import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(1234);
        byte[] receiveBuffer = new byte[65535];
        byte[] sendBuffer;

        System.out.println("Server is running...");

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            String data = new String(receivePacket.getData(), 0, receivePacket.getLength());
            if (data.trim().equalsIgnoreCase("end")) {
                System.out.println("Client sent end... EXITING");
                break;
            }

            String[] edges = data.split(" ");
            String response;

            if (edges.length == 3) {
                try {
                    int a = Integer.parseInt(edges[0]);
                    int b = Integer.parseInt(edges[1]);
                    int c = Integer.parseInt(edges[2]);

                    if (a + b > c && a+c >b && b+c >a) {
                        response = "It is a triangle";
                    } else {
                        response = "It is not a triangle";
                    }
                } catch (NumberFormatException e) {
                    response = "Invalid input. Enter integers.";
                }
            } else {
                response = "Invalid input. Enter exactly 3 edges.";
            }

            sendBuffer = response.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length,
                    receivePacket.getAddress(), receivePacket.getPort());
            socket.send(sendPacket);

            receiveBuffer = new byte[65535];
        }

        socket.close();
    }
}
