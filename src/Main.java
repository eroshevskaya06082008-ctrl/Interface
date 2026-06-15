import javax.swing.*;

public class Main{
    public static void main(String[] args){
        JFrame frame = new JFrame("Chat");
        ChatForm chatForm = new ChatForm();
        frame.setContentPane(chatForm.panel1);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 400);
        frame.setVisible(true);
    }
}