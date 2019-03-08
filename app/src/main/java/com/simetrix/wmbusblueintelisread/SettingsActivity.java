package com.simetrix.wmbusblueintelisread;

import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.simetrix.wmbusblueintelisread.contracts.BlueDevice;

public class SettingsActivity extends AppCompatActivity implements BlueDevicePaireFragment.OnBluethDevicePairFragmentInteractionListener {
    private final String TAG = SettingsActivity.class.getSimpleName();
    public final static int CHOOSE_MBWBLUE_DEVICE = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
//        getSupportFragmentManager().beginTransaction()
//                .add(android.R.id.content, new SettingsFragment(), "settings")
//                .addToBackStack(null)
//                .commit();

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void bluethDevicePairActions() {

    }

    // #defines for identifying shared types between calling functions
    private final static int REQUEST_ENABLE_BT = 1; // used to identify adding bluetooth names
    private final static int MESSAGE_READ = 2; // used in bluetooth handler to identify message update
    private final static int CONNECTING_STATUS = 3; // used in bluetooth handler to identify message status

    // Enter here after user selects "yes" or "no" to enabling radio
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Data){
        // Check which request we're responding to
        if(requestCode == SettingsActivity.CHOOSE_MBWBLUE_DEVICE){
            Log.d(TAG, "request result from CHOOSE_MBWBLUE_DEVICE" + requestCode);
//            String dev = (String) Data.getExtras().get("message_return");
            BlueDevice dev = Data.getExtras().getParcelable("com.simetrix.wmbusblueintelisread.contracts.BlueDevice");
            sendDataToSettingsFrag(dev);
        }
    }


    private void sendDataToSettingsFrag(BlueDevice dev){
        SettingsFragment settingsFrag = (SettingsFragment)getSupportFragmentManager().findFragmentByTag("settings");
        if(settingsFrag != null){
            settingsFrag.initPrefs_MBWBlueDevicesOptions(dev);
        }
    }


}
