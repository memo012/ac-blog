package com.qiang.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: qiang
 * @Description: 角色表
 * @Date: 2019/7/11 0011 20:13
 */
@Data
@TableName(value = "roles")
public class RoleEntity implements Serializable {

    private static final long serialVersionUID = 2147116123095650916L;
    /**
     * 标识符
     */
    private Integer rid;

    /**
     * 角色名
     */
    private String rname;

}
