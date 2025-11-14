import java.util.Random;

public class stopandwait {

    // Frame class
    static class Frame {
        int seqNum;
        String data;

        Frame(int seqNum, String data) {
            this.seqNum = seqNum;
            this.data = data;
        }
    }

    // Sender class
    static class Sender {
        int seqNum = 0;
        int timeout = 100; // Timeout in ms
        Random random = new Random();

        public void send(Frame frame) {
            System.out.println("[Sender] Sending frame: " + frame.data + " (Seq#" + frame.seqNum + ")");
        }

        public boolean waitForAck(int seqNum) {
            // Simulate random ACK loss (30%)
            boolean ackLost = random.nextDouble() < 0.3;

            if (ackLost) {
                System.out.println("[Sender] ACK lost for Seq#" + seqNum);
                return false;
            } else {
                System.out.println("[Sender] ACK received for Seq#" + seqNum);
                return true;
            }
        }
    }

    // Receiver class
    static class Receiver {
        int expectedSeqNum = 0;

        public boolean receive(Frame frame) {
            if (frame.seqNum == expectedSeqNum) {
                System.out.println("[Receiver] Received frame: " + frame.data + " (Seq#" + frame.seqNum + ")");
                expectedSeqNum = 1 - expectedSeqNum; // Toggle
                return true;
            } else {
                System.out.println("[Receiver] Duplicate frame received. Discarding...");
                return false;
            }
        }
    }

    // Main function
    public static void main(String[] args) {

        Sender sender = new Sender();
        Receiver receiver = new Receiver();

        int totalFrames = 5;

        for (int i = 1; i <= totalFrames; ) {
            Frame frame = new Frame(sender.seqNum, "Frame-" + i);
            sender.send(frame);

            boolean correctFrame = receiver.receive(frame);

            if (correctFrame) {
                boolean ackReceived = sender.waitForAck(frame.seqNum);

                if (ackReceived) {
                    sender.seqNum = 1 - sender.seqNum; // Toggle sequence number
                    i++; // Go to next frame
                } else {
                    System.out.println("[Sender] Timeout! Retransmitting frame: " + frame.data);
                    try { Thread.sleep(sender.timeout); } catch (Exception e) {}
                }

            } else {
                System.out.println("[Sender] Receiver did not accept the frame. Retransmitting...");
                try { Thread.sleep(sender.timeout); } catch (Exception e) {}
            }
        }

        System.out.println("Simple Stop-and-Wait ARQ Simulation Completed.");
    }
}
