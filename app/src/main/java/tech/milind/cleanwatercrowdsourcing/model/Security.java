package tech.milind.cleanwatercrowdsourcing.model;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Created by whe1996 on 2/20/17.
 */

public class Security {

    private HashMap<String, User> userList;

    public Security() {
        userList = new HashMap<>(50);
        userList.put("user", new User("user", "pass"));
    }

    public User findUser(String username) {
        if (userList.containsKey(username)) {
            return userList.get(username);
        } else {
            throw new NoSuchElementException("User Not Found.");
        }
    }

    public User addUser(String username, String password) {
        if (userList.containsKey(username)) {
            throw new NoSuchElementException("User already existed");
        }
        User newUser = new User(username, password);
        userList.put(username, newUser);
        return newUser;
    }


}
