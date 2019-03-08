package com.simetrix.wmbusblueintelisread;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class BluethActivity extends AppCompatActivity implements BlueDevicePaireFragment.OnBluethDevicePairFragmentInteractionListener {
    private final String TAG = BluethActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_layout);
//        getSupportFragmentManager().beginTransaction()
//                .replace(android.R.id.content, new BlueDevicePaireFragment(), "mbwblue_pair")
//                .addToBackStack("mbwblue_pair")
//                .commit();
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

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void bluethDevicePairActions() {

    }
}
