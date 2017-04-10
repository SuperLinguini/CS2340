package tech.milind.cleanwatercrowdsourcing.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tech.milind.cleanwatercrowdsourcing.R;

/**
 * Created by SuperLinguini on 3/5/2017.
 */

public class FragmentAdmin extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }
}
