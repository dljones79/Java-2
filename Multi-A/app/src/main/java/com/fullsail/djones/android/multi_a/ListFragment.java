// David Jones
// Java 2 - 1408
// Week 3 Multi Activity App
// Full Sail University

package com.fullsail.djones.android.multi_a;



import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ListFragment extends Fragment {

    public static final String TAG = "ListFragment.TAG";
    public static final String ARG_Text = "ListFragment.ARG_TEXT";
    private ArrayList<Contact> contacts;
    private static Contact passedContact = null;
    private ContactListener mListener;

    public interface ContactListener {
        public void viewContact(int position);
        public void deleteContact(int position);
        public ArrayList<Contact> getContacts();
    }

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    } // end of ListFragment newInstance()


    public ListFragment() {
        // Required empty public constructor
    }


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

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Button Pressed.");

                AddFragment frag = (AddFragment)getFragmentManager().findFragmentByTag(AddFragment.TAG);

                frag = AddFragment.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.container1, frag, AddFragment.TAG).commit();
            }
        });

        if (passedContact != null){
            contacts.add(passedContact);

            ListView contactListView = (ListView) getView().findViewById(R.id.contactList);
            ContactAdapter contactAdapter = new ContactAdapter(getActivity(), contacts);
            contactListView.setAdapter(contactAdapter);
        }

    } // end onActivityCreated

    public void setContactObject(Contact contact){
        passedContact = contact;
    }
}
