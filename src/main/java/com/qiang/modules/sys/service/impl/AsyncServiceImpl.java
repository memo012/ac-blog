package com.qiang.modules.sys.service.impl;

import com.qiang.common.utils.Constant;
import com.qiang.common.utils.RedisOperator;
import com.qiang.common.utils.StringAndArray;
import com.qiang.modules.sys.dao.BlogDao;
import com.qiang.modules.sys.dao.RegisterDao;
import com.qiang.modules.sys.entity.LabelEntity;
import com.qiang.modules.sys.entity.UsersEntity;
import com.qiang.modules.sys.entity.VO.BlogMessageVOEntity;
import com.qiang.modules.sys.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.service.impl
 * @Description: 异步任务(数据库和redis保持一致)
 * @Date: 2019/8/9 0009 15:24
 **/
@Service
public class AsyncServiceImpl implements AsyncService {

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private RegisterDao registerDao;

    @Autowired
    private BlogDao blogDao;

    @Async
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updBlogLook(Long id, Long look) {
        BlogMessageVOEntity blogMessageVOEntity = new BlogMessageVOEntity();
        blogMessageVOEntity.setLook(look);
        blogMessageVOEntity.setId(id);
        int i = blogDao.updateById(blogMessageVOEntity);
        System.out.println(i);
    }

    @Async
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void insUserPhone() {
        List<UsersEntity> allUsers = registerDao.selectList(null);
        for (UsersEntity u:
                allUsers) {
            // 手机号加入缓存
            redisOperator.hset(Constant.USER_PHONE_EXIST, u.getPhone(), 1);
        }
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void insUserName() {
        List<UsersEntity> allUsers = registerDao.selectList(null);
        for (UsersEntity u:
                allUsers) {
            // 用户名加入缓存
            redisOperator.hset(Constant.USER_NAME_EXIST, u.getUsername(), 1);
        }
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void insPageBlog() {
        List<BlogMessageVOEntity> list = blogDao.selectList(null);
        for (BlogMessageVOEntity b:
             list) {
            b.setTagValue(StringAndArray.stringToArray(b.getLabelValues()));
            b.setArticleUrl("/article/" + b.getId());
            // 存入缓存中(首页分页查询)
            redisOperator.lpush(Constant.PAGE_BLOG, b);
        }
    }

    @Async
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insLabelName(List<LabelEntity> list) {
        for (LabelEntity label:
             list) {
            redisOperator.lpush(Constant.LABEL_ALL, label);
        }
    }
}
