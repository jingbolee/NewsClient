package com.ljb.zhbj.domain;

import java.util.List;

/**
 * @FileName: com.ljb.zhbj.domain.NewsDetailInfoBean.java
 * @Author: Li Jingbo
 * @Date: 2016-05-23 16:08
 * @Version V1.0 <描述当前版本功能>
 */
public class NewsDetailInfoBean {
    private static final String TAG = "NewsDetailInfoBean";

    public String retcode;
    public Data data;


    public class Data {

        public String more;
        public String title;
        public List< NewsInfo > news;
        public List< TopicInfo > topic;
        public List< TopNewsInfo > topnews;


    }

    public class NewsInfo {
        public String url;
        public String type;
        public String title;
        public String pubdate;
        public String listimage;
        public String id;
    }

    public class TopicInfo {
        public String description;
        public String listimage;
        public String sort;
        public String title;
        public String url;
    }

    public class TopNewsInfo {
        public String pubdate;
        public String title;
        public String topimage;
        public String type;
        public String url;
    }
}
