package tech.milind.cleanwatercrowdsourcing.controllers;

import android.app.Activity;
import android.support.v4.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import tech.milind.cleanwatercrowdsourcing.R;
import tech.milind.cleanwatercrowdsourcing.model.Model;
import tech.milind.cleanwatercrowdsourcing.model.WaterQualityReport;

public class EditHistoricalReportActivity extends AppCompatActivity {
    private static final String TAG = "EditHistoricalReport";
    final int PLACE_PICKER_REQUEST = 1;

    private EditText year;
    private EditText radius;
    private EditText location;
    private RadioGroup qualityType;
    private String type;
    private LatLng latLng;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_historical_report);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        year = (EditText) findViewById(R.id.yearHistEditText);
        radius = (EditText) findViewById(R.id.radiusHistEditText);
        qualityType = (RadioGroup) findViewById(R.id.HistradioGroup);
        button = (Button) findViewById(R.id.HistButton);
        location = (EditText) findViewById(R.id.locationHistReportEditText);
        location.setEnabled(false);
        qualityType = (RadioGroup) findViewById(R.id.HistradioGroup);
        qualityType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rbVirus: {
                        type = "virus";
                        break;
                    }
                    case R.id.rbContaminant: {
                        type = "contaminant";
                        break;
                    }
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(isEmpty(year.getText().toString())|| isEmpty(radius.getText().toString()) ||
                        type == null || latLng == null)) {
                    List<WaterQualityReport> reports = getPurityReportsInRadius();
                    Bundle bundle = new Bundle();
                    bundle.putDoubleArray("quality averages", getQualityAverages(reports));
                    bundle.putString("quality type", type);
                    Log.d("added Bundle", "bundle");
//                    FragmentManager fm = new FragmentManager();
//                    fm.setArguments(bundle);
//                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.fragmentContainer, fm);
//                    transaction.addToBackStack(null);
//
//                    transaction.commit();
                    //finish();
                    //bundle.put
                    Intent output = new Intent();
                    output.putExtras(bundle);
                    setResult(Activity.RESULT_OK, output);
                    finish();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_edit_historical_report),
                            R.string.sourceReportSubmitError, Snackbar.LENGTH_LONG);
                    View sbview = snackbar.getView();
                    sbview.setBackgroundColor(Color.RED);
                    snackbar.show();
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addLocationHistFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(EditHistoricalReportActivity.this),
                            PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    Log.e(TAG, "Failed", e);
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG, "Failed", e);
                }
            }
        });
    }

    /**
     * Determines whether a TextView is empty
     * @param input String from TextView
     * @return whether the String is empty
     */
    public boolean isEmpty(String input) {
        return input.isEmpty() || input.length() == 0 || input.equals("") ||
                input == null;
    }


    /**
     * Determines whether the Google Place Picker was successful and modifies Location EditText
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                location.setText("" + place.getLatLng());
                latLng = place.getLatLng();
            }
        }
    }

    private List<WaterQualityReport> getPurityReportsInRadius() {
        Model model = Model.getInstance();
        List<WaterQualityReport> reports = model.getQualityReports();
        List<WaterQualityReport> reportsInRadius = new ArrayList<WaterQualityReport>();
        Location center = new Location("center");
        center.setLatitude(latLng.latitude);
        center.setLongitude(latLng.longitude);
        for(WaterQualityReport report: reports) {
            Location loc = new Location("Report Location");
            loc.setLatitude(report.getLocation().getLatitude());
            loc.setLongitude(report.getLocation().getLongitude());
            double rad = Double.parseDouble(radius.getText().toString());
            double distanceInMiles = center.distanceTo(loc) / 1609.34;
            int yr = Integer.parseInt(year.getText().toString());
            int reportYear = report.getDate().getYear() + 1900;
            if (distanceInMiles <= rad && reportYear == yr) {
                reportsInRadius.add(report);

            }
        }
        return reportsInRadius;
    }

    private double[] getQualityAverages(List<WaterQualityReport> reports) {
        int[] numPerMonth = new int[12];
        int[] qualitySum = new int[12];
        double[] qualityAverages = new double[12];
        for(WaterQualityReport report: reports) {
            int month = report.getDate().getMonth();
            int ppm = (type.equals("virus"))? report.getVirusPPM() : report.getContaminantPPM();
            numPerMonth[month]++;
            qualitySum[month] += ppm;
        }
        for(int i = 0; i < qualityAverages.length; i++) {
            qualityAverages[i] = qualitySum[i] /((double) numPerMonth[i]);
        }
        return qualityAverages;
    }

    @Override
    public void onBackPressed() {
        Intent output = new Intent();
        setResult(Activity.RESULT_CANCELED, output);
        super.onBackPressed();
    }

}
