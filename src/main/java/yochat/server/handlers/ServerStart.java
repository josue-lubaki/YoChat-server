package yochat.server.handlers;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import yochat.server.models.User;

public class ServerStart implements Runnable {

    public HashMap<User, PrintWriter> onlineUsers;

    @Override
    public void run() {
        onlineUsers = new HashMap<>();

        try {
            ServerSocket serverSocket = new ServerSocket(5050);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
                

                Thread listener = new Thread(new ClientHandler(clientSocket, printWriter));
				listener.start();
            }
           
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }

}
