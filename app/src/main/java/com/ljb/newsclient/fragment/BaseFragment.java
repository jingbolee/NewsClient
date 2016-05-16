package com.ljb.newsclient.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        showSlideMenuIcom();
    }

    protected void initData(){

    };

    public abstract String getFragmentTitle();


    public abstract void showSlideMenuIcom();

}
