// David Jones
// Java 2 - 1408
// Full Sail University

package com.fullsail.djones.android.moviesearch;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by David on 8/13/14.
 */
public class MovieObj implements Serializable {
    final String TAG = "Movie Class";
    private static final long serialVersionUID = 4738492837482849392L;

    private String mTitle;
    private String mRating;
    private String mRuntime;
    private String mYear;

    public MovieObj(){}

    public MovieObj(JSONArray jArray, int i){
       try {
           mTitle = jArray.getJSONObject(i).getString("title");
           mYear = jArray.getJSONObject(i).getString("year");
           mRating = jArray.getJSONObject(i).getString("mpaa_rating");
           mRuntime = jArray.getJSONObject(i).getString("runtime");
       } catch (Exception e) {
           Log.e(TAG, "Failure");
       }
    }

    public String getTitle() { return mTitle; }

    public void setTitle(String mTitle) { this.mTitle = mTitle; }

    public String getYear() { return mYear; }

    public void setYear(String mYear) { this.mYear = mYear; }

    public String getRating() { return mRating; }

    public void setRating(String mRating) { this.mRating = mRating; }

    public String getRuntime() { return mRuntime; }

    public void setRuntime(String mRuntime) { this.mRuntime = mRuntime; }
}
