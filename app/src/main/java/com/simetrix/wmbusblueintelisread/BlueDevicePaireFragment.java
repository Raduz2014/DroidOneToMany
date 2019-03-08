package com.simetrix.wmbusblueintelisread;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.simetrix.wmbusblueintelisread.base.BaseFragment;
import com.simetrix.wmbusblueintelisread.contracts.BlueDevice;
import com.simetrix.wmbusblueintelisread.utils.AppConstants;
import com.simetrix.wmbusblueintelisread.utils.FragmentUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.app.Activity.RESULT_OK;

public class BlueDevicePaireFragment extends BaseFragment {
    private final static String TAG = "BlueDevice_Pair_Fragment";
    private static final String EXTRA_NAME = "extra_name";


    private final static int REQUEST_ENABLE_BT = 1; // used to identify adding bluetooth names
    private final static int CONNECTING_STATUS = 3; // used in bluetooth handler to identify message status

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    //Context activity
    private Context mContext;
    private Activity mActivity;

    private BluetoothAdapter mBTAdapter;
    private Set<BluetoothDevice> mPairedDevices;

    private BlueDevice mSelMBWBlueDevice = null;
    private ArrayAdapter<String> mBTArrayAdapter;
    private BlueDeviceAdapter mBTDeviceAdapter;
    private BlueDeviceAdapter mBTPairedDeviceAdapter;
    private List<BlueDevice> mBTArrayList = new ArrayList<>();
    private List<BlueDevice> mBTPairedArrayList = new ArrayList<>();

    private OnBluethDevicePairFragmentInteractionListener mListener;
    private CheckBox mCheckboxBlueStatus;
    private Button mBtnShowPairedDevs, mBtnDiscoverDevs, mBtnOk;
    private ListView mListPairedDevs, mListDiscoverDevs;
    private ProgressBar mProgressBtScanner;

    private final BroadcastReceiver blReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final Bundle extras = intent.getExtras();
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                BlueDevice bluedev = new BlueDevice(device.getName(), device.getAddress());

                if(mBTArrayList.size() > 0){
                    int found = mBTArrayList.indexOf(bluedev);
                    if(found == -1){
                        mBTArrayList.add(bluedev);
                    }
                }
                else {
                    mBTArrayList.add(bluedev);
                }
                mBTDeviceAdapter.notifyDataSetChanged();
            }
        }
    };



    private class BlueDeviceAdapter extends ArrayAdapter<BlueDevice> implements View.OnClickListener {
        private Context context;
        private List<BlueDevice> dataSet;
        private int lastPosition = -1;

        @Override
        public void onClick(View v) {
            int position=(Integer) v.getTag();
            Object object= getItem(position);
            BlueDevice dataModel=(BlueDevice)object;
            //switch (v.getId()) {    }
        }

        private class BlueDeviceViewHolder {
            TextView upperText;
            TextView lowerText;
        }

        public BlueDeviceAdapter(List<BlueDevice> data, Context ctx) {
            super(ctx, R.layout.bluedevice_row_item);
            this.context = ctx;
            this.dataSet = data;
        }

        @Override
        public int getCount() {
            return dataSet.size();
        }

        @Override
        public BlueDevice getItem(int position) {
            return dataSet.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            BlueDevice dataModel = getItem(position);
            BlueDeviceViewHolder viewHolder;

            final View result;

            if (convertView == null) {
                viewHolder = new BlueDeviceViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.bluedevice_row_item, null, false);
                viewHolder.upperText = (TextView)convertView.findViewById(R.id.lblDevName);
                viewHolder.lowerText = (TextView)convertView.findViewById(R.id.lblDevMacAdr);
                result = convertView;
                convertView.setTag(viewHolder);
            }
            else {
                viewHolder = (BlueDeviceViewHolder)convertView.getTag();
                result = convertView;
            }

            lastPosition = position;

            viewHolder.upperText.setText(dataModel.getDeviceName());
            viewHolder.lowerText.setText(dataModel.getDevMac());

            return convertView;
        }
    }

    public BlueDevicePaireFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param name Parameter 1.
     * @return A new instance of fragment BlueDevicePaireFragment.
     */
    public static BlueDevicePaireFragment newInstance(String name) {
        BlueDevicePaireFragment fragment = new BlueDevicePaireFragment();
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
        Log.i(TAG,"BlueDevice Frag onCreate");

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mActivity = getActivity();

        // Ask for location permission if not already allowed
        if(ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        }else{

        }

        mBTAdapter = BluetoothAdapter.getDefaultAdapter();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Intent actIntent = mActivity.getIntent();
//        String msg = (String) actIntent.getExtras().get("message");
//        Log.d(TAG, "Intent msg: " + msg);
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_blue_device_pairing, container, false);

        mBtnDiscoverDevs = (Button)view.findViewById(R.id.btnDiscoverBthDevices);
        mBtnShowPairedDevs = (Button)view.findViewById(R.id.btnShowPairedBthDevices);
        mBtnOk = (Button)view.findViewById(R.id.btnOK);
        mCheckboxBlueStatus = (CheckBox)view.findViewById(R.id.checkboxBlueStatus);
        mProgressBtScanner = (ProgressBar)view.findViewById(R.id.progressSpinScanning);

        mListPairedDevs = (ListView)view.findViewById(R.id.listPairedDevices);
        mListDiscoverDevs = (ListView)view.findViewById(R.id.listDiscovredDevices);

