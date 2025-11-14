import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {

        ServerSocket server = new ServerSocket(4000);
        System.out.println("Server waiting for image...");

        Socket socket = server.accept();
        System.out.println("Client connected.");

        DataInputStream dis = new DataInputStream(socket.getInputStream());

        int len = dis.readInt();   // read size
        System.out.println("Image Size: " + (len / 1024) + " KB");

        byte[] data = new byte[len];
        dis.readFully(data);
        dis.close();

        // Convert bytes back to image
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedImage img = ImageIO.read(bais);

        // Display image in JFrame
        JFrame frame = new JFrame("Server");
        JLabel label = new JLabel(new ImageIcon(img));
        frame.add(label);
        frame.pack();
        frame.setVisible(true);

        socket.close();
        server.close();
    }
}
