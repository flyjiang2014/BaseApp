package com.base.flyjiang.baseapp.bean;

/**
 * Created by ${flyjiang} on 2016/8/2.
 * 文件说明：登入成功返回bean
 */
public class LoginSuccessBean {
    private String result="";
    private String token="";
    private String imPassword="";
    private String imUsername="";
    private String portrait ="";
    private String reason ="";

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImPassword() {
        return imPassword;
    }

    public void setImPassword(String imPassword) {
        this.imPassword = imPassword;
    }

    public String getImUsername() {
        return imUsername;
    }

    public void setImUsername(String imUsername) {
        this.imUsername = imUsername;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
