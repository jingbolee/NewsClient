package com.ljb.zhbj.utils;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @FileName: com.ljb.newsclient.http.HttpUtils.java
 * @Author: Li Jingbo
 * @Date: 2016-05-16 11:34
 * @Version V1.0 http请求类，封装了OKhttp。
 */

//OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url(GlobalContants.CATEGORIES_URL).build();
//        client.newCall(request).enqueue(new Callback() {
//@Override
//public void onFailure(Call call, IOException e) {
//        Toast.makeText(mActivity, "请确认网络是否有问题", Toast.LENGTH_SHORT).show();
//        }
//
//@Override
//public void onResponse(Call call, Response response) throws IOException {
//        if ( response.isSuccessful() && response.code() == 200 ) {
//        System.out.println(response.body().string());
//        String result = response.body().string();
//        parseData(result);
//        }
//
//        }
//        });
public class HttpUtils {
    private static final String TAG = "HttpUtils";

    private static final OkHttpClient mOkHttpClient = new OkHttpClient();
    static {

    }

    private static Request setUrl(String url) {
       return new Request.Builder().url(url).build();
    }

    public  static void requestHttp(String url, Callback callBack){
        Call call = mOkHttpClient.newCall(setUrl(url));
        call.enqueue(callBack);
    }


}
