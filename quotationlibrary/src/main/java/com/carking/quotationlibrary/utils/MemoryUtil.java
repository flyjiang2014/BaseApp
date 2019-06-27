package com.carking.quotationlibrary.utils;

/**
 * 作者：flyjiang
 * 时间: 2015/5/20 14:56
 * 邮箱：caiyoufei@looip.cn
 * 说明: 用于获取APP内存信息
 */
public class MemoryUtil {
    /**
     * 获取APP最大可用内存
     *
     * @return APP最大可用内存
     */
    public static long getMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }
}
