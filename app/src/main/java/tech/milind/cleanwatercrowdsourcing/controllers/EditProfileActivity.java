package tech.milind.cleanwatercrowdsourcing.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tech.milind.cleanwatercrowdsourcing.R;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import tech.milind.cleanwatercrowdsourcing.model.Model;
import tech.milind.cleanwatercrowdsourcing.model.UserType;
import tech.milind.cleanwatercrowdsourcing.model.User;
import android.content.Intent;

/**
 * Created by whe1996 on 2/20/17.
 */
public class EditProfileActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);
        Button saveButton = (Button) findViewById(R.id.save_profile);
        Button cancelButton = (Button) findViewById(R.id.cancel_profile);
        final EditText nameField = (EditText) findViewById(R.id.edit_name);
        final EditText homeAdressField = (EditText) findViewById(R.id.edit_home_adress);
        final EditText emailAdressField = (EditText) findViewById(R.id.edit_email_address);
        final Spinner userTypeSpinner = (Spinner) findViewById(R.id.spinner_user_type);
        final Model model = Model.getInstance();

        ArrayAdapter<String> userTypeAdapter = new ArrayAdapter(
                                                    this,android.R.layout.simple_spinner_item,
                                                    UserType.toList());
        userTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(userTypeAdapter);

        user = model.getCurrentUser();
        nameField.setText(user.getName());
        homeAdressField.setText(user.getHomeAddress());
        emailAdressField.setText(user.getEmailAddress());
        userTypeSpinner.setSelection(user.getUserType().getLevel());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setName(nameField.getText().toString());
                user.setHomeAddress(homeAdressField.getText().toString());
                user.setEmailAddress(emailAdressField.getText().toString());
                user.setUserType(UserType.findUserType((String) userTypeSpinner.getSelectedItem()));
                Intent i = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
