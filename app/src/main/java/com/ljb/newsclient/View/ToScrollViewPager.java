package com.ljb.newsclient.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @FileName: com.ljb.newsclient.View.ToScrollViewPager.java
 * @Author: Li Jingbo
 * @Date: 2016-05-17 09:00
 * @Version V1.0 <描述当前版本功能>
 */
public class ToScrollViewPager extends ViewPager {
    private static final String TAG = "ToScrollViewPager";

    public ToScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToScrollViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }
}
