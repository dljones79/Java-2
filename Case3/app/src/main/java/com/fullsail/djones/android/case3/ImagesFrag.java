package com.fullsail.djones.android.case3;



import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ImagesFrag extends Fragment {


    public static ImagesFrag newInstance() {
        ImagesFrag fragment = new ImagesFrag();

        return fragment;
    }

    public ImagesFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_images, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageView one = (ImageView) getView().findViewById(R.id.imageView);
        one.setImageResource(R.drawable.img1);
        ImageView two = (ImageView) getView().findViewById(R.id.imageView2);
        two.setImageResource(R.drawable.img2);
        ImageView three = (ImageView) getView().findViewById(R.id.imageView3);
        three.setImageResource(R.drawable.img3);
        ImageView four = (ImageView) getView().findViewById(R.id.imageView4);
        four.setImageResource(R.drawable.img5);
        ImageView five = (ImageView) getView().findViewById(R.id.imageView5);
        five.setImageResource(R.drawable.img6);
        ImageView six = (ImageView) getView().findViewById(R.id.imageView6);
        six.setImageResource(R.drawable.img7);
        ImageView seven = (ImageView) getView().findViewById(R.id.imageView7);
        seven.setImageResource(R.drawable.img8);
        ImageView eight = (ImageView) getView().findViewById(R.id.imageView8);
        eight.setImageResource(R.drawable.img9);
        ImageView nine = (ImageView) getView().findViewById(R.id.imageView9);
        nine.setImageResource(R.drawable.img4);


    }

}
