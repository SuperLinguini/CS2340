package tech.milind.cleanwatercrowdsourcing.controllers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.Arrays;
import java.util.List;

import tech.milind.cleanwatercrowdsourcing.R;
import tech.milind.cleanwatercrowdsourcing.model.LatLng;
import tech.milind.cleanwatercrowdsourcing.model.Model;
import tech.milind.cleanwatercrowdsourcing.model.WaterQualityReport;

public class EditQualityReportActivity extends AppCompatActivity {
    private static final String TAG = "AddQualityReport";
    final int PLACE_PICKER_REQUEST = 1;
    final static int RESULT_CHANGED = 24;

    private Spinner conditionSpinner;
    private EditText reportName;
    private EditText virusPPM;
    private EditText contamPPM;
    private LatLng latLng;
    private int position;

    private WaterQualityReport _waterQualityReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_quality_report);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        position = i.getIntExtra("position", position);
        WaterQualityReport wqr = Model.getInstance().getQualityReports().get(position);

        reportName = (EditText) findViewById(R.id.namePurityReportEditTextEdit);
        reportName.setText(wqr.getReportName());

        EditText mEdit = (EditText) findViewById(R.id.locationPurityReportEditTextEdit);
        mEdit.setText(wqr.getLocation().toString());
        latLng = wqr.getLocation();
        mEdit.setEnabled(false);

        conditionSpinner = (Spinner) findViewById(R.id.spinnerPurityConditionEdit);

        List<WaterQualityReport.conditionOfWater> conditions =
                Arrays.asList(WaterQualityReport.conditionOfWater.values());
        ArrayAdapter<WaterQualityReport.conditionOfWater> conditionOfWaterArrayAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item, conditions);
        conditionOfWaterArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditionSpinner.setAdapter(conditionOfWaterArrayAdapter);
        conditionSpinner.setSelection(conditions.indexOf(wqr.getCondition()));

        virusPPM = (EditText) findViewById(R.id.virusPurityReportEditTextEdit);
        virusPPM.setText(String.valueOf(wqr.getVirusPPM()));

        contamPPM = (EditText) findViewById(R.id.contamPurityReportEditTextEdit);
        contamPPM.setText(String.valueOf(wqr.getContaminantPPM()));

        Button submitButton = (Button) findViewById(R.id.submitPurityReportEdit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameText = reportName.getText().toString().trim();
                if(!(isEmpty(nameText) || latLng == null)) {
                    Model model = Model.getInstance();
                    _waterQualityReport = model.getQualityReports().get(position);
                    _waterQualityReport.setReportName(reportName.getText().toString());
                    _waterQualityReport.setLocation(new LatLng(latLng.getLatitude(), latLng.getLongitude()));
                    _waterQualityReport.setCondition((WaterQualityReport.conditionOfWater)
                            conditionSpinner.getSelectedItem());
                    _waterQualityReport.setVirusPPM((int) Integer.parseInt(virusPPM.getText().toString()));
                    _waterQualityReport.setContaminantPPM((int) Integer.parseInt(contamPPM.getText().toString()));
                    model.editWaterQualityReport(_waterQualityReport);
                    Intent output = new Intent();
                    output.putExtra("position", position);
                    setResult(RESULT_CHANGED, output);
                    finish();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_edit_quality_report),
                            R.string.sourceReportSubmitError, Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(Color.RED);
                    snackbar.show();
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addPurityLocationEdit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(EditQualityReportActivity.this),
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
                EditText editText = (EditText) findViewById(R.id.locationReportEditText);
                editText.setText("" + place.getLatLng());
                latLng = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        Intent output = new Intent();
        setResult(Activity.RESULT_CANCELED, output);
        finish();
        return true;
    }
}
