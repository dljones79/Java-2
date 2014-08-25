package com.fullsail.djones.android.multiactivity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by David on 8/24/14.
 */
public class ContactArray implements Serializable{
    final String TAG = "ContactArray";

    private ArrayList<Contact> contactsArray;

    public ContactArray(){
        contactsArray = null;
    }

    public ContactArray(ArrayList<Contact> passedContacts){
        contactsArray = passedContacts;
    }

    public ArrayList<Contact> getArray () { return contactsArray; }

    public void setContactsArray(ArrayList<Contact> mContactsArray){ this.contactsArray = mContactsArray;}
}
