package com.ljb.newsclient.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ljb.newsclient.R;
import com.ljb.newsclient.domain.NewsData;
import com.ljb.newsclient.global.GlobalContants;
import com.ljb.newsclient.http.HttpUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 *
 */
public class NewsFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = "NewsFragment";
    public static final String TITLE = "新闻中心";

    public static final int CODE_SERVICE_DATA_OK = 0;

    private ListView lvNewsMenu;
    private RelativeLayout rlMenu;
    private SlidingMenu slidingMenu;

    private int mCurrentPos; //当前选择的menu
    private NewsMenuAdatper newsMenuAdatper;

    private List< NewsData.MenuData > menuDatas;
    private ViewPager vpNewstabcontent;


    public NewsFragment() {
        // Required empty public constructor
    }

    Handler mHandler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch ( msg.what ) {
                case CODE_SERVICE_DATA_OK:
                    newsMenuAdatper = new NewsMenuAdatper();
                    lvNewsMenu.setAdapter(newsMenuAdatper);

                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getNetWorkInfo();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText(getFragmentTitle());
        rlMenu = (RelativeLayout) view.findViewById(R.id.rl_menu);
        slidingMenu = (SlidingMenu) view.findViewById(R.id.slidingmenu);
        lvNewsMenu = (ListView) view.findViewById(R.id.lv_news_menu);
        vpNewstabcontent = (ViewPager) view.findViewById(R.id.vp_newsTabContent);
        return view;
    }

    @Override
    protected void initData() {
        lvNewsMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView< ? > parent, View view, int position, long id) {
                slidingMenu.toggle();
                showNewsTabContent();
                mCurrentPos = position;
                if ( newsMenuAdatper!=null ){
                    newsMenuAdatper.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public String getFragmentTitle() {
        return TITLE;
    }

    @Override
    public void showSlideMenuIcom() {
        rlMenu.setVisibility(View.VISIBLE);
        if ( rlMenu.isClickable() ) {
            rlMenu.setOnClickListener(this);
        }
    }


    //注意：okhttp的回调函数是异步获取的，所以获取到的数据在子线程，无法直接更新UI线程。
    private void getNetWorkInfo() {
        HttpUtils.requestHttp(GlobalContants.CATEGORIES_URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mActivity, "网络请求失败：", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("网络请求结果：" + response.isSuccessful() + " code:" + response.code());
//                System.out.println("网络请求：" + response.body().string());
                if ( response.isSuccessful() && response.code() == 200 ) {
                    String result = response.body().string();
                    parseData(result);
                }
            }
        });
    }


    //解析数据
    private void parseData(String data) {
        Gson gson = new Gson();
        NewsData newsData = gson.fromJson(data, NewsData.class);
        menuDatas = newsData.getData();
        mHandler.sendEmptyMessage(CODE_SERVICE_DATA_OK);

    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.rl_menu:
                slidingMenu.toggle();
                break;
        }
    }

    private void showNewsTabContent() {
    }


    class NewsMenuAdatper extends BaseAdapter {

        @Override
        public int getCount() {
            return menuDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return menuDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            NewsData.MenuData data = menuDatas.get(position);
            View view = View.inflate(mActivity, R.layout.item_menu_left, null);
            TextView tvMenuName = (TextView) view.findViewById(R.id.tv_menu_name);
            tvMenuName.setText(data.getTitle());
            if ( mCurrentPos == position ) {
                tvMenuName.setEnabled(true);
            } else {
                tvMenuName.setEnabled(false);
            }
            return view;
        }
    }
}
