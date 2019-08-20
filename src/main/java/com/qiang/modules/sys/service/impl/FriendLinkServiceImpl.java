package com.qiang.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiang.common.utils.Constant;
import com.qiang.common.utils.RedisOperator;
import com.qiang.common.utils.TimeUtil;
import com.qiang.modules.sys.dao.*;
import com.qiang.modules.sys.entity.*;
import com.qiang.modules.sys.service.FriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.service.impl
 * @Description:
 * @Date: 2019/8/19 0019 19:15
 **/
@Service
public class FriendLinkServiceImpl implements FriendLinkService {

    @Autowired
    private FriendlinkDao friendlinkDao;

    @Autowired
    private FriendurlDao friendurlDao;

    @Autowired
    private RepFriendlinkDao repFriendlinkDao;

    @Autowired
    private FriendLikesDao friendLikesDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<FriendLinkEntity> getAllGuest() {
        List<FriendLinkEntity> list = null;
        list = friendlinkDao.getAllGuest();
        return list;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<FriendurlEntity> getAllFriend() {
        List<FriendurlEntity> list = null;
        list = friendurlDao.selectList(null);
        return list;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean findIsLikes(FriendLikesEntity guestLikes) {
        QueryWrapper queryWrapper = new QueryWrapper<FriendLikesEntity>()
                .eq("friend_id", guestLikes.getFriendId())
                .eq("like_name", guestLikes.getLikeName());
        return friendLikesDao.selectOne(queryWrapper) != null ? true : false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int delIsLikes(FriendLikesEntity guestLikes) {
        QueryWrapper queryWrapper = new QueryWrapper<FriendLikesEntity>()
                .eq("friend_id", guestLikes.getFriendId())
                .eq("like_name", guestLikes.getLikeName());
        return friendLikesDao.delete(queryWrapper);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updInsLikes(Long id) {
        return friendlinkDao.updInsRepGuest(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<FriendLinkEntity> insIsLikes(FriendLikesEntity guestLikes) {
        TimeUtil timeUtil = new TimeUtil();
        long id = timeUtil.getLongTime();
        guestLikes.setId(id);
        int i = friendLikesDao.insert(guestLikes);
        int k = updInsLikes(guestLikes.getFriendId());
        List<FriendLinkEntity> list = null;
        if (i > 0 && k > 0) {
            list = getAllGuest();
        }
        return list;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<FriendLinkEntity> updDesLikes(Long id) {
        int i = friendlinkDao.updDesRepGuest(id);
        List<FriendLinkEntity> list = null;
        if (i > 0) {
            list = getAllGuest();
        }
        return list;
    }



    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<FriendLinkEntity> insRepGuest(RepFriendLinkEntity repGuest) {
        TimeUtil timeUtil = new TimeUtil();
        long id = timeUtil.getLongTime();
        repGuest.setRid(id);
        repGuest.setRcreateTime(timeUtil.getParseDateForSix());
        repGuest.setRisRead(1);
        List<FriendLinkEntity> list = null;
        int i = repFriendlinkDao.insert(repGuest);

        if (i > 0) {
            list = getAllGuest();
        }
        return list;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<FriendLinkEntity> insGuest(FriendLinkEntity guest) {
        TimeUtil timeUtil = new TimeUtil();
        long id = timeUtil.getLongTime();
        guest.setId(id);
        guest.setCreateTime(timeUtil.getParseDateForSix());
        guest.setLikes(0);
        guest.setIsRead(1);
        List<FriendLinkEntity> list = null;
        int i = friendlinkDao.insert(guest);
        if (i > 0) {
            list = getAllGuest();
        }
        return list;
    }

}
