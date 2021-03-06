package tech.milind.cleanwatercrowdsourcing.controllers;

import tech.milind.cleanwatercrowdsourcing.R;
import tech.milind.cleanwatercrowdsourcing.model.Model;
import tech.milind.cleanwatercrowdsourcing.model.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_landing);
        Model model = Model.getInstance();
        User currentUser = model.getCurrentUser();


        Button editButton = (Button) findViewById(R.id.edit_profile);
        TextView nameText = (TextView) findViewById(R.id.txt_view_name);
        TextView homeAddressText = (TextView) findViewById(R.id.txt_view_home_address);
        TextView emailAddressText = (TextView) findViewById(R.id.txt_view_email_address);
        TextView userTypeText = (TextView) findViewById(R.id.txt_view_user_type);

        if (currentUser.getName().equals("")) {
            nameText.setText("(Please add your name)");
        } else {
            nameText.setText(currentUser.getName());

        }

        if (currentUser.getHomeAddress().equals("")) {
            homeAddressText.setText("(Please add your home address)");
        } else {
            homeAddressText.setText(currentUser.getHomeAddress());
        }

        if (currentUser.getEmailAddress().equals("")) {
            emailAddressText.setText("(Please add your email address)");
        } else {
            emailAddressText.setText(currentUser.getEmailAddress());
        }

        userTypeText.setText(currentUser.getUserType().getType());

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewProfileActivity.this, EditProfileActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
