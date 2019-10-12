package com.qiang.modules.sys.entity.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qiang.modules.sys.entity.BlogMessageEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.entity
 * @Description:
 * @Date: 2019/7/8 0008 17:32
 **/
@Data
@TableName(value = "blog")
public class BlogMessageVOEntity extends BlogMessageEntity implements Serializable {

    private static final long serialVersionUID = -2401597387508779531L;
    /**
     * 含HTML文章
     */
    @TableField(exist = false)
    private String articleHtmlContent;

    /**
     * 标签数组
     */
    @TableField(exist = false)
    private String[] tagValue;

    /**
     * 文章地址
     */
    @TableField(exist = false)
    private String articleUrl;

    /**
     * 规定标签
     */
    @TableField(exist = false)
    private String specificTag;

}
