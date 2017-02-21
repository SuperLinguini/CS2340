package tech.milind.cleanwatercrowdsourcing.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import java.util.NoSuchElementException;

import tech.milind.cleanwatercrowdsourcing.model.*;

import tech.milind.cleanwatercrowdsourcing.R;

public class RegistrationActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        model = Model.getInstance();
    }

    protected void onRegisterPressed(View view) {
        try {
            model.register(username.getText().toString(), password.getText().toString());
            Intent i = new Intent(RegistrationActivity.this, EditProfileActivity.class);
            startActivity(i);
            finish();
        } catch (NoSuchElementException e) {
            Log.i("registrationError", e.getMessage());
            Toast.makeText(this, "Registration failed. Please try again.",
                    Toast.LENGTH_LONG).show();
        }
    }

    protected void onCancelPressed(View view) {
        finish();
    }
}
