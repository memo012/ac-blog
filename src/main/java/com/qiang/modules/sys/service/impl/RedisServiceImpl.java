package com.qiang.modules.sys.service.impl;

import com.qiang.common.constatnt.BlogConstant;
import com.qiang.common.utils.RedisOperator;
import com.qiang.modules.sys.entity.VO.BlogMessageVOEntity;
import com.qiang.modules.sys.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: qiang
 * @Description:
 * @Date: 2019/8/17 0017 12:16
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisOperator redisOperator;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void SavePhoneAndUsername(String phone, String username) {
        // 手机号加入缓存
        redisOperator.hset(BlogConstant.USER_PHONE_EXIST.val(), phone, 1);
        // 用户名加入缓存
        redisOperator.hset(BlogConstant.USER_NAME_EXIST.val(), username, 1);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void SaveEditBlog(BlogMessageVOEntity blogMessageVOEntity) {
        // 存入缓存中(首页分页查询)
        redisOperator.lpush(BlogConstant.PAGE_BLOG.val(), blogMessageVOEntity);
        // 存入缓存（博客具体详情）
        redisOperator.hset(BlogConstant.BLOG_DETAIL.val(), String.valueOf(blogMessageVOEntity.getId()), blogMessageVOEntity);
        // 文章浏览次数
        redisOperator.set(BlogConstant.BLOG_DETAIL.val() + blogMessageVOEntity.getId(), 0);
    }

}
