package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class UserThread extends Thread {

    private Socket socket;
    private ChatServer chatServer;
    private PrintWriter writer;
    private String nickname;

    public UserThread(Socket socket, ChatServer chatServer) {
        this.socket = socket;
        this.chatServer = chatServer;
    }

    @Override
    public void run() {
        try {
            BufferedReader b = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.writer = new PrintWriter(this.socket.getOutputStream(), true);
            nickname = b.readLine();
            System.err.println(nickname + " connected!");
            this.sendMessage("Connected users: " + getUserNames());
            sendToAll("New user connected: " + this.nickname, this);
            String message;
            while ((message = b.readLine()) != null) {
                if (message.equalsIgnoreCase("bye")) {
                    break;
                }
                System.out.println("[" + nickname + "]: " + message);
                sendToAll("[" + nickname + "]: " + message, this);
            }
            remove(this);
            this.socket.close();
            sendToAll(this.nickname + " has left the chat.", this);
        } catch (IOException e) {
            remove(this);
            sendToAll(this.nickname + " has left the chat.", this);
            return;
        }
    }

    void sendToAll(String message, UserThread user) {
        this.chatServer.users.stream().filter(u -> u!= user).forEach(u -> u.sendMessage(message));
    }

    void sendMessage(String message) {
        if (this.writer != null)
            this.writer.println(message);
    }

    void remove(UserThread user) {
        String username = user.getNickname();
        chatServer.users.remove(user);
        System.err.println(username + " disconnected!");
    }

    String getNickname() {
        return this.nickname;
    }

    List<String> getUserNames() {
        return this.chatServer.users.stream().map(UserThread::getNickname).collect(Collectors.toList());
    }
}
