package com.qiang.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiang.common.utils.Constant;
import com.qiang.common.utils.PagedResult;
import com.qiang.common.utils.RedisOperator;
import com.qiang.common.utils.StringAndArray;
import com.qiang.modules.sys.dao.BlogDao;
import com.qiang.modules.sys.entity.VO.BlogMessageVOEntity;
import com.qiang.modules.sys.service.ArticleService;
import com.qiang.modules.sys.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.service.impl
 * @Description: 首页操作业务逻辑层
 * @Date: 2019/7/8 0008 15:47
 **/
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private AsyncService asyncService;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updLike(long articleId) {
        // 先从缓存中查询
        if(redisOperator.hasKey(Constant.BLOG_LIKES+articleId)){
            redisOperator.incr(Constant.BLOG_LIKES+articleId, 1);
        }else{
            long likes = blogDao.selectById(articleId).getLikes();
            redisOperator.set(Constant.BLOG_LIKES+articleId, likes+1);
        }
        return (int)redisOperator.get(Constant.BLOG_LIKES+articleId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedResult findByTime(Integer pageNum, Integer pageSize, String time) {
        QueryWrapper queryWrapper = new QueryWrapper<BlogMessageVOEntity>()
                .like("create_time", time)
                .orderByDesc("id");
        IPage<BlogMessageVOEntity> blog = blogDao.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
        for (BlogMessageVOEntity b:
                blog.getRecords()) {
            b.setTagValue(StringAndArray.stringToArray(b.getLabelValues()));
        }
        PagedResult grid = new PagedResult();
        grid.setPage(pageNum);
        grid.setTotal(blog.getPages());
        grid.setRecords(blog.getTotal());
        grid.setRows(blog.getRecords());
        return grid;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedResult findByCategories(Integer pageNum, Integer pageSize, String categories) {
        QueryWrapper queryWrapper = new QueryWrapper<BlogMessageVOEntity>()
                .like("select_categories", categories)
                .orderByDesc("id");
        IPage<BlogMessageVOEntity> blog = blogDao.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
        for (BlogMessageVOEntity b:
                blog.getRecords()) {
            b.setTagValue(StringAndArray.stringToArray(b.getLabelValues()));
        }
        PagedResult grid = new PagedResult();
        grid.setPage(pageNum);
        grid.setTotal(blog.getPages());
        grid.setRecords(blog.getTotal());
        grid.setRows(blog.getRecords());
        return grid;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedResult findByTag(Integer pageNum, Integer pageSize, String tag) {
        QueryWrapper queryWrapper = new QueryWrapper<BlogMessageVOEntity>()
                .like("label_values", tag)
                .orderByDesc("id");
        IPage<BlogMessageVOEntity> blog = blogDao.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
        for (BlogMessageVOEntity b:
                blog.getRecords()) {
            b.setTagValue(StringAndArray.stringToArray(b.getLabelValues()));
            b.setSpecificTag(tag);
        }
        PagedResult grid = new PagedResult();
        grid.setPage(pageNum);
        grid.setTotal(blog.getPages());
        grid.setRecords(blog.getTotal());
        grid.setRows(blog.getRecords());
        return grid;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedResult findAllBlog(Integer pageNum, Integer pageSize) {
        PagedResult grid = new PagedResult();
        // 先从缓存中查询
        if(redisOperator.hasKey(Constant.PAGE_BLOG)){
            int start = (pageNum - 1) * pageSize;
            int stop = pageNum * pageSize - 1;
            List<BlogMessageVOEntity> range = (List<BlogMessageVOEntity>) redisOperator.range(Constant.PAGE_BLOG, start, stop);
            long length = redisOperator.llen(Constant.PAGE_BLOG);
            grid.setRecords(length);
            grid.setRows(range);
            grid.setPage(pageNum);
            grid.setTotal(length);
        }else{
            IPage<BlogMessageVOEntity> blog = blogDao.selectPage(new Page<>(pageNum, pageSize), new QueryWrapper<BlogMessageVOEntity>().orderByDesc("id"));
            for (BlogMessageVOEntity b:
                    blog.getRecords()) {
                b.setTagValue(StringAndArray.stringToArray(b.getLabelValues()));
                b.setArticleUrl("/article/" + b.getId());
            }
            // 存入缓存中(异步存储)
            if(blog != null){
                asyncService.insPageBlog();
            }
            grid.setPage(pageNum);
            grid.setTotal(blog.getPages());
            grid.setRecords(blog.getTotal());
            grid.setRows(blog.getRecords());
        }
        return grid;
    }
}
