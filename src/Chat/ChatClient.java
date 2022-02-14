package Chat;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private String hostName;
    private int port;
    private String nickname;

    public ChatClient(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient("localhost", ChatServer.DEFAULT_PORT);
        System.out.println("Connecting to localhost ...");

        try {
            System.out.print("Enter your nickname: ");
            Scanner sc = new Scanner(System.in);
            chatClient.nickname = sc.nextLine();
            Socket socket = new Socket(chatClient.hostName, chatClient.port);
            System.err.println("Connected to chat server @ " + chatClient.hostName + " on port: " + chatClient.port);
            new WriteThread(chatClient.nickname, socket).start();
            new ReadThread(chatClient.nickname, socket).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
