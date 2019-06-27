package com.base.flyjiang.baseapp.daStringData;

import org.litepal.crud.DataSupport;

/**
 * Created by ${flyjiang} on 2017/2/15.
 * 文件说明：
 */
public class DbProductBean extends DataSupport {
    private String id;
    private String result;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
