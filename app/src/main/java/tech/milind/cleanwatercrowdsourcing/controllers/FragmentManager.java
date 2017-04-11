package tech.milind.cleanwatercrowdsourcing.controllers;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import tech.milind.cleanwatercrowdsourcing.R;
import tech.milind.cleanwatercrowdsourcing.model.HistoricalReport;
import tech.milind.cleanwatercrowdsourcing.model.Model;
import tech.milind.cleanwatercrowdsourcing.model.WaterQualityReport;


/**
 * Created by kiran on 3/12/2017.
 */

public class FragmentManager extends Fragment {
    final int REQUEST = 1;
    final String[] xAxisLabels= {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private LineChart chart;
    private HistoricalReport hr;
    Model model = Model.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager,container, false);
        hr = model.getHistoricalReport();
        FloatingActionButton edit = (FloatingActionButton) view.findViewById(R.id.editHistoricalFAB);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), EditHistoricalReportActivity.class);
                startActivityForResult(i, REQUEST);
            }
        });
        chart = (LineChart) view.findViewById(R.id.chart);
        if (hr == null) {
            Intent i = new Intent(getActivity(), EditHistoricalReportActivity.class);
            startActivityForResult(i, REQUEST);
        }
        else {
            displayChart();
        }

        return view;

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                displayChart();
            }
        }
    }

    /**
     * Finds the reports that are within the specified radius
     * @param reports the list of all water quality reports
     * @return the list of all the reports within the specified radius
     */
    private List<WaterQualityReport> getPurityReportsInRadius(List<WaterQualityReport> reports) {
        List<WaterQualityReport> reportsInRadius = new ArrayList<WaterQualityReport>();
        Location center = new Location("center");
        center.setLatitude(hr.getLocation().getLatitude());
        center.setLongitude(hr.getLocation().getLongitude());
        for(WaterQualityReport report: reports) {
            Location loc = new Location("Report Location");
            loc.setLatitude(report.getLocation().getLatitude());
            loc.setLongitude(report.getLocation().getLongitude());
            double rad = hr.getRadius();
            double distanceInMiles = center.distanceTo(loc) / 1609.34;
            int yr = hr.getYear();
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
    private double[] getQualityAverages(List<WaterQualityReport> reports) {
        int[] numPerMonth = new int[12];
        int[] qualitySum = new int[12];
        double[] qualityAverages = new double[12];
        for(WaterQualityReport report: reports) {
            int month = report.getDate().getMonth();
            int ppm = (hr.getType() == HistoricalReport.purityType.Virus )?
                    report.getVirusPPM() : report.getContaminantPPM();
            numPerMonth[month]++;
            qualitySum[month] += ppm;
        }
        for(int i = 0; i < qualityAverages.length; i++) {
            qualityAverages[i] = qualitySum[i] /((double) numPerMonth[i]);
        }
        return qualityAverages;
    }

    /**
     * Handles getting data points and plotting them on chart
     */
    private void displayChart() {
        hr = model.getHistoricalReport();
        List<WaterQualityReport> qualityReports = model.getQualityReports();
        double[] qualityAverages = getQualityAverages(getPurityReportsInRadius(qualityReports));
        ArrayList<Entry> entries = new ArrayList<Entry>();
        for(int i = 0; i < qualityAverages.length; i++){
            entries.add(new Entry((float)i, (float)qualityAverages[i]));
        }
        LineDataSet dataSet = new LineDataSet(entries,hr.getType().name()+" PPM");
        LineData chartData = new LineData(dataSet);
        chart.setData(chartData);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xAxisLabels[(int)value];
            }
        });
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSet.setDrawFilled(true);

        chart.animateY(5000);

        chart.getDescription().setText("Average Quality per month");
    }

}
