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
    public DefaultListModel<String> listModel = new DefaultListModel<>();


    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public ChatForm(Socket socket) throws IOException {
        this.socket = socket;
        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        sendButton.addActionListener(e -> sendMessage());
        messageField.addActionListener(e -> sendMessage());//standard messageFiled action is press enter
        new Thread(new ClientReceiver(this, reader)).start();
        userList.setModel(listModel);
        writer.println("/get online");
    }

    public void sendMessage() {
        String text = messageField.getText().trim();
        if (!text.isEmpty()) {
            writer.println(text);
            messageField.setText("");//clear
            messageField.requestFocus();//returns focus to message field
        }

    }
    public JPanel getMainPanel(){
        return this.panel1;
    }

    public void send(String message){
        this.chatArea.append(message + "\n");
    }

    public void addUser(String user){
        this.listModel.addElement(user);
    }
    public void removeUser(String user){
        this.listModel.removeElement(user);
    }
    public void clearUsers(){
        this.listModel.clear();
    }

    public void updateOnline(String[] online){
        clearUsers();
        for(String onl : online){
            this.listModel.addElement(onl);
        }
    }



}



