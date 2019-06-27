package com.base.flyjiang.baseapp.bean;

import com.carking.quotationlibrary.base.BaseBean;

/**
 * 作者：蔡有飞
 * 时间: 2015/8/18 11:00
 * 邮箱：caiyoufei@looip.cn
 * 说明: 欢迎页Bean
 */
public class WelcomeBean extends BaseBean {
    private WelcomeBeanData data = new WelcomeBeanData();

    public WelcomeBeanData getData() {
        return data;
    }

    public void setData(WelcomeBeanData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WelcomeBean{" +
                "data=" + data +
                '}';
    }

    public class WelcomeBeanData {
        private String imageUrl = "";

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        @Override
        public String toString() {
            return "WelcomeBeanData{" +
                    "imageUrl='" + imageUrl + '\'' +
                    '}';
        }
    }
}
