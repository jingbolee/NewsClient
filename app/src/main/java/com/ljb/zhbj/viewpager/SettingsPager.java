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
public class SettingsPager extends BasePager {
    private static final String TAG = "SettingsPager";

    public SettingsPager(Activity activity) {
        super(activity);
    }


    @Override
    public void initData() {
        super.initData();
        setSlidingMenuShow(false);
        setTitleText("设置");
        TextView view = new TextView(mActivity);
        view.setGravity(Gravity.CENTER);
        view.setTextColor(Color.RED);
        view.setTextSize(25);
        view.setText("SettingsPager");
        setFrameLayoutView(view);
    }

}
