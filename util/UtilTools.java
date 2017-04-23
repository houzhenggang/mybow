package com.taobao.mybow.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qinanhg@gmail.com on 2017/2/4 0004 下午 1:16.
 */
public class UtilTools {

    /**
     * 获取当前日期
     * @return 字符串当前日期
     */
    public static String getStringDate() {
        return getStringDate1(new SimpleDateFormat("yyyy-MM-dd"), new Date());
    }

    public static String getStringDateTime() {
        return getStringDate1(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), new Date());
    }

    public static String getStringDateTimeByDate(Date date) {
        return getStringDate1(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), date);
    }

    private static String getStringDate1(SimpleDateFormat df, Date date) {
        return df.format(date);
    }

    public static String  regexFind(String allAddress, String regex1) {
        Pattern pattern = Pattern.compile(regex1);
        Matcher matcher = pattern.matcher(allAddress);

        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}
