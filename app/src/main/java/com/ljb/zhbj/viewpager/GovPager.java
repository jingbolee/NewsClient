package com.ljb.zhbj.viewpager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

/**
 * @FileName: com.ljb.zhbj.viewpager.HomePager.java
 * @Author: Li Jingbo
 * @Date: 2016-05-20 10:35
 * @Version V1.0 <描述当前版本功能>
 */
public class GovPager extends BasePager {
    private static final String TAG = "GovPager";

    public GovPager(Activity activity) {
        super(activity);
    }


    @Override
    public void initData() {
        super.initData();
        setSlidingMenuShow(false);
        setTitleText("政务");

        TextView view = new TextView(mActivity);
        view.setGravity(Gravity.CENTER);
        view.setTextColor(Color.RED);
        view.setTextSize(25);
        view.setText("GovPager");
        setFrameLayoutView(view);
    }


}
