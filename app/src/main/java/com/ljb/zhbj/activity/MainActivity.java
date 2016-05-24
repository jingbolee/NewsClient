package com.ljb.zhbj.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.ljb.zhbj.R;
import com.ljb.zhbj.fragment.ContentFragment;
import com.ljb.zhbj.fragment.LeftMenuFragment;
import com.ljb.zhbj.utils.DensityUtils;

public class MainActivity extends SlidingFragmentActivity {


    public SlidingMenu slidingMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.left_menu);
        initFragment();
        slidingMenu = getSlidingMenu();
        int px = DensityUtils.dip2px(this, 200);
        slidingMenu.setBehindOffset(px);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
    }

    private void initFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_content, new ContentFragment(), ContentFragment.TAG);
        transaction.replace(R.id.fl_menu, new LeftMenuFragment(), LeftMenuFragment.TAG);
        transaction.commit();
    }

    public LeftMenuFragment getLeftMenuFragment() {
        return (LeftMenuFragment) getFragmentManager().findFragmentByTag(LeftMenuFragment.TAG);
    }

    public ContentFragment getContentFragment() {
        return (ContentFragment) getFragmentManager().findFragmentByTag(ContentFragment.TAG);
    }


}
