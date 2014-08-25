package com.fullsail.djones.android.multiactivity;



import android.app.Activity;
import android.content.Intent;
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
    } // end of ListFragment newInstance()

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        View view = getView();

        final Contact contact = new Contact();
        final EditText first = (EditText) view.findViewById(R.id.fNameText);
        final EditText last = (EditText) view.findViewById(R.id.lNameText);
        final EditText phone = (EditText) view.findViewById(R.id.pNumText);

        Button saveButton = (Button) view.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fName = first.getText().toString();
                String lName = last.getText().toString();
                String pNum = phone.getText().toString();

                contact.setFirstName(fName);
                contact.setLastName(lName);
                contact.setPhoneNumber(pNum);

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("firstName", contact.getFirstName());
                intent.putExtra("lastName", contact.getLastName());
                intent.putExtra("phoneNumber", contact.getPhoneNumber());
                startActivity(intent);

            }
        });
    }
}
