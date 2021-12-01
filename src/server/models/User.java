package server.models;

/**
 * @author Josue Lubaki & Ismael Coulibaly
 * @version 1.0
 */
public class User {

    private String username;

    public User(String username) {
        this.username = username;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return this.username.equals(other.username);
    }

}
