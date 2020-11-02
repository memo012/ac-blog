package com.qiang.common.constatnt;

/**
 * @Author: qiang
 * @Description: 常量
 * @Date: 2019/7/21 0021 16:28
 */
public enum BlogConstant {
    /**
     * 首页分页博客
     */
    PAGE_BLOG("redis:index:page:blog"),

    /**
     * 首页标签
     */
    LABEL_ALL("redis:index:all:label"),

    /**
     * 首页标签数
     */
    LABEL_ALL_COUNT("redis:index:label:count"),

    /**
     * 文章详情
     */
    BLOG_DETAIL("redis:article:detail:blog"),

    /**
     * 文章评论
     */
    BLOG_REPORT("redis:article:report:blog"),

    /**
     * 文章点赞数
     */
    BLOG_LIKES("redis:article:like:blog"),

    /**
     * 网站留言(总数)
     */
    BLOG_GUEST_COUNT("redis:guest:report:blog:count"),

    /**
     * 评论总数
     */
    BLOG_REPORT_COUNT("redis:report:blog:count"),

    /**
     * 文章总篇
     */
    BLOG_COUNT("redis:article:blog:count"),

    /**
     * 访客量
     */
    BLOG_VISIT_COUNT("redis:blog:visit:count"),

    /**
     * 用户手机
     */
    USER_PHONE_EXIST("redis:blog:user:phone"),

    /**
     * 用户名
     */
    USER_NAME_EXIST("redis:blog:user:name"),

    /**
     * 验证码(注册)
     */
    USER_PHONE_CODE("redis:blog:user:code");

    private String name;

    private BlogConstant() {
    }

    private BlogConstant(String name) {
        this.name = name;
    }

    public String val() {
        return name;
    }

}
