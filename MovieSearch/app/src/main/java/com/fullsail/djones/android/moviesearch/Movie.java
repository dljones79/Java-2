// David Jones
// Java 2 - 1408
// Full Sail University

package com.fullsail.djones.android.moviesearch;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by David on 8/11/14.
 */
public class Movie {

    final String TAG = "Movie Class";

    private String mTitle;
    private Integer mYear;
    private String mRating;
    private Integer mRuntime;

    public Movie(){}

    public Movie(String title, Integer year, String rating, Integer runtime){
        mTitle = title;
        mYear = year;
        mRating = rating;
        mRuntime = runtime;
    }

    public Movie(JSONObject movieData){
        try{
            mTitle = movieData.getString("title");
            mYear = movieData.getInt("year");
            mRating = movieData.getString("rating");
            mRuntime = movieData.getInt("runtime");
        } catch (Exception e) {
            Log.e(TAG, "Failure");
        }
    }

    public String getTitle() { return mTitle; }

    public void setTitle(String mTitle) { this.mTitle = mTitle; }

    public Integer getYear() { return mYear; }

    public void setYear(Integer mYear) { this.mYear = mYear; }

    public String getRating() { return mRating; }

    public void setRating(String mRating) { this.mRating = mRating; }

    public Integer getRuntime() { return mRuntime; }

    public void setRuntime(Integer mRuntime) { this.mRuntime = mRuntime; }
}
