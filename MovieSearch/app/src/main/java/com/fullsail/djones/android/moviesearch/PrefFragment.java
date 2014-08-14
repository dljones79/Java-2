// David Jones
// Java 2 - 1408
// Full Sail University

package com.fullsail.djones.android.moviesearch;



import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * A simple {@link Fragment} subclass.
 *
 */
public class PrefFragment extends PreferenceFragment {

    public static final String TAG = "PrefFragment.TAG";

    public static PrefFragment newInstance() {
        PrefFragment pFragment = new PrefFragment();
        return pFragment;
    } // end PrefFragment newInstance()

    @Override
    public void onCreate(Bundle _savedInstanceState){
        super.onCreate(_savedInstanceState);

        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public void onActivityCreated(Bundle _savedInstanceState){
        super.onActivityCreated(_savedInstanceState);

        // Click Listener for WIFI Button
        Preference wifiOn = findPreference("WIFI_BUTTON");
        wifiOn.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ConnectivityManager connect = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                connect.setNetworkPreference(ConnectivityManager.TYPE_WIFI);
                return false;
            }
        });

        // Click Listener for Mobile Button
        Preference mobileOn = findPreference("MOBILE_BUTTON");
        mobileOn.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ConnectivityManager connect = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                connect.setNetworkPreference(ConnectivityManager.TYPE_MOBILE);
                return false;
            }
        });

        // Click Listener for Off Button
        Preference dataOff = findPreference("OFF_BUTTON");
        dataOff.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ConnectivityManager connect = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

                WifiManager wifi = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
                wifi.setWifiEnabled(false);

                return false;
            }
        });

        // Click Listener for Close Button
        Preference closeSettings = findPreference("CLOSE_SETTINGS");
        closeSettings.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                removeSettingsFrag();
                return false;
            }
        });

    }

    // Custom method to close the settings fragment
    public void removeSettingsFrag() {
        FragmentManager fMan = getFragmentManager();
        PrefFragment pFrag = (PrefFragment)fMan.findFragmentByTag(PrefFragment.TAG);

        if (pFrag == null) {

        } else {
            FragmentTransaction fTrans = fMan.beginTransaction();
            fTrans.remove(pFrag);
            fTrans.commit();
        }
    } // end removeSettingFrag

}
