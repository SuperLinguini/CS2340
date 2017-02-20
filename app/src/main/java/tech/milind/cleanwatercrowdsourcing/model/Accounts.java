package tech.milind.cleanwatercrowdsourcing.model;

import java.util.HashMap;

/**
 * Created by whe1996 on 2/19/17.
 */
public class Accounts {

    private static final Accounts _instance = new Accounts();
    public static Accounts getInstance() { return _instance; }

    private HashMap<String, String> accounts;


    public Accounts() {
        accounts = new HashMap<>(20);
        accounts.put("user", "pass");
    }

    public boolean hasUser(String username, String password) {
        if (accounts.containsKey(username) && accounts.get(username).equals(password)) {
            return true;
        }
        return false;
    }

    public boolean addAccount(String username, String password) {
        if (accounts.containsKey(username)) {
            return false;
        }
        accounts.put(username, password);
        return true;
    }
}
