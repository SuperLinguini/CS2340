package tech.milind.cleanwatercrowdsourcing.controllers;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import tech.milind.cleanwatercrowdsourcing.R;
import tech.milind.cleanwatercrowdsourcing.model.*;

public class EditProfileActivity extends AppCompatActivity {

    private Model model;
    private User currentUser;

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

        Button saveButton = (Button) findViewById(R.id.save_profile);
        Button cancelButton = (Button) findViewById(R.id.cancel_profile);
        nameField = (EditText) findViewById(R.id.edit_name);
        homeAddressField = (EditText) findViewById(R.id.edit_home_address);
        emailAddressField = (EditText) findViewById(R.id.edit_email_address);
        userTypeSpinner = (Spinner) findViewById(R.id.spinner_user_type);

        ArrayAdapter<String> userTypeAdapter
                = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, UserType.toList());
        userTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(userTypeAdapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if (getIntent().getExtras() != null &&
                    getIntent().getStringExtra("call").equals("newUser")) {
                nameField.setHint("Enter text here");
                emailAddressField.setText(user.getEmail());
                homeAddressField.setHint("Enter home address here.");
            } else {
                nameField.setText(user.getDisplayName());
                emailAddressField.setText(user.getEmail());
                homeAddressField.setText(currentUser.getHomeAddress());
                userTypeSpinner.setSelection(currentUser.getUserType().getLevel());
            }
        }


//        if (currentUser.getName().equals("")) {
//            nameField.setHint("Enter text here");
//        } else {
//            nameField.setText(currentUser.getName());
//        }


//        if (currentUser.getHomeAddress().equals("")) {
//            homeAddressField.setHint("Enter text here.");
//        } else {
//            homeAddressField.setText(currentUser.getHomeAddress());
//        }

//        if (currentUser.getEmailAddress().equals("")) {
//            emailAddressField.setHint("Enter text here.");
//        } else {
//            emailAddressField.setText(currentUser.getEmailAddress());
//        }

//        userTypeSpinner.setSelection(currentUser.getUserType().getLevel());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    currentUser.setName(nameField.getText().toString());
                    currentUser.setHomeAddress(homeAddressField.getText().toString());
//                    currentUser.setEmailAddress(emailAddressField.getText().toString());
                    currentUser.setUserType(UserType.findUserType((String) userTypeSpinner.getSelectedItem()));
                }
                Intent i = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(getIntent().getBooleanExtra("New user", false)) {
//                    model.deleteUser(currentUser.getUsername());
//                    Toast.makeText(getApplicationContext(),
//                            "Registration failed.", Toast.LENGTH_SHORT).show();
//                }
//                finish();
            }
        });

    }

    /**
     * Prevents the user from being registered if back button is pressed during registration
     */
    @Override
    public void onBackPressed() {
        if(getIntent().getBooleanExtra("New user", false)) {
//            model.deleteUser(currentUser.getUsername());
            Toast.makeText(getApplicationContext(),
                    "Registration failed.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
