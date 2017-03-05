package tech.milind.cleanwatercrowdsourcing.controllers;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import tech.milind.cleanwatercrowdsourcing.R;
import tech.milind.cleanwatercrowdsourcing.model.Model;
import tech.milind.cleanwatercrowdsourcing.model.WaterSourceReport;

public class ListSourceFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        View recyclerView = view.findViewById(R.id.source_report_list);
        setupRecyclerView((RecyclerView) recyclerView);
        return view;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        Model model = Model.getInstance();
        List<WaterSourceReport> reports = model.getReports();
        Collections.sort(reports);
        Collections.reverse(reports);
        recyclerView.setAdapter(new SimpleSourceAdapter(reports));
    }

    public class SimpleSourceAdapter extends RecyclerView
            .Adapter<SimpleSourceAdapter.ViewHolder> {
        private List<WaterSourceReport> reports;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView reportNumAndName;
            public TextView reportDate;
            public TextView reportLocation;
            public TextView reportTypeCondition;

            public ViewHolder(View v) {
                super(v);
                reportNumAndName = (TextView) v.findViewById(R.id.reportNumAndName);
                reportDate = (TextView) v.findViewById(R.id.reportDate);
                reportLocation = (TextView) v.findViewById(R.id.reportLocation);
                reportTypeCondition = (TextView) v.findViewById(R.id.reportTypeCondition);
            }
        }

        public SimpleSourceAdapter(List<WaterSourceReport> list) {
            reports = list;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Model model = Model.getInstance();
            WaterSourceReport wrs = reports.get(position);
            holder.reportDate.setText(String.format("Date: %tD %<tR", wrs.getDate()));
            holder.reportNumAndName.setText(String.format("#%s %s",
                    wrs.getReportNumber(), wrs.getName()));
            holder.reportLocation.setText(wrs.getLocation().toString());
            holder.reportTypeCondition.setText(wrs.getType() + ", " + wrs.getCondition());
        }

        @Override
        public int getItemCount() {
            return reports.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.source_list_content, parent, false);
            return new ViewHolder(view);
        }
    }
}
