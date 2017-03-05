package tech.milind.cleanwatercrowdsourcing.controllers;


import android.app.ListFragment;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
        recyclerView.setAdapter(new SimpleSourceAdapter(model.getReports()));
    }

    public class SimpleSourceAdapter extends RecyclerView
            .Adapter<SimpleSourceAdapter.ViewHolder> {
        private List<WaterSourceReport> reports;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView reportName;
            public TextView reportNumAndDate;
            public TextView reportLocation;
            public TextView reportTypeCondition;

            public ViewHolder(View v) {
                super(v);
                reportName = (TextView) v.findViewById(R.id.reportName);
                reportNumAndDate = (TextView) v.findViewById(R.id.reportNumAndDate);
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
            holder.reportNumAndDate.setText(String.format("#%1$d Date: %2$tD %2$tR",
                    wrs.getReportNumber(), wrs.getDate()));
            holder.reportName.setText(wrs.getName());
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
