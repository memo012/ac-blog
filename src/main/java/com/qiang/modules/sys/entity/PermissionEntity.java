package com.qiang.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.entity
 * @Description: 权限表
 * @Date: 2019/7/11 0011 20:16
 **/
@Data
@TableName(value = "permission")
public class PermissionEntity implements Serializable {
    private static final long serialVersionUID = -6885924187336494473L;
    private Integer pid;
    private String pname;
    private String url;

}
