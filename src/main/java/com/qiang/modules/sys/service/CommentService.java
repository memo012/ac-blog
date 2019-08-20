package com.qiang.modules.sys.service;

import com.qiang.modules.sys.entity.CommentEntity;
import com.qiang.modules.sys.entity.CommentLikesEntity;
import com.qiang.modules.sys.entity.ReportCommentEntity;

import java.util.List;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.service
 * @Description: 评论接口
 * @Date: 2019/7/22 0022 15:03
 **/
public interface CommentService {

    /**
     * 新增评论
     * @param comment
     * @return 全部评论消息
     */
    List<CommentEntity> insComment(CommentEntity comment);

    /**
     * 评论查询
     * @return 全部评论消息
     */
    List<CommentEntity> getAllComment(long blogId);

    /**
     *  新增回复评论
     * @param reportComment
     * @return 全部评论消息
     */
    List<CommentEntity> insRepComment(ReportCommentEntity reportComment);

    /**
     * 判断该用户是否点赞该评论
     * @param commentLikes
     * @return true -- 点赞 false -- 未点赞
     */
    boolean isCommLikes(CommentLikesEntity commentLikes);

    /**
     * 添加该用户点赞记录
     * @param commentLikes
     * @return
     */
    int insCommLikes(CommentLikesEntity commentLikes);

    /**
     *  点赞数减一
     * @param blogId
     * @param commentId
     * @return
     */
    List<CommentEntity> updDecCommLikes(Long blogId, Long commentId);

    /**
     * 增加该评论点赞数
     * @param blogId
     * @param commentId
     * @return
     */
    List<CommentEntity> updInsCommLikes(Long blogId, Long commentId);

    /**
     * 删除点赞用户
     * @param commentLikes
     * @return
     */
    int delCommLikes(CommentLikesEntity commentLikes);

    /**
     * 我的评论
     * @param username
     * @return
     */
    List<ReportCommentEntity> getUserRepMessNotRead(String username);

    /**
     * 全部消息已读
     * @param username
     * @return
     */
    List<ReportCommentEntity> updComIsRead(String username);

    /**
     * 部分消息已读
     * @param id
     * @return
     */
    int updOneNotComm(Long id);

}
