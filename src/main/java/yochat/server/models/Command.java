package yochat.server.models;

public enum Command {
    LIST("list"),
    CHAT("chat"),
    CONNECT("connect"),
    DISCONNECT("disconnect"),
    SERVER_ERROR("server_error");

    private String command;

    Command(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
