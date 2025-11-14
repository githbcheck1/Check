import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TalkClient {
    public static final String HOST = "localhost";
    public static final int PORT = 1234;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner console = new Scanner(System.in)) {

            System.out.println("Connected to server " + HOST + ":" + PORT);

            while (true) {
                System.out.print("Enter the message for server (type 'exit' to quit): ");
                String msg = console.nextLine();
                out.println(msg); // send to server

                if ("exit".equalsIgnoreCase(msg.trim())) {
                    System.out.println("Requested exit. Waiting for server response...");
                    String serverResp = in.readLine();
                    if (serverResp != null) System.out.println("Message from Server: " + serverResp);
                    break;
                }

                String serverResp = in.readLine();
                if (serverResp == null) {
                    System.out.println("Server closed the connection.");
                    break;
                }
                System.out.println("Message from Server: " + serverResp);
            }

        } catch (IOException e) {
            System.err.println("I/O error in client: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Client stopped.");
    }
}
