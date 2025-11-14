import java.io.*;
import java.net.*;
import java.util.Scanner;

class fileclient {
    public static void main(String args[]) {
        try {
            Scanner sc = new Scanner(System.in);

            Socket socket = new Socket("localhost", 1391);
            System.out.println("Connected to server...");

            BufferedReader din = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());

            System.out.print("Enter the file name: ");
            String fileName = sc.nextLine();
            dout.writeBytes(fileName + "\n");

            System.out.print("Enter the new file name: ");
            String newFileName = sc.nextLine();

            FileWriter fw = new FileWriter(newFileName);

            String serverLine;
            while (true) {
                serverLine = din.readLine();

                if (serverLine.equals("-1"))
                    break;

                System.out.println(serverLine);
                fw.write(serverLine + "\n");
            }

            fw.close();
            socket.close();
            System.out.println("File received and saved as " + newFileName);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
