import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TalkServer {
    public static final int PORT = 1234;

    public static void main(String[] args) {
        System.out.println("Server starting on port " + PORT + " ...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 Scanner console = new Scanner(System.in)) {

                System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress());

                String clientMsg;
                while ((clientMsg = in.readLine()) != null) {
                    System.out.println("\nMessage from client: " + clientMsg);
                    if ("exit".equalsIgnoreCase(clientMsg.trim())) {
                        System.out.println("Client requested exit. Closing connection.");
                        out.println("Server closed the connection.");
                        break;
                    }

                    System.out.print("Enter the message for client (type 'exit' to quit): ");
                    String reply = console.nextLine();
                    out.println(reply); // auto-flush because PrintWriter created with true
                    if ("exit".equalsIgnoreCase(reply.trim())) {
                        System.out.println("Server requested exit. Closing connection.");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("I/O error in server: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Server stopped.");
    }
}
