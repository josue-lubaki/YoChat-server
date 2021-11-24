package yochat.server.handlers;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import yochat.server.models.User;
import static yochat.server.ui.serverForm.*;

public class ServerStart implements Runnable {

    public static HashMap<User, PrintWriter> onlineUsers;

    @Override
    public void run() {
        onlineUsers = new HashMap<>();

        try {
            ServerSocket serverSocket = new ServerSocket(5002);

            taConsole.append("Server started at port 5002\n");
            taConsole.append("Waiting for clients...\n");
            taConsole.setCaretPosition(taConsole.getDocument().getLength());

            btnStart.setEnabled(false);
            btnStop.setEnabled(true);
            btnList.setEnabled(true);
            jButton2.setEnabled(true);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());

                Thread listener = new Thread(new ClientHandler(clientSocket, printWriter));
                listener.start();
                taConsole.append("New client connected\n");
                taConsole.setCaretPosition(taConsole.getDocument().getLength());
            }

        } catch (Exception e) {
            taConsole.append("Error lors de la création de la Socket Server: " + e.getMessage() + "\n");
        }

    }

}
