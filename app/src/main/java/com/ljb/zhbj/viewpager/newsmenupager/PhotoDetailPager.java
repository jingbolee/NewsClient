package com.ljb.zhbj.viewpager.newsmenupager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * @FileName: com.ljb.zhbj.viewpager.newsmenupager.NewsDetailPager.java
 * @Author: Li Jingbo
 * @Date: 2016-05-21 16:35
 * @Version V1.0 <描述当前版本功能>
 */
public class PhotoDetailPager extends BaseMenuDetailPager {
    private static final String TAG = "PhotoDetailPager";

    public PhotoDetailPager(Activity activity) {
        super(activity);
    }


    @Override
    protected View initView() {
        TextView view = new TextView(mActivity);
        view.setGravity(Gravity.CENTER);
        view.setTextColor(Color.RED);
        view.setTextSize(25);
        view.setText("新闻中心-组图");
        return view;
    }
}
