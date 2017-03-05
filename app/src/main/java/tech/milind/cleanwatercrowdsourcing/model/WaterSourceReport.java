package tech.milind.cleanwatercrowdsourcing.model;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by SuperLinguini on 3/2/2017.
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

    public WaterSourceReport() {
        date = new Date();
        reportNumber = currentID++;
    }

    public WaterSourceReport(String name, LatLng location,
                             typeOfWater type, conditionOfWater condition) {
        date = new Date();
        reportNumber = currentID++;
        this.name = name;
        this.location = location;
        this.type = type;
        this.condition = condition;
    }

    public Date getDate() {
        return date;
    }
    public int getReportNumber() {
        return reportNumber;
    }
    public String getName() {
        return name;
    }
    public LatLng getLocation() {
        return location;
    }
    public typeOfWater getType() {
        return type;
    }
    public conditionOfWater getCondition() {
        return condition;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setType(typeOfWater type) {
        this.type = type;
    }

    public void setCondition(conditionOfWater condition) {
        this.condition = condition;
    }

    @Override
    public int compareTo(@NonNull WaterSourceReport o) {
        return this.getReportNumber() > o.getReportNumber() ? 1 : -1;
    }

    public String getSnippet() {
        return String.format("#%d %tD %<tR %s/%s", reportNumber, date,
                type.toString(), condition.toString());
    }
}
