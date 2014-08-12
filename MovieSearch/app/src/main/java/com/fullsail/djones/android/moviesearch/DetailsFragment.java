package com.fullsail.djones.android.moviesearch;



import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class DetailsFragment extends Fragment {

    public static final String TAG = "DetailsFragment.TAG";

    private static final String ARG_Text = "DetailsFragment.ARG_TEXT";

    public static DetailsFragment newInstance(String _text) {
        DetailsFragment frag = new DetailsFragment();

        Bundle args = new Bundle();
        args.putString(ARG_Text, _text);
        frag.setArguments(args);

        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        if(args != null && args.containsKey(ARG_Text)) {
            setDetailsText(args.getString(ARG_Text));
        }
    }


    public void setDetailsText(String _text) {
        TextView textView = (TextView)getView().findViewById(R.id.textView);
        textView.setText(_text);
    }

}
