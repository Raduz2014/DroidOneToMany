package com.simetrix.wmbusblueintelisread;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simetrix.wmbusblueintelisread.InterFragmentCommunication.DefaultInterface;
import com.simetrix.wmbusblueintelisread.base.BaseFragment;

public class DefaultFragment extends BaseFragment {
    private static final String TAG = "Deafult_Frag";
    private static final String EXTRA_NAME = "extra_name";

    private DefaultInterface mListener;

    public DefaultFragment() {
        // Required empty public constructor
    }

    public static DefaultFragment newInstance(String name) {
        DefaultFragment fragment = new DefaultFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_NAME, name);
        fragment.setArguments(args);
        return fragment;
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
        return inflater.inflate(R.layout.fragment_default, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DefaultInterface) {
            mListener = (DefaultInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DefaultInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        mListener.showDefaultFragment(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        mListener.showDefaultFragment(false);

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

    public interface OnDefaultFragmentInteractionListener {
//        void onDefaultFragmentInteraction(Uri uri);
        String getName();
//        void showDefaultFragment(boolean state);
    }
}
