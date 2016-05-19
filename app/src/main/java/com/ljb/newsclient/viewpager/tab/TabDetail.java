package com.ljb.newsclient.viewpager.tab;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.ljb.newsclient.viewpager.BaseMenuDetailPager;

/**
 * @FileName: com.ljb.newsclient.viewpager.tab.TabDetail.java
 * @Author: Li Jingbo
 * @Date: 2016-05-18 10:49
 * @Version V1.0 <描述当前版本功能>
 */
public class TabDetail extends BaseMenuDetailPager {
    private static final String TAG = "TabDetail";


    public TabDetail(Activity activity) {
        super(activity);


    }

    @Override
    public View initView() {
        TextView view = new TextView(mActivity);
        view.setText("中国");

        return view;
    }
}
