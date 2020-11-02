package com.qiang.modules.sys.service;

/**
 * @Author: qiang
 * @Description: 网页信息
 * @Date: 2019/8/8 0008 16:09
 */
public interface IndexService {

    /**
     * 文章总数
     *
     * @return
     */
    Long myArticlesCount();


    /**
     * 标签总数
     *
     * @return
     */
    int myLabelsCount();


    /**
     * 评论总数
     *
     * @return
     */
    Integer myReportsCount();


    /**
     * 留言总数
     *
     * @return
     */
    Integer myGuestCount();


    /**
     * 网站访问量
     *
     * @return
     */
    int myWebCount();

}