//        mBTArrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1);

        mBTPairedDeviceAdapter = new BlueDeviceAdapter(mBTPairedArrayList, mContext);
        mListPairedDevs.setAdapter(mBTPairedDeviceAdapter);
        mListPairedDevs.setOnItemClickListener(mPairedDeviceClickListener);

        mBTDeviceAdapter = new BlueDeviceAdapter(mBTArrayList, mContext);
        mListDiscoverDevs.setAdapter(mBTDeviceAdapter);
        mListDiscoverDevs.setOnItemClickListener(mDeviceClickListener);

        mCheckboxBlueStatus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean state = false;
                CheckBox cBlueState = (CheckBox) v;
                if(cBlueState.isChecked()){
                    state = true;
                    bluetoothOn(v);
                }
                else {
                    state = false;
                    bluetoothOff(v);
                }

                Log.d(TAG, "check box state: " + state);
            }
        });

        mBtnDiscoverDevs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discover(v);
            }
        });

        mBtnShowPairedDevs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBlueDevices(v);
            }
        });

        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToSettings(v);
            }
        });


        return view;
    }

    private void showBlueDevices(View v) {
        listPairedBlueDevices();
    }

    private void backToSettings(View v) {
        stopBTDiscovering();

        FragmentManager fm = getFragmentManager();
        Fragment stackFrag = FragmentUtil.getFragmentByTagName(fm,"settings-frag");
        Intent intent = new Intent(mContext, BlueDevicePaireFragment.class);
        intent.putExtra("com.simetrix.wmbusblueintelisread.contracts.BlueDevice",mSelMBWBlueDevice);

        int ddd = AppConstants.FRAGMENT_CODE;
        stackFrag.onActivityResult(ddd, RESULT_OK, intent);
        getFragmentManager().popBackStack();
    }

    private void listPairedBlueDevices(){
        mPairedDevices = mBTAdapter.getBondedDevices();
        if(mBTAdapter.isEnabled()){
            mBTPairedArrayList.clear();
            for(BluetoothDevice device : mPairedDevices){
                BlueDevice bluedev = new BlueDevice(device.getName(), device.getAddress());
                mBTPairedArrayList.add(bluedev);
            }
            Toast.makeText(mContext, "Show Paired Devices", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mContext, "Bluetooth not on", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"BlueDevice Frag onResume");

        if(mBTAdapter == null) {
            Toast.makeText(mContext,"No Bluetooth found!!",Toast.LENGTH_SHORT).show();
            getFragmentManager().popBackStack();
            return;
        }

        if(mBTAdapter.isEnabled()){
            mCheckboxBlueStatus.setChecked(true);
            listPairedBlueDevices();
        }
        else {
            mCheckboxBlueStatus.setChecked(false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void stopBTDiscovering(){
        if(mBTAdapter.isDiscovering()){
            mBTAdapter.cancelDiscovery();
            mProgressBtScanner.setVisibility(View.INVISIBLE);
            getActivity().unregisterReceiver(blReceiver);
        }
    }

    private void discover(View v) {
        //Check if device is already in discovering mode
        if(mBTAdapter.isDiscovering()){
            mBTAdapter.cancelDiscovery();
            mProgressBtScanner.setVisibility(View.INVISIBLE);
            Toast.makeText(mContext,"Discovery stopped",Toast.LENGTH_SHORT).show();
        }
        else {
            if(mBTAdapter.isEnabled()){
                mBTDeviceAdapter.clear();
                mBTAdapter.startDiscovery();
                mProgressBtScanner.setVisibility(View.VISIBLE);
                Toast.makeText(mContext,"Discovery started",Toast.LENGTH_SHORT).show();
                getActivity().registerReceiver(blReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
            }
            else {
                Toast.makeText(mContext,"Bluetooth not on",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void bluetoothOn(View v) {
        if (!mBTAdapter.isEnabled()) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mActivity);
            alertBuilder.setCancelable(true);
            alertBuilder.setMessage("Do you want to enable bluetooth");
            alertBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    mBTAdapter.enable();
                    Toast.makeText(mContext,"Bluetooth turned on",Toast.LENGTH_SHORT).show();
                }
            }).setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = alertBuilder.create();
            alert.show();
        }
        else{
            Toast.makeText(mContext,"Bluetooth is already on", Toast.LENGTH_SHORT).show();
        }
    }

    private void bluetoothOff(View v) {
        stopBTDiscovering();
        mBTAdapter.disable(); // turn off
        Toast.makeText(mContext,"Bluetooth turned Off", Toast.LENGTH_SHORT).show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBluethDevicePairFragmentInteractionListener) {
            mContext = context;
            mListener = (OnBluethDevicePairFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBluethDevicePairFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(!mBTAdapter.isEnabled()){
                Toast.makeText(mContext, "Bluetooth not on", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    };

    private AdapterView.OnItemClickListener mPairedDeviceClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            View item = view;
            mSelMBWBlueDevice =  mBTPairedDeviceAdapter.getItem(position);
        }
    };

    public interface OnBluethDevicePairFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
        void bluethDevicePairActions();
        //String getName();
    }
}
