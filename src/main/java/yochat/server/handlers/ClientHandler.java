package yochat.server.handlers;

import static yochat.server.handlers.ServerStart.onlineUsers;
import static yochat.server.ui.serverForm.taConsole;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;

import yochat.server.models.Paquet;
import yochat.server.models.User;
import yochat.server.utility.Command;

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

                // taConsole.append("Received : " + messageRecu.replace("%%", " - ") + "\n");
                messageSplit = messageRecu.split("%%");

                User user = new User(messageSplit[0]);
                String message = messageSplit[1];
                String command = messageSplit[2];

                // Usernane : message : command
                Paquet paquet = new Paquet(user, message, command);
                Paquet paquetToSendClient = new Paquet(user, message, command);

                taConsole.append("Received : " + paquet.toString().replace("%%", " | ") + "\n");

                switch (paquet.getCommand()) {
                case Command.CONNECT:
                    // enregistrement d'un nouvel utilisateur
                    // vérifier si un User avec le meme username existe déjà
                    boolean isExistUser = onlineUsers.keySet().stream()
                            .anyMatch(onlineUser -> onlineUser.getUsername().equalsIgnoreCase(user.getUsername()));

                    if (isExistUser) {
                        paquetToSendClient.setCommand(Command.SERVER_ERROR);
                        paquetToSendClient.setMessage(
                                " L'Utilisateur avec comme username [" + user.getUsername() + "] existe déjà !");
                        user.setUsername("SERVEUR");
                        paquetToSendClient.setUser(user);
                        clientPrintWriter.println(paquetToSendClient);
                        clientPrintWriter.flush();

                        // reset username
                        user.setUsername(messageSplit[0]);
                        return;
                    }

                    // ajouter l'utilisateur à la liste des utilisateurs connectés
                    onlineUsers.put(paquet.getUser(), clientPrintWriter);

                    // configurer le paquet avant de l'envoyer
                    paquet.setMessage(user.getUsername() + " vient de se connecter");
                    user.setUsername("SERVEUR");
                    paquet.setUser(user);
                    paquet.setCommand(Command.CHAT);

                    // Informer les autres d'un nouvel utilisateur
                    String usernameExcept = paquetToSendClient.getUser().getUsername();
                    notifyEveryClient(paquet.toString(), usernameExcept);

                    // faire un set du nom de l'utilisateur, récupérer le nom de l'utilisateur sur
                    // le message que le server écrit
                    user.setUsername(paquet.getMessage().split(" ")[0]);

                    // envoyer le message au client
                    paquetToSendClient.setMessage(user.getUsername() + " Connection Réussi !");
                    clientPrintWriter.println(paquetToSendClient.toString());
                    clientPrintWriter.flush();
                    break;

                case Command.DISCONNECT:
                    // Supprimer l'utilisateur de la liste des utilisateurs connectés
                    // l'utilisateur avec le nom user.getUsername()
                    onlineUsers.keySet().removeIf(user1 -> user1.getUsername().equals(user.getUsername()));

                    // configurer le paquet avant de l'envoyer
                    paquet.setMessage(user.getUsername() + " vient de se déconnecter ");
                    user.setUsername("SERVEUR");
                    paquet.setUser(user);
                    paquet.setCommand(Command.CHAT);

                    notifyEveryClient(paquet.toString(), null);

                    // faire un set du nom de l'utilisateur, récupérer le nom de l'utilisateur sur
                    // le message que le server écrit
                    user.setUsername(paquet.getMessage().split(" ")[0]);

                    // envoyer message au client
                    paquetToSendClient.setMessage("Déconnection Réussi !");
                    clientPrintWriter.println(paquetToSendClient.toString());
                    clientPrintWriter.flush();

                    // fermer la connexion avec le socket du client
                    try {
                        Thread.sleep(1000);
                        clientSocket.close();
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case Command.CHAT:
                    // Vérifier si le message contient @
                    if (!message.contains("@")) {
                        // Envoyer le message à tous les utilisateurs
                        notifyEveryClient(paquet.toString(), null);
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
                        usersToSendMessage.forEach(userToSend -> {
                            // vérifier s'il est dans onlineUsers, si oui envoyer le message
                            for (User userOnline : onlineUsers.keySet()) {
                                if (userOnline.equals(userToSend)) {
                                    onlineUsers.get(userOnline).println(paquet);
                                    onlineUsers.get(userOnline).flush();
                                }
                            }
                        });

                        paquet.setMessage(messageSplit[1]);
                        // écrire au client son message envoyé
                        clientPrintWriter.println(paquet);
                        clientPrintWriter.flush();
                    }
                    break;

                case Command.LIST:
                    // configurer le paquet avant de l'envoyer
                    user.setUsername("SERVEUR");
                    paquetToSendClient.setUser(user);
                    paquetToSendClient.setCommand(Command.LIST);

                    StringBuilder msgToSend = new StringBuilder();
                    msgToSend.append(messageSplit[0] + ", voici la liste des utilisateurs actuellement ligne : ");

                    // configuer le message à envoyer au client demandant la liste
                    onlineUsers.keySet().forEach(userOnline -> {
                        msgToSend.append(" @" + userOnline.getUsername());
                    });

                    paquetToSendClient.setMessage(msgToSend.toString());
                    clientPrintWriter.println(paquetToSendClient);
                    clientPrintWriter.flush();

                    // reset username
                    user.setUsername(messageSplit[0]);
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
     * @param message          le message à envoyer
     * @param usernameExcepted le nom de l'utilisateur à exclure, sinon null
     */
    public static void notifyEveryClient(String message, String usernameExcepted) {
        try {
            // vérifier si usernameExcepted est null
            if (usernameExcepted == null) {
                // envoyer le message à tous les utilisateurs
                onlineUsers.forEach((user, printWriter) -> {
                    printWriter.println(message);
                    printWriter.flush();
                });
            } else {
                // envoyer le message à tous les utilisateurs sauf usernameExcepted
                onlineUsers.forEach((user, printWriter) -> {
                    if (!user.getUsername().equals(usernameExcepted)) {
                        printWriter.println(message);
                        printWriter.flush();
                    }
                });
            }

            taConsole.setCaretPosition(taConsole.getDocument().getLength());
        } catch (Exception e) {
            taConsole.append("Erreur lors de l'envoi de la commande à tous les utilisateurs\n");
        }
    }
}