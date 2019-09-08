package com.qiang.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qiang.common.utils.RedisOperator;
import com.qiang.modules.sys.dao.*;
import com.qiang.modules.sys.entity.*;
import com.qiang.modules.sys.entity.VO.UsersVOEntity;
import com.qiang.modules.sys.service.UserService;
import com.qiang.modules.sys.shiro.ShiroMD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.service.impl
 * @Description:
 * @Date: 2019/6/20 0020 11:26
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersLoginDao usersLoginDao;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private RepGuestDao repGuestDao;

    @Autowired
    private CommentLikeDao commentLikeDao;

    @Autowired
    private RepCommentDao repCommentDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private GuestDao guestDao;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public UsersEntity insUserMess(UsersEntity users) {
        int i = usersDao.updUserMess(users);
        UsersEntity u = null;
        if (i > 0) {
            u = findUserMess(users.getUsername());
        }
        return u;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<RepGuestEntity> updNotGuest(String username) {
        List<Long> id = repGuestDao.findNotGuest(username);
        List<RepGuestEntity> list = null;
        if (id.size() == 0) {
            return list;
        }
        int i = repGuestDao.updGuestIsRead(id);
        if (i > 0) {
            list = findNotRepReadGuest(username);
        }
        return list;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updOneBlogNotLikes(Long id) {
        return commentDao.updOneBlogNotLikes(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updOneBlogNotComm(Long id) {
        return commentDao.updOneBlogNotComm(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public int findMessageNotReadUser(String username) {
        int c4 = repGuestDao.selectCount(new QueryWrapper<RepGuestEntity>()
                .eq("ris_read", 1)
                .eq("guest_name", username));
        int c2 = repCommentDao.selectCount(new QueryWrapper<ReportCommentEntity>()
                .eq("ris_read", 1)
                .eq("com_name", username));
        return c2 + c4;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public int findMessageNotRead(String username) {
        int c1 = commentDao.selectCount(new QueryWrapper<CommentEntity>().eq("is_read", 1));
        int c2 = repCommentDao.selectCount(new QueryWrapper<ReportCommentEntity>()
                .eq("ris_read", 1)
                .eq("com_name", username));
        int c3 = guestDao.selectCount(new QueryWrapper<GuestEntity>().eq("is_read", 1));
        int c4 = repGuestDao.selectCount(new QueryWrapper<RepGuestEntity>()
                    .eq("ris_read", 1)
                    .eq("guest_name", username));
        return c1 + c2 + c3 + c4;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CommentEntity> findAllComment() {
        return commentDao.selectList(null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updOneNotGuestUser(Long id) {
        RepGuestEntity repGuestEntity = new RepGuestEntity();
        repGuestEntity.setRid(id);
        repGuestEntity.setRisRead(0);
        return repGuestDao.updateById(repGuestEntity);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updOneNotGuestMana(Long id) {
        return guestDao.updOneNotGuestMana(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<RepGuestEntity> findNotRepReadGuest(String username) {
        List<RepGuestEntity> list = repGuestDao.selectList(new QueryWrapper<RepGuestEntity>()
                .eq("guest_name", username)
                .orderByDesc("rid"));
        return list;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<GuestEntity> findAllGuest() {
        return guestDao.selectList(null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<CommentLikesEntity> updComIsRead(String username) {
        List<Long> id = commentLikeDao.findNotReadLikesIdByUsername(username);
        List<CommentLikesEntity> list = null;
        if (id.size() == 0) {
            return list;
        }
        int i = commentLikeDao.updLikesIsRead(id);
        if (i > 0) {
            list = findLikes(username);
        }
        return list;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CommentLikesEntity> findLikes(String username) {
        return commentLikeDao.findLikes(username);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updUserPwd(String phone, String password) {
        Object o = ShiroMD5.MD5(phone, password);
        return usersDao.updUserPwd(phone, String.valueOf(o));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UsersEntity findUserMess(String username) {
        return usersDao.selectOne(new QueryWrapper<UsersEntity>().eq("username", username));

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UsersVOEntity findUsersByPhone(String phone) {
        return usersLoginDao.findByPhone(phone);
    }
}
