package tech.milind.cleanwatercrowdsourcing.model;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;


/**
 * Created by gunoupark on 12/03/2017.
 */

public class WaterQualityReport implements Comparable<WaterQualityReport> {
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
     * Default constructor for the WaterQualityReport
     */
    public WaterQualityReport() {
        date = new Date();
        reportNumber = currentID++;
    }

    /**
     * Constructor for the WaterQualityReport with all inputs
     * @param name the name to be set to the WaterQualityReport
     * @param location the new LatLng location to be set to the WaterQualityReport
     * @param condition the water condition to be set to the WaterQualityReport
     */
    public WaterQualityReport(String name, LatLng location,
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
     * Gets the date of the WaterQualityReport
     * @return the date of the WaterQualityReport
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the report number of the WaterQualityReport
     * @return the report number of the WaterQualityReport
     */
    public int getReportNumber() {
        return reportNumber;
    }

    /**
     * Gets the name of the WaterQualityReport
     * @return the name of the WaterQualityReport
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the location (in LatLng) of the WaterQualityReport
     * @return the location of the WaterQualityReport
     */
    public LatLng getLocation() {
        return location;
    }

    /**
     * Gets the condition of the WaterQualityReport
     * @return the condition of the WaterQualityReport
     */
    public WaterQualityReport.conditionOfWater getCondition() {
        return condition;
    }

    /**
     * Gets the virus PPM of the WaterQualityReport
     * @return the virus PPM of the WaterQualityReport
     */
    public int getVirusPPM() {
        return virusPPM;
    }

    /**
     * Gets the contaminantPPM of the WaterQualityReport
     * @return the contaminantPPM of the WaterQualityReport
     */
    public int getContaminantPPM() {
        return contaminantPPM;
    }

    /**
     * Sets the name of the WaterQualityReport
     * @param name the new name to be set to the WaterQualityReport
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the location of the WaterQualityReport
     * @param location the new LatLng location to be set to the WaterQualityReport
     */
    public void setLocation(LatLng location) {
        this.location = location;
    }

    /**
     * Sets the condition of the WaterQualityReport
     * @param condition the new condition to be set to the WaterQualityReport
     */
    public void setCondition(WaterQualityReport.conditionOfWater condition) {
        this.condition = condition;
    }

    /**
     * Sets the virus PPM of the WaterQualityReport
     * @param virusPPM the new virus PPM to be set to the WaterQualityReport
     */
    public void setVirusPPM(int virusPPM) {
        this.virusPPM = virusPPM;
    }

    /**
     * Sets the contaminant PPM of the WaterQualityReport
     * @param contaminantPPM the new contaminant PPM to be set to the WaterQualityReport
     */
    public void setContaminantPPM(int contaminantPPM) {
        this.contaminantPPM = contaminantPPM;
    }

    @Override
    public int compareTo(@NonNull WaterQualityReport o) {
        return this.getReportNumber() > o.getReportNumber() ? 1 : -1;
    }
}
