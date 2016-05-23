package com.ljb.zhbj.fragment;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.ljb.zhbj.R;
import com.ljb.zhbj.view.NoScrollViewPager;
import com.ljb.zhbj.viewpager.BasePager;
import com.ljb.zhbj.viewpager.GovPager;
import com.ljb.zhbj.viewpager.HomePager;
import com.ljb.zhbj.viewpager.NewsPager;
import com.ljb.zhbj.viewpager.SettingsPager;
import com.ljb.zhbj.viewpager.SmartPager;

import java.util.ArrayList;
import java.util.List;

/**
 * ContentFragment
 */
public class ContentFragment extends BaseFragment {

    public static final String TAG = "ContentFragment";
    private NoScrollViewPager vpContent;
    private List< BasePager > mRadioList;
    private RadioGroup rgGroup;


    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragemnt_content, null);
        vpContent = (NoScrollViewPager) view.findViewById(R.id.vp_content);
        rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);
        return view;
    }

    @Override
    public void initData() {
        mRadioList = new ArrayList<>();
        mRadioList.add(new HomePager(mActivity));
        mRadioList.add(new NewsPager(mActivity));
        mRadioList.add(new SmartPager(mActivity));
        mRadioList.add(new GovPager(mActivity));
        mRadioList.add(new SettingsPager(mActivity));
        ViewContentAdapter adapter = new ViewContentAdapter();
        vpContent.setAdapter(adapter);
        vpContent.setCurrentItem(0);

        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mRadioList.get(position).initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mRadioList.get(0).initData();

        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch ( checkedId ) {
                    case R.id.rb_home:
                        vpContent.setCurrentItem(0, false);
                        break;
                    case R.id.rb_news:
                        vpContent.setCurrentItem(1, false);
                        break;
                    case R.id.rb_smart_service:
                        vpContent.setCurrentItem(2, false);
                        break;
                    case R.id.rb_gov:
                        vpContent.setCurrentItem(3, false);
                        break;
                    case R.id.rb_setting:
                        vpContent.setCurrentItem(4, false);
                        break;
                }
            }
        });
    }


    class ViewContentAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mRadioList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager basePager = mRadioList.get(position);
            container.addView(basePager.mRootView);
            return basePager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public NewsPager getNewsPager() {
        return (NewsPager) mRadioList.get(1);
    }
}
