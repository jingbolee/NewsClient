package com.ljb.newsclient.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ljb.newsclient.R;

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
        View view = inflater.inflate(R.layout.fragment_gov, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText(getFragmentTitle());
        return view;
    }

    @Override
    public String getFragmentTitle() {
        return TITLE;
    }

    @Override
    public void showSlideMenuIcom() {

    }
}
