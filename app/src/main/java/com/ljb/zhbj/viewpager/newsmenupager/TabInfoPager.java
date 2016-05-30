package com.ljb.zhbj.viewpager.newsmenupager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ljb.zhbj.R;
import com.ljb.zhbj.activity.NewsDetailActivity;
import com.ljb.zhbj.domain.NewsDetailInfoBean;
import com.ljb.zhbj.domain.NewsMenuDataBean;
import com.ljb.zhbj.global.GlobalContants;
import com.ljb.zhbj.utils.CacheUtils;
import com.ljb.zhbj.utils.DensityUtils;
import com.ljb.zhbj.utils.DrawableUtils;
import com.ljb.zhbj.utils.HttpUtils;
import com.ljb.zhbj.utils.MD5Utils;
import com.ljb.zhbj.utils.PreferenUtils;
import com.ljb.zhbj.view.RefreshListView;
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
    private static final int CODE_REFRESH_OK = 4;
    private static final int CODE_LODERMORE_OK = 5;
    private static final int CODE_NO_MORE_URL = 6;
    private String mUrl;
    private ViewPager vpTopNews;
    private RefreshListView lvNewsInfo;
    private TopNewsAdapter mTopNewsAdapter;
    private NewsDetailInfoBean newsDetailInfoBean;
    private List< NewsDetailInfoBean.NewsInfo > mNewsInfoList; //listview用的数据
    private List< NewsDetailInfoBean.TopNewsInfo > mTopNewsInfoList; //头条新闻用的数据
    private CirclePageIndicator indicatorTopNewsCircle;
    private TextView tvTopNewsTitle;

    private NewsDetailAdapter mNewsDetailAdapter;
    private String mMoreUrl;

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
                    if ( mTopNewsAdapter == null ) {
                        mTopNewsAdapter = new TopNewsAdapter();
                        vpTopNews.setAdapter(mTopNewsAdapter);
                    } else {
                        mTopNewsAdapter.notifyDataSetChanged();
                    }
                    tvTopNewsTitle.setText(mTopNewsInfoList.get(0).title);
                    indicatorTopNewsCircle.setViewPager(vpTopNews);
                    indicatorTopNewsCircle.setOnPageChangeListener(TabInfoPager.this);
                    if ( mNewsDetailAdapter == null ) {
                        mNewsDetailAdapter = new NewsDetailAdapter();
                        lvNewsInfo.setAdapter(mNewsDetailAdapter);
                    } else {
                        mNewsDetailAdapter.notifyDataSetChanged();
                        lvNewsInfo.refreshComplete(true);
                    }

                    break;
                case CODE_SERVICE_RESPONSE_ERROR:
                    Toast.makeText(mActivity, "服务器返回的数据有问题", Toast.LENGTH_SHORT).show();
                    lvNewsInfo.refreshComplete(true);
                    break;

                case CODE_SERVICE_REQUEST_ERROR:
                    Toast.makeText(mActivity, "请检查网络，是否可以上网", Toast.LENGTH_SHORT).show();
                    lvNewsInfo.refreshComplete(false);
                    break;

                case CODE_NO_MORE_URL:
                    Toast.makeText(mActivity, "已到最后一页了...", Toast.LENGTH_SHORT).show();
                    lvNewsInfo.refreshComplete(true);
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
        lvNewsInfo = (RefreshListView) view.findViewById(R.id.lv_news_info);

        lvNewsInfo.addHeaderView(headerView);

        lvNewsInfo.setRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNewsInfoFromService(mUrl);
            }

            @Override
            public void onLoadMore() {
                if ( mMoreUrl != null ) {
                    getNewsDataMoreFromService(mMoreUrl);
                } else {
                    mHandler.sendEmptyMessage(CODE_NO_MORE_URL);
                }
            }
        });

        lvNewsInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView< ? > parent, View view, int position, long id) {
                String url = mNewsInfoList.get(position).url;
                TextView tvTitle = (TextView) view.findViewById(R.id.tv_news_title);
                tvTitle.setTextColor(Color.GRAY);
                String readItem = PreferenUtils.getReadItem(mActivity);
                if ( TextUtils.isEmpty(readItem) || !readItem.contains(mNewsInfoList.get(position).id) ) {
                    PreferenUtils.putReadItem(mActivity, mNewsInfoList.get(position).id);
                }
                Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                intent.putExtra("url", url);
                mActivity.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void initData() {
        String cache = CacheUtils.getCache(mActivity, MD5Utils.encode(mUrl));
        if ( !TextUtils.isEmpty(cache) ) {
            parseNewsInfoData(cache, false);
        }
        getNewsInfoFromService(mUrl);
    }

    //从服务器获取数据
    private void getNewsInfoFromService(final String url) {
        HttpUtils.requestHttp(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessageDelayed(CODE_SERVICE_REQUEST_ERROR, 500);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if ( response.isSuccessful() && response.code() == 200 ) {
                    String result = response.body().string();
                    parseNewsInfoData(result, false);
                    CacheUtils.putCache(mActivity, MD5Utils.encode(url), result);
                } else {
                    Log.e(TAG, "...." + response.code());
                    mHandler.sendEmptyMessage(CODE_SERVICE_RESPONSE_ERROR);
                }

            }
        });
    }

    //从服务器请求下一页数据
    private void getNewsDataMoreFromService(String moreUrl) {
        HttpUtils.requestHttp(moreUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(CODE_SERVICE_REQUEST_ERROR);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if ( response.isSuccessful() && response.code() == 200 ) {
                    String result = response.body().string();
                    parseNewsInfoData(result, true);
                } else {
                    mHandler.sendEmptyMessageDelayed(CODE_SERVICE_RESPONSE_ERROR, 500);
                }
            }
        });
    }

    //解析从服务器获取到的数据
    private void parseNewsInfoData(String result, boolean isMore) {
        Gson gson = new Gson();
        newsDetailInfoBean = gson.fromJson(result, NewsDetailInfoBean.class);
        String more = newsDetailInfoBean.data.more;
        if ( !TextUtils.isEmpty(more) ) {
            mMoreUrl = GlobalContants.SERVICE_HTTP + more;
        } else {
            mMoreUrl = null;
        }
        if ( !isMore ) {
            mNewsInfoList = newsDetailInfoBean.data.news;
            mTopNewsInfoList = newsDetailInfoBean.data.topnews;
        } else {
            mNewsInfoList.addAll(newsDetailInfoBean.data.news);
        }
        mHandler.sendEmptyMessageDelayed(CODE_DATAS_OK, 500);

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
            String readItem = PreferenUtils.getReadItem(mActivity);

            String id = newsInfo.id;

            if ( !TextUtils.isEmpty(readItem) && readItem.contains(id) ) {
                holder.tvNewsTitle.setTextColor(Color.GRAY);
            } else {
                holder.tvNewsTitle.setTextColor(Color.BLACK);
            }
            holder.tvNewsData.setText(newsInfo.pubdate);
            //主动让系统去测量holder.ivNewsImage的宽和高
            convertView.measure(0, 0);
            int measuredWidth = holder.ivNewsImage.getMeasuredWidth();
            int measuredHeight = holder.ivNewsImage.getMeasuredHeight();
            DrawableUtils.drawableLoader(mActivity, holder.ivNewsImage, newsInfo.listimage, measuredWidth, measuredHeight, R.drawable.pic_item_list_default);
            return convertView;
        }
    }

    //listview的ViewHolder
    static class ViewHolder {
        public TextView tvNewsTitle;
        public TextView tvNewsData;
        public ImageView ivNewsImage;
    }
}
