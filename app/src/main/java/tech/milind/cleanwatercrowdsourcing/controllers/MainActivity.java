package tech.milind.cleanwatercrowdsourcing.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Field;

import tech.milind.cleanwatercrowdsourcing.R;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView =(BottomNavigationView)
                findViewById(R.id.nav_bar);
        disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch(item.getItemId()) {
                        case R.id.nav_home:

                    }
                    return true;
                }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.addFAB);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SubmitSourceReportActivity.class);
                startActivity(i);
            }
        });
    }

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
                finish();
                break;
            case R.id.profile:
                Intent i = new Intent(MainActivity.this, ViewProfileActivity.class);
                startActivity(i);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
