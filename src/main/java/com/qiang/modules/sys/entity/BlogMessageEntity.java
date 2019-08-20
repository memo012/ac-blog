package com.qiang.modules.sys.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.entity
 * @Description: 博客信息表
 * @Date: 2019/7/4 0004 11:13
 **/
@Data
@TableName(value = "blog")
public class BlogMessageEntity implements Serializable {
    private static final long serialVersionUID = 6202944650911776915L;
    /**
     * 标识符
     */
    @TableId
    private long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 正文
     */
    private String text;

    /**
     * 标签id
     */
    private String labelValues;

    /**
     * 文章类型
     */
    private String selectType;

    /**
     * 博客分类
     */
    private String selectCategories;

    /**
     * 文章等级
     */
    private int selectGrade;

    /**
     * 原文章作者
     */
    private String originalAuthor;

    /**
     * 文章（0-公开  1-私密）
     */
    private String message;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 点赞
     */
    private Integer likes;

    /**
     * 作者名字
     */
    private String name;

    /**
     * 文章摘要
     * @return
     */
    private String articleTabled;


    /**
     * 浏览次数
     */
    private Long look;

}
