package com.qiang.modules.sys.service;

import com.qiang.common.utils.PagedResult;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.service
 * @Description: 首页操作业务逻辑接口
 * @Date: 2019/7/8 0008 15:45
 **/
public interface ArticleService {

    /**
     * 分页查询文章
     * @param page 显示数据(一页)
     * @param pageSize 当前页数
     * @return
     */
    PagedResult findAllBlog(Integer page, Integer pageSize);

    /**
     * 通过标签查询文章
     * @param page
     * @param pageSize
     * @param tag 标签名
     * @return
     */
    PagedResult findByTag(Integer page, Integer pageSize, String tag);

    /**
     * 通过文章归类查询
     * @param page
     * @param pageSize
     * @param categories
     * @return
     */
    PagedResult findByCategories(Integer page, Integer pageSize, String categories);

    /**
     * 通过时间查询
     * @param page
     * @param pageSize
     * @param time
     * @return
     */
    PagedResult findByTime(Integer page, Integer pageSize, String time);

    /**
     * 点赞人数
     * @param articleId
     * @return
     */
    int updLike(long articleId);

}
