// David Jones
// Java 2 - 1408
// Week 3 Project - Multi Activity Application
// Full Sail University

package com.fullsail.djones.android.multiactivity;

import android.app.Activity;
import android.app.ListFragment;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements com.fullsail.djones.android.multiactivity.ListFragment.ContactListener{
    public Contact contact = new Contact();
    private ArrayList<Contact> contacts = new ArrayList<Contact>();
    private final static String TAG = "MainActivity.TAG";
    static final String STATE_ARRAY = "contacts";
    public static final String OBJFILENAME = "data.txt";
    private ContactArray cArray = new ContactArray();

    public static final int DELETEREQUEST = 1;
    public static final String DELETECONTACTEXTRA = "com.fullsail.djones.android.multiactivity.Delete";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Attempting to load savedInstanceState here....currently not working as intended.
        try {
            contacts = (ArrayList<Contact>) savedInstanceState.getSerializable(STATE_ARRAY);
            Log.i(TAG, "Loaded data from saved instance state.");
        } catch (Exception e) {
            Log.e(TAG, "Noting loaded from saved Instance State.");
        }

        // This try/catch block is attempting to read from saved file...currently not working as intended.
        try {
            readFromFile(OBJFILENAME);
            Log.i(TAG, "Read from file.");
        } catch (Exception e){
            e.printStackTrace();
            Log.i(TAG, "Nothing to read.");
        }

        // Get intent passed from previous activity
        Intent intent = getIntent();
        if (intent != null) {
            contact.setFirstName(intent.getStringExtra("firstName"));
            contact.setLastName(intent.getStringExtra("lastName"));
            contact.setPhoneNumber(intent.getStringExtra("phoneNumber"));

            Log.i(TAG, contacts.toString());

            try {
                Log.i(TAG, intent.getStringExtra("firstName"));
                contacts.add(contact);
                writeObjectToFile(OBJFILENAME, contacts);
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

    // Save instanceSate
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

    // Method called upon pressing delete button on Details Frag
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

    private void writeObjectToFile(String _filename, ArrayList<Contact> _obj){
        try {
            FileOutputStream fos = this.openFileOutput(_filename, this.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(_obj);

            Log.i(TAG, "Data Saved.");

            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile(String _filename){
        try {
            FileInputStream fin = openFileInput(_filename);
            ObjectInputStream oin = new ObjectInputStream(fin);

            try {
                cArray = (ContactArray)oin.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_add:
                Log.i(TAG, "Add Button Pressed.");
                Button addButton = (Button) this.findViewById(R.id.addButton);
                addButton.performClick();
                break;
        }

        return true;
    }
    // Interface Methods Below

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
