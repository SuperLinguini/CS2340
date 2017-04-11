package tech.milind.cleanwatercrowdsourcing.model;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiran on 4/4/2017.
 */

public class HistoricalReport {
    public enum purityType {
        Contaminant, Virus
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

    /**
     * Finds the reports that are within the specified radius
     * @param reports the list of all water quality reports
     * @return the list of all the reports within the specified radius
     */
    public List<WaterQualityReport> getPurityReportsInRadius(List<WaterQualityReport> reports) {
        List<WaterQualityReport> reportsInRadius = new ArrayList<>();
        Location center = new Location("center");
        center.setLatitude(this.getLocation().getLatitude());
        center.setLongitude(this.getLocation().getLongitude());
        for(WaterQualityReport report: reports) {
            Location loc = new Location("Report Location");
            loc.setLatitude(report.getLocation().getLatitude());
            loc.setLongitude(report.getLocation().getLongitude());
            double rad = this.getRadius();
            double distanceInMiles = center.distanceTo(loc) / 1609.34;
            int yr = this.getYear();
            int reportYear = report.getDate().getYear() + 1900;
            if (distanceInMiles <= rad && reportYear == yr) {
                reportsInRadius.add(report);

            }
        }
        return reportsInRadius;
    }

    /**
     * Gets the average quality per month
     * @param reports the reports that are within the specified radius
     * @return an array of size 12 with each element being the average quality for that month
     */
    public double[] getQualityAverages(List<WaterQualityReport> reports) {
        int[] numPerMonth = new int[12];
        int[] qualitySum = new int[12];
        double[] qualityAverages = new double[12];
        for(WaterQualityReport report: reports) {
            int month = report.getDate().getMonth();
            int ppm = (this.getType() == HistoricalReport.purityType.Virus )?
                    report.getVirusPPM() : report.getContaminantPPM();
            numPerMonth[month]++;
            qualitySum[month] += ppm;
        }
        for(int i = 0; i < qualityAverages.length; i++) {
            if (qualitySum[i] == 0 && numPerMonth[i] == 0) {
                qualityAverages[i] = 0;
            } else {
                qualityAverages[i] = qualitySum[i] / ((double) numPerMonth[i]);
            }
        }
        return qualityAverages;
    }

}
