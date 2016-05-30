package com.ljb.zhbj.utils;

import android.content.Context;

/**
 * @FileName: com.ljb.zhbj.utils.CacheUtils.java
 * @Author: Li Jingbo
 * @Date: 2016-05-30 09:44
 * @Version V1.0 Cache缓存工具
 */
public class CacheUtils {
    private static final String TAG = "CacheUtils";

    public static void putCache(Context context, String url, String json) {
        PreferenUtils.putString(context, url, json);
    }

    //返回为null的时候，证明没有做缓存，需要从网络获取数据
    public static String getCache(Context context, String url) {
        return PreferenUtils.getString(context, url, null);
    }
}
