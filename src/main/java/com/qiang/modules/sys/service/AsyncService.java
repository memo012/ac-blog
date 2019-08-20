package com.qiang.modules.sys.service;

import com.qiang.modules.sys.entity.LabelEntity;
import com.qiang.modules.sys.entity.VO.BlogMessageVOEntity;

import java.util.List;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.service
 * @Description: 异步任务(数据库和redis保持一致)
 * @Date: 2019/8/9 0009 15:21
 **/
public interface AsyncService {

    /**
     * 标签名
     */
    public void insLabelName(List<LabelEntity> list);

    /**
     * 首页博文
     * @param
     */
    public void insPageBlog();


    /**
     * 博客文章访问量
     * @param id
     */
    public void updBlogLook(Long id, Long look);

    /**
     * 存入数据库中手机号
     */
    public void insUserPhone();

    /**
     * 存入数据库中用户名
     */
    public void insUserName();




}
