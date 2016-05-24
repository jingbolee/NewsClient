package com.ljb.zhbj.viewpager.newsmenupager;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
 * @Version V1.0 <描述当前版本功能>
 */
public class TabInfoPager extends BaseMenuDetailPager {
    private static final String TAG = "TabInfoPager";
    public static final int CODE_DATAS_OK = 0;
    public static final int CODE_SERVICE_RESPONSE_ERROR = 1;
    public static final int CODE_SERVICE_REQUEST_ERROR = 2;
    private String mUrl;
    private ViewPager vpTopNews;
    private ListView lvNewsInfo;
    private TopNewsAdapter mTopNewsAdapter;
    private NewsDetailInfoBean newsDetailInfoBean;
    private List< NewsDetailInfoBean.NewsInfo > newsInfoList;
    private List< NewsDetailInfoBean.TopNewsInfo > topNewsInfoList;
    private CirclePageIndicator indicatorTopNewsCircle;
    private TextView tvTopNewsTitle;

    public TabInfoPager(Activity activity, NewsMenuDataBean.NewsTab newsTab) {
        super(activity);
        mUrl = GlobalContants.HTTP_URL + newsTab.getUrl();
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
        vpTopNews = (ViewPager) view.findViewById(R.id.vp_top_news);
        lvNewsInfo = (ListView) view.findViewById(R.id.lv_news_info);
        tvTopNewsTitle = (TextView) view.findViewById(R.id.tv_top_news_title);
        indicatorTopNewsCircle = (CirclePageIndicator) view.findViewById(R.id.indicator_top_news_circle);
        return view;

    }

    @Override
    public void initData() {
        getNewsInfoFromService(mUrl);

    }

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

    private void parseNewsInfoData(String result) {
        Gson gson = new Gson();
        newsDetailInfoBean = gson.fromJson(result, NewsDetailInfoBean.class);
        newsInfoList = newsDetailInfoBean.data.news;
        topNewsInfoList = newsDetailInfoBean.data.topnews;
        mHandler.sendEmptyMessage(CODE_DATAS_OK);
    }

    class TopNewsAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return topNewsInfoList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            NewsDetailInfoBean.TopNewsInfo topNewsInfo = topNewsInfoList.get(position);
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
}
