package com.qiang.modules.sys.entity.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qiang.modules.sys.entity.PermissionEntity;
import com.qiang.modules.sys.entity.RoleEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.entity.VO
 * @Description: 身份验证
 * @Date: 2019/8/17 0017 12:38
 **/
@TableName("roles")
@Data
public class RoleVOEntity extends RoleEntity implements Serializable {

    private static final long serialVersionUID = -4902035294884582203L;

    /**
     * 当前角色所具备的权限
     */
    @TableField(exist = false)
    private Set<PermissionEntity> permissionSet;
}
