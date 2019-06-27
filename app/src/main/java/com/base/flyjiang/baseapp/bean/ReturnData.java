package com.base.flyjiang.baseapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${flyjiang} on 2016/11/30.
 * 文件说明：
 */
public class ReturnData implements Serializable{


    private String success ="";
    private String message = "";
    private TwoReturnData data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public TwoReturnData getData() {
        return data;
    }

    public void setData(TwoReturnData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class TwoReturnData{
       private int more ;
       private int page;
       private List<ProductBean> items = new ArrayList<>();

        public int getMore() {
            return more;
        }

        public void setMore(int more) {
            this.more = more;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public List<ProductBean> getItems() {
            return items;
        }

        public void setItems(List<ProductBean> items) {
            this.items = items;
        }
    }

}
