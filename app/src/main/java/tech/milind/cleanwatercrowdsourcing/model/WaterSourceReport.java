package tech.milind.cleanwatercrowdsourcing.model;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by Milind Lingineni on 3/2/2017.
 */

public class WaterSourceReport implements Comparable<WaterSourceReport> {
    private static int currentID = 1;
    public enum typeOfWater {
        Bottled, Well, Stream, Lake, Spring, Other
    }
    public enum conditionOfWater {
        Potable, Treatable_Clear, Treatable_Muddy, Waste
    }

    private Date date;
    private int reportNumber;
    private String name;
    private LatLng location;
    private typeOfWater type;
    private conditionOfWater condition;

    /**
     * Default constructor for the WaterSourceReport
     */
    public WaterSourceReport() {
        date = new Date();
        reportNumber = currentID++;
    }

    /**
     * Constructor for the WaterSourceReport with all inputs
     * @param name the name to be set to the WaterSourceReport
     * @param location the new LatLng location to be set to the WaterSourceReport
     * @param type the water type to be set to the WaterSourceReport
     * @param condition the water condition to be set to the WaterSourceReport
     */
    public WaterSourceReport(String name, LatLng location,
                             typeOfWater type, conditionOfWater condition) {
        date = new Date();
        reportNumber = currentID++;
        this.name = name;
        this.location = location;
        this.type = type;
        this.condition = condition;
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
     * Gets the type of the WaterSourceReport
     * @return the type of the WaterSourceReport
     */
    public typeOfWater getType() {
        return type;
    }

    /**
     * Gets the condition of the WaterSourceReport
     * @return the condition of the WaterSourceReport
     */
    public conditionOfWater getCondition() {
        return condition;
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
     * Sets the type of the WaterSourceReport
     * @param type the new type to be set to the WaterSourceReport
     */
    public void setType(typeOfWater type) {
        this.type = type;
    }

    /**
     * Sets the condition of the WaterSourceReport
     * @param condition the new condition to be set to the WaterSourceReport
     */
    public void setCondition(conditionOfWater condition) {
        this.condition = condition;
    }

    @Override
    public int compareTo(@NonNull WaterSourceReport o) {
        return this.getReportNumber() > o.getReportNumber() ? 1 : -1;
    }

    /**
     * Gets the snippet for the map pin for each WaterSourceReport
     * @return the String details for the map pin snippet
     */
    public String getSnippet() {
        return String.format("#%d %tD %<tR %s/%s", reportNumber, date,
                type.toString(), condition.toString());
    }
}
