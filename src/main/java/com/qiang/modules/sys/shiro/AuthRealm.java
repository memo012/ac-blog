package com.qiang.modules.sys.shiro;

import com.qiang.modules.sys.entity.PermissionEntity;
import com.qiang.modules.sys.entity.VO.RoleVOEntity;
import com.qiang.modules.sys.entity.VO.UsersVOEntity;
import com.qiang.modules.sys.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.shiro
 * @Description: shiro认证授权
 * @Date: 2019/6/20 0020 13:02
 **/
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    // 用于授权的方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取用户
        UsersVOEntity users = (UsersVOEntity) SecurityUtils.getSubject().getPrincipal();
        List<String> list = new ArrayList<>();
        List<String> roleNameList = new ArrayList<>();
        Set<RoleVOEntity> roles = users.getRoles();
        if(CollectionUtils.isNotEmpty(roles)){
            for (RoleVOEntity role:
                    roles) {
                roleNameList.add(role.getRname());
                Set<PermissionEntity> permissionSet = role.getPermissionSet();
                if(CollectionUtils.isNotEmpty(permissionSet)) {
                    for (PermissionEntity permission :
                            permissionSet) {
                        list.add(permission.getPname());
                    }
                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(list);
        info.addRoles(roleNameList);
        return info;
    }

    // 用户认证的方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //token携带了用户信息
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        //获取前端输入的手机号
        String phone = usernamePasswordToken.getUsername();
        UsersVOEntity vo = userService.findUsersByPhone(phone);
        if (vo == null) {
            throw new UnknownAccountException("该手机号不存在！");
        } else {
            //当前realm对象的name
            String realmName = getName();
            //盐值
            ByteSource credentialsSalt = ByteSource.Util.bytes(vo.getPhone());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(vo, vo.getPassword(), credentialsSalt, realmName);
            return info;
        }
    }
}
