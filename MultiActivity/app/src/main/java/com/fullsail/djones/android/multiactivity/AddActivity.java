package com.fullsail.djones.android.multiactivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class AddActivity extends Activity {

    public static final String TAG = "AddActivity.TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        if (savedInstanceState == null) {
            AddFragment frag = AddFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.container1, frag, AddFragment.TAG).commit();
        }
    }

}
