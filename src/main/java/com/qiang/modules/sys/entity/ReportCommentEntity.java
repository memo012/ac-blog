package com.qiang.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.entity
 * @Description:
 * @Date: 2019/7/22 0022 18:15
 **/
@Data
@TableName(value = "reportcomment")
public class ReportCommentEntity implements Serializable {

    private static final long serialVersionUID = -5264662268008848306L;
    /**
     * 标识符
     */
    @TableId
    private Long rid;

    /**
     * 评论文章id
     */
    private Long commentId;

    /**
     * 内容
     */
    private String repMess;

    /**
     *  回复者id
     */
    private String reportedId;

    /**
     * 创建时间
     */
    private String rcreateTime;

    /**
     *  该条评论是否已读  1--未读   0--已读
     */
    private Integer risRead = 1;

    /**
     * 回复者名字
     */
    private String repName;

    /**
     * 被评论者名字
     */
    private String comName;

    /**
     * 博客id
     */
    @TableField(exist = false)
    private Long blogId;

    /**
     * 博客标题
     */
    @TableField(exist = false)
    private String title;



}
