package com.ljb.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @FileName: com.ljb.zhbj.view.NoScrollViewPager.java
 * @Author: Li Jingbo
 * @Date: 2016-05-20 10:29
 * @Version V1.0 继承ViewPager，重写dispathTouchEvent()方法，返回为false，不可滑动
 */
public class NoScrollViewPager extends ViewPager {
    private static final String TAG = "NoScrollViewPager";


    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //事件分发
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    //事件拦截，false表示不拦截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    //事件处理
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
