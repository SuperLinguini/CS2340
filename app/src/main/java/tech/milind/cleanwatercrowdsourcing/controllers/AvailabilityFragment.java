package tech.milind.cleanwatercrowdsourcing.controllers;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import tech.milind.cleanwatercrowdsourcing.R;
import tech.milind.cleanwatercrowdsourcing.model.Model;
import tech.milind.cleanwatercrowdsourcing.model.WaterQualityReport;
import tech.milind.cleanwatercrowdsourcing.model.WaterSourceReport;

public class AvailabilityFragment extends Fragment implements OnMapReadyCallback {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_availability, container, false);
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
            if (cur != null) {
                googleMap.addMarker(new MarkerOptions().position(cur.getLocation().getMapsLatLng())
                        .title(cur.getReportName())
                        .snippet(cur.getSnippet()))
                        .setTag(cur);
            }
        }
        List<WaterQualityReport> qualityReports = model.getPurityReports();
        WaterQualityReport purity;
        for(int i = 0; i < qualityReports.size(); i++) {
            purity = qualityReports.get(i);
            googleMap.addMarker(new MarkerOptions().position(purity.getLocation())
                    .title(purity.getName())
                    .snippet(purity.getSnippet())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)))
                    .setTag(purity);
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(reports
                .get(reports.size() - 1).getLocation().getMapsLatLng()));
    }
}
