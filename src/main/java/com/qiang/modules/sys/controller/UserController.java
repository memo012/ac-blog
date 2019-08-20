package com.qiang.modules.sys.controller;

import com.qiang.common.utils.BlogJSONResult;
import com.qiang.modules.sys.entity.CommentEntity;
import com.qiang.modules.sys.entity.CommentLikesEntity;
import com.qiang.modules.sys.entity.RepGuestEntity;
import com.qiang.modules.sys.entity.UsersEntity;
import com.qiang.modules.sys.entity.VO.UsersVOEntity;
import com.qiang.modules.sys.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.controller
 * @Description: 用户管理
 * @Date: 2019/7/14 0014 16:05
 **/
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("admin")
    public String geAdmin(){
        return "admin";
    }

    /**
     * 用户退出
     * @return
     */
    @GetMapping("logout")
    public BlogJSONResult logout(HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        if(subject != null){
            request.getSession().invalidate();
            subject.logout();
        }
        return BlogJSONResult.ok();
    }

    /**
     * 该用户是否过期
     * @param request
     * @return
     */
    @GetMapping("/isLogin")
    public BlogJSONResult isLogin(HttpServletRequest request){
        UsersVOEntity user = (UsersVOEntity) request.getSession().getAttribute("user");
        if(user == null){
            return BlogJSONResult.errorMsg("用户已过期");
        }
        return BlogJSONResult.ok();
    }

    /**
     * 获取个人信息
     * @param username
     * @return
     */
    @GetMapping("getUserMess")
    public BlogJSONResult getUserMess(@RequestParam("username") String username){
        UsersEntity userMess = userService.findUserMess(username);
        return BlogJSONResult.ok(userMess);
    }

    /**
     * 新增个人信息
     * @param users
     * @return
     */
    @PostMapping("/insUserMess")
    public BlogJSONResult insUserMess(@RequestBody UsersEntity users){
        UsersEntity users1 = userService.insUserMess(users);
        if(users1 != null){
            return BlogJSONResult.ok(users1);
        }else{
            return BlogJSONResult.errorMsg("修改失败");
        }
    }




    /**
     * 我的点赞
     * @return
     */
    @GetMapping("getUserLikes")
    public BlogJSONResult getUserLikes(@RequestParam("username") String username){
        List<CommentLikesEntity> likes = userService.findLikes(username);
        return BlogJSONResult.ok(likes);
    }

    /**
     * 全部点赞消息已读
     * @param username
     * @return
     */
    @GetMapping("clearLikeNotRead")
    public BlogJSONResult clearLikeNotRead(@RequestParam("username") String username){
        List<CommentLikesEntity> reportCommentVOS = userService.updComIsRead(username);
        if(reportCommentVOS != null){
            return BlogJSONResult.ok(reportCommentVOS);
        }
        return BlogJSONResult.errorMsg("错误");
    }




    /**
     * 我的留言（用户）
     * @return
     */
    @GetMapping("getUserGuest")
    public BlogJSONResult getUserGuest(@RequestParam("username") String username){
        List<RepGuestEntity> notRepReadGuest = userService.findNotRepReadGuest(username);
        return BlogJSONResult.ok(notRepReadGuest);
    }


    /**
     * 部分留言已读（用户）
     * @param id 留言id
     * @return
     */
    @GetMapping("clearFirstNotGuestUser")
    public BlogJSONResult clearFirstNotGuestUser(@RequestParam("id") Long id){
        int i = userService.updOneNotGuestUser(id);
        return BlogJSONResult.ok();
    }


    /**
     * 全部留言消息已读（用户）
     * @param username
     * @return
     */
    @GetMapping("clearNotGuest")
    public BlogJSONResult clearNotGuest(@RequestParam("username") String username){
        List<RepGuestEntity> reportCommentVOS = userService.updNotGuest(username);
        if(reportCommentVOS != null){
            return BlogJSONResult.ok(reportCommentVOS);
        }
        return BlogJSONResult.errorMsg("错误");
    }


    /**
     * 我的评论（博客）
     * @return
     */
    @GetMapping("getBlogUserReport")
    public BlogJSONResult getBlogUserReport(){
        List<CommentEntity> allComment = userService.findAllComment();
        return BlogJSONResult.ok(allComment);
    }


    /**
     *  部分博客评论消息已读
     * @return
     */
    @GetMapping("clearOneBlogNotComm")
    public BlogJSONResult clearOneBlogNotComm(@RequestParam("id") Long id){
        userService.updOneBlogNotComm(id);
        return BlogJSONResult.ok();
    }


    /**
     *  部分博客评论消息已读
     * @return
     */
    @GetMapping("clearOneBlogNotLikes")
    public BlogJSONResult clearOneBlogNotLikes(@RequestParam("id") Long id){
        userService.updOneBlogNotLikes(id);
        return BlogJSONResult.ok();
    }


    /**
     * 消息通信(用户)
     * @param username
     * @return
     */
    @GetMapping("messageNotReadUser")
    public BlogJSONResult messageNotReadUser(@RequestParam("username") String username){
        int messageNotRead = userService.findMessageNotReadUser(username);
        return BlogJSONResult.ok(messageNotRead);
    }



}
