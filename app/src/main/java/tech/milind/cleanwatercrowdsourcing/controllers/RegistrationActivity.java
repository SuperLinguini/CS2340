package tech.milind.cleanwatercrowdsourcing.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

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
        boolean isSuccessful = model.register(username.getText().toString(),
                                    password.getText().toString());
        if (isSuccessful) {
            Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            //throws error in the form of a toast
            Toast.makeText(this,"Registration failed. Please try again.",
                    Toast.LENGTH_LONG).show();
        }
    }

    protected void onCancelPressed(View view) {
        finish();
    }
}
