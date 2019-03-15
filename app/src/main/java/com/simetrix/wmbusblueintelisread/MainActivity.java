package com.simetrix.wmbusblueintelisread;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.simetrix.wmbusblueintelisread.InterFragmentCommunication.DefaultInterface;
import com.simetrix.wmbusblueintelisread.base.BaseFragment;
import com.simetrix.wmbusblueintelisread.utils.AppConstants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.net.URI;

public class MainActivity
        extends
            AppCompatActivity
        implements
            DefaultFragment.OnDefaultFragmentInteractionListener,
            BlueDevicePaireFragment.OnBluethDevicePairFragmentInteractionListener,
            StartMenuFragment.OnStartMenuFragmentInteractionListener,
            ReadMeterFragment.OnReadMeterFragmentInteractionListener,
            DefaultInterface,
            MainInterface
{
    private final String TAG = MainActivity.class.getSimpleName();
    public final static int CHOOSE_MBWBLUE_DEVICE = 12;

    private FrameLayout frameContainer;
    FragmentManager fm;
    private BaseFragment selectedFragment;
    private boolean isWarnedToClose = false;
    private short backBtnCliks = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }


        frameContainer = (FrameLayout)findViewById(R.id.frags_container);

        fm = getSupportFragmentManager();

        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Start Read Task", Snackbar.LENGTH_LONG)
//                        .setAction("Import meters to read", new FabActionImportListener())
                        .setAction("Start Read Task", new FabActionReadTaskListener())
//                        .setAction("Export readings", new FabActionExportTaskListener())
                        .show()
                        ;
            }
        });


        fm.beginTransaction()
        .replace(R.id.frags_container, new StartMenuFragment(), "start-frag")
        .addToBackStack(null)
        .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        backBtnCliks = 2;
    }

    @Override
    public void onStartMenuFragmentInteraction(Uri uri) {

    }

    @Override
    public void OnReadMeterFragmentInteraction(Uri uri) {

    }

    public class FabActionImportListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            Toast.makeText(getApplicationContext(), "App action <<Import>>", Toast.LENGTH_SHORT).show();

        }
    }

    public class FabActionReadTaskListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            Toast.makeText(getApplicationContext(), "App action <<Read Task>>", Toast.LENGTH_SHORT).show();

        }
    }

    public class FabActionExportTaskListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            Toast.makeText(getApplicationContext(), "App action <<Export Task>>", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent configIntent = new Intent(this, SettingsActivity.class);
            startActivity(configIntent);
//            fm.beginTransaction()
//                    .replace(R.id.frags_container, new SettingsFragment(), "settings-frag")
//                    .addToBackStack("settings-frag")
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                    .commit();
            return true;
        }
        else if(id == R.id.home)  {
            onBackPressed();
            return true;
        }
        else {
            onBackPressed();
            return true;
        }
        //return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        // Check if selectedFragment is not consuming back press
        if(selectedFragment.onBackPressed()) {
            // If not consumed, handle it.
            handleBackPressInThisActivity();
        }
    }

    /**
     * Will close this Activity if double back pressed within 2000 ms
     */
    private void handleBackPressInThisActivity() {
        if(selectedFragment instanceof StartMenuFragment) {
            --backBtnCliks;

            if(backBtnCliks <= 0){
                isWarnedToClose = true;
            }

            if (isWarnedToClose ) {
                finish();
            } else {
                isWarnedToClose = true;

                Toast.makeText(this, "Activity: Tap again to close application: " + backBtnCliks , Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        isWarnedToClose = false;
                        backBtnCliks = 2;
                    }
                }, 2000);
            }
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void showDefaultFragment(boolean state) {
        if(state){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        }
        else {
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
        }
    }

    @Override
    public void showReadingMeterFragment(boolean state) {
        if(state){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        }
        else {
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void bluethDevicePairActions() {

    }


    @Override
    public void setSelectedFragment(BaseFragment fragment) {
        this.selectedFragment = fragment;
        if(!(selectedFragment instanceof StartMenuFragment)){
            backBtnCliks = 2;
        }
//        if(selectedFragment instanceof DrawerItemBaseFragment) {
//            // If foreground fragment is drawer item, unlock drawer
//            unlockDrawer();
//        } else {
//            // If foreground fragment is not drawer item, lock drawer
//            lockDrawer();
//        }
    }

    @Override
    public void popBackStack() {
        if(!(selectedFragment instanceof StartMenuFragment))
            fm.popBackStackImmediate();
    }

    @Override
    public void popBackStackTillTag(String tag) {
        fm.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void addFragment(BaseFragment fragment, boolean withAnimation) {
        FragmentTransaction ft = fm.beginTransaction();

        if(withAnimation) {
            // TO ENABLE FRAGMENT ANIMATION
            // Format: setCustomAnimations(old_frag_exit, new_frag_enter, old_frag_enter, new_frag_exit);
            //ft.setCustomAnimations(R.anim.fragment_slide_in_left, R.anim.fragment_slide_out_left, R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_right);
        }

        ft.replace(R.id.frags_container, fragment, fragment.getTagText());
        ft.addToBackStack(fragment.getTagText());
        ft.commit();
    }

    @Override
    public void addMultipleFragments(BaseFragment[] fragments) {
        // Initialize a Fragment Transaction.
        FragmentTransaction ft = fm.beginTransaction();

        // Record all steps for the transaction.
        for(int i = 0 ; i < fragments.length ; i++) {
//            ft.setCustomAnimations(R.anim.fragment_slide_in_left, R.anim.fragment_slide_out_left, R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_right);
            ft.replace(R.id.frags_container, fragments[i], fragments[i].getTagText());
        }

        // Add the transaction to backStack with tag of first added fragment
        ft.addToBackStack(fragments[0].getTagText());

        // Commit the transaction.
        ft.commit();
    }

    @Override
    public void showBlueDeviceSettings(String name) {
        addFragment(BlueDevicePaireFragment.newInstance("bluedevice_pair"), false);
    }

    @Override
    public void showDefaultFragments(String name) {
        addFragment(DefaultFragment.newInstance("default_fragment"), false);
    }

    @Override
    public void showStartMenu(String name) {
        addFragment(StartMenuFragment.newInstance("StartMenu_Frag"), false);
    }
}
