// David Jones
// Java 2 - Week 4
package com.fullsail.djones.android.case2;



import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * A simple {@link Fragment} subclass.
 *
 */
public class WeeklyForcastFrag extends Fragment {


    public static WeeklyForcastFrag newInstance() {
        WeeklyForcastFrag fragment = new WeeklyForcastFrag();
        return fragment;
    } // end of WeeklyForcastFrag newInstance()

    public WeeklyForcastFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weekly_forcast, container, false);
    }


}
