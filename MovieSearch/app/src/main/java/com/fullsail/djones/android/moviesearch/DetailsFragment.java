package com.fullsail.djones.android.moviesearch;



import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class DetailsFragment extends Fragment {

    public static final String TAG = "DetailsFragment.TAG";

    private static final String ARG_Text = "DetailsFragment.ARG_TEXT";

    private static JSONObject passedMovieObject = null;

    public static DetailsFragment newInstance(String _text, JSONObject _object) {
        DetailsFragment frag = new DetailsFragment();

        Bundle args = new Bundle();
        args.putString(ARG_Text, _text);
        frag.setArguments(args);
        frag.setMovieObject(_object);

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
        TextView titleText = (TextView)getView().findViewById(R.id.titleText);
        titleText.setText(_text);

        TextView yearText = (TextView)getView().findViewById(R.id.yearText);
        TextView ratingText = (TextView)getView().findViewById(R.id.ratingText);
        TextView runtimeText = (TextView)getView().findViewById(R.id.runtimeText);

        Log.i(TAG, passedMovieObject.toString());

        try {
            yearText.setText(passedMovieObject.getString("year"));
            ratingText.setText(passedMovieObject.getString("mpaa_rating"));
            runtimeText.setText((passedMovieObject.getString("runtime")) + " minutes.");
        } catch (Exception e) {
            Log.e(TAG, "Can't pull strings.");
        }
    }

    public void setMovieObject(JSONObject movieObject) {
        passedMovieObject = movieObject;
    }


}
