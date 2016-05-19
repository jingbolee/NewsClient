package com.ljb.newsclient.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.RelativeLayout;


public abstract class BaseFragment extends Fragment {

    public Activity mActivity;
    private RelativeLayout rlMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        showSlideMenuIcom();
    }

    protected void initData(){

    };

    public abstract String getFragmentTitle();


    public abstract void showSlideMenuIcom();

}
