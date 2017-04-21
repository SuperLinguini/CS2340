package tech.milind.cleanwatercrowdsourcing.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by whe1996 on 2/20/17.
 */

public class Security {

    final private HashMap<String, User> userList;

    public Security() {
        userList = new HashMap<>(50);
        for (int i = 0; i < 5; i++) {
            String username = String.format("user%2d", i).replace(" ", "0");
            User u = new User(username, "pass");
            u.setName(String.format("User %2d", i));
            u.setUserType(UserType.findUserTypebyLevel(i%3));
            userList.put(username, u);
        }
        User manager = new User("a", "a");
        manager.setName("Milind");
        manager.setUserType(UserType.ADMIN);
        userList.put("a", manager);
    }

    /**
     * Searches whether the user exists in the hashmap given the username
     * @param username username of the user that is being searched for
     * @return the User object that is found in the hashmap given the username
     * @throws NoSuchElementException if User does not exist in the hashmap given the username
     */
    public User findUser(String username) {
        if (userList.containsKey(username)) {
            return userList.get(username);
        } else {
            throw new NoSuchElementException("User Not Found.");
        }
    }

    /**
     * Creates a new User and adds user to hashmap if username is unique and valid
     * @param username username of the new User
     * @param password password of the new User
     * @return The user object that was just added to the hashmap
     * @throws NoSuchElementException if username already exists in hashmap
     * @throws IllegalArgumentException if the username does not contain at least one character
     */
    public User addUser(String username, String password) {
        if (userList.containsKey(username)) {
            throw new NoSuchElementException("User already existed");
        }
        if (username.length()==0) {
            throw new IllegalArgumentException("Username must contain at least one " +
                    "character. Please try again.");
        }
        User newUser = new User(username, password);
        userList.put(username, newUser);
        return newUser;
    }

    /**
     * Removes the user from the user list given the username of the user
     * @param username the username of the user that is to be removed
     */
    public void removeUser(String username) {
        userList.remove(username);
    }

    public List<User> getUserList() {
        List<User> list = new ArrayList<>();
        for (String userName : userList.keySet()) {
            User u = userList.get(userName);
            if (u.getUserType() != UserType.ADMIN) {
                list.add(u);
            }
        }
        Collections.sort(list);
        return list;
    }

}