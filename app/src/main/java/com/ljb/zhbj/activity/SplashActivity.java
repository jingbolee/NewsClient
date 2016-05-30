package com.ljb.zhbj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.ljb.zhbj.R;
import com.ljb.zhbj.utils.PreferenUtils;

public class SplashActivity extends Activity {
    private ImageView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        view = (ImageView) findViewById(R.id.view_bg);
        openAnimation(view);

    }

    private void openAnimation(View view) {
        AnimationSet set = new AnimationSet(false);
        //设置渐变动画
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(1000);
        alpha.setFillAfter(true);

        //设置缩放动画
        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(1000);
        scale.setFillAfter(true);

        //设置旋转动画
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(2000);
        rotate.setFillAfter(true);


        set.addAnimation(alpha);
        set.addAnimation(scale);
        set.addAnimation(rotate);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                goToNextActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(set);

    }

    private void goToNextActivity() {
        //判断是否第一次运行
        boolean isUserFirst = PreferenUtils.getBoolean(SplashActivity.this, "is_user_first", true);
        if ( isUserFirst ) {
            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
        finish();
    }

}
