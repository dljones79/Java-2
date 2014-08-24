// David Jones
// Java 2 - 1408
// Week 3 Multi Activity App
// Full Sail University

package com.fullsail.djones.android.multi_a;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;


public class ListActivity extends Activity implements AddFragment.OnSaveListener{

    private Contact contact;
    private final static String TAG = "List Activity";
    public static final int DELETEREQUEST = 1;
    public static final String DELETECONTACTEXTRA = "com.fullsail.djones.android.multi_a.Delete";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if (savedInstanceState == null) {
            ListFragment frag = ListFragment.newInstance();
            frag.setContactObject(contact);
            getFragmentManager().beginTransaction().replace(R.id.container1, frag, ListFragment.TAG).commit();
        }


    } // end of onCreate

    public void saveContact(Contact savedContact){
        ListFragment frag = (ListFragment)getFragmentManager().findFragmentByTag(ListFragment.TAG);

        if (frag == null) {
            frag = ListFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.container1, frag, ListFragment.TAG).commit();
        } else {
            frag.setContactObject(savedContact);
        }

        contact = savedContact;
        Log.i(TAG, savedContact.toString());
    }
} // end of ListActivity
