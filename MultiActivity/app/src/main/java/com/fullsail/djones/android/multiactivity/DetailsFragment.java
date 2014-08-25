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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class DetailsFragment extends Fragment {

    private final String TAG = "DetailsFragment";

    private DetailListener mListener;

    public interface DetailListener{
        public Contact getContact();
        public int getDelete();
        public void deleteContact();
    }


    public DetailsFragment() {
        // Required empty public constructor
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity instanceof DetailListener) {
            mListener = (DetailListener) activity;

        } else {
            throw new IllegalArgumentException("Containing activity must implement DetailListener interface.");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        final TextView first = (TextView) getView().findViewById(R.id.firstName);
        first.setText(mListener.getContact().getFirstName());
        final TextView last = (TextView) getView().findViewById(R.id.lastName);
        last.setText(mListener.getContact().getLastName());
        final TextView phone = (TextView) getView().findViewById(R.id.phoneNum);
        phone.setText(mListener.getContact().getPhoneNumber());

        if (mListener.getDelete() > 0) {
            Button deleteButton = (Button) getView().findViewById(R.id.deleteButton);
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.deleteContact();
                }
            });
        }

        Button shareButton = (Button) getView().findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Contact:/n" + first.getText().toString() +
                    " " + last.getText().toString() + "/n" + phone.getText().toString());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share data with..."));
            }
        });
    }

}
