package tech.milind.cleanwatercrowdsourcing.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Model {

    private static final Model _instance = new Model();
    public static Model getInstance() {
        return _instance;
    }

    private List<WaterSourceReport> reports;
    private List<WaterQualityReport> purityReports;
    private Security security;
    private User currentUser;

    private Model() {
        reports = new ArrayList<>();
        purityReports = new ArrayList<>();
        security = new Security();
    }

    /**
     * Adds filler test data until we implement persistence
     */
    public void addTestData() {
        reports.add(new WaterSourceReport("Test Report", currentUser.getUsername(), new LatLng(33.77,-84.39),
                WaterSourceReport.typeOfWater.Bottled, WaterSourceReport.conditionOfWater.Potable));
        reports.add(new WaterSourceReport("A Report", currentUser.getUsername(), new LatLng(33.77248,-84.393003),
                WaterSourceReport.typeOfWater.Spring, WaterSourceReport.conditionOfWater.Treatable_Clear));
        reports.add(new WaterSourceReport("My Report", currentUser.getUsername(), new LatLng(33.76873,-84.37565),
                WaterSourceReport.typeOfWater.Stream, WaterSourceReport.conditionOfWater.Treatable_Muddy));
        purityReports.add(new WaterQualityReport("Panda Express", new LatLng(43.22, -72.21),
                WaterQualityReport.conditionOfWater.Safe, 40, 50));
        purityReports.add(new WaterQualityReport("Chick-fil-A", new LatLng(50.23, -68.53),
                WaterQualityReport.conditionOfWater.Treatable, 120, 90));
        purityReports.add(new WaterQualityReport("Taco Bell", new LatLng(38.7532, -79.293),
                WaterQualityReport.conditionOfWater.Unsafe, 340, 380));
    }

    /**
     * Gets the current user object that is using the application
     * @return the current user logged into the application
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current user object that is using the application
     * @param user the new user to be set to for the application
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Gets the WaterSourceReport list used in the application
     * @return the WaterSourceReport list
     */
    public List<WaterSourceReport> getReports() {
        return reports;
    }

    /**
     * Gets the WaterQualityReport list used in the application
     * @return the WaterQualityReport list
     */
    public List<WaterQualityReport> getPurityReports() { return purityReports; }

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

    /**
     * Add a report to the WaterSourceReport list
     * @param report WaterSourceReport to add
     */
    public void addReport(WaterSourceReport report) {
        reports.add(report);
    }

    /**
     * Add a report to the WaterQualityReport list
     * @param report WaterQualityReport to add
     */
    public void addPurityReport(WaterQualityReport report) {purityReports.add(report);}
}
