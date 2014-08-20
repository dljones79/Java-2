// David Jones
// Java 2 - 1408
// Full Sail University

package com.fullsail.djones.android.moviesearch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.text.method.MovementMethod;
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
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private ListView mListView;
    private List<String> listContents;
    private Integer length;
    private JSONObject apiData = null;
    private JSONArray apiArray = null;
    private String pulledData;
    private JSONArray jArray = null;
    private List<MovieObj> objectList;

    public static final String FILENAME = "data.txt";
    public static final String OBJFILENAME = "objects.txt";

    public static MasterFragment newInstance() {
        MasterFragment fragment = new MasterFragment();
        return fragment;
    } // end MasterFragment newInstance()

    public MasterFragment() {
        // Required empty public constructor
    } // end MasterFragment()

    public interface OnFragmentInteractionListener {
        public void displayMovie(String _text, JSONObject _object);
    } // end OnFragmentInteractionListener interface

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_master, container, false);
    } // end onCreateView method

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
                        URL queryURL = new URL(baseURL + queryText + "&page_limit=20&page=1&apikey=shw7dfp2wfn754x5ak7gyw4h");
                        Log.i(TAG, queryURL.toString());
                        new GetMovieData().execute(queryURL);
                    } catch (Exception e) {
                        Log.e(TAG, "No data found." + queryText);
                    }
                } else if (!isConnected) {
                    //Log.i(TAG, "Not Connected");

                    // If device isn't connected to internet...alert will show.
                    showAlert("No Connection", "Attempting to retrieve data from file.");

                    pulledData = pullDataFromFile(FILENAME);

                    if (pulledData != null){
                        //Log.i(TAG, "Data pulled from file." + pulledData);
                        makeToast("Data pulled from file.");

                        try {
                            jArray = new JSONArray(pulledData);

                            listContents = new ArrayList<String>(jArray.length());
                            length = jArray.length();

                            for (int i = 0; i < length; i++){
                                listContents.add(jArray.getJSONObject(i).getString("title"));
                            } // end for loop to add titles to listContents array

                            popListView(listContents);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        //Log.i(TAG, "No stored data.");
                        showAlert("Error", "No Stored Data!");
                    }
                } // end else
            } // end onClick
        }); // end on click listener for search

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                JSONObject selectedObj = null;
                Log.i(TAG, "List item clicked.");
                TextView itemSelected = (TextView) view;
                String selectedString = itemSelected.getText().toString();
                Log.i(TAG, selectedString);
                if (isConnected) {
                    try {
                        selectedObj = apiArray.getJSONObject(i);
                        Log.i(TAG, "Selected Obj: " + selectedObj.toString());
                    } catch (Exception e) {
                        Log.e(TAG, "Can't pull JSONObj");
                    }
                } else if (!isConnected){
                    try {
                        selectedObj = jArray.getJSONObject(i);
                    } catch (Exception e) {
                        Log.e(TAG, "No data to retrieve.");
                    }
                }
                mListener.displayMovie(selectedString, selectedObj);
            } // end onItemClick
        }); // end mListView.setOnItemClickListener
    } // end onActivityCreated

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof OnFragmentInteractionListener){
            mListener = (OnFragmentInteractionListener)activity;
        } else {
            throw new IllegalArgumentException("Containing activity must implement OnFragmentInteractionLIstener interface.");
        } // end else
    } // end onAttach

    private class GetMovieData extends AsyncTask<URL, Integer, JSONObject> {

        final String TAG = "Async Task";
        View view = getView();

        @Override
        protected JSONObject doInBackground(URL... urls) {
            String jsonString = "";
            objectList = new ArrayList<MovieObj>();

            // Collect the string responses from the API
            for (URL queryString : urls){
                try {
                    URLConnection connection = queryString.openConnection();
                    jsonString = IOUtils.toString(connection.getInputStream());
                    break;
                } catch (Exception e) {
                    Log.e(TAG, "No connection found.");
                } // end catch
            } // end for loop

            Log.i(TAG, "Data Received: " + jsonString);

            try {
                apiData = new JSONObject(jsonString);
                Log.i(TAG, "APIData" + apiData.toString());
            } catch (Exception e) {
                Log.e(TAG, "Can't convert API Response to JSON");
                apiData = null;
            }

            // Call custom method to parse json
            parseJSON(apiData);

            // This try/catch block saves an array of custom MovieObj objects
            try {
                for (int i = 0; i < apiArray.length(); i++){
                    Integer count = i;
                    MovieObj movieObj = new MovieObj(apiArray, count);
                    objectList.add(movieObj);
                }
            } catch (Exception e){
                e.printStackTrace();
                Log.i(TAG, "nope");
            }

            Log.i(TAG, "Object List: " + objectList.toString());
            writeObjectToFile(OBJFILENAME, objectList);

            // Save data to file
            String str = apiArray.toString();
            writeDataToFile(FILENAME, str);

            return apiData;
        } // End of DoInBackground

        protected void onPostExecute(JSONObject apiData){
            Log.i(TAG, "onPostExecute working.");
            popListView(listContents);
            EditText query = (EditText)view.findViewById(R.id.editText);
            query.setText("");

        } // end onPostExecute
    } // End of GetMovieData

    private void parseJSON(JSONObject obj){
        MovieObj movie = null;
        try {
            apiArray = (obj != null) ? obj.getJSONArray("movies") : null;
            Log.d("apiArray", apiArray.toString());
            obj = (obj != null) ? obj.getJSONArray("movies").getJSONObject(0) : null;
            Log.i(TAG, "Checking" + obj.toString());

            listContents = new ArrayList<String>(obj.length());
            length = apiArray.length();

            for (int i = 0; i < length; i++){
                listContents.add(apiArray.getJSONObject(i).getString("title"));
            } // end for loop to add titles to listContents array
            Log.i(TAG, listContents.toString());

        } catch (Exception e) {
            Log.e(TAG, "Failure to parse data.");
        } // end catch
    } // end parseJSON method

    // Custom method to populate the listview
    private void popListView(List currentList){
        Log.i(TAG, "popListView working.");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, currentList);
        mListView.setAdapter(arrayAdapter);
    } // end popListView

    private void writeObjectToFile(String _filename, List<MovieObj> _obj){
        try {
            FileOutputStream fos = this.getActivity().openFileOutput(_filename, this.getActivity().MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(_obj);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Custom method to save data
    private void writeDataToFile(String _filename, String _data) {

        try {
            FileOutputStream fos = this.getActivity().openFileOutput(_filename, this.getActivity().MODE_PRIVATE);
            fos.write(_data.getBytes());
            fos.close();
            Log.i(TAG, "Data Saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    } // end of writeDataToFile

    //TODO Figure out how to read these objects back from file
    /*
    private String readObjectFromFile(String _filename) {
        try{
            FileInputStream fin = this.getActivity().openFileInput(_filename);
            ObjectInputStream ois = new ObjectInputStream(fin);

            MovieObj mObj = (MovieObj)ois.readObject();
            ois.close();



        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    */

    // Custom method to load stored data
    private String pullDataFromFile(String _filename){

        try{
            FileInputStream fin = this.getActivity().openFileInput(_filename);
            InputStreamReader inReader = new InputStreamReader(fin);
            BufferedReader reader = new BufferedReader(inReader);

            StringBuffer buffer = new StringBuffer();
            String text;

            while ((text = reader.readLine()) != null){
                buffer.append(text + "\n");
            }
            reader.close();
            return buffer.toString().trim();

        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    } // end pullDataFromFile method

    // custom alert method
    private void showAlert(String title, String message){
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    } // end showAlert method

    // custom toast method
    private void makeToast(String message){
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        toast.show();
    } // end makeToast
} // end MasterFragment


