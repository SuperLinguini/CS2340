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

        Button editButton = (Button) findViewById(R.id.edit_button);
        TextView nameText = (TextView) findViewById(R.id.text_name);
        TextView homeAddressText = (TextView) findViewById(R.id.text_home_address);
        TextView emailAddressText = (TextView) findViewById(R.id.text_email_address);
        TextView userTypeText = (TextView) findViewById(R.id.text_user_type);

        User user = model.getCurrentUser();
        nameText.setText(user.getName());
        homeAddressText.setText(user.getHomeAddress());
        emailAddressText.setText(user.getEmailAddress());
        userTypeText.setText(user.getUserType().getType());

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewProfileActivity.this, EditProfileActivity.class);
                startActivity(i);
            }
        });
    }
}
