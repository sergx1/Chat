package Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {

    public static final int DEFAULT_PORT = 12345;

    private int port;
    public ArrayList<UserThread> users = new ArrayList<>();

    public ChatServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        ChatServer ChatServer = new ChatServer(DEFAULT_PORT);
        try (ServerSocket server = new ServerSocket(ChatServer.port)) {
            System.err.println("Listening ...");;
            while (true) {
                Socket socket = server.accept();
                UserThread user = new UserThread(socket, ChatServer);
                ChatServer.users.add(user);
                user.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

