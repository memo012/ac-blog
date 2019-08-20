package com.qiang.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qiang.common.utils.Constant;
import com.qiang.common.utils.RedisOperator;
import com.qiang.common.utils.TimeUtil;
import com.qiang.modules.sys.dao.CommentDao;
import com.qiang.modules.sys.dao.CommentLikeDao;
import com.qiang.modules.sys.dao.RepCommentDao;
import com.qiang.modules.sys.entity.CommentEntity;
import com.qiang.modules.sys.entity.CommentLikesEntity;
import com.qiang.modules.sys.entity.ReportCommentEntity;
import com.qiang.modules.sys.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.service.impl
 * @Description: 评论业务逻辑层
 * @Date: 2019/7/22 0022 15:04
 **/
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private RepCommentDao repCommentDao;

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private CommentLikeDao commentLikeDao;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int insCommLikes(CommentLikesEntity commentLikes) {
        TimeUtil timeUtil = new TimeUtil();
        long id = timeUtil.getLongTime();
        commentLikes.setId(id);
        commentLikes.setLikeTime(timeUtil.getParseDateForSix());
        return commentLikeDao.insert(commentLikes);
    }

    @Override
    public int updOneNotComm(Long id) {
        ReportCommentEntity reportCommentEntity = new ReportCommentEntity();
        reportCommentEntity.setRid(id);
        reportCommentEntity.setRisRead(0);
        System.out.println(123);
        return repCommentDao.updateById(reportCommentEntity);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ReportCommentEntity> updComIsRead(String username) {
        List<Long> rid = repCommentDao.selComByUsername(username);
        List<ReportCommentEntity> userRepMessNotRead = null;
        if(rid.size() == 0){
            return userRepMessNotRead;
        }
        int i = repCommentDao.updComIsRead(rid);
        if(i > 0){
            userRepMessNotRead = getUserRepMessNotRead(username);
        }
        return userRepMessNotRead;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<CommentEntity> updDecCommLikes(Long blogId, Long commentId) {
        int i = commentDao.updDesCommIsLikes(blogId, commentId);
        List<CommentEntity> allComment = null;
        if (i > 0) {
            redisOperator.lpop(Constant.BLOG_REPORT + blogId);
            getAllComment(blogId);
            allComment = (List<CommentEntity>) redisOperator.range(Constant.BLOG_REPORT + blogId, 0, -1);
        }
        return allComment;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int delCommLikes(CommentLikesEntity commentLikes) {
        QueryWrapper commentLikesEntityQueryWrapper = new QueryWrapper<CommentLikesEntity>()
                .eq("blog_id", commentLikes.getBlogId())
                .eq("comment_id", commentLikes.getCommentId())
                .eq("like_name", commentLikes.getLikeName());
        return commentLikeDao.delete(commentLikesEntityQueryWrapper);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ReportCommentEntity> getUserRepMessNotRead(String username) {
        return repCommentDao.getUserRepMessNotRead(username);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<CommentEntity> updInsCommLikes(Long blogId, Long commentId) {
        int i = commentDao.updInsCommIsLikes(blogId, commentId);
        List<CommentEntity> allComment = null;
        if (i > 0) {
            redisOperator.lpop(Constant.BLOG_REPORT + blogId);
            getAllComment(blogId);
            allComment = (List<CommentEntity>) redisOperator.range(Constant.BLOG_REPORT + blogId, 0, -1);
        }
        return allComment;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean isCommLikes(CommentLikesEntity commentLikes) {
        QueryWrapper queryWrapper = new QueryWrapper<CommentLikesEntity>()
                .eq("blog_id", commentLikes.getBlogId())
                .eq("comment_id", commentLikes.getCommentId())
                .eq("like_name", commentLikes.getLikeName());
        return commentLikeDao.selectOne(queryWrapper) != null ? true : false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<CommentEntity> insRepComment(ReportCommentEntity reportComment) {
        TimeUtil timeUtil = new TimeUtil();
        long id = timeUtil.getLongTime();
        reportComment.setRid(id);
        reportComment.setRcreateTime(timeUtil.getParseDateForSix());
        reportComment.setRisRead(1);
        List<CommentEntity> list = null;
        int result = repCommentDao.insert(reportComment);
        if (result > 0) {
            list = commentDao.findByBlogIdAndPid(reportComment.getBlogId());
            redisOperator.lpop(Constant.BLOG_REPORT + reportComment.getBlogId());
            redisOperator.lpush(Constant.BLOG_REPORT + reportComment.getBlogId(), list);
            list = getAllComment(reportComment.getBlogId());
        }
        // 增加博客评论（缓存）
        redisOperator.incr(Constant.BLOG_REPORT_COUNT, 1);
        return list;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CommentEntity> getAllComment(long blogId) {
        List<CommentEntity> list = null;
        if (redisOperator.hasKey(Constant.BLOG_REPORT + blogId)) {
            list = (List<CommentEntity>) redisOperator.range(Constant.BLOG_REPORT + blogId, 0, -1);
        } else {
            list = commentDao.findByBlogIdAndPid(blogId);
            if (list.size() > 0) {
                redisOperator.lpush(Constant.BLOG_REPORT + blogId, list);
            } else {
                redisOperator.lpush(Constant.BLOG_REPORT + blogId, "");
            }
        }
        return list;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<CommentEntity> insComment(CommentEntity comment) {
        TimeUtil timeUtil = new TimeUtil();
        long id = timeUtil.getLongTime();
        comment.setId(id);
        comment.setCreateTime(timeUtil.getParseDateForSix());
        comment.setLikes(0);
        List<CommentEntity> byBlogId = null;
        List<CommentEntity> list = null;
        int result = commentDao.insert(comment);
        long blogId = comment.getBlogId();
        if (result > 0) {
            // 查询评论
            byBlogId = commentDao.findByBlogIdAndPid(blogId);
            redisOperator.lpop(Constant.BLOG_REPORT + blogId);
            redisOperator.lpush(Constant.BLOG_REPORT + blogId, byBlogId);
            list = getAllComment(blogId);
        }
        // 增加博客评论（缓存）
        redisOperator.incr(Constant.BLOG_REPORT_COUNT, 1);
        return list;
    }

}
