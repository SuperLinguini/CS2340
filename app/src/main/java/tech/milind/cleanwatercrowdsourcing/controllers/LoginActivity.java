package tech.milind.cleanwatercrowdsourcing.controllers;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.NoSuchElementException;
import tech.milind.cleanwatercrowdsourcing.R;
import tech.milind.cleanwatercrowdsourcing.model.*;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    final Model model = Model.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText username = (EditText) findViewById(R.id.editTextUsername);
        final EditText password = (EditText) findViewById(R.id.editTextPassword);
        Button loginButton = (Button) findViewById(R.id.buttonLogin);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();


        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
//                startActivity(i);

                startActivityForResult(
                        // Get an instance of AuthUI based on the default app
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setProviders(
                                        Arrays.asList(
                                                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                                new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()
                                        ))
                                .setIsSmartLockEnabled(false)
                                .build(),
                        RC_SIGN_IN);

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
//                model.setCurrentUser(new User());
//                model.getmDatabase().child("users").child(model.getAuth().getCurrentUser().getUid())
//                        .addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
////                                Log.e("tag", dataSnapshot.toString());
////                                Log.e("tag", model.getAuth().getCurrentUser().getUid());
//                                exists = dataSnapshot.exists();
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
                String email = "";
                String pass = "";
                FirebaseAuth auth = FirebaseAuth.getInstance();
                if (auth.getCurrentUser() != null) {
                    email = auth.getCurrentUser().getEmail();
                    pass = auth.getCurrentUser().getUid();
                } else {
                    // not signed in
                }
                boolean found = false;
                try {
                    model.getSecurity().findUser(email);
                    found = true;
                } catch (NoSuchElementException e) {
                    model.register(email, pass);
                }
                if (found) {
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(this, EditProfileActivity.class);
                    startActivity(i);
                    finish();
                }

                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showSnackbar(R.string.sign_in_cancelled);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackbar(R.string.no_internet_connection);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showSnackbar(R.string.unknown_error);
                    return;
                }
            }

            showSnackbar(R.string.unknown_sign_in_response);
        }
    }

    public void showSnackbar(int message) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_login),
                message, Snackbar.LENGTH_SHORT).setActionTextColor(Color.RED);
        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
