package com.ljb.newsclient.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ljb.newsclient.R;
import com.ljb.newsclient.fragment.BaseFragment;
import com.ljb.newsclient.fragment.GovFragment;
import com.ljb.newsclient.fragment.HomeFragment;
import com.ljb.newsclient.fragment.NewsFragment;
import com.ljb.newsclient.fragment.SettingFragment;
import com.ljb.newsclient.fragment.SmartFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FrameLayout flContentContainer;
    private RadioGroup rgGroup;
    private List< RadioButton > radioButtonList;
    private RadioButton rbHome;
    private RadioButton rbSmart;
    private RadioButton rbSetting;
    private RadioButton rbGov;
    private RadioButton rbNews;
//    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();


    }

    private void initView() {
        setContentView(R.layout.activity_main);
        flContentContainer = (FrameLayout) findViewById(R.id.fl_content_container);
        rgGroup = (RadioGroup) findViewById(R.id.rg_group);
//        tvTitle = (TextView) findViewById(R.id.tv_title);
        rbHome = (RadioButton) findViewById(R.id.rb_home);
        rbSmart = (RadioButton) findViewById(R.id.rb_smart_service);
        rbGov = (RadioButton) findViewById(R.id.rb_gov);
        rbNews = (RadioButton) findViewById(R.id.rb_news);
        rbSetting = (RadioButton) findViewById(R.id.rb_setting);


        radioButtonList = new ArrayList<>();
        radioButtonList.add(rbHome);
        radioButtonList.add(rbGov);
        radioButtonList.add(rbNews);
        radioButtonList.add(rbSetting);
        radioButtonList.add(rbSmart);

        //进入app默认实现主页
        showFragment(new HomeFragment(), HomeFragment.TAG);
        rbHome.setChecked(true);
    }

    private void initData() {
    }

    private void initListener() {

        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch ( checkedId ) {
                    case R.id.rb_home:
                        showFragment(new HomeFragment(), HomeFragment.TAG);
                        rbHome.setChecked(true);
                        break;
                    case R.id.rb_news:
                        showFragment(new NewsFragment(), NewsFragment.TAG);
                        rbNews.setChecked(true);
                        break;
                    case R.id.rb_smart_service:
                        showFragment(new SmartFragment(), SmartFragment.TAG);
                        rbSmart.setChecked(true);
                        break;
                    case R.id.rb_gov:
                        showFragment(new GovFragment(), GovFragment.TAG);
                        rbGov.setChecked(true);
                        break;
                    case R.id.rb_setting:
                        showFragment(new SettingFragment(), SettingFragment.TAG);
                        rbSetting.setChecked(true);
                        break;
                }
            }
        });
    }

    //切换fragment
    private void showFragment(BaseFragment fragment, String TAG) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_content_container, fragment);
        transaction.commit();
//        tvTitle.setText(fragment.getFragmentTitle());
    }
}
