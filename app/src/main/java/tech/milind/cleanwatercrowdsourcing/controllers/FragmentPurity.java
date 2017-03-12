package tech.milind.cleanwatercrowdsourcing.controllers;

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
import tech.milind.cleanwatercrowdsourcing.model.WaterPurityReport;

/**
 * Created by SuperLinguini on 3/5/2017.
 */

public class FragmentPurity extends Fragment {
    private SimplePurityAdapter mAdapter;
    final int REQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quality, container, false);
        View recyclerView = view.findViewById(R.id.purity_report_list);
        setupRecyclerView((RecyclerView) recyclerView);
        FloatingActionButton add = (FloatingActionButton) view.findViewById(R.id.addPurityFAB);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SubmitPurityReportActivity.class);
                startActivityForResult(i, REQUEST);
            }
        });
        return view;
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
        List<WaterPurityReport> reports = model.getPurityReports();
        mAdapter = new SimplePurityAdapter(reports);
        recyclerView.setAdapter(mAdapter);
    }

    public class SimplePurityAdapter extends RecyclerView
            .Adapter<FragmentPurity.SimplePurityAdapter.ViewHolder> {
        private List<WaterPurityReport> reports;


        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView reportNumAndName;
            public TextView reportDate;
            public TextView reportLocation;
            public TextView reportConditionPPM;

            /**
             * Constructor for ViewHolder that links the Views
             * @param v the view acted on
             */
            public ViewHolder(View v) {
                super(v);
                reportNumAndName = (TextView) v.findViewById(R.id.purityNumAndName);
                reportDate = (TextView) v.findViewById(R.id.purityDate);
                reportLocation = (TextView) v.findViewById(R.id.purityLocation);
                reportConditionPPM = (TextView) v.findViewById(R.id.purityConditionPPM);
            }
        }

        /**
         * Sets the reports of the adapter to the given list of reports
         * @param list the list of reports to add to the adapter
         */
        public SimplePurityAdapter(List<WaterPurityReport> list) {
            reports = list;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Model model = Model.getInstance();
            WaterPurityReport wrp = reports.get(position);
            holder.reportDate.setText(String.format("Date: %tD %<tR", wrp.getDate()));
            holder.reportNumAndName.setText(String.format("#%s %s", wrp.getReportNumber(),
                    wrp.getName()));
            holder.reportLocation.setText(wrp.getLocation().toString());
            holder.reportConditionPPM.setText(wrp.getCondition() + ", " + wrp.getVirusPPM() + ", "
                    + wrp.getContaminantPPM());
        }

        @Override
        public int getItemCount() {
            return reports.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.purity_list_content, parent, false);
            return new ViewHolder(view);
        }
    }
}
