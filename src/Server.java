import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private ServerSocket serverSocket;
    private ConcurrentHashMap<String, PrintWriter> writerHashMap = new ConcurrentHashMap<>();
    public Server() throws IOException {
        serverSocket = new ServerSocket(8888);
    }
    public void listen() throws IOException {
        System.out.println("Server started");
        while(true){
            Socket socket = serverSocket.accept();
            System.out.println("new client connected");
            new Thread(() -> {
                try{
                    serveClient(socket);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }


    }
    public void serveClient(Socket socket){
        PrintWriter writer = null;
        String nickname = null;
        try{
            InputStream input = socket.getInputStream();
            BufferedReader reader= new BufferedReader(new InputStreamReader(input));
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            String message;
            nickname = reader.readLine();
            if(nickname != null){
                broadcast("User " + nickname + " connected to server");
                writerHashMap.put(nickname, writer);
            }
            while((message = reader.readLine()) != null){
                if(message.startsWith("/get online")) {
                    String list = String.join(",", this.writerHashMap.keySet());
                    writer.println("Online users: " + list);
                } else if(message.startsWith("/w")){
                    String[] parts = message.split(" ", 3);
                    if(parts.length >= 3){
                        String targetUser = parts[1];
                        String privateMessage = parts[2];
                        PrintWriter targetWriter = writerHashMap.get(targetUser);
                        if(targetWriter != null){
                            targetWriter.println("Whisper from " + nickname + ": " + privateMessage);
                            writer.println("Whisper to " + targetUser + ": " + privateMessage);
                        } else {
                            writer.println("Error send private msg");
                        }
                    }
                } else {
                    broadcast(nickname + ": " +message);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error serveClient");
        } finally{
            if(nickname != null){
                broadcast("User " + nickname + " disconnected");
                writerHashMap.remove(nickname);
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Client already disconnected");
            }
        }


    }
    public void broadcast(String message){
        for(PrintWriter writer : writerHashMap.values()){
            try{
                writer.println(message);
            } catch (Exception e){
                System.err.println("Error send message");
            }
        }
    }

    public String online(){
        String online = String.join(",", this.writerHashMap.keySet());
        return online;
    }
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.listen();
    }
}
