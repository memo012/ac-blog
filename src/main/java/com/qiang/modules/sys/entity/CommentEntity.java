package com.qiang.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.entity
 * @Description: 评论实体类
 * @Date: 2019/7/22 0022 14:58
 **/
@Data
@TableName(value = "comment")
public class CommentEntity implements Serializable {

    private static final long serialVersionUID = -6530678643599384025L;
    /**
     * 标识符
     */
    @TableId
    private Long id;

    /**
     * 发布博客者id
     */
    private String userId;

    /**
     * 博客id
     */
    private Long blogId;

    /**
     * 评论内容
     */
    private String message;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 点赞人数
     */
    private Integer likes;

    /**
     * 发布者名称
     */
    private String authorName;


    /**
     *  该条评论是否已读  1--未读   0--已读
     */
    private Integer isRead = 1;

    /**
     * 一条评论有N条消息
     */
    @TableField(exist = false)
    private Set<ReportCommentEntity> reportComments;


}
