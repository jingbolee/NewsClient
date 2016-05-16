package com.ljb.newsclient.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ljb.newsclient.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SmartFragment extends BaseFragment {
    public static final String TAG = "SmartFragment";
    public static final String TITLE = "智能服务";

    public SmartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smart, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText(getFragmentTitle());
        return view;
    }


    public String getFragmentTitle() {
        return TITLE;
    }

    @Override
    public void showSlideMenuIcom() {

    }

}
