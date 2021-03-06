package tech.milind.cleanwatercrowdsourcing.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tech.milind.cleanwatercrowdsourcing.R;

public class WelcomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button login = (Button) findViewById(R.id.buttonLogin);
        Button register = (Button) findViewById(R.id.buttonRegister);


        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(WelcomeActivity.this, RegistrationActivity.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
