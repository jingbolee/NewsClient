package com.ljb.zhbj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ljb.zhbj.R;
import com.ljb.zhbj.utils.DensityUtils;

public class GuideActivity extends Activity {
    private ViewPager vpGuide;   //viewpager
    private Button btnStartApp;   //开始体验按钮
    private LinearLayout llPointGroup;  //point容器
    private ImageView pointFouce;

    private int[] mImages;
    private int pointDis;  //两个point的距离

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        setContentView(R.layout.activity_guide);
        vpGuide = (ViewPager) findViewById(R.id.vp_guide);
        btnStartApp = (Button) findViewById(R.id.tbn_start_app);
        llPointGroup = (LinearLayout) findViewById(R.id.ll_point_group);
        pointFouce = (ImageView) findViewById(R.id.iv_point_fouce);

        //获取两个point的距离在oncreate中不能立即获取，需要等待界面layout布局完成以后才能获取到，所以采用ViewTree观察者异步获取数据
        llPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llPointGroup.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                pointDis = llPointGroup.getChildAt(1).getLeft() - llPointGroup.getChildAt(0).getLeft();
            }
        });

        btnStartApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                getSharedPreferences("config", MODE_PRIVATE).edit().putBoolean("is_user_first", false).commit();
                finish();
            }
        });
    }

    private void initData() {
        mImages = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};

        //添加与引导页相同数量的point
        for ( int i = 0; i < mImages.length; i++ ) {
            View view = new View(GuideActivity.this);
            view.setBackgroundResource(R.drawable.point_unfouce);
            int pointPx = DensityUtils.dip2px(GuideActivity.this, 8);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pointPx, pointPx);
            if ( i > 0 ) {
                params.leftMargin = DensityUtils.dip2px(GuideActivity.this, 6);
            }
            view.setLayoutParams(params);
            llPointGroup.addView(view);
        }
    }

    private void initListener() {
        vpGuide.setAdapter(new GuideAdapter());
        vpGuide.addOnPageChangeListener(new GuideOnPageChangeListener());
    }


    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = new View(GuideActivity.this);
            view.setBackgroundResource(mImages[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class GuideOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int dis = (int) (pointDis * positionOffset + position * pointDis);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) pointFouce.getLayoutParams();
            params.leftMargin = dis;
            pointFouce.setLayoutParams(params);


        }

        @Override
        public void onPageSelected(int position) {
            //判断是否显示button
            if ( position == mImages.length - 1 ) {
                btnStartApp.setVisibility(View.VISIBLE);
            } else {
                btnStartApp.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
