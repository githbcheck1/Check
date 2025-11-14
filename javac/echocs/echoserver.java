import java.io.*;
import java.net.*;
import java.util.Scanner;

public class echoserver {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(5678);
            System.out.println("Server started. Waiting for client...");

            Socket socket = server.accept();
            System.out.println("Client connected!");

            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                if (in.hasNextLine()) {
                    String msg = in.nextLine();
                    System.out.println("Message from Client: " + msg);
                    out.println(msg);   // echo back
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
