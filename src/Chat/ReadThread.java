package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread {
    private String nickname;
    private BufferedReader reader;

    public ReadThread(String nickname, Socket socket) {
        this.nickname = nickname;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        String message;
        while (true) {
            try {
                message = this.reader.readLine();
                if (message == null) {
                    System.err.println("\rConnection lost.");
                    return;
                }
                System.out.println("\r" + message);
                System.out.print("\r[" + this.nickname + "]: ");

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

        }

    }
}

