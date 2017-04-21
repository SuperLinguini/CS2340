package tech.milind.cleanwatercrowdsourcing.controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import tech.milind.cleanwatercrowdsourcing.R;
import tech.milind.cleanwatercrowdsourcing.model.Model;
import tech.milind.cleanwatercrowdsourcing.model.User;

/**
 * Created by whe1996 on 4/21/17.
 */

public class EditUserActivity extends AppCompatActivity {
    private static final String TAG = "AddResourceReport";
    final static int RESULT_CHANGED = 24;
    final static int RESULT_REMOVED = 35;

    private Button banUser;
    private Button deleteUser;
    private int position;
    private User _user;
    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        model = Model.getInstance();
        _user = (User) model.getSecurity().findUser(i.getStringExtra("username"));
        position = i.getIntExtra("position", position);
        getSupportActionBar().setTitle(String.format("%s   (%s)",
                _user.getName(),
                _user.getUserType().getType().toUpperCase()));
        banUser = (Button) findViewById(R.id.ban_button);
        deleteUser = (Button) findViewById(R.id.delete_button);
        if (!_user.isBanned()) {
            banUser.setText("BAN USER");
            banUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(v.getContext());
                    dialogBuilder.setMessage("Are you sure you want to ban this user?");
                    dialogBuilder.setCancelable(true);
                    dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            _user.ban();
                            Intent output = new Intent();
                            output.putExtra("position", position);
                            setResult(RESULT_CHANGED, output);
                            finish();
                        }
                    });
                    dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    dialogBuilder.show();
                }
            });
        } else {
            banUser.setText("RECOVER USER");
            banUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditUserActivity.this);
                    dialogBuilder.setMessage("Are you sure you want to recover this user?");
                    dialogBuilder.setCancelable(true);

                    dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            _user.recover();
                            Intent output = new Intent();
                            output.putExtra("position", position);
                            setResult(RESULT_CHANGED, output);
                            finish();
                        }
                    });
                    dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    dialogBuilder.show();
                }
            });
        }
        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditUserActivity.this);
                dialogBuilder.setMessage("Are you sure you want to delete this user?");
                dialogBuilder.setCancelable(true);

                dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        model.deleteUser(_user.getUsername());
                        Intent output = new Intent();
                        output.putExtra("position", position);
                        setResult(RESULT_REMOVED, output);
                        finish();
                    }
                });
                dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                dialogBuilder.show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        Intent output = new Intent();
        setResult(Activity.RESULT_CANCELED, output);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(EditUserActivity.this, MainActivity.class);
        startActivity(i);
    }
}
