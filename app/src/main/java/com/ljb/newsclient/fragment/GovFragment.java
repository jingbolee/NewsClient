package com.ljb.newsclient.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GovFragment extends BaseFragment {
    public static final String TAG = "GovFragment";
    public static final String TITLE = "政务";

    public GovFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText("GovFragment");
        return textView;
    }

    @Override
    public String getFragmentTitle() {
        return TITLE;
    }
}
