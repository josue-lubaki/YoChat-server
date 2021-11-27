package server.models;

public class Paquet {
    private User user;
    private String message;
    private String command;

    public Paquet(User user, String message, String command) {
        this.user = user;
        this.message = message;
        this.command = command;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public String getCommand() {
        return command;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return user.getUsername() + "%%" + message + "%%" + command;
    }
}
