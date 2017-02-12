package tech.milind.cleanwatercrowdsourcing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class LoginAcitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    protected void onSignInPressed(View view) {
        Intent i = new Intent(LoginAcitivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    protected void onCancelPressed(View view) {
        finish();
    }
}
