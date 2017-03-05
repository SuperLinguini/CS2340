package tech.milind.cleanwatercrowdsourcing.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Model {

    private static final Model _instance = new Model();
    public static Model getInstance() {
        return _instance;
    }

    private List<WaterSourceReport> reports;
    private Security security;
    private User currentUser;

    private Model() {
        reports = new ArrayList<>();
        security = new Security();
    }

    /**
     * Gets the current user object that is using the application
     * @return the current user logged into the application
     */
    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Checks if the username and password entered is a valid user
     * @param username username entered by the user to login
     * @param password password entered by the user to login
     * @throws NoSuchElementException when the username or password entered is not a valid user
     */
    public void login(String username, String password) {
        User user = security.findUser(username);
        if (user.checkPassword(password)) {
            currentUser = user;
        } else {
            throw new NoSuchElementException("Incorrect password");
        }
    }

    /**
     * Creates a new user with the entered username and password
     * @param username the username of the new user
     * @param password the password of the new user
     */
    public void register(String username, String password) {
        currentUser = security.addUser(username, password);
    }

    /**
     * Deletes a current user given their username
     * @param username username of the user that needs to be deleted
     */
    public void deleteUser(String username) {
        security.removeUser(username);
    }

    public void addReport(WaterSourceReport report) {
        reports.add(report);
    }
}
