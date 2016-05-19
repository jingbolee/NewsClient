package com.ljb.newsclient.viewpager;

import android.app.Activity;
import android.view.View;

/**
 * @FileName: com.ljb.newsclient.viewpager.BaseMenuDetailPager.java
 * @Author: Li Jingbo
 * @Date: 2016-05-16 21:31
 * @Version V1.0 <描述当前版本功能>
 */
public abstract class BaseMenuDetailPager {
    private static final String TAG = "BaseMenuDetailPager";

    public Activity mActivity;
    public View mRootView;

    public BaseMenuDetailPager(Activity activity) {
        mActivity = activity;
        mRootView = initView();
    }


    public abstract View initView();

    public void initData() {

    }
}
