import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatForm {
    JPanel panel1;
    private JPanel mainPanel;
    private JList userList;
    private JTextField messageField;
    private JButton sendButton;
    private JTextArea chatArea;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public ChatForm(Socket socket) throws IOException {
        this.socket = socket;
        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        sendButton.addActionListener(e -> sendMessage());
        messageField.addActionListener(e -> sendMessage());//standard messageFiled action is press enter

        new Thread(()-> {
            try{
                String mes;
                while ((mes = reader.readLine()) != null){
                    chatArea.append(mes + "\n");
                }

            } catch (Exception e){
                e.printStackTrace();
            }


        }).start();
    }

    public void sendMessage() {
        String text = messageField.getText().trim();
        if (!text.isEmpty()) {
//            chatArea.append(text + "\n");//show on display
            writer.println(text);
            messageField.setText("");//clear
            messageField.requestFocus();//returns focus to message field
        }

    }
    public JPanel getMainPanel(){
        return this.panel1;
    }


}



