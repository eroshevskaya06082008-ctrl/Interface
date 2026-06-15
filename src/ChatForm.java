import javax.swing.*;
import java.awt.*;

public class ChatForm {
    JPanel panel1;
    private JPanel mainPanel;
    private JList userList;
    private JTextField messageField;
    private JButton sendButton;
    private JTextArea chatArea;

    public ChatForm() {
        sendButton.addActionListener(e -> sendMessage());
        messageField.addActionListener(e -> sendMessage());//standard messageFiled action is press enter
    }

    public void sendMessage() {
        String text = messageField.getText().trim();
        if (!text.isEmpty()) {
            chatArea.append(text + "\n");//show on display
            messageField.setText("");//clear
            messageField.requestFocus();//returns focus to message field
        }

    }


}



