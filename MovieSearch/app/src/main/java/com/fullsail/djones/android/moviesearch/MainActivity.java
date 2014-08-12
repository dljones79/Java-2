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
    Boolean isConnected = false;    // This is to test connection
    ConnectionDetection detector;   // ConnectionDetection Class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            MasterFragment frag = MasterFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.container1, frag, MasterFragment.TAG).commit();
        }
    }

    public void displayMovie(String _text){
        DetailsFragment frag = (DetailsFragment)getFragmentManager().findFragmentByTag(DetailsFragment.TAG);

        if(frag == null){
            frag = DetailsFragment.newInstance(_text);
            getFragmentManager().beginTransaction().replace(R.id.container2, frag, DetailsFragment.TAG).commit();
        } else {
            frag.setDetailsText(_text);
        }
    }
}
