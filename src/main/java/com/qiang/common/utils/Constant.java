package com.qiang.common.utils;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.common.utils
 * @Description: 常量
 * @Date: 2019/7/21 0021 16:28
 **/
public class Constant {

    /**
     * 首页分页博客
     */
    public static final String PAGE_BLOG = "redis:index:page:blog";

    /**
     * 首页标签
     */
    public static  final String LABEL_ALL = "redis:index:all:label";

    /**
     * 首页标签数
     */
    public static  final String LABEL_ALL_COUNT = "redis:index:label:count";
    /**
     * 文章详情
     */
    public static final String BLOG_DETAIL = "redis:article:detail:blog";

    /**
     * 文章评论
     */
    public static final String BLOG_REPORT = "redis:article:report:blog";


    /**
     * 文章点赞数
     */
    public static final String BLOG_LIKES = "redis:article:like:blog";
    /**
     * 网站留言(总数)
     */
    public static final String BLOG_GUEST_COUNT = "redis:guest:report:blog:count";


    /**
     * 评论总数
     */
    public static final String BLOG_REPORT_COUNT = "redis:report:blog:count";

    /**
     * 文章总篇
     */
    public static final String BLOG_COUNT = "redis:article:blog:count";

    /**
     * 访客量
     */
    public static final String BLOG_VISIT_COUNT = "redis:blog:visit:count";


    /**
     * 用户手机
     */
    public static final String USER_PHONE_EXIST = "redis:blog:user:phone";

    /**
     * 用户名
     */
    public static final String USER_NAME_EXIST = "redis:blog:user:name";

    /**
     * 验证码(注册)
     */
    public static final String USER_PHONE_CODE = "redis:blog:user:code";


}
