package com.carking.quotationlibrary.utils;

import java.math.BigDecimal;

/**
 * 作者：flyjiang
 * 时间: 2015/5/20 15:14
 * 邮箱：caiyoufei@looip.cn
 * 说明: 用于数学数据的处理
 */
public class MathUtil {
    /**
     * 四舍五入保留确定位数小数
     *
     * @param number  原数
     * @param decimal 保留几位小数
     * @return 四舍五入后的值
     */
    public static double round(double number, int decimal) {
        return new BigDecimal(number).setScale(decimal, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
