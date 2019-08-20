package com.qiang.modules.sys.service;

import com.qiang.common.utils.PagedResult;
import com.qiang.modules.sys.entity.FriendurlEntity;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.service
 * @Description:
 * @Date: 2019/8/2 0002 18:00
 **/
public interface AdminService {


    /**
     * 增加友链
     * @param friendurlEntity
     * @return
     */
    int insFriendUrl(FriendurlEntity friendurlEntity);


    /**
     * 全部用户信息
     * @return
     */
    PagedResult findAllUsers(Integer pageSize, Integer pageNum);

    /**
     * 全部友链信息
     * @return
     */
    PagedResult getAllFriendsUrl(Integer pageSize, Integer pageNum);

    /**
     * 全部博客信息
     * @return
     */
    PagedResult findAllBlogs(Integer pageSize, Integer pageNum);



    /**
     * 删除用户
     * @return
     */
    int deleteUsers(String username);

    /**
     * 删除友链
     * @return
     */
    int delFriendUrl(Long id);


    /**
     * 删除博客
     * @return
     */
    int deleteBlog(Long id);

//    /**
//     * 博客编辑
//     * @param id
//     * @return
//     */
//    BlogMessage editByBlogId(Long id);





}
