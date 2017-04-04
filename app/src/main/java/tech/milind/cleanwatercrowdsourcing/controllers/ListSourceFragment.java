package tech.milind.cleanwatercrowdsourcing.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.List;

import tech.milind.cleanwatercrowdsourcing.R;
import tech.milind.cleanwatercrowdsourcing.model.Model;
import tech.milind.cleanwatercrowdsourcing.model.WaterSourceReport;

public class ListSourceFragment extends Fragment {
    private SimpleSourceAdapter mAdapter;
    final int REQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        View recyclerView = view.findViewById(R.id.source_report_list);
        setupRecyclerView((RecyclerView) recyclerView);
        FloatingActionButton add = (FloatingActionButton) view.findViewById(R.id.addFAB);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SubmitSourceReportActivity.class);
                startActivityForResult(i, REQUEST);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                mAdapter.notifyItemInserted(mAdapter.reports.size() - 1);
            }
        }
    }

    /**
     * Sets up the RecyclerView and reverses the layout to show the most recent report, adds
     * the adapter and list to the RecyclerView
     * @param recyclerView the RecyclerView to set up
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        Model model = Model.getInstance();
        mAdapter = new SimpleSourceAdapter(model.getReports());
        recyclerView.setAdapter(mAdapter);
    }

    public class SimpleSourceAdapter extends RecyclerView
            .Adapter<SimpleSourceAdapter.ViewHolder> {
        private List<WaterSourceReport> reports;


        public class ViewHolder extends RecyclerView.ViewHolder {
            public View mView;
            public TextView reportNumAndName;
            public TextView reportDate;
            public TextView reportLocation;
            public TextView reportTypeCondition;
            public TextView reportReporter;

            /**
             * Constructor for ViewHolder that links the Views
             * @param v the view acted on
             */
            public ViewHolder(View v) {
                super(v);
                mView = v;
                reportNumAndName = (TextView) v.findViewById(R.id.reportNumAndName);
                reportReporter = (TextView) v.findViewById(R.id.reportReporter);
                reportDate = (TextView) v.findViewById(R.id.reportDate);
                reportLocation = (TextView) v.findViewById(R.id.reportLocation);
                reportTypeCondition = (TextView) v.findViewById(R.id.reportTypeCondition);
            }
        }

        /**
         * Sets the reports of the adapter to the given list of reports
         * @param list the list of reports to add to the adapter
         */
        public SimpleSourceAdapter(List<WaterSourceReport> list) {
            reports = list;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final Model model = Model.getInstance();
            if (reports.get(holder.getAdapterPosition()) != null) {
                final WaterSourceReport wrs = reports.get(holder.getAdapterPosition());
                holder.reportDate.setText(String.format("Date: %tD %<tR", wrs.getDate()));
                holder.reportNumAndName.setText(String.format("#%s %s",
                        wrs.getReportNumber(), wrs.getReportName()));
                holder.reportReporter.setText(wrs.getReporter());
                holder.reportLocation.setText(wrs.getLocation().toString());
                holder.reportTypeCondition.setText(wrs.getType() + ", " + wrs.getCondition());
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), EditSourceReportActivity.class);
                        i.putExtra("report_object", wrs);
                        i.putExtra("position", holder.getAdapterPosition());
                        startActivityForResult(i, REQUEST);
                    }
                });
            }
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
