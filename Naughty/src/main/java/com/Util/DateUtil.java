package com.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fowafolo
 * Date: 16/3/30
 * Time: 14:37
 */
public class DateUtil {

    /**
     * 默认为年月日时分秒 24小时制
     */
    private static String defaultDatePattern = "yyyy-MM-dd HH:mm:ss";

    /**
     * 返回预设Format的当前日期字符串
     */
    public static String getTodayStr()
    {
        Date today = new Date();
        return getStrByDefaultFormat(today);
    }

    /**
     * 使用预设Format格式化Date成字符串
     */
    public static String getStrByDefaultFormat(Date date)
    {
        return date == null ? " " : getStrByCustomFormat(date, getDefaultDatePattern());
    }

    /**
     * 使用参数Format格式化Date成字符串
     */
    public static String getStrByCustomFormat(Date date, String pattern)
    {
        return date == null ? " " : new SimpleDateFormat(pattern).format(date);
    }

    public static String getDefaultDatePattern() {
        return defaultDatePattern;
    }

    public static void setDefaultDatePattern(String defaultDatePattern) {
        DateUtil.defaultDatePattern = defaultDatePattern;
    }
}
