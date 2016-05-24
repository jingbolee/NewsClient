package com.ljb.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @FileName: com.ljb.zhbj.view.TopNewsViewPager.java
 * @Author: Li Jingbo
 * @Date: 2016-05-24 15:20
 * @Version V1.0 <描述当前版本功能>
 */
public class TopNewsViewPager extends ViewPager {
    private static final String TAG = "TopNewsViewPager";
    private int startY;
    private int startX;

    public TopNewsViewPager(Context context) {
        super(context);
    }

    public TopNewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //1.当viewpager在0的位置的时候，
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        getParent().requestDisallowInterceptTouchEvent(true); //true,表示child不希望父类拦截
//        if ( getCurrentItem() == 0 ) {  //当前位置为0，由父类处理事件
//            getParent().requestDisallowInterceptTouchEvent(false);
//        } else {
//            getParent().requestDisallowInterceptTouchEvent(true);
//        }
        switch ( ev.getAction() ) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                startX = (int) ev.getRawX();
                startY = (int) ev.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getRawX();
                int endY = (int) ev.getRawY();
                //左右滑动
                if ( Math.abs(startX - endX) > Math.abs(startY - endY) ) {
                    if ( endX > startX ) {//右滑动事件
                        if ( getCurrentItem() == 0 ) {//第一个位置的时候，由父类处理事件
                            getParent().requestDisallowInterceptTouchEvent(false);
                        } else {//剩下位置，由自己处理
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }
                    } else {//左滑动
                        if ( getCurrentItem() == getAdapter().getCount()-1) {//左滑到最后一个位置
                            getParent().requestDisallowInterceptTouchEvent(false);
                        } else {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }
                    }
                } else {//上下滑动,由父类处理Touch事件
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;
        }

        return super.dispatchTouchEvent(ev);
    }
}
