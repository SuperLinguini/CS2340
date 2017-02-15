package tech.milind.cleanwatercrowdsourcing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginAcitivity extends AppCompatActivity {
    EditText username;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
    }

    protected void onSignInPressed(View view) {
        if (username.getText().toString().equals("user") &&
                password.getText().toString().equals("pass")) {
            Intent i = new Intent(LoginAcitivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        else {
            //throws error in the form of a toast
            Toast.makeText(this,"The username or password entered is incorrect. " +
                    "Please try again.", Toast.LENGTH_LONG).show();
        }
    }

    protected void onCancelPressed(View view) {
        finish();
    }
}
