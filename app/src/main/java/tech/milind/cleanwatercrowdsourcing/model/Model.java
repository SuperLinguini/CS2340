package tech.milind.cleanwatercrowdsourcing.model;

import java.util.NoSuchElementException;
import android.util.Log;

/**
 * Created by whe1996 on 2/19/17.
 */
public class Model {

    private static final Model _instance = new Model();
    public static Model getInstance() { return _instance; }

    private Security security;
    private User currentUser;

    public Model() {
        security = new Security();
    }

    public User getUser(String username) {
        return security.findUser(username);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void login(String username, String password) {
        User user = security.findUser(username);
        if (user.checkPassword(password)) {
            currentUser = user;
        } else {
            throw new NoSuchElementException("Incorrect password");
        }
    }

    public void register(String username, String password) {
        currentUser = security.addUser(username, password);
    }
}
