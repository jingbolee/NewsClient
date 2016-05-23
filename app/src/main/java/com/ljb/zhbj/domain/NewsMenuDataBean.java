package com.ljb.zhbj.domain;

import java.util.List;

/**
 * @FileName: com.ljb.zhbj.domain.NewsMenuDataBean.java
 * @Author: Li Jingbo
 * @Date: 2016-05-21 14:15
 * @Version V1.0 <描述当前版本功能>
 */
public class NewsMenuDataBean {
    private static final String TAG = "NewsMenuDataBean";

    private String retcode;
    private List< NewsMenu > data;
    private List< Integer > extend;

    @Override
    public String toString() {
        return "NewsMenuDataBean{" +
                "data=" + data +
                '}';
    }

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public List< NewsMenu > getData() {
        return data;
    }

    public void setData(List< NewsMenu > data) {
        this.data = data;
    }

    public List< Integer > getExtend() {
        return extend;
    }

    public void setExtend(List< Integer > extend) {
        this.extend = extend;
    }

    public class NewsMenu {
        private String id;
        private String title;
        private int type;
        private List< NewsTab > children;
        private String url;

        @Override
        public String toString() {
            return "NewsMenu{" +
                    "title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    ", children=" + children +
                    '}';
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public List< NewsTab > getChildren() {
            return children;
        }

        public void setChildren(List< NewsTab > children) {
            this.children = children;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public class NewsTab {
        private String id;
        private String title;
        private int type;
        private String url;

        @Override
        public String toString() {
            return "NewsTab{" +
                    "title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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


    }


}
