package com.fullsail.djones.android.multi_a;



import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class AddFragment extends Fragment {

    public static final String TAG = "AddFragment.TAG";
    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
        return fragment;
    } // end of AddFragment newInstance()

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
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
            }
        });
    }


}
