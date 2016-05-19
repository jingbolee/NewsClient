package com.ljb.newsclient.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.viewpagerindicator.TabPageIndicator;

/**
 * @FileName: com.ljb.newsclient.view.ScrollTabPageIndicator.java
 * @Author: Li Jingbo
 * @Date: 2016-05-19 15:39
 * @Version V1.0 <描述当前版本功能>
 */
public class ScrollTabPageIndicator extends TabPageIndicator {
    private static final String TAG = "ScrollTabPageIndicator";

    public ScrollTabPageIndicator(Context context) {
        super(context);
    }

    public ScrollTabPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
