package com.simetrix.wmbusblueintelisread;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simetrix.wmbusblueintelisread.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DefaultFragment.OnDefaultFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DefaultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DefaultFragment extends BaseFragment {
    private static final String TAG = "Deafult_Frag";
    private static final String EXTRA_NAME = "extra_name";

    private OnDefaultFragmentInteractionListener mListener;

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
        if (context instanceof OnDefaultFragmentInteractionListener) {
            mListener = (OnDefaultFragmentInteractionListener) context;
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
        void onDefaultFragmentInteraction(Uri uri);
        String getName();
    }
}
