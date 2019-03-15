package com.simetrix.wmbusblueintelisread;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simetrix.wmbusblueintelisread.InterFragmentCommunication.ReadingMeterInterface;
import com.simetrix.wmbusblueintelisread.base.BaseFragment;

public class ReadMeterFragment extends BaseFragment {
    private static final String TAG = "ReadMeters_Frag";
    private static final String EXTRA_NAME = "extra_name";

    private OnReadMeterFragmentInteractionListener mListener;
    private ReadingMeterInterface mListener2;

    public ReadMeterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param name Parameter 1.
     * @return A new instance of fragment ReadMeterFragment.
     */
    public static ReadMeterFragment newInstance(String name) {
        ReadMeterFragment fragment = new ReadMeterFragment();
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
        if (getArguments() != null) {
            String name = getArguments().getString(EXTRA_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_read_meter, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.OnReadMeterFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnReadMeterFragmentInteractionListener) {
            mListener = (OnReadMeterFragmentInteractionListener) context;
            mListener2 = (ReadingMeterInterface)context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnReadMeterFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mListener2.showReadingMeterFragment(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        mListener2.showReadingMeterFragment(false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnReadMeterFragmentInteractionListener {
        void  OnReadMeterFragmentInteraction(Uri uri);
    }
}
