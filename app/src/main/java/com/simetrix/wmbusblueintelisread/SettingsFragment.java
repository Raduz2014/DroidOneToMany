package com.simetrix.wmbusblueintelisread;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.simetrix.wmbusblueintelisread.contracts.BlueDevice;
import com.simetrix.wmbusblueintelisread.utils.AppConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.SwitchPreference;
import androidx.preference.PreferenceManager;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;

import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = SettingsFragment.class.getSimpleName();

    public static final String BluethDev = "PairedBluethDev";

    public OnSelectionOf_MBWBluePairedDevice mCallback;

    public interface OnSelectionOf_MBWBluePairedDevice {
        void onSelectMBWBlueDevice(BlueDevice device);
    }


    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"Settings Frag onCreate");

        if (getArguments() != null) {
            int mParam1 = getArguments().getInt(BluethDev);
            Log.i(TAG,"Message from BlueDeviceFrag>>" + mParam1);
        }
    }

    public boolean savePairedBlueDeviceArray(BlueDevice d)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        SharedPreferences.Editor mEdit1 = sp.edit();


        int savedNo = 0;
        if(sp.contains("Saved_device"))
            savedNo = sp.getInt("Saved_device",0);

        savedNo = (savedNo == 0) ? 1 : ++savedNo;
        mEdit1.putInt("Saved_device", savedNo);
        mEdit1.putString("PairedBlueDev_" + String.valueOf(savedNo), d.getDeviceName());
        mEdit1.putString("PairedBlueDevMac_" + String.valueOf(savedNo), d.getDevMac());

        return mEdit1.commit();
    }

    public List<String> loadPairedBlueDeviceArray(Context mContext)
    {
        SharedPreferences mSharedPreference1 =   PreferenceManager.getDefaultSharedPreferences(mContext);
        int size = mSharedPreference1.getInt("Saved_device", 0);

        List<String> entrys = new ArrayList<>();
        for(int i=1;i<=size;i++)
        {
            String v = mSharedPreference1.getString("PairedBlueDev_" + i, null);
            entrys.add(v);
        }

        return entrys;
    }

    public List<String> loadPairedBlueDeviceValuesArray(Context mContext)
    {
        SharedPreferences mSharedPreference1 =   PreferenceManager.getDefaultSharedPreferences(mContext);
        int size = mSharedPreference1.getInt("Saved_device", 0);

        List<String> entrys = new ArrayList<>();
        for(int i=1;i<=size;i++)
        {
            String v = mSharedPreference1.getString("PairedBlueDevMac_" + i, null);
            entrys.add(v);
        }

        return entrys;
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        //add xml
        addPreferencesFromResource(R.xml.settings_prefs);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        onSharedPreferenceChanged(sharedPreferences, getString(R.string.movies_categories_key));
        onSharedPreferenceChanged(sharedPreferences, "list_preference_mbwbluedevice");

        final ListPreference listPreference = (ListPreference) findPreference("list_preference_mbwbluedevice");

        // THIS IS REQUIRED IF YOU DON'T HAVE 'entries' and 'entryValues' in your XML
        setListPreferenceData(listPreference);

//        listPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
////                setListPreferenceData(listPreference);
//                return false;
//            }
//        });

        final Preference p = (Preference) findPreference("paire_mbwblue_device");
        p.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                openBluePairedDeviceFragment();
                return false;
            }
        });

        final Preference mbwblue_pref = (Preference) findPreference("choose_mbwblue_devices");
//        mbwblue_pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
////                Intent intent = new Intent(getActivity(), BluethActivity.class);
////                intent.putExtra("message", "This message comes from PassingDataSourceActivity's second button");
////                getActivity().startActivityForResult(intent, SettingsActivity.CHOOSE_MBWBLUE_DEVICE);
//                openBluePairedDeviceFragment();
//                return true;
//            }
//        });

    }

    private void setListPreferenceData(ListPreference lp) {
        CharSequence[] entries = loadPairedBlueDeviceArray(getContext()).toArray(new CharSequence[0]);
        CharSequence[] entryValues = loadPairedBlueDeviceValuesArray(getContext()).toArray(new CharSequence[0]);
        lp.setEntries(entries);
        lp.setEntryValues(entryValues);
    }

    public void initPrefs_MBWBlueDevicesOptions(BlueDevice device){
        Log.i(TAG,"Message from SettingsActivity>>" + device);
        PreferenceCategory targetCategory = (PreferenceCategory) findPreference("TARGET_BLUE_DEVICE");

        final ListPreference lp = (ListPreference) findPreference("list_preference_mbwbluedevice");

        List<CharSequence> entries = new ArrayList<>(Arrays.asList(lp.getEntries()));

        List<CharSequence> entries_values =  new ArrayList<>(Arrays.asList(lp.getEntryValues()));

        if(entries.size() == 0){
            entries.add(new String(device.getDeviceName()));
            entries_values.add(device.getDevMac());
//
        }
        else {
            if(entries.indexOf(device.getDeviceName()) == -1){
                entries.add(new String(device.getDeviceName()));
                entries_values.add(device.getDevMac());
            }
        }

        CharSequence[] ccentries = entries.toArray(new CharSequence[0]);
        lp.setEntries(ccentries);
        CharSequence[] ccentries_vals =  entries_values.toArray(new CharSequence[0]);
        lp.setEntryValues(ccentries_vals);
        lp.setValue((String) ccentries_vals[0]);
        lp.setSummary(ccentries[0]);
        lp.setPersistent(true);

//        targetCategory.addPreference(lp);

    }



    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"Settings Frag onResume");
        //unregister the preferenceChange listener
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(sharedPreferences.getString(key, ""));
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
        else if(preference instanceof SwitchPreference){
            SwitchPreference switchPref = (SwitchPreference)preference;

//            switch (key){
//                case "mbusw_blue_device":
//                    break;
//                case "bluetooth_dev":
////                    if(switchPref.isChecked()){
////                        preference.setSummary(switchPref.getSwitchTextOn());
////                        openBluePairedDeviceFragment();
////                    }
////                    else {
////                        preference.setSummary(switchPref.getSwitchTextOff());
////                    }
//                    break;
//            }


            if(switchPref.isChecked()){
                preference.setSummary(switchPref.getSwitchTextOn());
            }
            else {
                preference.setSummary(switchPref.getSwitchTextOff());
            }
        }
        else {
//            preference.setSummary(sharedPreferences.getString(key, ""));
        }
    }

    private void openBluePairedDeviceFragment(){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        BlueDevicePaireFragment bluefrag = new BlueDevicePaireFragment();
        ft.addToBackStack("settings-frag");
        ft.hide(SettingsFragment.this);
        ft.add(R.id.frags_container, bluefrag, "bluedevice-frag");
        ft.commit();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == AppConstants.FRAGMENT_CODE){
//                int addID = data.getIntExtra("addressID", 0);
//                String addressLine= (String)data.getStringExtra("addressLine");
                BlueDevice bluedev = data.getExtras().getParcelable("com.simetrix.wmbusblueintelisread.contracts.BlueDevice");

                savePairedBlueDeviceArray(bluedev);

//                initPrefs_MBWBlueDevicesOptions(bluedev);
            }
        }
    }



    private void addChooseWMBlueDeviceListener(){

    }

    @Override
    public void onPause() {
        super.onPause();
        //unregister the preference change listener
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }



}
