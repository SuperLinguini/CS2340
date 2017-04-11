package tech.milind.cleanwatercrowdsourcing;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;

import tech.milind.cleanwatercrowdsourcing.model.HistoricalReport;
import tech.milind.cleanwatercrowdsourcing.model.LatLng;
import tech.milind.cleanwatercrowdsourcing.model.WaterQualityReport;

import static org.junit.Assert.*;

/**
 * Created by kiran on 4/11/2017.
 */

public class KiranHistoricalReportTest {
    private HistoricalReport hr;

    @Before
    public void setUp() {
        hr = new HistoricalReport(new LatLng(30.2, -17.3), 2017, 50, HistoricalReport.purityType.Contaminant);
    }

    @Test
    public void testGetEmptyQualityAverages() {
        ArrayList<WaterQualityReport> reports = new ArrayList<>();
        double[] empty = new double[12];
        double[] qualityAverages = hr.getQualityAverages(reports);
        assertArrayEquals(empty, qualityAverages, 0);
    }

    @Test
    public void testGetQualityAverages(){
        ArrayList<WaterQualityReport> reports = new ArrayList<>();
        WaterQualityReport one = new WaterQualityReport("one", new LatLng(31.4, 56.3),
                WaterQualityReport.conditionOfWater.Safe ,20, 50);
        WaterQualityReport two = new WaterQualityReport("two", new LatLng(31.4, 56.3),
                WaterQualityReport.conditionOfWater.Unsafe ,100, 20);
        WaterQualityReport three = new WaterQualityReport("three", new LatLng(31.4, 56.3),
                WaterQualityReport.conditionOfWater.Treatable ,45, 65);
        WaterQualityReport four = new WaterQualityReport("four", new LatLng(31.4, 56.3),
                WaterQualityReport.conditionOfWater.Safe ,70, 58);
        WaterQualityReport five = new WaterQualityReport("five", new LatLng(31.4, 56.3),
                WaterQualityReport.conditionOfWater.Treatable ,35, 22);
        reports.add(one);
        reports.add(two);
        reports.add(three);
        reports.add(four);
        reports.add(five);
        double[] qualityAverages = hr.getQualityAverages(reports);
        assertEquals(qualityAverages[0], 0.0, 0);
        assertEquals(qualityAverages[3], 43.0, 0);

        hr = new HistoricalReport(new LatLng(30.2, -17.3), 2017, 50, HistoricalReport.purityType.Virus);
        qualityAverages = hr.getQualityAverages(reports);
        assertEquals(qualityAverages[0], 0.0, 0);
        assertEquals(qualityAverages[3], 54.0, 0);
    }
}
