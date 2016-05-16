package com.ljb.newsclient.domain;

import java.util.Arrays;
import java.util.List;

/**
 * @FileName: com.ljb.newsclient.domain.NewsData.java
 * @Author: Li Jingbo
 * @Date: 2016-05-13 14:37
 * @Version V1.0 <描述当前版本功能>
 */
public class NewsData {
    private int retcode;
    private List<MenuData> data;
    private int[] extend;

    @Override
    public String toString() {
        return "NewsData{" +
                "retcode=" + retcode +
                ", data=" + data +
                ", extend=" + Arrays.toString(extend) +
                '}';
    }

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public List< MenuData > getData() {
        return data;
    }

    public void setData(List< MenuData > data) {
        this.data = data;
    }

    public int[] getExtend() {
        return extend;
    }

    public void setExtend(int[] extend) {
        this.extend = extend;
    }

    public class MenuData{
        private String id;
        private String title;
        private int type;
        private String url;

        private List<NewsTabData> children;

        @Override
        public String toString() {
            return "MenuData{" +
                    "title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }


        public List< NewsTabData > getChildren() {
            return children;
        }

        public void setChildren(List< NewsTabData > children) {
            this.children = children;
        }
    }

    public class NewsTabData{
        private String id;
        private String title;
        private int type;
        private String url;

        @Override
        public String toString() {
            return "NewsTabData{" +
                    "title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
