package com.ljb.zhbj.viewpager;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.ljb.zhbj.R;
import com.ljb.zhbj.activity.MainActivity;

/**
 * @FileName: com.ljb.zhbj.viewpager.BasePager.java
 * @Author: Li Jingbo
 * @Date: 2016-05-20 10:32
 * @Version V1.0 BasePager,基类，子类：HomePager,NewsPager，SettingsPager，SmartPager，GovPager
 */
public abstract class BasePager {
    private static final String TAG = "BasePager";
    public Activity mActivity;
    public View mRootView;
    private RelativeLayout rlMenu;
    private TextView tvTitle;
    public FrameLayout flBasePager;
    private SlidingMenu slidingMenu;

    public BasePager(Activity activity) {
        mActivity = activity;
        mRootView = initView();

        MainActivity mainUi = (MainActivity) this.mActivity;
        slidingMenu = mainUi.slidingMenu;

    }

    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_base, null);
        flBasePager = (FrameLayout) view.findViewById(R.id.fl_base_pager);
        rlMenu = (RelativeLayout) view.findViewById(R.id.rl_menu);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        rlMenu.setClickable(true);
        rlMenu.setEnabled(true);
        rlMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        return view;
    }


    public void initData() {


    }

    public void setTitleText(String title) {
        tvTitle.setText(title);
    }

    public void setFrameLayoutView(View view){
        flBasePager.removeAllViews();
        flBasePager.addView(view);
    }


    protected void setSlidingMenuShow(boolean show) {
        if ( show ) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
            rlMenu.setVisibility(View.VISIBLE);

        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
            rlMenu.setVisibility(View.INVISIBLE);
        }
    }


}
