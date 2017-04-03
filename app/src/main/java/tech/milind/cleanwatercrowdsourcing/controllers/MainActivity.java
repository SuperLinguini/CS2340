package tech.milind.cleanwatercrowdsourcing.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.lang.reflect.Field;

import tech.milind.cleanwatercrowdsourcing.R;
import tech.milind.cleanwatercrowdsourcing.model.Model;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Model model = Model.getInstance();
//        model.addTestData();
        model.loadWaterSourceReports();

        getSupportActionBar().setTitle("Water Availability");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, new AvailabilityFragment())
                .commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_bar);

        switch(model.getCurrentUser().getUserType()) {
            case USER:
                bottomNavigationView.inflateMenu(R.menu.navbar_user);
                break;
            case WORKER:
                bottomNavigationView.inflateMenu(R.menu.navbar_worker);
                break;
            case MANAGER:
                bottomNavigationView.inflateMenu(R.menu.navbar_manager);
                break;
            case ADMIN:
                bottomNavigationView.inflateMenu(R.menu.navbar_admin);
                break;
        }

        disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch(item.getItemId()) {
                        case R.id.nav_home:
                            if(!(getSupportFragmentManager().findFragmentById(R.id.fragmentContainer)
                                    instanceof AvailabilityFragment)) {
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragmentContainer, new AvailabilityFragment())
                                        .commit();
                                getSupportActionBar().setTitle("Water Availability");
                            }
                            break;
                        case R.id.nav_source:
                            if(!(getSupportFragmentManager().findFragmentById(R.id.fragmentContainer)
                                    instanceof ListSourceFragment)) {
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragmentContainer, new ListSourceFragment())
                                        .commit();
                                getSupportActionBar().setTitle("Water Source Report");
                            }
                            break;
                        case R.id.nav_purity:
                            if(!(getSupportFragmentManager().findFragmentById(R.id.fragmentContainer)
                                    instanceof FragmentQuality)) {
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragmentContainer, new FragmentQuality())
                                        .commit();
                                getSupportActionBar().setTitle("Water Purity Report");
                            }
                            break;
                        case R.id.nav_admin:
                            if(!(getSupportFragmentManager().findFragmentById(R.id.fragmentContainer)
                                    instanceof FragmentAdmin)) {
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragmentContainer, new FragmentAdmin())
                                        .commit();
                                getSupportActionBar().setTitle("Admin Page");
                            }
                            break;
                    }
                    return true;
                }
        });
    }

    /**
     * Disables shifting for BottomNavigationView nav bar
     * @param view the BottomNavigationView for the app
     */
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Intent logoutIntent = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(logoutIntent);
                break;
            case R.id.profile:
                Intent viewProfileIntent = new Intent(MainActivity.this, ViewProfileActivity.class);
                startActivity(viewProfileIntent);
                break;
        }
        return true;
    }

    /**
     * shows alert dialog to logout if back button is pressed on homescreen
     */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage("Are you sure you want to logout?");
        dialogBuilder.setCancelable(true);

        dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}
