package com.ljb.zhbj.fragment;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.ljb.zhbj.R;
import com.ljb.zhbj.activity.MainActivity;
import com.ljb.zhbj.domain.NewsMenuDataBean;
import com.ljb.zhbj.viewpager.NewsPager;

import java.util.List;

/**
 * @FileName: com.ljb.zhbj.fragment.LeftMenuFragment.java
 * @Author: Li Jingbo
 * @Date: 2016-05-20 09:46
 * @Version V1.0 <描述当前版本功能>
 */
public class LeftMenuFragment extends BaseFragment {
    public static final String TAG = "LeftMenuFragment";
    private ListView lvNewsMenu;
    private int currentPosition;
    private List< NewsMenuDataBean.NewsMenu > mMenuList;
    private NewsLeftMenuAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
        lvNewsMenu = (ListView) view.findViewById(R.id.lv_news_menu);
        return view;
    }

    @Override
    public void initData() {
        lvNewsMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView< ? > parent, View view, int position, long id) {
                currentPosition = position;
                adapter.notifyDataSetChanged();
                setNewsPagerViewFromFragment(currentPosition);
                slidingMenuToggle();
            }
        });
    }

    //选择listview中的内容，关闭slidingmenu侧边栏
    private void slidingMenuToggle() {
        MainActivity mainUi = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUi.getSlidingMenu();
        slidingMenu.toggle();
    }

    private void setNewsPagerViewFromFragment(int position) {
        Log.e(TAG, "从侧滑菜单给ViewPager设置View.position:" + position);
        MainActivity mainUi = (MainActivity) mActivity;
        NewsPager newsPager = mainUi.getContentFragment().getNewsPager();
        newsPager.setNewsPagerView(position);
        newsPager.setTitleText(mMenuList.get(position).getTitle());
    }


    public void setSlidingMenuList(List< NewsMenuDataBean.NewsMenu > menuList) {
        mMenuList = menuList;
        adapter = new NewsLeftMenuAdapter();
        lvNewsMenu.setAdapter(adapter);
        setNewsPagerViewFromFragment(currentPosition);
    }

    //slidingment左侧的listview的adapter
    class NewsLeftMenuAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mMenuList.size();
        }

        @Override
        public Object getItem(int position) {
            return mMenuList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            NewsMenuDataBean.NewsMenu newsMenu = mMenuList.get(position);
            View view = View.inflate(mActivity, R.layout.item_news_menu, null);
            TextView tvNewsMenu = (TextView) view.findViewById(R.id.tv_news_menu);
            tvNewsMenu.setText(newsMenu.getTitle());

            if ( currentPosition == position ) {
                tvNewsMenu.setEnabled(true);
            } else {
                tvNewsMenu.setEnabled(false);
            }

            return view;
        }
    }
}
