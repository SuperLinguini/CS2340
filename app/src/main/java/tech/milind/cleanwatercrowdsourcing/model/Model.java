package tech.milind.cleanwatercrowdsourcing.model;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeSet;

import tech.milind.cleanwatercrowdsourcing.controllers.ListSourceFragment;

public class Model {

    private static final Model _instance = new Model();
    public static Model getInstance() {
        return _instance;
    }

    private List<WaterSourceReport> reports;
    private List<WaterQualityReport> purityReports;
    private Security security;
    private User currentUser;
    private DatabaseReference mDatabase;

    private Model() {
        purityReports = new ArrayList<>();
        security = new Security();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Adds filler test data until we implement persistence
     */
    public void addTestData() {
//        reports.add(new WaterSourceReport("Test Report", currentUser.getUsername(), new LatLng(33.77,-84.39),
//                WaterSourceReport.typeOfWater.Bottled, WaterSourceReport.conditionOfWater.Potable));
//        reports.add(new WaterSourceReport("A Report", currentUser.getUsername(), new LatLng(33.77248,-84.393003),
//                WaterSourceReport.typeOfWater.Spring, WaterSourceReport.conditionOfWater.Treatable_Clear));
//        reports.add(new WaterSourceReport("My Report", currentUser.getUsername(), new LatLng(33.76873,-84.37565),
//                WaterSourceReport.typeOfWater.Stream, WaterSourceReport.conditionOfWater.Treatable_Muddy));
//        purityReports.add(new WaterQualityReport("Panda Express", new com.google.android.gms.maps.model.LatLng(43.22, -72.21),
//                WaterQualityReport.conditionOfWater.Safe, 40, 50));
//        purityReports.add(new WaterQualityReport("Chick-fil-A", new com.google.android.gms.maps.model.LatLng(50.23, -68.53),
//                WaterQualityReport.conditionOfWater.Treatable, 120, 90));
//        purityReports.add(new WaterQualityReport("Taco Bell", new com.google.android.gms.maps.model.LatLng(38.7532, -79.293),
//                WaterQualityReport.conditionOfWater.Unsafe, 340, 380));
//        mDatabase.child("waterSourceReportLinked").setValue(reports);
        addWaterSourceReport(new WaterSourceReport("Test Report", currentUser.getUsername(), new LatLng(33.77,-84.39),
                WaterSourceReport.typeOfWater.Bottled, WaterSourceReport.conditionOfWater.Potable));
        addWaterSourceReport(new WaterSourceReport("A Report", currentUser.getUsername(), new LatLng(33.77248,-84.393003),
                WaterSourceReport.typeOfWater.Spring, WaterSourceReport.conditionOfWater.Treatable_Clear));
        addWaterSourceReport(new WaterSourceReport("My Report", currentUser.getUsername(), new LatLng(33.76873,-84.37565),
                WaterSourceReport.typeOfWater.Stream, WaterSourceReport.conditionOfWater.Treatable_Muddy));
    }

    public void addWaterSourceReport(WaterSourceReport wsr) {
        if (reports == null) {
            reports = new LinkedList<>();
        }
        wsr.setReportNumber(reports.size());
        mDatabase.child("waterSourceReports").child(""+wsr.getReportNumber()).setValue(wsr);
    }

    public void editWaterSourceReport(WaterSourceReport wsr) {
        mDatabase.child("waterSourceReports").child(""+wsr.getReportNumber()).setValue(wsr);
    }


    public void loadWaterSourceReports() {
        purityReports.add(new WaterQualityReport("Panda Express", new com.google.android.gms.maps.model.LatLng(43.22, -72.21),
                WaterQualityReport.conditionOfWater.Safe, 40, 50));
        purityReports.add(new WaterQualityReport("Chick-fil-A", new com.google.android.gms.maps.model.LatLng(50.23, -68.53),
                WaterQualityReport.conditionOfWater.Treatable, 120, 90));
        purityReports.add(new WaterQualityReport("Taco Bell", new com.google.android.gms.maps.model.LatLng(38.7532, -79.293),
                WaterQualityReport.conditionOfWater.Unsafe, 340, 380));
        //.child("date").child("time").orderByValue().limitToLast(100)

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
//        mDatabase.child("waterSourceReport").limitToLast(100).orderByChild("date").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                GenericTypeIndicator<Map<String, WaterSourceReport>> t =
//                        new GenericTypeIndicator<Map<String, WaterSourceReport>>() {};
//                reportMap = dataSnapshot.getValue(t);
//                if (reports != null) {
//                    reports.clear();
//                } else {
//                    reports = new LinkedList<WaterSourceReport>();
//                }
//                for (WaterSourceReport wsr: new LinkedList<WaterSourceReport>(reportMap.values())) {
//                    reports.add(wsr);
//                }
//                Collections.sort(reports);
////                reports.clear();
////                for (DataSnapshot wsr: dataSnapshot.getChildren()) {
////                    reports.add(wsr.getValue(WaterSourceReport.class));
////                }
////                GenericTypeIndicator<List<WaterSourceReport>> t =
////                        new GenericTypeIndicator<List<WaterSourceReport>>() {};
////                reports = dataSnapshot.getValue(t);
////                reports.removeAll(Collections.singleton(null));
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    public void getDataBlocking() {
//        Query query = mDatabase.child("waterSourceReport").limitToLast(100).orderByChild("date");
//        Tasks.await(query.start)
//        mDatabase.child("waterSourceReport").limitToLast(100).orderByChild("date")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                GenericTypeIndicator<Map<String, WaterSourceReport>> t =
//                        new GenericTypeIndicator<Map<String, WaterSourceReport>>() {
//                        };
//                reportMap = dataSnapshot.getValue(t);
//                reports = new LinkedList<WaterSourceReport>(reportMap.values());
//                Collections.sort(reports);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
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
