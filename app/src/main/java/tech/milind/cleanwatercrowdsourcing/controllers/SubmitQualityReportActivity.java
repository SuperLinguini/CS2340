package tech.milind.cleanwatercrowdsourcing.controllers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import tech.milind.cleanwatercrowdsourcing.R;
import tech.milind.cleanwatercrowdsourcing.model.Model;
import tech.milind.cleanwatercrowdsourcing.model.WaterQualityReport;
import tech.milind.cleanwatercrowdsourcing.model.LatLng;

/**
 * Created by gunoupark on 12/03/2017.
 */

public class SubmitQualityReportActivity extends AppCompatActivity {
    private static final String TAG = "AddPurityReport";
    final private int PLACE_PICKER_REQUEST = 1;

    private Spinner conditionSpinner;
    private EditText name;
    private LatLng latLng;
    private EditText virusPPM;
    private EditText contaminationPPM;

    private WaterQualityReport _waterQualityReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_purity_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (EditText) findViewById(R.id.namePurityReportEditText);

        conditionSpinner = (Spinner) findViewById(R.id.spinnerPurityCondition);

        EditText mEdit = (EditText) findViewById(R.id.locationPurityReportEditText);
        mEdit.setEnabled(false);

        final ArrayAdapter<WaterQualityReport.conditionOfWater> conditionOfWaterArrayAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item,
                        WaterQualityReport.conditionOfWater.values());
        conditionOfWaterArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        conditionSpinner.setAdapter(conditionOfWaterArrayAdapter);

        virusPPM = (EditText) findViewById(R.id.virusPurityReportEditText);
        contaminationPPM = (EditText) findViewById(R.id.contamPurityReportEditText);

        Button submitButton = (Button) findViewById(R.id.submitPurityReport);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameText = name.getText().toString().trim();
                int virus = Integer.parseInt(virusPPM.getText().toString());
                int contamination = Integer.parseInt(contaminationPPM.getText().toString());
                if(!(isEmpty(nameText) || latLng == null)) {
                        _waterQualityReport = new WaterQualityReport();
                    _waterQualityReport.setReportName(name.getText().toString());
                    _waterQualityReport.setLocation(latLng);
                    _waterQualityReport.setCondition((WaterQualityReport.conditionOfWater)
                            conditionSpinner.getSelectedItem());
                    _waterQualityReport.setVirusPPM(virus);
                    _waterQualityReport.setContaminantPPM(contamination);
                    Model model = Model.getInstance();
                    model.addWaterQualityReport(_waterQualityReport);
                    Intent output = new Intent();
                    setResult(Activity.RESULT_OK, output);
                    finish();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_submit_purity_report),
                            R.string.purityReportSubmitError, Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(Color.RED);
                    snackbar.show();
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addPurityLocation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(SubmitQualityReportActivity.this),
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
    private boolean isEmpty(String input) {
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
                EditText editText = (EditText) findViewById(R.id.locationPurityReportEditText);
                editText.setText("" + place.getLatLng());
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
