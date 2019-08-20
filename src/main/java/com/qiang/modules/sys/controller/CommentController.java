package com.qiang.modules.sys.controller;

import com.qiang.common.utils.BlogJSONResult;
import com.qiang.modules.sys.entity.CommentEntity;
import com.qiang.modules.sys.entity.CommentLikesEntity;
import com.qiang.modules.sys.entity.ReportCommentEntity;
import com.qiang.modules.sys.entity.VO.UsersVOEntity;
import com.qiang.modules.sys.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.controller
 * @Description: 评论controller
 * @Date: 2019/7/21 0021 21:36
 **/
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 新增评论
     * @return
     */
    @PostMapping("/insComment")
    public BlogJSONResult InsComment(@RequestBody CommentEntity comment, HttpServletRequest request){
        UsersVOEntity user = (UsersVOEntity) request.getSession().getAttribute("user");
        if(user == null){
            return BlogJSONResult.errorMsg("用户已过期");
        }
        CommentEntity comments = new CommentEntity();
        comments.setMessage(comment.getMessage());
        comments.setBlogId(comment.getBlogId());
        comments.setAuthorName(user.getUsername());
        comments.setUserId(user.getId());
        List<CommentEntity> result = commentService.insComment(comments);
        if(result != null){
            return BlogJSONResult.ok(result);
        }
        return BlogJSONResult.errorMsg("新增评论失败");
    }

    /**
     * 新增回复评论
     * @param reportComment
     * @param request
     * @return
     */
    @PostMapping("/InsRepComment")
    public BlogJSONResult InsRepComment(@RequestBody ReportCommentEntity reportComment, HttpServletRequest request){
        UsersVOEntity user = (UsersVOEntity) request.getSession().getAttribute("user");
        if(user == null){
            return BlogJSONResult.errorTokenMsg("用户已过期");
        }
        reportComment.setRepName(user.getUsername());
        reportComment.setReportedId(user.getId());
        List<CommentEntity> allComment = commentService.insRepComment(reportComment);
        if(allComment != null){
            return BlogJSONResult.ok(allComment);
        }else{
            return BlogJSONResult.errorMsg("无果");
        }

    }

    /**
     * 评论查询
     * @return
     */
    @GetMapping("/getComment")
    public BlogJSONResult getComment(@RequestParam("blogId") long blogId){
        List<CommentEntity> allComment = commentService.getAllComment(blogId);
        if(allComment != null){
            return BlogJSONResult.ok(allComment);
        }else{
            return BlogJSONResult.errorMsg("无果");
        }
    }

    /**
     * 点赞更新
     * @param commentLikes
     * @param request
     * @return
     */
    @PostMapping("/updLikes")
    public BlogJSONResult updLikes(@RequestBody CommentLikesEntity commentLikes,
                                   HttpServletRequest request){
        UsersVOEntity user = (UsersVOEntity) request.getSession().getAttribute("user");
        if(user == null){
            return BlogJSONResult.errorTokenMsg("用户已过期");
        }
        commentLikes.setLikeName(user.getUsername());
        boolean commLikes = commentService.isCommLikes(commentLikes);
        if(commLikes){
            // 已点赞
            List<CommentEntity> comments = commentService.updDecCommLikes(commentLikes.getBlogId(), commentLikes.getCommentId());
            if(comments != null){
                commentService.delCommLikes(commentLikes);
                return BlogJSONResult.ok(comments);
            }else{
                return BlogJSONResult.errorMsg("点赞失败");
            }
        }else{
            // 未点赞
            int i = commentService.insCommLikes(commentLikes);
            if(i != 0){
                List<CommentEntity> comments = commentService.updInsCommLikes(commentLikes.getBlogId(), commentLikes.getCommentId());
                return BlogJSONResult.ok(comments);
            }else{
                return BlogJSONResult.errorMsg("点赞失败");
            }
        }
    }

    /**
     * 我的评论
     * @param username
     * @return
     */
    @GetMapping("getUserReport")
    public BlogJSONResult getUserReport(@RequestParam("username") String username){
        List<ReportCommentEntity> messNotRead = commentService.getUserRepMessNotRead(username);
        return BlogJSONResult.ok(messNotRead);
    }

    /**
     * 全部消息已读
     * @param username
     * @return
     */
    @GetMapping("clearComNotRead")
    public BlogJSONResult clearComNotRead(@RequestParam("username") String username){
        List<ReportCommentEntity> reportCommentVOS = commentService.updComIsRead(username);
        if(reportCommentVOS != null){
            return BlogJSONResult.ok(reportCommentVOS);
        }
        return BlogJSONResult.errorMsg("错误");
    }

    /**
     * 部分评论消息已读
     * @param id
     * @return
     */
    @GetMapping("clearOneNotComm")
    public BlogJSONResult clearOneNotComm(@RequestParam("id") Long id){

        int i = commentService.updOneNotComm(id);
        return BlogJSONResult.ok();
    }

}
