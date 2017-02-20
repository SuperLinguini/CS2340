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

    public Model() {
        security = new Security();
    }


    public boolean login(String username, String password) {
        try {
            return security.findUser(username).checkPassword(password);
        } catch(NoSuchElementException e) {
            Log.i("loginError", e.getMessage());
            return false;
        }
    }

    public boolean register(String username, String password) {
        return security.addUser(username, password);
    }
}
