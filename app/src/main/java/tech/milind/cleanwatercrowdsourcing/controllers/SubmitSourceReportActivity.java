package tech.milind.cleanwatercrowdsourcing.controllers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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
import com.google.android.gms.maps.model.LatLng;

import tech.milind.cleanwatercrowdsourcing.R;
import tech.milind.cleanwatercrowdsourcing.model.Model;
import tech.milind.cleanwatercrowdsourcing.model.WaterSourceReport;

public class SubmitSourceReportActivity extends AppCompatActivity {
    private static final String TAG = "AddResourceReport";
    final int PLACE_PICKER_REQUEST = 1;

    private Spinner typeSpinner;
    private Spinner conditionSpinner;
    private EditText name;
    private LatLng latLng;

    private WaterSourceReport _waterSourceReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_source_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (EditText) findViewById(R.id.nameReportEditText);

        EditText mEdit = (EditText) findViewById(R.id.locationReportEditText);
        mEdit.setEnabled(false);

        typeSpinner = (Spinner) findViewById(R.id.spinnerType);
        conditionSpinner = (Spinner) findViewById(R.id.spinnerCondition);

        ArrayAdapter<WaterSourceReport.typeOfWater> typeOfWaterArrayAdapter =
                new ArrayAdapter<WaterSourceReport.typeOfWater>(this,
                        android.R.layout.simple_spinner_dropdown_item,
                        WaterSourceReport.typeOfWater.values());
        typeOfWaterArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeOfWaterArrayAdapter);

        ArrayAdapter<WaterSourceReport.conditionOfWater> conditionOfWaterArrayAdapter =
                new ArrayAdapter<WaterSourceReport.conditionOfWater>(this,
                        android.R.layout.simple_spinner_dropdown_item,
                        WaterSourceReport.conditionOfWater.values());
        conditionOfWaterArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        conditionSpinner.setAdapter(conditionOfWaterArrayAdapter);

        Button submitButton = (Button) findViewById(R.id.submitReport);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameText = name.getText().toString().trim();
                if(!(isEmpty(nameText) || latLng == null)) {
                    _waterSourceReport = new WaterSourceReport();
                    _waterSourceReport.setName(name.getText().toString());
                    _waterSourceReport.setLocation(latLng);
                    _waterSourceReport.setType((WaterSourceReport.typeOfWater)
                            typeSpinner.getSelectedItem());
                    _waterSourceReport.setCondition((WaterSourceReport.conditionOfWater)
                            conditionSpinner.getSelectedItem());
                    Model model = Model.getInstance();
                    model.addReport(_waterSourceReport);
                    Intent output = new Intent();
                    setResult(Activity.RESULT_OK, output);
                    finish();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_submit_source_report),
                            R.string.sourceReportSubmitError, Snackbar.LENGTH_LONG);
                    View sbview = snackbar.getView();
                    sbview.setBackgroundColor(Color.RED);
                    snackbar.show();
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addLocation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(SubmitSourceReportActivity.this),
                            PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    Log.e(TAG, "Failed", e);
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG, "Failed", e);
                }
            }
        });
    }

    public boolean isEmpty(String input) {
        return input.isEmpty() || input.length() == 0 || input.equals("") ||
                input == null;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                EditText editText = (EditText) findViewById(R.id.locationReportEditText);
                editText.setText("" + place.getLatLng());
                latLng = place.getLatLng();
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
