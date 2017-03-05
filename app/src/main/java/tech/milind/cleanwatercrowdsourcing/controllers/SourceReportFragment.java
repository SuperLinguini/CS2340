package tech.milind.cleanwatercrowdsourcing.controllers;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import tech.milind.cleanwatercrowdsourcing.R;
import tech.milind.cleanwatercrowdsourcing.model.Model;
import tech.milind.cleanwatercrowdsourcing.model.WaterSourceReport;

public class SourceReportFragment extends Fragment implements OnMapReadyCallback {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_source_report, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Model model = Model.getInstance();
        List<WaterSourceReport> reports = model.getReports();
        WaterSourceReport cur;
        for(int i = 0; i < reports.size(); i++) {
            cur = reports.get(i);
            googleMap.addMarker(new MarkerOptions().position(cur.getLocation())
                .title(cur.getName())
                .snippet(cur.getSnippet()))
                .setTag(cur);
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(reports
                .get(reports.size() - 1).getLocation()));
    }
}
