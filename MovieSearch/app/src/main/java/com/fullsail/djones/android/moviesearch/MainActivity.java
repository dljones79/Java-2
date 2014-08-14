// David Jones
// Java 2 1408
// Full Sail University

package com.fullsail.djones.android.moviesearch;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLConnection;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends Activity implements MasterFragment.OnFragmentInteractionListener {

    final String TAG = "Connected App";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            MasterFragment frag = MasterFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.container1, frag, MasterFragment.TAG).commit();
        }

        // Set up a click listener for the settings button
        // Load the PrefFragment
        Button settings = (Button) findViewById(R.id.settingsButton);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefFragment pFrag = PrefFragment.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.container2, pFrag, PrefFragment.TAG).commit();
            }
        });
    }

    // Custom method to display the DetailsFragment with passed data
    public void displayMovie(String _text, JSONObject _object){
        DetailsFragment frag = (DetailsFragment)getFragmentManager().findFragmentByTag(DetailsFragment.TAG);

        if(frag == null){
            frag = DetailsFragment.newInstance(_text, _object);
            getFragmentManager().beginTransaction().replace(R.id.container2, frag, DetailsFragment.TAG).commit();
        } else {
            frag.setDetailsText(_text);
            frag.setMovieObject(_object);
        }
    }

}
