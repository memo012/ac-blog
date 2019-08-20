package com.qiang.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiang.common.utils.Constant;
import com.qiang.common.utils.RedisOperator;
import com.qiang.common.utils.TimeUtil;
import com.qiang.modules.sys.dao.GuestDao;
import com.qiang.modules.sys.dao.GuestLikesDao;
import com.qiang.modules.sys.dao.RepGuestDao;
import com.qiang.modules.sys.entity.GuestEntity;
import com.qiang.modules.sys.entity.GuestLikesEntity;
import com.qiang.modules.sys.entity.RepGuestEntity;
import com.qiang.modules.sys.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.service.impl
 * @Description: 留言业务逻辑层
 * @Date: 2019/7/24 0024 17:31
 **/
@Service
public class GuestServiceImpl implements GuestService {

    @Autowired
    private GuestDao guestDao;

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private RepGuestDao repGuestDao;

    @Autowired
    private GuestLikesDao guestLikesDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<GuestEntity> getAllGuest() {
        List<GuestEntity> list = null;
        list = guestDao.getAllGuest();
        return list;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean findIsLikes(GuestLikesEntity guestLikes) {
        QueryWrapper queryWrapper = new QueryWrapper<GuestLikesEntity>()
                .eq("guest_id", guestLikes.getGuestId())
                .eq("like_name", guestLikes.getLikeName());
        return guestLikesDao.selectOne(queryWrapper) != null ? true : false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int delIsLikes(GuestLikesEntity guestLikes) {
        QueryWrapper queryWrapper = new QueryWrapper<GuestLikesEntity>()
                .eq("guest_id", guestLikes.getGuestId())
                .eq("like_name", guestLikes.getLikeName());
        return guestLikesDao.delete(queryWrapper);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updInsLikes(Long id) {
        return guestDao.updInsRepGuest(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<GuestEntity> insIsLikes(GuestLikesEntity guestLikes) {
        TimeUtil timeUtil = new TimeUtil();
        long id = timeUtil.getLongTime();
        guestLikes.setId(id);
        int i = guestLikesDao.insert(guestLikes);
        int k = updInsLikes(guestLikes.getGuestId());
        List<GuestEntity> list = null;
        if(i > 0 && k > 0){
            list = getAllGuest();
        }
        return list;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<GuestEntity> updDesLikes(Long id) {
        int i = guestDao.updDesRepGuest(id);
        List<GuestEntity> list = null;
        if(i > 0){
            list = getAllGuest();
        }
        return list;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<GuestEntity> insRepGuest(RepGuestEntity repGuest) {
        TimeUtil timeUtil = new TimeUtil();
        long id = timeUtil.getLongTime();
        repGuest.setRid(id);
        repGuest.setRcreateTime(timeUtil.getParseDateForSix());
        repGuest.setRisRead(1);
        List<GuestEntity> list = null;
        int i = repGuestDao.insert(repGuest);

        if(i > 0){
            list = getAllGuest();
        }
        // 存入缓存 增加留言（+1）
        redisOperator.incr(Constant.BLOG_GUEST_COUNT, 1);
        return list;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<GuestEntity> insGuest(GuestEntity guest) {
        TimeUtil timeUtil = new TimeUtil();
        long id = timeUtil.getLongTime();
        guest.setId(id);
        guest.setCreateTime(timeUtil.getParseDateForSix());
        guest.setLikes(0);
        guest.setIsRead(1);
        List<GuestEntity> list = null;
        int i = guestDao.insert(guest);
        if(i > 0){
            list = getAllGuest();
        }
        // 存入缓存 增加留言（+1）
        redisOperator.incr(Constant.BLOG_GUEST_COUNT, 1);
        return list;
    }
}
