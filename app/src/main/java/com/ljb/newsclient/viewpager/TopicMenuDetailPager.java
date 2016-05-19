package com.ljb.newsclient.viewpager;

import android.app.Activity;
import android.view.View;

import com.ljb.newsclient.R;

/**
 * @FileName: com.ljb.newsclient.viewpager.NewsMenuDetailPager.java
 * @Author: Li Jingbo
 * @Date: 2016-05-16 21:40
 * @Version V1.0 <描述当前版本功能>
 */
public class TopicMenuDetailPager extends BaseMenuDetailPager {
    private static final String TAG = "TopicMenuDetailPager";

    public TopicMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_topic_menu_detail, null);
        return view;
    }
}
