package com.ljb.zhbj.viewpager.newsmenupager;

import android.app.Activity;
import android.view.View;

/**
 * @FileName: com.ljb.zhbj.viewpager.newsmenupager.BaseMenuDetailPager.java
 * @Author: Li Jingbo
 * @Date: 2016-05-21 16:34
 * @Version V1.0 <描述当前版本功能>
 */
public abstract class BaseMenuDetailPager {
    private static final String TAG = "BaseMenuDetailPager";
    public View mRootView;
    public Activity mActivity;

    public BaseMenuDetailPager(Activity activity) {
        mActivity = activity;
        mRootView = initView();
    }

    protected abstract View initView();

    private void initData() {
    }

}
