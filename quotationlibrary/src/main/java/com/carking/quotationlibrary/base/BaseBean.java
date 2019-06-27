package com.carking.quotationlibrary.base;

import org.litepal.crud.DataSupport;

/**
 * 作者：flyjiang
 * 时间: 2015/6/3 16:37
 * 邮箱：caiyoufei@looip.cn
 * 说明: 需要保存到数据库的信息，直接继承DataSupport(应用需要添加到assets文件夹下的litepal.xml中)
 */
public class BaseBean extends DataSupport {
    protected int status = 0;
    protected String message = "";

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
