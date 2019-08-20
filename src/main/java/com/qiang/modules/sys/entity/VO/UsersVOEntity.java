package com.qiang.modules.sys.entity.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qiang.modules.sys.entity.UsersEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.entity.VO
 * @Description:
 * @Date: 2019/8/17 0017 11:36
 **/
@Data
@TableName(value = "users")
public class UsersVOEntity implements Serializable {

    private static final long serialVersionUID = -6861142406460018777L;

    /**
     * 标识符id
     */
    @TableId("id")
    private String id;

    /**
     * 用户
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机
     */
    private String phone;

    /**
     * 性别（0-男 1-女）
     */
    private int sex;

    /**
     * 最后一次登录时间
     */
    private String lastTime;

    /**
     * 角色(1-超级管理员 2-普通用户 3-会员)
     */
    private int roleId;

    /**
     * 真实姓名
     */
    private String realname = "";

    /**
     * QQ号
     */
    private String qq = "";

    /**
     * 邮箱
     */
    private String email = "";

    /**
     * 个人简历
     */
    private String intro = "";
    /**
     *  角色
     */
    @TableField(exist = false)
    private Set<RoleVOEntity> roles;
}
