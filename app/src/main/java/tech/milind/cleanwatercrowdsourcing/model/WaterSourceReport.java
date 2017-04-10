package tech.milind.cleanwatercrowdsourcing.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Milind Lingineni on 3/2/2017.
 */

public class WaterSourceReport implements Comparable<WaterSourceReport>, Parcelable {
    public enum typeOfWater {
        Bottled, Well, Stream, Lake, Spring, Other
    }
    public enum conditionOfWater {
        Potable, Treatable_Clear, Treatable_Muddy, Waste
    }

    private Date date;
    private int reportNumber;
    private String reportName;
    private String reporter;
    private LatLng location;
    private typeOfWater type;
    private conditionOfWater condition;

    /**
     * Default constructor for the WaterSourceReport
     */
    public WaterSourceReport() {
        date = new Date();
    }

    /**
     * Constructor for the WaterSourceReport with all inputs
     * @param reportName the report name to be set to the WaterSourceReport
     * @param reporter the reporter to be set to the WaterSourceReport
     * @param location the new LatLng location to be set to the WaterSourceReport
     * @param type the water type to be set to the WaterSourceReport
     * @param condition the water condition to be set to the WaterSourceReport
     */
    public WaterSourceReport(String reportName, String reporter, LatLng location,
                             typeOfWater type, conditionOfWater condition) {
        date = new Date();
        this.reportName = reportName;
        this.reporter = reporter;
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
    public String getReportName() {
        return reportName;
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
     * Gets the reporter name of the WaterSourceReport
     * @return the reporter of the WaterSourceReport
     */
    public String getReporter() {
        return reporter;
    }

    /**
     * Sets the name of the WaterSourceReport
     * @param reportName the new name to be set to the WaterSourceReport
     */
    public void setReportName(String reportName) {
        this.reportName = reportName;
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

    /**
     * Sets the date of the WaterSourceReport
     * @param date the new date to be set to the WaterSourceReport
     */
    @SuppressWarnings("unused")
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Sets the report number of the WaterSourceReport
     * @param reportNumber the new report number to be set to the WaterSourceReport
     */
    public void setReportNumber(int reportNumber) {
        this.reportNumber = reportNumber;
    }

    /**
     * Sets the reporter of the WaterSourceReport
     * @param reporter the new reporter to be set to the WaterSourceReport
     */
    public void setReporter(String reporter) {
        this.reporter = reporter;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(reportName);
        dest.writeString(reporter);
        dest.writeInt(reportNumber);
        dest.writeString(type.name());
        dest.writeString(condition.name());
        dest.writeParcelable(location, flags);
        dest.writeLong(date.getTime());
    }

    public static final Parcelable.Creator<WaterSourceReport> CREATOR =
            new Parcelable.Creator<WaterSourceReport>() {
                @Override
                public WaterSourceReport createFromParcel(Parcel source) {
                    return new WaterSourceReport(source);
                }

                @Override
                public WaterSourceReport[] newArray(int size) {
                    return new WaterSourceReport[size];
                }
            };

    private WaterSourceReport(Parcel in) {
        reportName = in.readString();
        reporter = in.readString();
        reportNumber = in.readInt();
        type = typeOfWater.valueOf(in.readString());
        condition = conditionOfWater.valueOf(in.readString());
        location = in.readParcelable(LatLng.class.getClassLoader());
        date = new Date(in.readLong());
    }
}
