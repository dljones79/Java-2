// David Jones
// Java 2 - 1408
// Week 3 Project - Multi Activity Application
// Full Sail University

package com.fullsail.djones.android.multiactivity;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ListFragment extends Fragment {

    public static final String TAG = "ListFragment.TAG";
    private ArrayList<Contact> passedContacts;
    ArrayAdapter<String> mOSAdapter;
    private ContactListener mListener;
    private static final int REQUEST_CODE = 2;

    public static final String SAVECONTACTEXTRA = "com.fullsail.djones.android.multiactivity.Save";

    public ListFragment() {
        // Required empty public constructor
    }

    public interface ContactListener{
        public void viewContact(int position);
        public void deleteContact(int position);
        public ArrayList<Contact> getContacts();
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);

        if (activity instanceof ContactListener){
            mListener = (ContactListener) activity;
        } else {
            throw new IllegalArgumentException("Containing activity must implement ContactListener interface.");
        }
    }

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    } // end of ListFragment newInstance()

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        View view = getView();

        Button addButton = (Button) view.findViewById(R.id.addButton);

        ListView listView = (ListView) getView().findViewById(R.id.contactList);
        ContactAdapter contactAdapter = new ContactAdapter(getActivity(), mListener.getContacts());
        listView.setAdapter(contactAdapter);

        //popListView(passedContacts);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Button Pressed.");

                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        ListView contactListView = (ListView) getView().findViewById(R.id.contactList);
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListener.viewContact(i);
            }
        });

        contactListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long l) {
                mListener.deleteContact(i);
                Log.i(TAG, "Long Click.");
                return true;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == getActivity().RESULT_OK && requestCode == REQUEST_CODE){
            if (data.hasExtra("returnKey")){
                passedContacts.add((Contact) data.getSerializableExtra("returnKey"));
                com.fullsail.djones.android.multiactivity.ListFragment lf = (com.fullsail.djones.android.multiactivity.ListFragment) getFragmentManager().findFragmentById(R.id.mainContainer);
                lf.updateListData();
            }
        }
    }

    public void setContacts (ArrayList<Contact> contacts) {
        passedContacts = contacts;
    }

    /*
    public void popListView(final ArrayList<Contact> contacts) {
        ListView contactsList = (ListView) getActivity().findViewById(R.id.contactList);

        Iterator iterator = (Iterator) contacts.iterator();

        ArrayList<String> names = new ArrayList<String>();

        while (iterator.hasNext()) {
            Contact contact = (Contact) iterator.next();
            names.add(contact.getFirstName());
        }

        mOSAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, names);
        contactsList.setAdapter(mOSAdapter);
    }
    */



    public void updateListData(){
        ListView contactList = (ListView) getView().findViewById(R.id.contactList);
        BaseAdapter contactAdapter = (BaseAdapter) contactList.getAdapter();
        contactAdapter.notifyDataSetChanged();
    }
}
