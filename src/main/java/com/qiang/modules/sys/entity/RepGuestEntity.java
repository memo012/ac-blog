package com.qiang.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.entity
 * @Description: 留言评论实体类
 * @Date: 2019/7/24 0024 17:16
 **/
@Data
@TableName(value = "repguest")
public class RepGuestEntity implements Serializable {

    private static final long serialVersionUID = 2692108952240624039L;
    /**
     * 主键
     */
    @TableId
    private Long rid;

    /**
     * 留言条id
     */
    private Long guestId;

    /**
     * 回复内容
     */
    private String repMess;

    /**
     * 评论者id
     */
    private String rguestId;

    /**
     * 创建时间
     */
    private String rcreateTime;

    /**
     * 1 - 未读  0 - 已读
     */
    private Integer risRead = 1;

    /**
     * 评论者名称
     */
    private String repName;

    /**
     *被评论者名称
     */
    private String guestName;

}
