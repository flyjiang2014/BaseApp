package com.carking.quotationlibrary.utils;

import android.util.Base64;

/**
 * 作者：flyjiang
 * 时间: 2015/5/20 15:20
 * 邮箱：caiyoufei@looip.cn
 * 说明: 用于Base64加解密
 */
public class Base64Util {
    /**
     * 加密为Base64
     *
     * @param data 加密前的值
     * @return 加密后的值
     */
    public static String stringToBase64(String data) {
        return Base64.encodeToString(data.getBytes(), Base64.DEFAULT);
    }

    /**
     * 解密为String
     *
     * @param data 解密前的值
     * @return 解密后的值
     */
    public static String base64Tostring(String data) {
        return new String(Base64.decode(data, Base64.DEFAULT));
    }
}
