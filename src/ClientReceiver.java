import java.io.BufferedReader;
import java.io.IOException;

public class ClientReceiver implements Runnable{
    //Zadanie 3.
    //
    //Stwórz klasę o nazwie ClientReceiver. Zdefiniuj w niej funkcje
    // odpowiadające wszystkim dotychczas obsługiwanym działaniom (broadcast, whisper,
    // login broadcast, logout broadcast, online). W wątku obsługującym połączenie z serwerem,
    // póki co, obsłuż broadcast. Wiadomość rozróżnij na podstawie prefiksu.
    private ChatForm chatForm;
    private BufferedReader reader;
    public ClientReceiver(ChatForm chatForm, BufferedReader reader){
        this.reader = reader;
        this.chatForm = chatForm;

    }
    @Override
    public void run() {
        try{
            String message;
            while((message = reader.readLine())!= null){
                parseMes(message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void parseMes(String message){
        if(message.startsWith("User ") && message.endsWith(" connected to server")){
            loginBroadcast(message);
        } else if(message.startsWith("User ") && message.endsWith(" disconnected")){
            logoutBroadcast(message);
        } else if(message.startsWith("Whisper from ")){
            whisper(message);
        } else if(message.startsWith("Online users:")){
            online(message);
        } else {
            broadcast(message);
        }
    }

    public void loginBroadcast(String message){
        String nickname = message.substring(5, message.indexOf(" connected to server"));
        chatForm.addUser(nickname);
        chatForm.send(message);
    }

    public void logoutBroadcast(String message){
        String nickname = message.substring(5, message.indexOf(" disconnected"));
        chatForm.removeUser(nickname);
        chatForm.send(message);
    }

    public void broadcast(String message){
        chatForm.send(message);
    }

    public void online(String message){
        String cleanMes = message.replace("Online users: ", "");
        String[] parts = cleanMes.split(",");
        chatForm.updateOnline(parts);
    }

    public void whisper(String message){
        chatForm.send(message);
    }


}
