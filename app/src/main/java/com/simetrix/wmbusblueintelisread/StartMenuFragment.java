package com.simetrix.wmbusblueintelisread;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.simetrix.wmbusblueintelisread.adapters.MainAppMenuAdapter;
import com.simetrix.wmbusblueintelisread.adapters.MainMenuAdapter;
import com.simetrix.wmbusblueintelisread.base.BaseFragment;
import com.simetrix.wmbusblueintelisread.contracts.AppMenuItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class StartMenuFragment extends BaseFragment {
    private static final String TAG = "StartMenu_Frag";
    private static final String EXTRA_NAME = "extra_name";


    private OnStartMenuFragmentInteractionListener mListener;
//    private AppMenuItem[] menuItems;
    private Context mContext;

    private ArrayList<AppMenuItem> menuItems = new ArrayList<>();

    FragmentManager fm;

    public StartMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param name Parameter 1.
     * @return A new instance of fragment StartMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StartMenuFragment newInstance(String name) {
        StartMenuFragment fragment = new StartMenuFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public String getTagText() {
        return TAG;
    }

    @Override
    public boolean onBackPressed() {
        hostActivityInterface.popBackStack();
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        if (getArguments() != null) {
            String name = getArguments().getString(EXTRA_NAME);
        }

        fm = getFragmentManager();

        menuItems.add(new AppMenuItem(ContextCompat.getDrawable(mContext, R.drawable.ic_settings_black_64dp), "Settings", "app_settings"));
        menuItems.add(new AppMenuItem(ContextCompat.getDrawable(mContext, R.drawable.ic_settings_bluetooth_black_64dp), "MBWBlue Device", "mbwblue_dev_settings"));
        menuItems.add(new AppMenuItem(ContextCompat.getDrawable(mContext, R.drawable.ic_tap_and_play_black_64dp), "Start Reading", "app_readings"));
        menuItems.add(new AppMenuItem(ContextCompat.getDrawable(mContext, R.drawable.ic_file_download_black_64dp), "Export readings", "app_export"));
        menuItems.add(new AppMenuItem(ContextCompat.getDrawable(mContext, R.drawable.ic_file_upload_black_64dp), "Upload meters", "app_import"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_start_menu, container, false);

//        ListView appMenuLV = (ListView) rootView.findViewById(R.id.appListMenuOptions);
        GridView appMenuGridView = (GridView) rootView.findViewById(R.id.appMenuOptionGridView);
        final MainMenuAdapter menuAdapter = new MainMenuAdapter(mContext, menuItems);
        appMenuGridView.setAdapter(menuAdapter);
        appMenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppMenuItem menuSelect = menuItems.get(position);

                switch (menuSelect.key){
                    case "app_settings":
                        Toast.makeText(mContext,"Open Settings fragment", Toast.LENGTH_SHORT).show();
//                        fm.beginTransaction()
//                                .replace(R.id.frags_container, new SettingsFragment(), "settings-frag")
//                                .addToBackStack("settings-frag")
//                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                                .commit();
                        Intent settingsIntent = new Intent(mContext, SettingsActivity.class);
                        startActivity(settingsIntent);
                        break;
                    case "mbwblue_dev_settings":
                        Toast.makeText(mContext,"Open MBWBlue device Settings fragment", Toast.LENGTH_SHORT).show();
//                        fm.beginTransaction()
//                                .replace(R.id.frags_container, new BlueDevicePaireFragment(), "mbwbluedev-frag")
//                                .addToBackStack("mbwbluedev-frag")
//                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                                .commit();
                        Intent blueIntent = new Intent(mContext, BluethActivity.class);
                        startActivity(blueIntent);

                        break;
                    case "app_readings":
                        Toast.makeText(mContext,"Open Reading task fragment", Toast.LENGTH_SHORT).show();
                        fm.beginTransaction()
                                .replace(R.id.frags_container, new ReadMeterFragment(), "reading-frag")
                                .addToBackStack("reading-frag")
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .commit();
                        break;
                    case "app_export":
                        Toast.makeText(mContext,"Open Export fragment", Toast.LENGTH_SHORT).show();
                        fm.beginTransaction()
                                .replace(R.id.frags_container, new DefaultFragment(), "reading-frag")
                                .addToBackStack("reading-frag")
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .commit();
                        break;
                    case "app_import":
                        Toast.makeText(mContext,"Open Import fragment", Toast.LENGTH_SHORT).show();
                        fm.beginTransaction()
                                .replace(R.id.frags_container, new DefaultFragment(), "reading-frag")
                                .addToBackStack("reading-frag")
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .commit();
                        break;

                }
            }
        });
//        String[] menuItems = {"Read", "Upload meters", "Export Read Meters"};
//        ArrayList<AppMenuItem> menuList = new ArrayList<>();

//        menuList.add(new AppMenuItem( ResourcesCompat.getDrawable(getResources(), R.drawable.icon8_home, null), "Home"));
//        menuList.add(new AppMenuItem( ResourcesCompat.getDrawable(getResources(), R.drawable.icon8_wiper, null), "Wireless Mbus Reader"));
//        menuList.add(new AppMenuItem( ResourcesCompat.getDrawable(getResources(), R.drawable.icon8_phone_jammer, null), "Blocks to read"));
//        menuList.add(new AppMenuItem( ResourcesCompat.getDrawable(getResources(), R.drawable.icon8_phone_jammer, null), "Import"));
//        menuList.add(new AppMenuItem( ResourcesCompat.getDrawable(getResources(), R.drawable.icon8_phone_jammer, null), "Export"));


//        ArrayAdapter<String> itemsAdapter =
//                new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, menuItems);
//        MainAppMenuAdapter menuAdapter = new MainAppMenuAdapter(mContext, menuList);
//        appMenuLV.setAdapter(menuAdapter);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onStartMenuFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStartMenuFragmentInteractionListener) {
            mListener = (OnStartMenuFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnStartMenuFragmentInteractionListener {
        void onStartMenuFragmentInteraction(Uri uri);
    }
}
