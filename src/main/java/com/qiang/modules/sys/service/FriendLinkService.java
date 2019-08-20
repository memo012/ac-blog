package com.qiang.modules.sys.service;

import com.qiang.modules.sys.entity.*;

import java.util.List;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.service
 * @Description:
 * @Date: 2019/8/19 0019 19:14
 **/
public interface FriendLinkService {

    /**
     * 查询全部友链
     * @return
     */
    List<FriendurlEntity> getAllFriend();


    /**
     * 查询全部留言
     * @return
     */
    List<FriendLinkEntity> getAllGuest();

    /**
     * 新增留言
     * @param guest
     * @return
     */
    List<FriendLinkEntity> insGuest(FriendLinkEntity guest);

    /**
     * 新增留言评论
     * @param repGuest
     * @return
     */
    List<FriendLinkEntity> insRepGuest(RepFriendLinkEntity repGuest);

    /**
     * 用户是否点赞
     * @param guestLikes
     * @return
     */
    boolean findIsLikes(FriendLikesEntity guestLikes);

    /**
     * 删除用户点赞
     * @param guestLikes
     * @return
     */
    int delIsLikes(FriendLikesEntity guestLikes);

    /**
     * 新增用户点赞信息
     * @param guestLikes
     * @return
     */
    List<FriendLinkEntity> insIsLikes(FriendLikesEntity guestLikes);

    /**
     * 新增点赞
     * @param id
     * @return
     */
    int updInsLikes(Long id);

    /**
     * 减少点赞
     * @param id
     * @return
     */
    List<FriendLinkEntity> updDesLikes(Long id);
}
