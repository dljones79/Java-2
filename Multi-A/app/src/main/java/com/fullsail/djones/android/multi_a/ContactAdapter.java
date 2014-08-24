package com.fullsail.djones.android.multi_a;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by David on 8/24/14.
 */
public class ContactAdapter extends BaseAdapter{
    private static final long CONSTANT_ID = 0x01000000;

    Context mContext;
    ArrayList<Contact> mContacts;

    public ContactAdapter(Context context, ArrayList<Contact> contacts){
        mContext = context;
        mContacts = contacts;
    }

    @Override
    public int getCount() { return mContacts.size(); }

    @Override
    public Contact getItem(int position) { return mContacts.get(position); }

    @Override
    public long getItemId(int position) { return CONSTANT_ID + position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.contact_list_item, parent, false);
        }

        Contact contact = getItem(position);
        TextView contactNameView = (TextView) convertView.findViewById(R.id.contactName);
        contactNameView.setText(contact.getFirstName());

        return convertView;
    }
}
