package tech.milind.cleanwatercrowdsourcing.model;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;


/**
 * Created by gunoupark on 12/03/2017.
 */

public class WaterPurityReport implements Comparable<WaterPurityReport> {
    private static int currentID = 1;
    public enum conditionOfWater {
        Safe, Treatable, Unsafe;
    }

    private Date date;
    private int reportNumber;
    private String name;
    private LatLng location;
    private conditionOfWater condition;
    private int virusPPM;
    private int contaminantPPM;

    /**
     * Default constructor for the WaterSourceReport
     */
    public WaterPurityReport() {
        date = new Date();
        reportNumber = currentID++;
    }

    /**
     * Constructor for the WaterSourceReport with all inputs
     * @param name the name to be set to the WaterSourceReport
     * @param location the new LatLng location to be set to the WaterSourceReport
     * @param condition the water condition to be set to the WaterSourceReport
     */
    public WaterPurityReport(String name, LatLng location,
                             conditionOfWater condition, int virus, int contam) {
        date = new Date();
        reportNumber = currentID++;
        this.name = name;
        this.location = location;
        this.condition = condition;
        this.virusPPM = virus;
        this.contaminantPPM = contam;
    }

    /**
     * Gets the date of the WaterSourceReport
     * @return the date of the WaterSourceReport
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the report number of the WaterSourceReport
     * @return the report number of the WaterSourceReport
     */
    public int getReportNumber() {
        return reportNumber;
    }

    /**
     * Gets the name of the WaterSourceReport
     * @return the name of the WaterSourceReport
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the location (in LatLng) of the WaterSourceReport
     * @return the location of the WaterSourceReport
     */
    public LatLng getLocation() {
        return location;
    }

    /**
     * Gets the condition of the WaterSourceReport
     * @return the condition of the WaterSourceReport
     */
    public WaterPurityReport.conditionOfWater getCondition() {
        return condition;
    }

    public int getVirusPPM() {
        return virusPPM;
    }

    public int getContaminantPPM() {
        return contaminantPPM;
    }

    /**
     * Sets the name of the WaterSourceReport
     * @param name the new name to be set to the WaterSourceReport
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the location of the WaterSourceReport
     * @param location the new LatLng location to be set to the WaterSourceReport
     */
    public void setLocation(LatLng location) {
        this.location = location;
    }

    /**
     * Sets the condition of the WaterSourceReport
     * @param condition the new condition to be set to the WaterSourceReport
     */
    public void setCondition(WaterPurityReport.conditionOfWater condition) {
        this.condition = condition;
    }

    public void setVirusPPM(int virusPPM) {
        this.virusPPM = virusPPM;
    }

    public void setContaminantPPM(int contaminantPPM) {
        this.contaminantPPM = contaminantPPM;
    }

    @Override
    public int compareTo(@NonNull WaterPurityReport o) {
        return this.getReportNumber() > o.getReportNumber() ? 1 : -1;
    }
}
