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


public class FeaturedFrag extends Fragment {


    public static FeaturedFrag newInstance() {
        FeaturedFrag fragment = new FeaturedFrag();
        return fragment;
    }

    public FeaturedFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_featured, container, false);
    }


}
