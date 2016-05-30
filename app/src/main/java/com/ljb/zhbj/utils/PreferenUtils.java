package com.ljb.zhbj.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @FileName: com.ljb.zhbj.utils.PreferenUtils.java
 * @Author: Li Jingbo
 * @Date: 2016-05-30 09:46
 * @Version V1.0 SharePrefenerce工具类
 */
public class PreferenUtils {
    private static final String TAG = "PreferenUtils";
    public static final String PREFER_NAME = "config";


    public static void putString(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        return preferences.getString(key, defaultValue);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defaultValue);
    }

    //标记是否阅读过item
    public static void putReadItem(Context context, String itemId) {
        String key = "read_item";
        SharedPreferences preferences = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        String records = preferences.getString(key, null);
        records = records + "," + itemId;
        preferences.edit().putString(key, records).commit();
    }

    public static String getReadItem(Context context) {
        String key = "read_item";
        SharedPreferences preferences = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        return preferences.getString(key, null);
    }
}
