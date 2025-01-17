import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getLocalHost();
        byte[] sendBuffer;
        byte[] receiveBuffer = new byte[65535];

        while (true) {
            System.out.println("Enter the 3 edge: 'a b c'");
            String input = scanner.nextLine();

            if (input.trim().equalsIgnoreCase("end")) {
                System.out.println("Exit...");
                break;
            }

            sendBuffer = input.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, 1234);
            socket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Answer= " + response);
        }

        socket.close();
        scanner.close();
    }
}
