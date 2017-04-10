package tech.milind.cleanwatercrowdsourcing.model;

/**
 * Created by kiran on 4/4/2017.
 */

public class HistoricalReport {
    public enum purityType {
        Contaminant, Virus;
    }

    private LatLng location;
    final private int year;
    final private double radius;
    final private purityType type;

    /**
     * Constructor for the Historical Report with all inputs
     * @param location the location of the report
     * @param year the year for the report
     * @param radius the radius for the report
     * @param type the purity type for the report
     */
    public HistoricalReport(LatLng location, int year, double radius, purityType type) {
        this.location = location;
        this.year = year;
        this.radius = radius;
        this.type = type;
    }

    /**
     * Gets the location of the report
     * @return the location in LatLng
     */
    public LatLng getLocation() {
        return location;
    }

    /**
     * Sets the location for the Historical report
     * @param location the location to be set
     */
    @SuppressWarnings("unused")
    public void setLocation(LatLng location) {
        this.location = location;
    }

    /**
     * Gets the year for the report
     * @return the year for the report
     */
    public int getYear() {
        return year;
    }

    /**
     * Gets the radius for the report
     * @return the radius for the report
     */
    public double getRadius() {
        return radius;
    }

    /**
     * gets the purity type for the report
     * @return the purity type for the report
     */
    public purityType getType() {
        return type;
    }

}
