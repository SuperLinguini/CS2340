package tech.milind.cleanwatercrowdsourcing.controllers;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import java.util.NoSuchElementException;
import tech.milind.cleanwatercrowdsourcing.R;
import tech.milind.cleanwatercrowdsourcing.model.*;

public class LoginActivity extends AppCompatActivity {
    Model model;
    EditText username;
    EditText password;
    Button loginButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        model = Model.getInstance();
        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        loginButton = (Button) findViewById(R.id.buttonLogin);
        cancelButton = (Button) findViewById(R.id.buttonCancel);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    model.login(username.getText().toString(), password.getText().toString());
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } catch (NoSuchElementException e) {
                    //throws error in the form of a toast
                    Toast.makeText(getApplicationContext(), "The username or password entered is "
                            + "incorrect. Please try again.", Toast.LENGTH_LONG).show();
                }
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
