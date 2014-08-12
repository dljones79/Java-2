package com.fullsail.djones.android.moviesearch;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static android.R.layout.*;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MasterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MasterFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class MasterFragment extends Fragment {

    public static final String TAG = "MasterFragment.TAG";

    private OnFragmentInteractionListener mListener;

    Boolean isConnected = false;
    ConnectionDetection detector;
    private ArrayList arrList;
    private ListView mListView;
    private ArrayAdapter arrayAdapter;
    private List<String> listContents;
    private Integer length;

    public static MasterFragment newInstance() {
        MasterFragment fragment = new MasterFragment();
        return fragment;
    }

    public MasterFragment() {
        // Required empty public constructor
    }

    public interface OnFragmentInteractionListener {
        public void displayMovie(String string);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_master, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();

        // Create a connection detector
        assert view != null;
        detector = new ConnectionDetection(view.getContext());

        // Check Network status
        isConnected = detector.connected();

        Log.i(TAG, isConnected.toString());

        Button search = (Button) view.findViewById(R.id.searchButton);
        final EditText query = (EditText)view.findViewById(R.id.editText);
        mListView = (ListView) view.findViewById(R.id.listView);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Button Pressed.");

                String queryText = query.getText().toString();

                if (isConnected) {
                    Log.i(TAG, "Connected");

                    try {
                        // Set up url string for API request
                        String baseURL = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?q=";
                        URL queryURL = new URL(baseURL + queryText + "&page_limit=5&page=1&apikey=shw7dfp2wfn754x5ak7gyw4h");
                        Log.i(TAG, queryURL.toString());
                        new GetMovieData().execute(queryURL);
                    } catch (Exception e) {
                        Log.e(TAG, "No data found." + queryText);
                    }
                } else if (!isConnected) {
                    Log.i(TAG, "Not Connected");
                }
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(TAG, "List item clicked.");
                TextView itemSelected = (TextView) view;
                String selectedString = itemSelected.getText().toString();
                Log.i(TAG, selectedString);
                mListener.displayMovie(selectedString);
            }
        });

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof OnFragmentInteractionListener){
            mListener = (OnFragmentInteractionListener)activity;
        } else {
            throw new IllegalArgumentException("Containing activity must implement OnFragmentInteractionLIstener interface.");
        }
        /*
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        */
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    private class GetMovieData extends AsyncTask<URL, Integer, JSONObject> {

        final String TAG = "Async Task";
        View view = getView();
        MasterFragment frag;
        JSONObject convertedData;

        @Override
        protected JSONObject doInBackground(URL... urls) {
            String jsonString = "";

            // Collect the string responses from the API
            for (URL queryString : urls){
                try {
                    URLConnection connection = queryString.openConnection();
                    jsonString = IOUtils.toString(connection.getInputStream());
                    break;
                } catch (Exception e) {
                    Log.e(TAG, "No connection found.");
                }
            }

            Log.i(TAG, "Data Received: " + jsonString);

            // Convert the API String to a JSONObject

            JSONObject apiData;
            JSONArray apiArray;

            try {
                apiData = new JSONObject(jsonString);
                Log.i(TAG, apiData.toString());
            } catch (Exception e) {
                Log.e(TAG, "Can't convert API Response to JSON");
                apiData = null;
            }

            try {
                /*
                apiData = (apiData != null) ? apiData.getJSONArray("movies").getJSONObject(0) : null;
                Log.i(TAG, "Movies: " + apiData.toString());
                */

                apiArray = (apiData != null) ? apiData.getJSONArray("movies") : null;
                Log.d("apiArray", apiArray.toString());
                apiData = (apiData != null) ? apiData.getJSONArray("movies").getJSONObject(0) : null;
                Log.i(TAG, "Checking" + apiData.toString());

                listContents = new ArrayList<String>(apiArray.length());
                length = apiArray.length();

                for (int i = 0; i < length; i++){
                    listContents.add(apiArray.getJSONObject(i).getString("title"));
                }
                Log.i(TAG, listContents.toString());

            } catch (Exception e) {
                Log.e(TAG, "Failure to parse data.");
            }

            return apiData;
        } // End of DoInBackground

        protected void onPostExecute(JSONObject apiData){
            Log.i(TAG, "onPostExecute working.");
            popListView(listContents);

            EditText query = (EditText)view.findViewById(R.id.editText);
            query.setText("");


        }
    } // End of GetMovieData


    private void popListView(List currentList){
        Log.i(TAG, "popListView working.");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, currentList);
        mListView.setAdapter(arrayAdapter);

    }
}


