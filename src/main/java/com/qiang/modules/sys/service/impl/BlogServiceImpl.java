package com.qiang.modules.sys.service.impl;

import com.qiang.common.utils.Constant;
import com.qiang.common.utils.RedisOperator;
import com.qiang.common.utils.StringAndArray;
import com.qiang.common.utils.TimeUtil;
import com.qiang.modules.sys.dao.BlogDao;
import com.qiang.modules.sys.entity.VO.BlogMessageVOEntity;
import com.qiang.modules.sys.service.AsyncService;
import com.qiang.modules.sys.service.BlogService;
import com.qiang.modules.sys.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.service.impl
 * @Description: 博客业务逻辑层
 * @Date: 2019/7/6 0006 15:07
 **/
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private RedisService redisService;

//    @Autowired
//    private EsService esService;

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private AsyncService asyncService;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public BlogMessageVOEntity EchoBlogByBlogId(long id) {
        BlogMessageVOEntity blogMessage = findBlogById(id);
        String[] labels = StringAndArray.stringToArray(blogMessage.getLabelValues());
        blogMessage.setTagValue(labels);
        return blogMessage;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public BlogMessageVOEntity findBlogById(long id) {
        BlogMessageVOEntity blogMessage = null;
        // 从缓存中查询
        if (redisOperator.hasHkey(Constant.BLOG_DETAIL, String.valueOf(id))) {
            if(redisOperator.hget(Constant.BLOG_DETAIL, String.valueOf(id)) == null){
                return blogMessage;
            }
            blogMessage = (BlogMessageVOEntity) redisOperator.hget(Constant.BLOG_DETAIL, String.valueOf(id));
            long looks = 0L; // 浏览次数
            Integer likes = 0; // 点赞数

            // 缓存中是否存在博客浏览数
            if (redisOperator.hasKey(Constant.BLOG_DETAIL + id)) {
                looks = redisOperator.incr(Constant.BLOG_DETAIL + id, 1L);
                // 异步存储
                asyncService.updBlogLook(id, looks);
            } else {
                BlogMessageVOEntity findLikes = blogDao.selectById(id);
                looks = findLikes.getLook() + 1;
                redisOperator.set(Constant.BLOG_DETAIL + id, looks);
            }

            // 缓存中是否存在博客点赞数
            if (redisOperator.hasKey(Constant.BLOG_LIKES + id)) {
                likes = (int)redisOperator.get(Constant.BLOG_LIKES + id);
            } else {
                BlogMessageVOEntity findLikes = blogDao.selectById(id);
                redisOperator.set(Constant.BLOG_LIKES + id, findLikes.getLikes());
            }
            blogMessage.setLikes(likes);
            blogMessage.setLook(looks);

        } else {
            // 从数据库中查 ， 然后存入缓存中
            blogMessage = blogDao.selectById(id);
            redisOperator.hset(Constant.BLOG_DETAIL, String.valueOf(id), blogMessage);
        }
        return blogMessage;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Boolean updBlogByBlogId(BlogMessageVOEntity blogMessageVO) {
//        EsBlogMessage esBlogMessage = null;
        TimeUtil timeUtil = new TimeUtil();
        blogMessageVO.setCreateTime(timeUtil.getFormatDateForThree());
        blogMessageVO.setTagValue(StringAndArray.stringToArray(blogMessageVO.getLabelValues()));
        blogMessageVO.setArticleUrl("/article/" + blogMessageVO.getId());
        int i = blogDao.updateById(blogMessageVO);
        // 搜索修改 -- 先删后存
//        esBlogMessage = esService.findById(blogMessageVO.getId());
//        esService.removeEsBlog(blogMessageVO.getId());
//        esBlogMessage.update(blogMessageVO);
//        esService.saveBlog(esBlogMessage);
         // 存入缓存中(首页分页查询) -- 先删后存
        redisOperator.lremove(Constant.PAGE_BLOG, 0, redisOperator.hget(Constant.BLOG_DETAIL, String.valueOf(blogMessageVO.getId())));
        redisOperator.lpush(Constant.PAGE_BLOG, blogMessageVO);
        // 存入缓存（博客具体详情） -- 先删后存
        redisOperator.hdel(Constant.BLOG_DETAIL, String.valueOf(blogMessageVO.getId()));
        redisOperator.hset(Constant.BLOG_DETAIL, String.valueOf(blogMessageVO.getId()), blogMessageVO);
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void publishBlog(BlogMessageVOEntity blogMessageVO) {
        long id = 0L;
        BlogMessageVOEntity blog = null;
//        EsBlogMessage esBlogMessage = null;
        if (blogMessageVO.getId() == 0) {
            id = new TimeUtil().getLongTime();
            blogMessageVO.setId(id);
            blogMessageVO.setLikes(0);
            blogMessageVO.setLook(0L);
            TimeUtil timeUtil = new TimeUtil();
            blogMessageVO.setCreateTime(timeUtil.getFormatDateForThree());
            blogMessageVO.setTagValue(StringAndArray.stringToArray(blogMessageVO.getLabelValues()));
            blogMessageVO.setArticleUrl("/article/" + blogMessageVO.getId());
            blogDao.insert(blogMessageVO);
            blog = blogDao.selectById(id);
//            esBlogMessage = new EsBlogMessage(blog);
        }
//        esService.saveBlog(esBlogMessage);
        // 存入缓存
        redisService.SaveEditBlog(blogMessageVO);
    }
}
