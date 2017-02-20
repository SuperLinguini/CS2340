package tech.milind.cleanwatercrowdsourcing.model;

/**
 * Created by whe1996 on 2/20/17.
 */
public class User {

    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
