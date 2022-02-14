package Chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread extends Thread {
    private String nickname;
    private PrintWriter writer;

    public WriteThread(String nickname, Socket socket) {
        this.nickname = nickname;
        try {
            this.writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.writer.println(this.nickname);
        System.out.println(this.nickname + ", welcome to chat!");

        try (Scanner sc = new Scanner(System.in)) {
            String message;
            while (true) {
                System.out.print("\r[" + this.nickname + "]: ");
                message = sc.nextLine();
                this.writer.println(message);
                if (message.equalsIgnoreCase("bye")) {
                    break;
                }
            }
        }
    }
}

