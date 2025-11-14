import java.io.*;
import java.net.*;

class fileserver {
    public static void main(String args[]) {
        try {
            ServerSocket server = new ServerSocket(1391);
            System.out.println("Server started...");

            while (true) {
                Socket socket = server.accept();
                System.out.println("Client connected");

                BufferedReader din = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                DataOutputStream dout = new DataOutputStream(socket.getOutputStream());

                String fileName = din.readLine();   // file to read
                FileReader fr = new FileReader(fileName);
                BufferedReader br = new BufferedReader(fr);

                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    dout.writeBytes(line + "\n");
                }

                br.close();
                dout.writeBytes("-1\n"); // end of file
                socket.close();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
