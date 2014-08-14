// David Jones
// Java 2 - 1408
// Full Sail University

package com.fullsail.djones.android.moviesearch;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by David on 8/13/14.
 */
public class MovieObj implements Serializable {
    final String TAG = "Movie Class";
    private static final long serialVersionUID = 4738492837482849392L;

    private JSONObject movieObj;

    public MovieObj(){}

    public MovieObj(JSONObject obj){
        movieObj = obj;
    }

    public JSONObject getMovieObj() { return movieObj; }

    public void setMovieObj(JSONObject obj) { this.movieObj = obj;}
}
