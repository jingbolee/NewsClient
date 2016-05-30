package com.ljb.zhbj.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.ljb.zhbj.R;

public class NewsDetailActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "NewsDetailActivity";

    private RelativeLayout rlBack;
    private ImageButton btnShare;
    private ImageButton btnText;
    private WebView mWebView;
    private WebSettings mWebViewSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        btnShare = (ImageButton) findViewById(R.id.btn_share);
        btnText = (ImageButton) findViewById(R.id.btn_text_size);
        mWebView = (WebView) findViewById(R.id.wv_webview);
        rlBack.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnText.setOnClickListener(this);
        String url = getIntent().getStringExtra("url");

        //获取到webview的设置
        mWebViewSettings = mWebView.getSettings();
        mWebViewSettings.setJavaScriptEnabled(true);//设置支持javaScript
//        mWebViewSettings.setBuiltInZoomControls(true);//支持放大和缩小
//        mWebViewSettings.setDisplayZoomControls(true);
        mWebViewSettings.setUseWideViewPort(true);  //支持双击缩放
        mWebView.loadUrl(url);//webview加载url
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.rl_back:
                finish();
                break;

            case R.id.btn_share:
                showShare();
                break;

            case R.id.btn_text_size:
                showTextSize();
                break;
        }
    }

    //分享，通过ShareSDK
    private void showShare() {

        //需要填写ShareSDK分享的代码
    }

    private int mCurrentItem = 2; //默认选中正常字体,这个变量是保存的用户确定后的选择
    private int mCurrentSiteItem; //这个是用户没有点击确定的选择，要把这个变量传递到webview的setting中

    //设置webview的字体大小
    private void showTextSize() {
        String[] items = new String[]{"超大号字体", "大号字体", "正常字体", "小号字体", "超小号字体"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置字体大小");
        builder.setSingleChoiceItems(items, mCurrentItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCurrentSiteItem = which;
            }
        });

//        SMALLEST(50),
//                SMALLER(75),
//                NORMAL(100),
//                LARGER(150),
//                LARGEST(200);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch ( mCurrentSiteItem ) {
                    case 0:
                        mWebViewSettings.setTextZoom(200);
                        break;

                    case 1:
                        mWebViewSettings.setTextZoom(150);
                        break;

                    case 2:
                        mWebViewSettings.setTextZoom(100);
                        break;

                    case 3:
                        mWebViewSettings.setTextZoom(75);
                        break;

                    case 4:
                        mWebViewSettings.setTextZoom(50);
                        break;
                }
                mCurrentItem = mCurrentSiteItem; //当点击确定以后，这两个变量才会相等
            }
        });

        builder.setNegativeButton("取消", null);
        builder.show();
    }
}
