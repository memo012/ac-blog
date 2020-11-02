package com.qiang.modules.sys.service;

import com.qiang.modules.sys.entity.UsersEntity;

/**
 * @Author: qiang
 * @Description:
 * @Date: 2019/8/9 0009 17:30
 */
public interface RegisterService {

    /**
     * 注册
     *
     * @param users
     * @return
     */
    int insUsers(UsersEntity users);


    /**
     * 手机号检测
     *
     * @param phone
     * @return
     */
    int findByPhone(String phone);


    /**
     * 用户名检测
     *
     * @param username
     * @return
     */
    int findByUsername(String username);

}
