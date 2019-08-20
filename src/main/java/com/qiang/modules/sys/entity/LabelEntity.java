package com.qiang.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.entity
 * @Description: 标签实体库
 * @Date: 2019/7/6 0006 11:45
 **/
@Data
@TableName(value = "label")
public class LabelEntity implements Serializable {

    private static final long serialVersionUID = -8912589778594195822L;
    @TableId
    private String id;
    private String labelName;

}
