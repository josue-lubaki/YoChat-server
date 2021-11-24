package yochat.server.handlers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;

import yochat.server.models.Command;
import yochat.server.models.Paquet;
import yochat.server.models.User;
import static yochat.server.ui.serverForm.*;
import static yochat.server.handlers.ServerStart.onlineUsers;

public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private PrintWriter clientPrintWriter;
    private BufferedReader clientBufferedReader;

    public ClientHandler(Socket client, PrintWriter writer) {
        try {
            this.clientSocket = client;
            this.clientPrintWriter = writer;
            InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            clientBufferedReader = new BufferedReader(inputStreamReader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        String messageRecu;

        String[] messageSplit;
        try {

            while ((messageRecu = clientBufferedReader.readLine()) != null) {
                taConsole.append("Received : " + messageRecu + "\n");
                messageSplit = messageRecu.split(":");

                User user = new User(messageSplit[0]);
                String message = messageSplit[1];
                String command = messageSplit[2];

                // Usernane : message : command
                Paquet paquet = new Paquet(user, message, command);

                switch (paquet.getCommand()) {
                case Command.CONNECT:
                    // enregistrement d'un nouvel utilisateur
                    if (onlineUsers.containsKey(paquet.getUser())) {
                        clientPrintWriter.println("SERVEUR: Le Username [" + paquet.getUser().getUsername()
                                + "] est déjà pris:" + Command.SERVER_ERROR);
                        clientPrintWriter.flush();
                        return;
                    }

                    onlineUsers.put(paquet.getUser(), clientPrintWriter);

                    // configurer le paquet avant de l'envoyer
                    paquet.setMessage(user.getUsername() + " vient de se connecter ");
                    user.setUsername("SERVEUR");
                    paquet.setUser(user);
                    paquet.setCommand(Command.CHAT);

                    // Informer les autres d'un nouvel utilisateur
                    notifyEveryClient(paquet.toString());
                    user.setUsername(paquet.getMessage().split(" ")[0]);
                    break;

                case Command.DISCONNECT:
                    // Supprimer l'utilisateur de la liste des utilisateurs connectés
                    onlineUsers.remove(user);

                    // configurer le paquet avant de l'envoyer
                    paquet.setMessage(user.getUsername() + " vient de se déconnecter ");
                    user.setUsername("SERVEUR");
                    paquet.setUser(user);
                    paquet.setCommand(Command.CHAT);

                    notifyEveryClient(paquet.toString());
                    user.setUsername(paquet.getMessage().split(" ")[0]);
                    break;

                case Command.CHAT:
                    // Vérifier si le message contient @
                    if (!message.contains("@")) {
                        // Envoyer le message à tous les utilisateurs
                        notifyEveryClient(paquet.toString());
                    } else {
                        // Faire un split du message contient @
                        String[] msgSplitWithArobase = message.split(" ");
                        HashSet<User> usersToSendMessage = new HashSet<>();

                        int i = 0;
                        while (true) {
                            if (!msgSplitWithArobase[i].contains("@")) {
                                break;
                            } else {
                                String username = msgSplitWithArobase[i].substring(1);
                                usersToSendMessage.add(new User(username));

                                int nbreCaractere = username.length() + 2;
                                message = message.substring(nbreCaractere);
                            }
                            i++;
                        }

                        // update le message dans paquet
                        paquet.setMessage(message);

                        // Envoyer le message à tous les usersToSendMessage
                        usersToSendMessage.stream().forEach(userToSend -> {
                            // vérifier s'il est dans onlineUsers, si oui envoyer le message
                            for (User userOnline : onlineUsers.keySet()) {
                                if (userOnline.equals(userToSend)) {
                                    onlineUsers.get(userOnline).println(paquet.toString());
                                    onlineUsers.get(userOnline).flush();
                                }
                            }
                        });
                    }

                    // écrire au client son message envoyé
                    clientPrintWriter.println(message);
                    clientPrintWriter.flush();
                    break;

                default:
                    System.out.println("Commande inconnue");
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Envoyer un message à tous les utilisateurs connectés
     * 
     * @param message le message à envoyer
     */
    public static void notifyEveryClient(String message) {
        try {
            for (PrintWriter writer : onlineUsers.values()) {
                writer.println(message);
                writer.flush();
            }
            taConsole.setCaretPosition(taConsole.getDocument().getLength());
        } catch (Exception e) {
            taConsole.append("Erreur lors de l'envoi de la commande à tous les utilisateurs\n");
        }
    }
}