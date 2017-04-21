package tech.milind.cleanwatercrowdsourcing.controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.graphics.Color;

import java.util.List;

import tech.milind.cleanwatercrowdsourcing.R;
import tech.milind.cleanwatercrowdsourcing.model.Model;
import tech.milind.cleanwatercrowdsourcing.model.User;

/**
 * Created by whe1996 on 3/5/2017.
 */

public class FragmentAdmin extends Fragment {
    private FragmentAdmin.SimpleUserAdapter mAdapter;
    final private int REQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        View recyclerView = view.findViewById(R.id.user_list);
        setupRecyclerView((RecyclerView) recyclerView);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        int position = data.getIntExtra("position", 0);
        if (requestCode == REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                mAdapter.notifyItemInserted(mAdapter.users.size() - 1);
            } else if (resultCode == EditUserActivity.RESULT_CHANGED) {
                mAdapter.notifyItemChanged(position);
            } else if (resultCode == EditUserActivity.RESULT_REMOVED) {
                mAdapter.users.remove(position);
//                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyDataSetChanged();
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
//        mLayoutManager.setReverseLayout(true);
//        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        Model model = Model.getInstance();
        List<User> users = model.getSecurity().getUserList();
        mAdapter = new FragmentAdmin.SimpleUserAdapter(users);
        recyclerView.setAdapter(mAdapter);
    }

    public class SimpleUserAdapter extends RecyclerView
            .Adapter<FragmentAdmin.SimpleUserAdapter.ViewHolder> {
        final private List<User> users;


        public class ViewHolder extends RecyclerView.ViewHolder {
            final public View mView;
            final public TextView userType;
            final public TextView userName;
            final public TextView userAccountName;
            final public TextView userStatus;
            final public TextView creationDate;

            /**
             * Constructor for ViewHolder that links the Views
             * @param v the view acted on
             */
            public ViewHolder(View v) {
                super(v);
                mView = v;
                userType = (TextView) v.findViewById(R.id.userType);
                userName = (TextView) v.findViewById(R.id.userName);
                userAccountName = (TextView) v.findViewById(R.id.userAccountName);
                userStatus = (TextView) v.findViewById(R.id.userStatus);
                creationDate = (TextView) v.findViewById(R.id.creationDate);
            }
        }

        public SimpleUserAdapter(List<User> list) {
            users = list;
        }

        @Override
        public void onBindViewHolder(final FragmentAdmin.SimpleUserAdapter.ViewHolder holder, final int position) {
            final Model model = Model.getInstance();
            if (users.get(holder.getAdapterPosition()) != null) {
                final User u = users.get(holder.getAdapterPosition());
                holder.userType.setText(u.getUserType().getType().toUpperCase());
                holder.userName.setText(u.getName());
                holder.userAccountName.setText(u.getUsername());
                if (u.isBanned()) {
                    holder.userStatus.setText("Banned");
                    int red = getResources().getColor(R.color.colorAccent);
                    holder.userStatus.setTextColor(red);
                } else {
                    holder.userStatus.setText("Active");
                    int green = getResources().getColor(R.color.green);
                    holder.userStatus.setTextColor(green);
                }
                holder.creationDate.setText(u.getCreationDate().toString());
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), EditUserActivity.class);
                        i.putExtra("username", u.getUsername());
                        i.putExtra("position", holder.getAdapterPosition());
                        startActivityForResult(i, REQUEST);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        @Override
        public FragmentAdmin.SimpleUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_list_content, parent, false);
            return new FragmentAdmin.SimpleUserAdapter.ViewHolder(view);
        }


    }

}
