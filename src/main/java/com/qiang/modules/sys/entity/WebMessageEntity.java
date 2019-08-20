package com.qiang.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.entity
 * @Description: 网站信息
 * @Date: 2019/8/9 0009 18:01
 **/
@Data
@TableName(value = "web")
public class WebMessageEntity {

    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 访问量
     */
    private Long visitor;

}
