package com.qiang.modules.sys.service;

/**
 * @Author: qiang
 * @Description: 定时任务存储信息
 * @Date: 2019/8/9 0009 16:31
 */
public interface ScheduledService {

    /**
     * 网站浏览次数存入数据库
     */
    void visitorCustom();


    /**
     * 博客访问量
     */
    void lookBlog();

}
