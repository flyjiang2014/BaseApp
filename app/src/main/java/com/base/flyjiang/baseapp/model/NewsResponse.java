package com.base.flyjiang.baseapp.model;

import java.io.Serializable;

/**
 * ================================================
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class NewsResponse<T> implements Serializable{

    private static final long serialVersionUID = -686453405647539973L;

    public String showapi_res_error;
    public int showapi_res_code;
    public T showapi_res_body;
}