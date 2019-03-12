package com.simetrix.wmbusblueintelisread;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.simetrix.wmbusblueintelisread.adapters.MainAppMenuAdapter;
import com.simetrix.wmbusblueintelisread.base.BaseFragment;
import com.simetrix.wmbusblueintelisread.contracts.AppMenuItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;


public class StartMenuFragment extends BaseFragment {
    private static final String TAG = "StartMenu_Frag";
    private static final String EXTRA_NAME = "extra_name";


    private OnStartMenuFragmentInteractionListener mListener;
    private AppMenuItem[] menuItems;
    private Context mContext;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_start_menu, container, false);

        ListView appMenuLV = (ListView) rootView.findViewById(R.id.appListMenuOptions);

        String[] menuItems = {"Read", "Upload meters", "Export Read Meters"};
        ArrayList<AppMenuItem> menuList = new ArrayList<>();

        menuList.add(new AppMenuItem( ResourcesCompat.getDrawable(getResources(), R.drawable.icon8_home, null), "Home"));
        menuList.add(new AppMenuItem( ResourcesCompat.getDrawable(getResources(), R.drawable.icon8_wiper, null), "Wireless Mbus Reader"));
        menuList.add(new AppMenuItem( ResourcesCompat.getDrawable(getResources(), R.drawable.icon8_phone_jammer, null), "Blocks to read"));
        menuList.add(new AppMenuItem( ResourcesCompat.getDrawable(getResources(), R.drawable.icon8_phone_jammer, null), "Import"));
        menuList.add(new AppMenuItem( ResourcesCompat.getDrawable(getResources(), R.drawable.icon8_phone_jammer, null), "Export"));


        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, menuItems);
        MainAppMenuAdapter menuAdapter = new MainAppMenuAdapter(mContext, menuList);
        appMenuLV.setAdapter(menuAdapter);
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
//        if (context instanceof OnStartMenuFragmentInteractionListener) {
//            mListener = (OnStartMenuFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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
