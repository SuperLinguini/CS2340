package tech.milind.cleanwatercrowdsourcing.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import tech.milind.cleanwatercrowdsourcing.model.*;

import tech.milind.cleanwatercrowdsourcing.R;

public class RegistrationActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
    }

    protected void onRegisterPressed(View view) {
        Accounts accounts = Accounts.getInstance();
        accounts.addAccount(username.getText().toString(), password.getText().toString());
        Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    protected void onCancelPressed(View view) {
        finish();
    }
}
