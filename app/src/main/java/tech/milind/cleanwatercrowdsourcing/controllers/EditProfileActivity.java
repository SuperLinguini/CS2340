package tech.milind.cleanwatercrowdsourcing.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import tech.milind.cleanwatercrowdsourcing.R;
import tech.milind.cleanwatercrowdsourcing.model.*;

public class EditProfileActivity extends AppCompatActivity {

    private Model model;
    private User currentUser;

    private Button saveButton;
    private Button cancelButton;
    private EditText nameField;
    private EditText homeAddressField;
    private EditText emailAddressField;
    private Spinner userTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);

        model = Model.getInstance();
        currentUser = model.getCurrentUser();

        saveButton = (Button) findViewById(R.id.save_profile);
        cancelButton = (Button) findViewById(R.id.cancel_profile);
        nameField = (EditText) findViewById(R.id.edit_name);
        homeAddressField = (EditText) findViewById(R.id.edit_home_address);
        emailAddressField = (EditText) findViewById(R.id.edit_email_address);
        userTypeSpinner = (Spinner) findViewById(R.id.spinner_user_type);

        ArrayAdapter<String> userTypeAdapter
                = new ArrayAdapter(this, android.R.layout.simple_spinner_item, UserType.toList());
        userTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(userTypeAdapter);

        if (currentUser.getName().equals("")) {
            nameField.setHint("Enter text here");
        } else {
            nameField.setText(currentUser.getName());
        }

        if (currentUser.getHomeAddress().equals("")) {
            homeAddressField.setHint("Enter text here.");
        } else {
            homeAddressField.setText(currentUser.getHomeAddress());
        }

        if (currentUser.getEmailAddress().equals("")) {
            emailAddressField.setHint("Enter text here.");
        } else {
            emailAddressField.setText(currentUser.getEmailAddress());
        }

        userTypeSpinner.setSelection(currentUser.getUserType().getLevel());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser.setName(nameField.getText().toString());
                currentUser.setHomeAddress(homeAddressField.getText().toString());
                currentUser.setEmailAddress(emailAddressField.getText().toString());
                currentUser.setUserType(UserType.findUserType((String) userTypeSpinner.getSelectedItem()));
                Intent i = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getBooleanExtra("New user", false)) {
                    model.deleteUser(currentUser.getUsername());
                    Toast.makeText(getApplicationContext(),
                            "Registration failed.", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

    }

    /**
     * Prevents the user from being registered if back button is pressed during registration
     */
    @Override
    public void onBackPressed() {
        if(getIntent().getBooleanExtra("New user", false)) {
            model.deleteUser(currentUser.getUsername());
            Toast.makeText(getApplicationContext(),
                    "Registration failed.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
