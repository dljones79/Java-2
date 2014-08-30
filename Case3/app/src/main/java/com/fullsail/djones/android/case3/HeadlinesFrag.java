package com.fullsail.djones.android.case3;



import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * A simple {@link Fragment} subclass.
 *
 */
public class HeadlinesFrag extends Fragment {


    public static HeadlinesFrag newInstance() {
        HeadlinesFrag fragment = new HeadlinesFrag();
        return fragment;
    }

    public HeadlinesFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_headlines, container, false);
    }


}
