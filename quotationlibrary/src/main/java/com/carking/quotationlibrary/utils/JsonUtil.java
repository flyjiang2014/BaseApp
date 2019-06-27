package com.carking.quotationlibrary.utils;

import com.alibaba.fastjson.JSON;

/**
 * 作者：flyjiang
 * 时间: 2015/5/20 15:14
 * 邮箱：caiyoufei@looip.cn
 * 说明: 用于JSON数据的处理(https://github.com/alibaba/fastjson)
 */
public class JsonUtil {
    /**
     * bean转换为json格式的string
     *
     * @param obj 转换前的JSON对象
     * @return 转换后字符串
     */
    public static String bean2Json(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * Sting转换为bean
     *
     * @param jsonStr  转换前的string
     * @param objClass 需要转换的bean类
     * @param <T>      泛型类
     * @return 转换后的bean，转换失败返回null
     */
    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
        try {
            return JSON.parseObject(jsonStr, objClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
