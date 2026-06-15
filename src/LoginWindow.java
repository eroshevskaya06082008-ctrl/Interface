import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class LoginWindow extends JFrame{

    private JTextField loginField;
    private JButton loginButton;
    public LoginWindow(){
        setTitle("Login window");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        add(new Label("Enter login: "));
        loginField = new JTextField(15);
        add(loginField);
        loginButton = new JButton("Login");
        add(loginButton);

        loginButton.addActionListener(e -> connectToServer());
        loginField.addActionListener(e-> connectToServer());

    }

    public void connectToServer(){
        String nickname = loginField.getText().trim();

        try{
            Socket socket = new Socket("localhost", 8888);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(nickname);
            ChatForm chatForm = new ChatForm(socket);
            JFrame frame = new JFrame();
            frame.setContentPane(chatForm.getMainPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            this.dispose();//close login window!!!

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new LoginWindow().setVisible(true));
    }

}
