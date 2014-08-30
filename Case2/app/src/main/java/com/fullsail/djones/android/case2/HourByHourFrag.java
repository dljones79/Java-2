package com.fullsail.djones.android.case2;


// David Jones
// Java 2 - Week 4
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * A simple {@link Fragment} subclass.
 *
 */
public class HourByHourFrag extends Fragment {


    public static HourByHourFrag newInstance() {
        HourByHourFrag fragment = new HourByHourFrag();
        return fragment;
    } // end of HourByHourFrag newInstance()

    public HourByHourFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hour_by_hour, container, false);
    }


}
