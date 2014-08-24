package com.fullsail.djones.android.multi_a;



import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class AddFragment extends Fragment {

    public static final String TAG = "AddFragment.TAG";
    private OnSaveListener onSave;

    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
        return fragment;
    } // end of AddFragment newInstance()

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof OnSaveListener) {
            onSave = (OnSaveListener) activity;
        } else {
            throw new IllegalArgumentException("Containing activity must implement OnSaveListener interface.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    public interface OnSaveListener {
        public void saveContact(Contact savedContact);
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        View view = getView();

        Button save = (Button) view.findViewById(R.id.saveButton);

        final Contact contact = new Contact();
        final EditText first = (EditText) view.findViewById(R.id.firstNameText);
        final EditText last = (EditText) view.findViewById(R.id.lastNameText);
        final EditText phone = (EditText) view.findViewById(R.id.phoneNumberText);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fName = first.getText().toString();
                String lName = last.getText().toString();
                String pNum = phone.getText().toString();

                contact.setFirstName(fName);
                contact.setLastName(lName);
                contact.setPhoneNumber(pNum);

                Log.i("Contact:", contact.toString());
                onSave.saveContact(contact);

                removeFrag();
            }
        });
    }

    public void removeFrag() {
        FragmentManager fMan = getFragmentManager();
        AddFragment aFrag = (AddFragment)fMan.findFragmentByTag(AddFragment.TAG);

        if (aFrag == null){

        } else {
            FragmentTransaction fTrans = fMan.beginTransaction();
            fTrans.remove(aFrag);
            fTrans.commit();
        }
    }

}
