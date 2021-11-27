package server.handlers;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import server.models.User;
import static server.ui.serverForm.*;

public class ServerStart implements Runnable {

    public static HashMap<User, PrintWriter> onlineUsers;

    @Override
    public void run() {
        onlineUsers = new HashMap<>();

        try {
            ServerSocket serverSocket = new ServerSocket(5000);

            taConsole.append("Server started at port 5000\n");
            taConsole.append("Waiting for clients...\n");
            taConsole.setCaretPosition(taConsole.getDocument().getLength());

            btnStart.setEnabled(false);
            btnStart.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            btnRefresh.setEnabled(true);
            btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            btnList.setEnabled(true);
            btnList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            btnClear.setEnabled(true);
            btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

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

    /**
     * Methode qui permet de supprimer tous les utilisateurs connectés
     */
    public static void deleteAllUsers() {
        onlineUsers.clear();
    }

}
