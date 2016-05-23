package com.ljb.zhbj.viewpager.newsmenupager;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ljb.zhbj.R;
import com.ljb.zhbj.domain.NewsMenuDataBean;
import com.viewpagerindicator.TabPageIndicator;

import java.util.List;

/**
 * @FileName: com.ljb.zhbj.viewpager.newsmenupager.NewsDetailPager.java
 * @Author: Li Jingbo
 * @Date: 2016-05-21 16:35
 * @Version V1.0 <描述当前版本功能>
 */
public class NewsDetailPager extends BaseMenuDetailPager {
    private static final String TAG = "NewsDetailPager";
    private TabPageIndicator indicatorNewsTab;
    private ViewPager vpNewsTop;
    private List< NewsMenuDataBean.NewsTab > mNewsMenus;

    public NewsDetailPager(Activity activity, List< NewsMenuDataBean.NewsTab > newsMenus) {
        super(activity);
        mNewsMenus = newsMenus;
    }


    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.view_news_detail_pager, null);

        indicatorNewsTab = (TabPageIndicator) view.findViewById(R.id.indicator_news_tab);
        vpNewsTop = (ViewPager) view.findViewById(R.id.vp_news_tab_detail);

        return view;
    }

    @Override
    public void initData() {
        NewsTabPager adapter = new NewsTabPager();
        vpNewsTop.setAdapter(adapter);
        indicatorNewsTab.setViewPager(vpNewsTop);
    }

    class NewsTabPager extends PagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {
            return mNewsMenus.get(position).getTitle();
        }

        @Override
        public int getCount() {
            return mNewsMenus.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            NewsMenuDataBean.NewsTab newsTab = mNewsMenus.get(position);
            TextView view = new TextView(mActivity);
            view.setText(newsTab.getTitle());

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
