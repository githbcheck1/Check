import java.io.*;
import java.net.*;
import java.util.Scanner;

public class echoclient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5678);
            System.out.println("Connected to server!");

            Scanner userInput = new Scanner(System.in);
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Enter your message:");

            while (true) {
                String msg = userInput.nextLine();  // user input
                out.println(msg);                    // send to server

                if (in.hasNextLine()) {
                    String echoed = in.nextLine();
                    System.out.println("Echo from server: " + echoed);
                }

                System.out.println("Enter your message:");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
