package com.qiang.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.common
 * @Description: 时间工具
 * @Date: 2019/7/4 0004 11:05
 **/
public class TimeUtil {

    /**
     * 格式化日期
     * 使用线程安全的DateTimeFormatter
     * @return “年-月-日”字符串
     */
    public String getFormatDateForThree(){

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return now.format(format);
    }

    /**
     * 格式化日期
     * 使用线程安全的DateTimeFormatter
     * @return “年-月-日 时:分:秒”字符串
     */
    public String getFormatDateForSix(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(format);
    }

    /**
     * 格式化日期
     * 使用线程安全的DateTimeFormatter
     * @return “年-月-日 时:分”字符串
     */
    public String getFormatDateForFive(){

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return now.format(format);
    }

    /**
     * 解析日期
     * @param date 日期 2018-06-21
     * @return
     */
    public LocalDate getParseDateForThree(String date){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, format);
    }

    /**
     * 解析日期
     * 日期 2018-06-21 12:01:25
     * @return
     */
    public String getParseDateForSix(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(format);
    }

    /**
     * 获得当前时间的时间戳
     * @return 时间戳
     */
    public long getLongTime(){
        Date now = new Date();
        return now.getTime()/1000;
    }

    /**
     * 时间中横杆转换为年
     * @param str 2018-08
     * @return 2018年8月
     */
    public String timeWhippletreeToYear(String str){
        StringBuilder s = new StringBuilder();
        s.append(str.substring(0,4));
        s.append("年");
        s.append(str.substring(5,7));
        s.append("月");
        return String.valueOf(s);
    }

    /**
     * 时间中的年转换为横杠
     * @param str 2018年07月
     * @return 2018-07
     */
    public String timeYearToWhippletree(String str){
        StringBuilder s = new StringBuilder();
        s.append(str.substring(0,4));
        s.append("-");
        s.append(str.substring(5,7));
        return String.valueOf(s);
    }
    
    public static void main(String[] args){
        System.out.println(new TimeUtil().getParseDateForSix());
    }
}
