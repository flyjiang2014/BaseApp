package com.base.flyjiang.baseapp.bean;

import com.carking.quotationlibrary.base.BaseBean;

/**
 * 作者：蔡有飞
 * 时间: 2015/5/26 17:40
 * 邮箱：caiyoufei@looip.cn
 * 说明: APP更新结果Bean
 */
public class UpdateBean extends BaseBean {

    private UpdateBeanData data = new UpdateBeanData();
    public UpdateBeanData getData() {
        return data;
    }

    public void setData(UpdateBeanData data) {
        this.data = data;
    }

    public class UpdateBeanData {
        private boolean forceUpdate = false;
        private boolean update = false;
        private String versionInfo = "";
        private String downloadUrl = "";
        private String version = "";

        public boolean isForceUpdate() {
            return forceUpdate;
        }

        public boolean isUpdate() {
            return update;
        }

        public String getVersionInfo() {
            return versionInfo;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public String getVersion() {
            return version;
        }

        public void setForceUpdate(boolean forceUpdate) {
            this.forceUpdate = forceUpdate;
        }

        public void setUpdate(boolean update) {
            this.update = update;
        }

        public void setVersionInfo(String versionInfo) {
            this.versionInfo = versionInfo;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        @Override
        public String toString() {
            return "UpdateBeanData{" +
                    "forceUpdate=" + forceUpdate +
                    ", update=" + update +
                    ", versionInfo='" + versionInfo + '\'' +
                    ", downloadUrl='" + downloadUrl + '\'' +
                    ", version='" + version + '\'' +
                    '}';
        }
    }
}
