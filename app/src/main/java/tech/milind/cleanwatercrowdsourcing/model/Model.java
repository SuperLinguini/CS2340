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

    public void deleteUser(String username) {
        security.removeUser(username);
    }

    public void addReport(WaterSourceReport report) {
        reports.add(report);
    }
}
