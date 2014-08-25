package com.fullsail.djones.android.multiactivity;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements com.fullsail.djones.android.multiactivity.ListFragment.ContactListener{
    public Contact contact = new Contact();
    private ArrayList<Contact> contacts = new ArrayList<Contact>();
    private final static String TAG = "MainActivity.TAG";
    static final String STATE_ARRAY = "contacts";

    public static final int DELETEREQUEST = 1;
    public static final String DELETECONTACTEXTRA = "com.fullsail.djones.android.multiactivity.Delete";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            contacts = (ArrayList<Contact>) savedInstanceState.getSerializable(STATE_ARRAY);
            Log.i(TAG, "Loaded data from saved instance state.");
        } catch (Exception e) {
            Log.e(TAG, "Noting loaded from saved Instance State.");
        }

        Intent intent = getIntent();
        if (intent != null) {
            contact.setFirstName(intent.getStringExtra("firstName"));
            contact.setLastName(intent.getStringExtra("lastName"));
            contact.setPhoneNumber(intent.getStringExtra("phoneNumber"));

            Log.i(TAG, contacts.toString());

            try {
                Log.i(TAG, intent.getStringExtra("firstName"));
                contacts.add(contact);
            } catch (Exception e){
                e.printStackTrace();
            }

            Log.i(TAG, contact.toString());
        } else {
            Log.i(TAG, "No data received.");
        }

        if (savedInstanceState == null) {
            Log.i("Test", "savedInstanceState = null");
            com.fullsail.djones.android.multiactivity.ListFragment frag = com.fullsail.djones.android.multiactivity.ListFragment.newInstance();
            try {
                frag.setContacts(contacts);
            } catch (Exception e) {
                e.printStackTrace();
            }

            getFragmentManager().beginTransaction().replace(R.id.mainContainer, frag, com.fullsail.djones.android.multiactivity.ListFragment.TAG).commit();
        } else {
            Log.i("Test", "savedInstanceState != null");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(STATE_ARRAY, contacts);
        Log.i("Saving: ", "Running onSaveInstanceState.");
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "Running onRestoreInstanceState.");

        contacts = (ArrayList<Contact>) savedInstanceState.getSerializable(STATE_ARRAY);
        Log.i("Contacts from storage: ", contacts.toString());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.i(TAG, "onActivityResult running.");

        if (resultCode == Activity.RESULT_OK && requestCode == DELETEREQUEST){
            Log.i(TAG, "resultCode check passed.");
            contacts.remove(data.getIntExtra(DELETECONTACTEXTRA, 0));
            //ListFragment listFrag = (ListFragment) getFragmentManager().findFragmentByTag(com.fullsail.djones.android.multiactivity.ListFragment.TAG);
            com.fullsail.djones.android.multiactivity.ListFragment lf = (com.fullsail.djones.android.multiactivity.ListFragment) getFragmentManager().findFragmentById(R.id.mainContainer);
            lf.updateListData();
        } else {
            Log.i(TAG, "resultCode check failed.");
        }
    }

    @Override
    public void viewContact(int position) {
        Log.i(TAG, "viewContact!!!!");
        Intent detailIntent = new Intent (this, DetailsActivity.class);
        detailIntent.putExtra(DetailsActivity.WILLDELETE, false);
        detailIntent.putExtra(DetailsActivity.CONTACTEXTRA, contacts.get(position));
        startActivity(detailIntent);
    }

    @Override
    public void deleteContact(int position) {
        Intent deleteIntent = new Intent(this, DetailsActivity.class);
        deleteIntent.putExtra(DetailsActivity.CONTACTEXTRA, contacts.get(position));
        deleteIntent.putExtra(DetailsActivity.DELETEEXTRA, position);
        startActivityForResult(deleteIntent, DELETEREQUEST);
    }

    @Override
    public ArrayList<Contact> getContacts() {
        return contacts;
    }
}
