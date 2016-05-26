package com.ljb.zhbj.viewpager.newsmenupager;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ljb.zhbj.R;
import com.ljb.zhbj.domain.NewsDetailInfoBean;
import com.ljb.zhbj.domain.NewsMenuDataBean;
import com.ljb.zhbj.global.GlobalContants;
import com.ljb.zhbj.utils.DensityUtils;
import com.ljb.zhbj.utils.DrawableUtils;
import com.ljb.zhbj.utils.HttpUtils;
import com.ljb.zhbj.utils.LogUtils;
import com.ljb.zhbj.view.TopNewsViewPager;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @FileName: com.ljb.zhbj.viewpager.newsmenupager.TabInfoPager.java
 * @Author: Li Jingbo
 * @Date: 2016-05-23 15:19
 * @Version V1.0 TabInfoPager
 */
public class TabInfoPager extends BaseMenuDetailPager implements ViewPager.OnPageChangeListener {
    private static final String TAG = "TabInfoPager";
    public static final int CODE_DATAS_OK = 0;
    public static final int CODE_SERVICE_RESPONSE_ERROR = 1;
    public static final int CODE_SERVICE_REQUEST_ERROR = 2;
    private String mUrl;
    private ViewPager vpTopNews;
    private ListView lvNewsInfo;
    private TopNewsAdapter mTopNewsAdapter;
    private NewsDetailInfoBean newsDetailInfoBean;
    private List< NewsDetailInfoBean.NewsInfo > mNewsInfoList; //listview用的数据
    private List< NewsDetailInfoBean.TopNewsInfo > mTopNewsInfoList; //头条新闻用的数据
    private CirclePageIndicator indicatorTopNewsCircle;
    private TextView tvTopNewsTitle;

    private NewsDetailAdapter mNewsDetailAdapter;

    public TabInfoPager(Activity activity, NewsMenuDataBean.NewsTab newsTab) {
        super(activity);
        mUrl = GlobalContants.SERVICE_HTTP + newsTab.getUrl();
        Log.e(TAG, "URL:" + mUrl);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch ( msg.what ) {
                case CODE_DATAS_OK:
                    mTopNewsAdapter = new TopNewsAdapter();
                    vpTopNews.setAdapter(mTopNewsAdapter);
                    indicatorTopNewsCircle.setViewPager(vpTopNews);
                    tvTopNewsTitle.setText(mTopNewsInfoList.get(0).title);
                    indicatorTopNewsCircle.setOnPageChangeListener(TabInfoPager.this);

                    mNewsDetailAdapter = new NewsDetailAdapter();
                    lvNewsInfo.setAdapter(mNewsDetailAdapter);
                    break;
                case CODE_SERVICE_RESPONSE_ERROR:
                    Toast.makeText(mActivity, "服务器返回的数据有问题", Toast.LENGTH_SHORT).show();
                    break;

                case CODE_SERVICE_REQUEST_ERROR:
                    Toast.makeText(mActivity, "服务器请求时出现了问题", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.view_tab_info_pager, null);
        View headerView = View.inflate(mActivity, R.layout.view_header_topnews, null);
        vpTopNews = (TopNewsViewPager) headerView.findViewById(R.id.vp_top_news);

        tvTopNewsTitle = (TextView) headerView.findViewById(R.id.tv_top_news_title);
        indicatorTopNewsCircle = (CirclePageIndicator) headerView.findViewById(R.id.indicator_top_news_circle);
        lvNewsInfo = (ListView) view.findViewById(R.id.lv_news_info);

        lvNewsInfo.addHeaderView(headerView);
        return view;

    }

    @Override
    public void initData() {
        getNewsInfoFromService(mUrl);
    }

    //从服务器获取数据
    private void getNewsInfoFromService(String url) {
        HttpUtils.requestHttp(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(CODE_SERVICE_REQUEST_ERROR);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if ( response.isSuccessful() && response.code() == 200 ) {
                    String result = response.body().string();
//                    Log.e(TAG, result);
                    LogUtils.e(result);
                    parseNewsInfoData(result);
                } else {
                    Log.e(TAG, "...." + response.code());
                    mHandler.sendEmptyMessage(CODE_SERVICE_RESPONSE_ERROR);
                }
            }
        });
    }

    //解析从服务器获取到的数据
    private void parseNewsInfoData(String result) {
        Gson gson = new Gson();
        newsDetailInfoBean = gson.fromJson(result, NewsDetailInfoBean.class);
        mNewsInfoList = newsDetailInfoBean.data.news;
        mTopNewsInfoList = newsDetailInfoBean.data.topnews;
        mHandler.sendEmptyMessage(CODE_DATAS_OK);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tvTopNewsTitle.setText(mTopNewsInfoList.get(position).title);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class TopNewsAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mTopNewsInfoList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            NewsDetailInfoBean.TopNewsInfo topNewsInfo = mTopNewsInfoList.get(position);
            ImageView image = new ImageView(mActivity);
            int width = DensityUtils.getDevicePx(mActivity)[0];
            int heigth = DensityUtils.dip2px(mActivity, 200);
//            Picasso.with(mActivity)
//                    .load(topNewsInfo.topimage)
//                    .placeholder(R.drawable.topnews_item_default)
//                    .resize(width, heigth)
//                    .centerCrop()
//                    .into(view);
            //使用Picasso封装以后的图片工具类
            DrawableUtils.drawableLoader(mActivity, image, topNewsInfo.topimage, width, heigth, R.drawable.topnews_item_default);
            container.addView(image);
            return image;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    //listview的adapter
    class NewsDetailAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mNewsInfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return mNewsInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            NewsDetailInfoBean.NewsInfo newsInfo = mNewsInfoList.get(position);
            ViewHolder holder;
            if ( convertView == null ) {
                convertView = View.inflate(mActivity, R.layout.item_listview_news_detail, null);
                holder = new ViewHolder();
                holder.tvNewsTitle = (TextView) convertView.findViewById(R.id.tv_news_title);
                holder.tvNewsData = (TextView) convertView.findViewById(R.id.tv_news_data);
                holder.ivNewsImage = (ImageView) convertView.findViewById(R.id.iv_news_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvNewsTitle.setText(newsInfo.title);
            holder.tvNewsData.setText(newsInfo.pubdate);
            int width = DensityUtils.dip2px(mActivity, 120);
            int heigth = DensityUtils.dip2px(mActivity, 70);
            DrawableUtils.drawableLoader(mActivity, holder.ivNewsImage, newsInfo.listimage, width, heigth, R.drawable.pic_item_list_default);
            return convertView;
        }
    }

    static class ViewHolder {
        public TextView tvNewsTitle;
        public TextView tvNewsData;
        public ImageView ivNewsImage;
    }
}
