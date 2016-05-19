package com.ljb.newsclient.viewpager;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.ljb.newsclient.R;
import com.ljb.newsclient.domain.NewsData;
import com.ljb.newsclient.viewpager.tab.TabDetail;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: com.ljb.newsclient.viewpager.NewsMenuDetailPager.java
 * @Author: Li Jingbo
 * @Date: 2016-05-16 21:40
 * @Version V1.0 <描述当前版本功能>
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager implements ViewPager.OnPageChangeListener {
    private static final String TAG = "NewsMenuDetailPager";

    private ViewPager vpNewsTop;
    private TabPageIndicator indicatorNewsTab;

    private List< NewsData.NewsTabData > mNewsTabDataList;
    private List< TabDetail > mTabDetailList;

    public NewsMenuDetailPager(Activity activity) {
        super(activity);
    }

    public NewsMenuDetailPager(Activity activity, List< NewsData.NewsTabData > newsTabDataList) {
        super(activity);
        mNewsTabDataList = newsTabDataList;
        initData();
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_new_menu_detail, null);
        vpNewsTop = (ViewPager) view.findViewById(R.id.vp_news_top);


        indicatorNewsTab = (TabPageIndicator) view.findViewById(R.id.indicator_news_tab);


        return view;
    }

    @Override
    public void initData() {
        mTabDetailList = new ArrayList<>();
        for ( int i = 0; i < mNewsTabDataList.size(); i++ ) {
            TabDetail tab = new TabDetail(mActivity);
            mTabDetailList.add(tab);
        }
        vpNewsTop.setAdapter(new VpNewsTopAdapter());
        indicatorNewsTab.setViewPager(vpNewsTop);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class VpNewsTopAdapter extends PagerAdapter {



        @Override
        public CharSequence getPageTitle(int position) {
            return mNewsTabDataList.get(position).getTitle();
        }

        @Override
        public int getCount() {
            return mNewsTabDataList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            NewsData.NewsTabData newsTabData = mNewsTabDataList.get(position);
            TabDetail tabDetail = mTabDetailList.get(position);

            container.addView(tabDetail.mRootView);

            return tabDetail.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
