package tech.milind.cleanwatercrowdsourcing.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.NoSuchElementException;

import tech.milind.cleanwatercrowdsourcing.R;
import tech.milind.cleanwatercrowdsourcing.model.*;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Model model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        model = Model.getInstance();
    }

    protected void onSignInPressed(View view) {
        try {
            model.login(username.getText().toString(), password.getText().toString());
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        } catch (NoSuchElementException e) {
            //throws error in the form of a toast
            Toast.makeText(this,"The username or password entered is incorrect. " +
                    "Please try again.", Toast.LENGTH_LONG).show();
        }
    }

    protected void onCancelPressed(View view) {
        finish();
    }
}
