package tech.milind.cleanwatercrowdsourcing.controllers;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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


/**
 * Created by kiran on 3/12/2017.
 */

public class FragmentManager extends Fragment {
    final int REQUEST = 1;
    final String[] xAxisLabels= {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private LineChart chart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager,container, false);
        FloatingActionButton edit = (FloatingActionButton) view.findViewById(R.id.editHistoricalFAB);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), EditHistoricalReportActivity.class);
                startActivityForResult(i, REQUEST);
            }
        });
        chart = (LineChart) view.findViewById(R.id.chart);
        return view;

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                String qualityType = data.getExtras().getString("quality type");
                double[] qualityAverages = data.getExtras().getDoubleArray("quality averages");
                ArrayList<Entry> entries = new ArrayList<Entry>();
                for(int i = 0; i < qualityAverages.length; i++){
                    entries.add(new Entry((float)i, (float)qualityAverages[i]));
                }
                Log.d("APP", "Made dataset with : " + entries.size());
                LineDataSet dataset = new LineDataSet(entries,qualityType+" PPM");
                LineData chartdata = new LineData(dataset);
                chart.setData(chartdata);
                XAxis xAxis = chart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setValueFormatter(new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return xAxisLabels[(int)value];
                    }
                });
                dataset.setColors(ColorTemplate.COLORFUL_COLORS);

                dataset.setDrawFilled(true);

                chart.animateY(5000);

                chart.getDescription().setText("Average Quality per month");
            }
        }
    }
}
