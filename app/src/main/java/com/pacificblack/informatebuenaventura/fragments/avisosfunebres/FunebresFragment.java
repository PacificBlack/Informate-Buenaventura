package com.pacificblack.informatebuenaventura.fragments.avisosfunebres;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pacificblack.informatebuenaventura.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FunebresFragment extends Fragment {


    public FunebresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_funebres, container, false);
    }

}
