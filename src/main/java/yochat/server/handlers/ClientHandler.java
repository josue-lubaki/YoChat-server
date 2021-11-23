package yochat.server.handlers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import yochat.server.models.Command;
import yochat.server.models.Paquet;
import yochat.server.models.User;

import static yochat.server.ui.serverForm.*;

public class ClientHandler implements Runnable {
    
    private Socket clientSocket;
    private PrintWriter clientPrintWriter;
    private BufferedReader clientBufferedReader;

    public ClientHandler(Socket client, PrintWriter writer) {
        try {
            this.clientSocket = client;
            InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            clientBufferedReader =  new BufferedReader(inputStreamReader);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @Override
    public void run() {
        
        String message;
        String [] data;
        try{

            while ((message = clientBufferedReader.readLine()) != null) {
                taConsole.append("Received : " + message + "\n");
                data = message.split(":");

                User user = new User(data[0]);
                String command = data[2];
                
                // Usernane : message : command
                Paquet paquet = new Paquet(user, data[1], command);

                switch(paquet.getCommand()) {
                    //case Command.CONNECT.getCommand():

                    break;

              }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

}

