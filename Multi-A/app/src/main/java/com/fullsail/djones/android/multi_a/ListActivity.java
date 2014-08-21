// David Jones
// Java 2 - 1408
// Week 3 Multi Activity App
// Full Sail University

package com.fullsail.djones.android.multi_a;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if (savedInstanceState == null) {
            ListFragment frag = ListFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.container1, frag, ListFragment.TAG).commit();
        }


    } // end of onCreate

} // end of ListActivity
