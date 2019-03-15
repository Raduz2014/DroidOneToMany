package com.simetrix.wmbusblueintelisread;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.simetrix.wmbusblueintelisread.base.BaseFragment;
import com.simetrix.wmbusblueintelisread.base.BluethInterface;

public class BluethActivity extends AppCompatActivity
        implements
        BlueDevicePaireFragment.OnBluethDevicePairFragmentInteractionListener,
        BluethInterface
{
    private final String TAG = BluethActivity.class.getSimpleName();
    private FrameLayout frameContainer;
    private FragmentManager fm;
    private BaseFragment loadedFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getSupportFragmentManager();

        setContentView(R.layout.blue_device_activity);
        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }


        frameContainer = (FrameLayout)findViewById(R.id.frags_container_blue);

        fm.beginTransaction()
                .replace(R.id.frags_container_blue, new BlueDevicePaireFragment(), "mbwbluedev-frag")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        // Check if selectedFragment is not consuming back press
        if(loadedFrag.onBackPressed()) {
            // If not consumed, handle it.
            handleBackPressInThisActivity();
        }
    }

    /**
     * Will close this Activity if double back pressed within 2000 ms
     */
    private void handleBackPressInThisActivity() {
        if(loadedFrag instanceof BlueDevicePaireFragment) {
                finish();
        }
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

    @Override
    public void setSelectedFragment(BaseFragment fragment) {
        this.loadedFrag = fragment;
    }

    @Override
    public void popBackStack() {
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

        ft.replace(R.id.frags_container_blue, fragment, fragment.getTagText());
        ft.addToBackStack(fragment.getTagText());
        ft.commit();
    }

    @Override
    public void addMultipleFragments(BaseFragment[] fragments) {
        FragmentTransaction ft = fm.beginTransaction();

        // Record all steps for the transaction.
        for(int i = 0 ; i < fragments.length ; i++) {
//            ft.setCustomAnimations(R.anim.fragment_slide_in_left, R.anim.fragment_slide_out_left, R.anim.fragment_slide_in_right, R.anim.fragment_slide_out_right);
            ft.replace(R.id.frags_container_blue, fragments[i], fragments[i].getTagText());
        }

        // Add the transaction to backStack with tag of first added fragment
        ft.addToBackStack(fragments[0].getTagText());

        // Commit the transaction.
        ft.commit();
    }
}
