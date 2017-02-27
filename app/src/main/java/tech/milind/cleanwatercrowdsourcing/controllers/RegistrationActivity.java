package tech.milind.cleanwatercrowdsourcing.controllers;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.NoSuchElementException;
import tech.milind.cleanwatercrowdsourcing.model.*;
import tech.milind.cleanwatercrowdsourcing.R;

public class RegistrationActivity extends AppCompatActivity {
    Model model;
    EditText username;
    EditText password;
    Button registerButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        model = Model.getInstance();
        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        registerButton = (Button) findViewById(R.id.buttonRegister);
        cancelButton = (Button) findViewById(R.id.buttonCancel);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    model.register(username.getText().toString(), password.getText().toString());
                    Intent i = new Intent(RegistrationActivity.this, EditProfileActivity.class);
                    i.putExtra("New user", true);
                    startActivity(i);
                    finish();
                } catch (NoSuchElementException e) {
                    Toast.makeText(getApplicationContext(), "Registration failed. Please try again.",
                            Toast.LENGTH_LONG).show();
                } catch (IllegalArgumentException e){
                    Toast.makeText(getApplicationContext(), e.getMessage(),
                            Toast.LENGTH_LONG).show();
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
