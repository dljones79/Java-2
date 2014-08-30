package com.fullsail.djones.android.case1;



import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class DetailsFragment extends Fragment {

    private String mText;
    private static String TAG = "Details Frag.";

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        mText = getTag();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        Log.i(TAG, mText);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        TextView pitcher = (TextView)getView().findViewById(R.id.pitcherText);
        //TextView pitcher = new TextView (getActivity().findViewById(R.id.pitcherText));
        TextView catcher = (TextView) getView().findViewById(R.id.cathcerText);
        TextView first = (TextView) getView().findViewById(R.id.firstBaseText);
        TextView second = (TextView) getView().findViewById(R.id.secondBaseText);
        TextView third = (TextView) getView().findViewById(R.id.thirdBaseText);
        TextView shortstop = (TextView) getView().findViewById(R.id.shortText);
        TextView left = (TextView) getView().findViewById(R.id.leftText);
        TextView center = (TextView) getView().findViewById(R.id.centerText);
        TextView right = (TextView) getView().findViewById(R.id.rightText);
        TextView teamName = (TextView) getView().findViewById(R.id.teamNameText);

        String dBacks = "Diamondbacks";

        if (mText != null) {
            teamName.setText(mText);
        }

        String test = "test";

        if (mText.length() == 5){
            pitcher.setText("Julius Caesar");
            catcher.setText("Marv Albert");
            first.setText("Al Pacino");
            second.setText("James Brown");
            third.setText("Peter Parker");
            shortstop.setText("Rob Lowe");
            left.setText("Duke Luke");
            center.setText("Moses Malone");
            right.setText("Barry Pepper");
        } else if (mText.length() == 12){
            pitcher.setText("Mark McGuire");
            catcher.setText("Yadier Molina");
            first.setText("Albert Pujols");
            second.setText("Jackie Robinson");
            third.setText("Mike Schmidt");
            shortstop.setText("Ozzie Smith");
            left.setText("Barry Bonds");
            center.setText("Ty Cobb");
            right.setText("Hank Aaron");
        } else if (mText.length() == 7){
            pitcher.setText("Tom");
            catcher.setText("Bob");
            first.setText("Dilbert");
            second.setText("Foo");
            third.setText("Will");
            shortstop.setText("Bill");
            left.setText("Dill");
            center.setText("Toto");
            right.setText("Jojo");
        } else if (mText.length() == 6){
            pitcher.setText("A");
            catcher.setText("B");
            first.setText("C");
            second.setText("D");
            third.setText("E");
            shortstop.setText("F");
            left.setText("G");
            center.setText("H");
            right.setText("I");
        } else if (mText.length() == 18){
            pitcher.setText("George 1");
            catcher.setText("George 2");
            first.setText("George 3");
            second.setText("George 4");
            third.setText("George 5");
            shortstop.setText("George 6");
            left.setText("George 7");
            center.setText("George 8");
            right.setText("George 9");
        } else if (mText.length() == 8){
            pitcher.setText("Running Bull");
            catcher.setText("Hercules");
            first.setText("Conan");
            second.setText("Tonto");
            third.setText("Groo");
            shortstop.setText("Jason");
            left.setText("Maximus");
            center.setText("Leonidas");
            right.setText("Thor");
        } else {
            pitcher.setText("Nothing");
        }

    }

}
