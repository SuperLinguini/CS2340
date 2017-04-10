package tech.milind.cleanwatercrowdsourcing.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class Model {

    private static final Model _instance = new Model();
    public static Model getInstance() {
        return _instance;
    }

    private List<WaterSourceReport> reports;
    private List<WaterQualityReport> qualityReports;
    private HistoricalReport historicalReport;
    private Security security;
    private User currentUser;
    private DatabaseReference mDatabase;

    private Model() {
        qualityReports = new ArrayList<>();
        security = new Security();
    }

    /**
     * Adds filler test data
     */
    public void addTestData() {
        addWaterSourceReport(new WaterSourceReport("Test Report", currentUser.getName(), new LatLng(33.77,-84.39),
                WaterSourceReport.typeOfWater.Bottled, WaterSourceReport.conditionOfWater.Potable));
        addWaterSourceReport(new WaterSourceReport("A Report", currentUser.getName(), new LatLng(33.77248,-84.393003),
                WaterSourceReport.typeOfWater.Spring, WaterSourceReport.conditionOfWater.Treatable_Clear));
        addWaterSourceReport(new WaterSourceReport("My Report", currentUser.getName(), new LatLng(33.76873,-84.37565),
                WaterSourceReport.typeOfWater.Stream, WaterSourceReport.conditionOfWater.Treatable_Muddy));
        addWaterQualityReport(new WaterQualityReport("Panda Express", new LatLng(43.22, -72.21),
                WaterQualityReport.conditionOfWater.Safe, 40, 50));
        addWaterQualityReport(new WaterQualityReport("Chick-fil-A", new LatLng(50.23, -68.53),
                WaterQualityReport.conditionOfWater.Treatable, 120, 90));
        addWaterQualityReport(new WaterQualityReport("Taco Bell", new LatLng(38.7532, -79.293),
                WaterQualityReport.conditionOfWater.Unsafe, 340, 380));
    }

    public void addWaterSourceReport(WaterSourceReport wsr) {
        if (reports == null) {
            reports = new LinkedList<>();
        }
        wsr.setReportNumber(reports.size());
        reports.add(wsr);
        mDatabase.child("waterSourceReports").child(""+wsr.getReportNumber()).setValue(wsr);
    }

    public void editWaterSourceReport(WaterSourceReport wsr) {
        mDatabase.child("waterSourceReports").child(""+wsr.getReportNumber()).setValue(wsr);
    }

    public void addWaterQualityReport(WaterQualityReport wqr) {
        if (qualityReports == null) {
            qualityReports = new LinkedList<>();
        }
        wqr.setReportNumber(qualityReports.size());
        qualityReports.add(wqr);
        mDatabase.child("waterQualityReports").child(""+wqr.getReportNumber()).setValue(wqr);
    }

    public void editWaterQualityReport(WaterQualityReport wqr) {
        mDatabase.child("waterQualityReports").child(""+wqr.getReportNumber()).setValue(wqr);
    }

    public void loadWaterSourceReports() {
        //.child("date").child("time").orderByValue().limitToLast(100)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("waterSourceReports").limitToLast(100).orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (reports == null) {
                    reports = new LinkedList<>();
                }
                GenericTypeIndicator<List<WaterSourceReport>> t =
                        new GenericTypeIndicator<List<WaterSourceReport>>() {};
                if (reports.size() == 0) {
                    reports = dataSnapshot.getValue(t);
                } else {
                    List<WaterSourceReport> list = dataSnapshot.getValue(t);
                    HashSet<Integer> set = new HashSet<Integer>();
                    for (WaterSourceReport wsr: reports) {
                        set.add(wsr.getReportNumber());
                    }
                    for (WaterSourceReport wsr: list) {
                        if (!set.contains(wsr.getReportNumber())) {
                            reports.add(wsr);
                        }
                    }
                }

//                reports.removeAll(list);
//                reports.addAll(list);
//                Collections.sort(reports);
//                for(WaterSourceReport r: dataSnapshot.getValue(t)) {
//                    reports.add(r);
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDatabase.child("waterQualityReports").limitToLast(100).orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (qualityReports == null) {
                    qualityReports = new LinkedList<>();
                }
                GenericTypeIndicator<List<WaterQualityReport>> t =
                        new GenericTypeIndicator<List<WaterQualityReport>>() {};
                if (qualityReports.size() == 0) {
                    qualityReports = dataSnapshot.getValue(t);
                } else {
                    List<WaterQualityReport> list = dataSnapshot.getValue(t);
                    HashSet<Integer> set = new HashSet<Integer>();
                    for (WaterQualityReport wqr: qualityReports) {
                        set.add(wqr.getReportNumber());
                    }
                    for (WaterQualityReport wqr: list) {
                        if (!set.contains(wqr.getReportNumber())) {
                            qualityReports.add(wqr);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Gets the Firebase database reference
     * @return the Firebase database reference
     */
    public DatabaseReference getmDatabase() {
        return mDatabase;
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
    public List<WaterQualityReport> getQualityReports() { return qualityReports; }

    /** gets the HistoricalReport used in this application
     * @return the Historical Report
     */
    public HistoricalReport getHistoricalReport() {
        return historicalReport;
    }

    /**
     * Sets the historicalReport to the report passed in
     * @param historicalReport the historical report to be set
     */
    public void setHistoricalReport(HistoricalReport historicalReport) {
        this.historicalReport = historicalReport;
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

    /**
     * Gets the Security object used in the application
     * @return the Security object
     */
    public Security getSecurity() {
        return security;
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
    public void addPurityReport(WaterQualityReport report) {
        qualityReports.add(report);}
}
