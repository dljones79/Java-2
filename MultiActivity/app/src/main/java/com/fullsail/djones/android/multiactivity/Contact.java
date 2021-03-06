// David Jones
// Java 2 - 1408
// Week 3 Project - Multi Activity Application
// Full Sail University

package com.fullsail.djones.android.multiactivity;

import java.io.Serializable;

/**
 * Created by David on 8/20/14.
 */
public class Contact implements Serializable {

    final String TAG = "Contact Class";

    private String mFirstName;
    private String mLastName;
    private String mPhoneNumber;

    public Contact(){
        mFirstName = "";
        mLastName = "";
        mPhoneNumber ="";
    }

    public Contact(String first, String last, String phone){
        mFirstName = first;
        mLastName = last;
        mPhoneNumber = phone;
    }

    public String getName() { return mFirstName + " " + mLastName; }

    public String getFirstName() { return mFirstName; }

    public void setFirstName(String mFirstName) { this.mFirstName = mFirstName; }

    public String getLastName() { return mLastName; }

    public void setLastName(String mLastName) { this.mLastName = mLastName; }

    public String getPhoneNumber() { return mPhoneNumber; }

    public void setPhoneNumber(String mPhoneNumber) { this.mPhoneNumber = mPhoneNumber; }
}
