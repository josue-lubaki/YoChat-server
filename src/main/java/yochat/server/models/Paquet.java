package yochat.server.models;

public class Paquet {
    private User user;
    private String message;
    private Command command;

    public Paquet(User user, String message, Command command) {
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

    public Command getCommand() {
        return command;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}


