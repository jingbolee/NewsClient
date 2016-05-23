package com.ljb.zhbj.viewpager;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.ljb.zhbj.activity.MainActivity;
import com.ljb.zhbj.domain.NewsMenuDataBean;
import com.ljb.zhbj.global.GlobalContants;
import com.ljb.zhbj.utils.HttpUtils;
import com.ljb.zhbj.viewpager.newsmenupager.BaseMenuDetailPager;
import com.ljb.zhbj.viewpager.newsmenupager.InteractDetailPager;
import com.ljb.zhbj.viewpager.newsmenupager.NewsDetailPager;
import com.ljb.zhbj.viewpager.newsmenupager.PhotoDetailPager;
import com.ljb.zhbj.viewpager.newsmenupager.TopicDetailPager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @FileName: com.ljb.zhbj.viewpager.HomePager.java
 * @Author: Li Jingbo
 * @Date: 2016-05-20 10:35
 * @Version V1.0 <描述当前版本功能>
 */
public class NewsPager extends BasePager {
    private static final String TAG = "NewsPager";
    private static final int CODE_LEFT_MENU_OK = 0;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch ( msg.what ) {
                case CODE_LEFT_MENU_OK:
                    setSlidingMenuDataFromPager(newsMenus);
                    break;
            }
        }
    };
    private List< NewsMenuDataBean.NewsMenu > newsMenus;
    private List< BaseMenuDetailPager > mPagerList;

    public NewsPager(Activity activity) {
        super(activity);
    }


    @Override
    public void initData() {
        super.initData();
        mPagerList = new ArrayList<>();
        mPagerList.add(new NewsDetailPager(mActivity));
        mPagerList.add(new TopicDetailPager(mActivity));
        mPagerList.add(new PhotoDetailPager(mActivity));
        mPagerList.add(new InteractDetailPager(mActivity));

        setSlidingMenuShow(true);
        getServiceInfo();

    }

    //从服务器获取数据
    private void getServiceInfo() {
        HttpUtils.requestHttp(GlobalContants.CATEGORIES_URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if ( response.isSuccessful() && response.code() == 200 ) {
                    String result = response.body().string();
                    Log.e(TAG, result);
                    parseMenuData(result);
                } else {
                    Log.e(TAG, "服务器返回的数据有问题:" + response.code() + " ," + response.isSuccessful());
                }
            }
        });
    }

    //解析从服务器获取的数据
    private void parseMenuData(String result) {
        Gson gson = new Gson();
        NewsMenuDataBean newsMenuDataBean = gson.fromJson(result, NewsMenuDataBean.class);
        newsMenus = newsMenuDataBean.getData();
        mHandler.sendEmptyMessage(CODE_LEFT_MENU_OK);
    }

    //给slidingmenu侧滑菜单设置数据
    private void setSlidingMenuDataFromPager(List< NewsMenuDataBean.NewsMenu > newsMenus) {
        MainActivity mainUi = (MainActivity) mActivity;
        mainUi.getLeftMenuFragment().setSlidingMenuList(newsMenus);
    }


    public void setNewsPagerView(int position) {
        setFrameLayoutView(mPagerList.get(position).mRootView);
    }

}
