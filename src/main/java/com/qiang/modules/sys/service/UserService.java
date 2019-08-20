package com.qiang.modules.sys.service;

import com.qiang.modules.sys.entity.*;
import com.qiang.modules.sys.entity.VO.UsersVOEntity;

import java.util.List;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang
 * @Description:
 * @Date: 2019/6/20 0020 11:25
 **/
public interface UserService {

    /**
     * 通过手机号查询
     * @param phone 手机号
     * @return
     */
    UsersVOEntity findUsersByPhone(String phone);


    /**
     * 修改密码
     * @param phone
     * @param password
     * @return
     */
    int updUserPwd(String phone, String password);

    /**
     * 个人信息
     * @param username
     * @return
     */
    UsersEntity findUserMess(String username);

    /**
     * 新增个人信息
     * @param users
     * @return
     */
    UsersEntity insUserMess(UsersEntity users);

    /**
     * 我的点赞
     * @param username
     * @return
     */
    List<CommentLikesEntity> findLikes(String username);

    /**
     * 全部消息已读(点赞)
     * @param username
     * @return
     */
    List<CommentLikesEntity> updComIsRead(String username);

    /**
     * 我的留言（管理员）
     * @return
     */
    List<GuestEntity> findAllGuest();

    /**
     * 我的留言（用户）
     * @param username
     * @return
     */
    List<RepGuestEntity> findNotRepReadGuest(String username);

    /**
     * 全部消息已读（留言 --- 用户）
     * @param username
     * @return
     */
    List<RepGuestEntity> updNotGuest(String username);

    /**
     * 部分留言消息已读（管理员）
     * @param id
     * @return
     */
    int updOneNotGuestMana(Long id);

    /**
     * 部分留言消息已读（用户）
     * @param id
     * @return
     */
    int updOneNotGuestUser(Long id);

    /**
     * 我的博客评论
     * @return
     */
    List<CommentEntity> findAllComment();

    /**
     * 我的博客评论部分消息已读
     * @param id
     * @return
     */
    int updOneBlogNotComm(Long id);


    /**
     * 我的点赞评论部分消息已读
     * @param id
     * @return
     */
    int updOneBlogNotLikes(Long id);

    /**
     *  反馈（管理员）
     * @param username
     * @return
     */
    int findMessageNotRead(String username);

    /**
     *  消息（未读）
     * @param username
     * @return
     */
    int findMessageNotReadUser(String username);




}
