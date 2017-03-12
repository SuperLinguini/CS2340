package tech.milind.cleanwatercrowdsourcing.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by SuperLinguini on 3/12/2017.
 */

public class WaterQualityReport {
    private static int currentID = 1;

    public enum overallCondition {
        Safe, Treatable, Unsafe
    }

    private int reportNumber;
    private String reportName;
    private String workerName;
    private overallCondition condition;
    private Date date;
    private LatLng location;
    private double virusPPM;
    private double contaminantPPM;

    /**
     * Default constructor for the WaterSourceReport
     */
    public WaterQualityReport() {
        date = new Date();
        reportNumber = currentID++;
    }

    public WaterQualityReport(String reportName, String workerName, LatLng location,
                              overallCondition condition, double virusPPM, double contaminantPPM) {
        date = new Date();
        reportNumber = currentID++;
        this.reportName = reportName;
        this.workerName = workerName;
        this.location = location;
        this.condition = condition;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
    }

    public int getReportNumber() {
        return reportNumber;
    }

    public String getWorkerName() {
        return workerName;
    }

    public Date getDate() {
        return date;
    }

    public LatLng getLocation() {
        return location;
    }

    public double getVirusPPM() {
        return virusPPM;
    }

    public double getContaminantPPM() {
        return contaminantPPM;
    }

    public overallCondition getCondition() {
        return condition;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportNumber(int reportNumber) {
        this.reportNumber = reportNumber;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setVirusPPM(double virusPPM) {
        this.virusPPM = virusPPM;
    }

    public void setContaminantPPM(double contaminantPPM) {
        this.contaminantPPM = contaminantPPM;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public void setCondition(overallCondition condition) {
        this.condition = condition;
    }
}
