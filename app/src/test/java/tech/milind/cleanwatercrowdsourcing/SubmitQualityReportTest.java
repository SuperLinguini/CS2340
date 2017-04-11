package tech.milind.cleanwatercrowdsourcing;

import static org.junit.Assert.*;

import org.junit.Test;

import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import tech.milind.cleanwatercrowdsourcing.controllers.SubmitQualityReportActivity;
import tech.milind.cleanwatercrowdsourcing.model.*;
/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SubmitQualityReportTest {

    private SubmitQualityReportActivity activity = new SubmitQualityReportActivity();

    Model model;

    List<WaterQualityReport> wqrList;
    WaterQualityReport report;

    @Before
    public void setUp() {
        model = Model.getInstance();

        wqrList = new ArrayList<WaterQualityReport>();


    }
    public void fillup(){
        model.addPurityReport(new WaterQualityReport("Panda Express", new tech.milind.cleanwatercrowdsourcing.model.LatLng(43.22, -72.21),
                WaterQualityReport.conditionOfWater.Safe, 40, 50));
        model.addPurityReport(new WaterQualityReport("Chick-fil-A", new tech.milind.cleanwatercrowdsourcing.model.LatLng(50.23, -68.53),
                WaterQualityReport.conditionOfWater.Treatable, 120, 90));
        model.addPurityReport(new WaterQualityReport("Taco Bell", new tech.milind.cleanwatercrowdsourcing.model.LatLng(38.7532, -79.293),
                WaterQualityReport.conditionOfWater.Unsafe, 340, 380));

        wqrList.add(new WaterQualityReport("Panda Express", new tech.milind.cleanwatercrowdsourcing.model.LatLng(43.22, -72.21),
                WaterQualityReport.conditionOfWater.Safe, 40, 50));
        wqrList.add(new WaterQualityReport("Chick-fil-A", new tech.milind.cleanwatercrowdsourcing.model.LatLng(50.23, -68.53),
                WaterQualityReport.conditionOfWater.Treatable, 120, 90));
        wqrList.add(new WaterQualityReport("Taco Bell", new tech.milind.cleanwatercrowdsourcing.model.LatLng(38.7532, -79.293),
                WaterQualityReport.conditionOfWater.Unsafe, 340, 380));
    }

    @Test
    public void testSameName() {

        setUp();
        fillup();
        for (int i = 0; i < wqrList.size(); i++) {
            assertEquals(wqrList.get(i).getReportName(), model.getQualityReports().get(i).getReportName());

        }
    }
    @Test
    public void testSameCondition() {

        setUp();
        fillup();
        for (int i = 0; i < wqrList.size(); i++) {
            assertEquals(wqrList.get(i).getCondition(), model.getQualityReports().get(i).getCondition());
        }
    }
    @Test
    public void testSameContainmentPPM() {

        setUp();

        fillup();
        for (int i = 0; i < wqrList.size(); i++) {
            assertEquals(wqrList.get(i).getContaminantPPM(), model.getQualityReports().get(i).getContaminantPPM());

        }
    }

    @Test
    public void testSameVirus() {
        setUp();
        fillup();
        for (int i = 0; i < wqrList.size(); i++) {
            assertEquals(wqrList.get(i).getVirusPPM(), model.getQualityReports().get(i).getVirusPPM());

        }
    }

    @Test(expected = NullPointerException.class)

    public void testNullException(){
        setUp();
        model.addPurityReport(null);
    }

}