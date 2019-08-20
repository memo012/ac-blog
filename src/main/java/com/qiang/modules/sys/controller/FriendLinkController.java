package com.qiang.modules.sys.controller;

import com.qiang.common.utils.BlogJSONResult;
import com.qiang.modules.sys.entity.*;
import com.qiang.modules.sys.entity.VO.UsersVOEntity;
import com.qiang.modules.sys.service.FriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.controller
 * @Description: 友链controller
 * @Date: 2019/8/19 0019 19:14
 **/
@RestController
public class FriendLinkController {

    @Autowired
    private FriendLinkService friendLinkService;




    @GetMapping("/getAllFriendlink")
    public BlogJSONResult getAllFriendlink(){
        List<FriendurlEntity> allFriend = friendLinkService.getAllFriend();
        return BlogJSONResult.ok(allFriend);
    }

    /**
     * 获取全部留言
     * @return
     */
    @GetMapping("/getAllFriend")
    public BlogJSONResult getAllFriend(){
        List<FriendLinkEntity> allGuest = friendLinkService.getAllGuest();
        if(allGuest != null){
            return BlogJSONResult.ok(allGuest);
        }else{
            return BlogJSONResult.errorMsg("无果");
        }
    }

    /**
     * 新增留言
     * @param guest
     * @param request
     * @return
     */
    @PostMapping("/insFriend")
    public BlogJSONResult insFriend(@RequestBody FriendLinkEntity guest,
                                   HttpServletRequest request){
        UsersVOEntity user = (UsersVOEntity) request.getSession().getAttribute("user");
        if(user == null){
            return BlogJSONResult.errorTokenMsg("用户已过期");
        }
        guest.setUserId(user.getId());
        guest.setAuthorName(user.getUsername());
        List<FriendLinkEntity> guests = friendLinkService.insGuest(guest);
        return BlogJSONResult.ok(guests);
    }

    /**
     * 新增留言评论
     * @param repGuest
     * @param request
     * @return
     */
    @PostMapping("/insRepFriend")
    public BlogJSONResult insRepFriend(@RequestBody RepFriendLinkEntity repGuest,
                                      HttpServletRequest request){
        UsersVOEntity user = (UsersVOEntity) request.getSession().getAttribute("user");
        if(user == null){
            return BlogJSONResult.errorTokenMsg("用户已过期");
        }
        repGuest.setRfriendId(user.getId());
        repGuest.setRepName(user.getUsername());
        List<FriendLinkEntity> guests = friendLinkService.insRepGuest(repGuest);
        if(guests != null){
            return BlogJSONResult.ok(guests);
        }else{
            return BlogJSONResult.errorMsg("新增失败");
        }
    }

    /**
     * 点赞更新
     * @param guestLikes
     * @param request
     * @return
     */
    @PostMapping("/updFriendLikes")
    public BlogJSONResult updFriendLikes(@RequestBody FriendLikesEntity guestLikes,
                                        HttpServletRequest request){
        UsersVOEntity user = (UsersVOEntity) request.getSession().getAttribute("user");
        if(user == null){
            return BlogJSONResult.errorTokenMsg("用户已过期");
        }
        guestLikes.setLikeName(user.getUsername());
        boolean isLikes = friendLinkService.findIsLikes(guestLikes);
        if(isLikes){
            // 已点过赞
            List<FriendLinkEntity> guests = friendLinkService.updDesLikes(guestLikes.getFriendId());
            if(guests != null){
                friendLinkService.delIsLikes(guestLikes);
                return BlogJSONResult.ok(guests);
            }else{
                // 点赞失败
                return BlogJSONResult.errorMsg("点赞失败");
            }
        }else{
            // 未点过赞
            List<FriendLinkEntity> guests = friendLinkService.insIsLikes(guestLikes);
            if(guests != null){
                // 点赞成功
                return  BlogJSONResult.ok(guests);
            }else{
                // 点赞失败
                return BlogJSONResult.errorMsg("点赞失败");
            }
        }
    }

}
