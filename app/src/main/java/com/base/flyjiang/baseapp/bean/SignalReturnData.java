package com.base.flyjiang.baseapp.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flyjiang on 2016/11/30.
 *
 */

public class SignalReturnData extends DataSupport implements Serializable {
    private int more;
    private int page;
    private List<ProductBean> items = new ArrayList<ProductBean>();
    private List<String> list = new ArrayList<>();

    public SignalReturnData(int more, int page) {
        this.more = more;
        this.page = page;
    }

    public SignalReturnData(int more, int page, List<ProductBean> items) {
        this.more = more;
        this.page = page;
        this.items = items;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

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

    @Override
    public String toString() {
        return "SignalReturnData{" +
                "more=" + more +
                ", page=" + page +
                '}';
    }
}
