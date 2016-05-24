package com.ljb.zhbj.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * @FileName: com.ljb.zhbj.utils.DrawableUtils.java
 * @Author: Li Jingbo
 * @Date: 2016-05-24 09:28
 * @Version V1.0 图片加载类，封装了Picasso
 */
public class DrawableUtils {
    private static final String TAG = "DrawableUtils";

//    Picasso.with(mActivity)
//            .load(topNewsInfo.topimage)
//    .placeholder(R.drawable.topnews_item_default)
//    .resize(width, heigth)
//    .centerCrop()
//    .into(view);

    public static void drawableLoader(Context context, ImageView view, String url, int viewWidth, int viewHeigth,int placeholderResId) {
        Picasso.with(context)
                .load(url)
                .placeholder(placeholderResId)
                .resize(viewWidth, viewHeigth)
                .centerCrop()
                .into(view);
    }

    public static void drawableLoader(Context context, ImageView view, String url) {
        Picasso.with(context)
                .load(url)
                .into(view);
    }
}
