package tech.milind.cleanwatercrowdsourcing.controllers;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.NoSuchElementException;
import tech.milind.cleanwatercrowdsourcing.R;
import tech.milind.cleanwatercrowdsourcing.model.*;

public class LoginActivityOld extends AppCompatActivity {
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
        FirebaseAuth mAuth = FirebaseAuth.getInstance();


        FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("TAG", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    model.login(username.getText().toString(), password.getText().toString());
                    Intent i = new Intent(LoginActivityOld.this, MainActivity.class);
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
