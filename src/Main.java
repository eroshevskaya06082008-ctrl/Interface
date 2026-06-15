import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class Main{
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Chat");
        ChatForm chatForm = new ChatForm(new Socket("localhost", 8888));
        frame.setContentPane(chatForm.panel1);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 400);
        frame.setVisible(true);
    }
}