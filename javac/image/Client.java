import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {

        Socket soc = new Socket("localhost", 4000);
        System.out.println("Client is running...");

        try {
            System.out.println("Reading image from disk...");
            BufferedImage img = ImageIO.read(new File("Balloons.jpg"));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", baos);
            baos.flush();

            byte[] bytes = baos.toByteArray();
            baos.close();

            System.out.println("Sending image to server...");
            DataOutputStream dos = new DataOutputStream(soc.getOutputStream());

            dos.writeInt(bytes.length);      // send size
            dos.write(bytes);                // send image data

            System.out.println("Image sent successfully.");

            dos.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        soc.close();
    }
}
