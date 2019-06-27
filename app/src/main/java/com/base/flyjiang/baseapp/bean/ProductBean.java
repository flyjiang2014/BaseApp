package com.base.flyjiang.baseapp.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * 商品实体类
 * Created by Administrator on 2016/3/18.
 *
 */
public class ProductBean extends DataSupport implements Serializable {
   // String id;
    private String originalPrice;
    private int stockNum;
    private String presentPrice;
    private String name;
    private int state; //状态  0：新建，1:上线,2:下线,9：删除
    private int peopleNum;
    private int saleNum;
    private String startDate;
    private String endDate;
    private String enterStartTime;
    private  String enterEndTime;
    private String surplusNum;
    private String description;
    private SignalReturnData signalReturnData;

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getStockNum() {
        return stockNum;
    }

    public void setStockNum(int stockNum) {
        this.stockNum = stockNum;
    }

    public String getPresentPrice() {
        return presentPrice;
    }

    public void setPresentPrice(String presentPrice) {
        this.presentPrice = presentPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
    }

    public int getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEnterStartTime() {
        return enterStartTime;
    }

    public void setEnterStartTime(String enterStartTime) {
        this.enterStartTime = enterStartTime;
    }

    public String getEnterEndTime() {
        return enterEndTime;
    }

    public void setEnterEndTime(String enterEndTime) {
        this.enterEndTime = enterEndTime;
    }

    public String getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(String surplusNum) {
        this.surplusNum = surplusNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //   List<ProductPicBean> pics;


/*
    public List<ProductPicBean> getPics() {
        return pics;
    }

    public void setPics(List<ProductPicBean> pic) {
        this.pics = pics;
    }*/

    public SignalReturnData getSignalReturnData() {
        return signalReturnData;
    }

    public void setSignalReturnData(SignalReturnData signalReturnData) {
        this.signalReturnData = signalReturnData;
    }
}
