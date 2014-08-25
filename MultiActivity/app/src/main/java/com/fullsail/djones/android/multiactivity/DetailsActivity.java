package com.fullsail.djones.android.multiactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class DetailsActivity extends Activity implements DetailsFragment.DetailListener{

    private final String TAG = "DetailsActivity";

    private Contact mContact;
    private int mDelete;

    public static final String CONTACTEXTRA = "com.fullsail.djones.android.multiactivity.Contact";
    public static final String DELETEEXTRA = "com.fullsail.djones.android.multiactivity.Delete";
    public static final String WILLDELETE = "com.fullsail.djones.android.multiactivity.WillDelete";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailsFragment())
                    .commit();
        }


        Intent detailIntent = getIntent();
        if (detailIntent != null){
            mContact = (Contact) detailIntent.getSerializableExtra(CONTACTEXTRA);
            mDelete = detailIntent.getIntExtra(DELETEEXTRA, 0);
        }
    }

    @Override
    public Contact getContact() {
        return mContact;
    }

    @Override
    public int getDelete() {
        return mDelete;
    }

    @Override
    public void deleteContact() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(MainActivity.DELETECONTACTEXTRA, mDelete);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
