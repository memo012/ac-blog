package com.qiang.modules.sys.service;

import com.qiang.modules.sys.entity.LabelEntity;

import java.util.List;

/**
 * @Author: qiang
 * @Description: 异步任务(数据库和redis保持一致)
 * @Date: 2019/8/9 0009 15:21
 */
public interface AsyncService {

    /**
     * 标签名
     */
    void insLabelName(List<LabelEntity> list);


    /**
     * 首页博文
     *
     * @param
     */
    void insPageBlog();


    /**
     * 博客文章访问量
     *
     * @param id
     */
    void updBlogLook(Long id, Long look);


    /**
     * 存入数据库中手机号
     */
    void insUserPhone();


    /**
     * 存入数据库中用户名
     */
    void insUserName();

}
