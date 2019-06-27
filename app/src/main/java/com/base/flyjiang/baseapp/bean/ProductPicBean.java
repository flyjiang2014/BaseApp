package com.base.flyjiang.baseapp.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/7.
 */
public class ProductPicBean extends DataSupport implements Serializable {
    String pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
