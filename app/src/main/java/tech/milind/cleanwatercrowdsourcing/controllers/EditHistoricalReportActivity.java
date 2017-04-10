package tech.milind.cleanwatercrowdsourcing.controllers;

import android.app.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import tech.milind.cleanwatercrowdsourcing.R;
import tech.milind.cleanwatercrowdsourcing.model.HistoricalReport;
import tech.milind.cleanwatercrowdsourcing.model.LatLng;
import tech.milind.cleanwatercrowdsourcing.model.Model;

public class EditHistoricalReportActivity extends AppCompatActivity {
    private static final String TAG = "EditHistoricalReport";
    final int PLACE_PICKER_REQUEST = 1;

    private EditText year;
    private EditText radius;
    private EditText location;
    private RadioGroup qualityType;
    private HistoricalReport.purityType type;
    private LatLng latLng;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_historical_report);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Model model = Model.getInstance();
        HistoricalReport existingHr = model.getHistoricalReport();
        year = (EditText) findViewById(R.id.yearHistEditText);
        radius = (EditText) findViewById(R.id.radiusHistEditText);
        qualityType = (RadioGroup) findViewById(R.id.HistRadioGroup);
        button = (Button) findViewById(R.id.HistButton);
        location = (EditText) findViewById(R.id.locationHistReportEditText);
        location.setEnabled(false);

        if(existingHr != null) {
            year.setText("" + existingHr.getYear());
            radius.setText("" + (int) existingHr.getRadius());
            latLng = existingHr.getLocation();
            location.setText("" + latLng);
            type = existingHr.getType();
            if(type == HistoricalReport.purityType.Contaminant) {
                qualityType.check(R.id.rbContaminant);
            } else {
                qualityType.check(R.id.rbVirus);
            }
        }
        qualityType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rbVirus: {
                        type = HistoricalReport.purityType.Virus;
                        break;
                    }
                    case R.id.rbContaminant: {
                        type = HistoricalReport.purityType.Contaminant;
                        break;
                    }
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(!isEmpty(year.getText().toString()) || !isEmpty(radius.getText().toString()) ||
                        type == null || latLng == null)) {

                    Model model = Model.getInstance();
                    int yr = Integer.parseInt(year.getText().toString());
                    double rad = Double.parseDouble(radius.getText().toString());
                    HistoricalReport hr = new HistoricalReport(latLng, yr, rad,type);
                    model.setHistoricalReport(hr);
                    Intent output = new Intent();
                    setResult(Activity.RESULT_OK, output);
                    finish();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_edit_historical_report),
                            R.string.sourceReportSubmitError, Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(Color.RED);
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
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
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
        return input != null && !input.isEmpty() && input.length() != 0 && !input.equals("");
    }


    /**
     * Determines whether the Google Place Picker was successful and modifies Location EditText
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                location.setText("" + place.getLatLng());
                latLng = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent output = new Intent();
        setResult(Activity.RESULT_CANCELED, output);
        super.onBackPressed();
    }

}
