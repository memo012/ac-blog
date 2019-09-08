package com.qiang.modules.sys.controller;

import com.qiang.common.utils.Constant;
import com.qiang.common.utils.RedisOperator;
import com.qiang.common.utils.TransCodingUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @author: qiang
 * @Date: 2019/7/06 19:24
 * Describe: 所有页面跳转
 */
@Controller
public class BackController {

    @Autowired
    private RedisOperator redisOperator;


    /**
     * 首页
     *
     * @return
     */
    @GetMapping(value = {"/", "index"})
    public String index() {
        return "index";
    }

    /**
     * 保存上一次浏览地址
     * @return
     */
    @GetMapping("lasturl")
    public String lasturl(HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin", "*");
        String url = (String) request.getSession().getAttribute("lastUrl");
        if (!StringUtils.isEmpty(url)) {
            response.setHeader("lastUrl", url);
            return "lasturl";
        }else{
            return "index";
        }
    }

    /**
     * 寻找密码
     *
     * @return
     */
    @GetMapping("/findPwd")
    public String findPwd() {
        return "findPwd";
    }

    /**
     * 关于我
     *
     * @return
     */
    @GetMapping("/aboutme")
    public String aboutme(HttpServletRequest request) {
        request.getSession().removeAttribute("lastUrl");
        return "aboutme";
    }


    /**
     * 后台管理界面
     *
     * @return
     */
    @GetMapping("SuperAdmin")
    public String SuperAdmin() {
        return "SuperAdmin";
    }

    /**
     * 登录页面
     *
     * @return
     */
    @GetMapping("login")
    public String login(HttpServletRequest request) {
        String url = request.getHeader("Referer");
        if(StringUtils.isEmpty(url)){
            return "login";
        }
        if (!url.contains("register") && !url.contains("findPwd") && !url.contains("login")) {
            //保存跳转页面的url
            request.getSession().setAttribute("lastUrl", request.getHeader("Referer"));
        }else{
            request.getSession().removeAttribute("lastUrl");
        }
        return "login";
    }

    /**
     * 登录前尝试保存上一个页面的url
     */
    @GetMapping("/toLogin")
    public @ResponseBody
    void toLogin(HttpServletRequest request) {
    }

    /**
     * 搜索页面
     *
     * @return
     */
    @GetMapping("/es/{message}")
    public String elasticsearch(@PathVariable("message") String message, HttpServletResponse response
            , HttpServletRequest request) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("message", TransCodingUtil.stringToUnicode(message));
        request.getSession().removeAttribute("lastUrl");
        return "/elasticsearch";
    }

    /**
     * 注册页面
     *
     * @return
     */
    @GetMapping("register")
    public String register() {
        return "register";
    }

    /**
     * 友链页面
     *
     * @return
     */
    @GetMapping("friendlink")
    public String friendlink() {
        return "friendlink";
    }

    /**
     * 博客发布页面
     *
     * @return
     */
    @GetMapping("editor")
    public String editor(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (!"".equals(id)) {
            request.getSession().setAttribute("id", id);
        }
        return "editor";
    }

    /**
     * 归档页面
     *
     * @return
     */
    @GetMapping("/archive")
    public String archive(HttpServletRequest request) {
        request.getSession().removeAttribute("lastUrl");
        return "archive";
    }

    /**
     * 更新页面
     *
     * @return
     */
    @GetMapping("/update")
    public String update(HttpServletRequest request) {
        request.getSession().removeAttribute("lastUrl");
        return "update";
    }


    /**
     * 跳转文章详情页面
     *
     * @param articleId
     * @return
     */
    @GetMapping("/article/{articleId}")
    public String show(@PathVariable("articleId") long articleId
            , HttpServletResponse response, HttpServletRequest request) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //将文章id存入响应头
        response.setHeader("articleId", String.valueOf(articleId));
        request.getSession().removeAttribute("lastUrl");
        return "show";
    }

    /**
     * 查过发布时间查询
     *
     * @param createTime
     * @param response
     * @return
     */
    @GetMapping("/time/{createTime}")
    public String time(@PathVariable("createTime") String createTime
            , HttpServletResponse response, HttpServletRequest request) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //将文章id存入响应头
        response.setHeader("createTime", String.valueOf(createTime));
        request.getSession().removeAttribute("lastUrl");
        return "time";
    }

    /**
     * 标签归档
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("tags")
    public String tags(HttpServletRequest request
            , HttpServletResponse response) {

        try {
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            String tag = request.getParameter("tag");
            //将文章id存入响应头
            response.setHeader("tag", TransCodingUtil.stringToUnicode(tag));
        } catch (UnsupportedEncodingException e) {
        }
        request.getSession().removeAttribute("lastUrl");
        return "tags";
    }

    /**
     * 文章归类查询
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("categories")
    public String categories(HttpServletRequest request
            , HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            String cate = request.getParameter("categorie");
            response.setHeader("categories", TransCodingUtil.stringToUnicode(cate));
        } catch (UnsupportedEncodingException e) {

        }
        request.getSession().removeAttribute("lastUrl");
        return "categories";
    }

    /**
     * 留言板
     *
     * @return
     */
    @GetMapping("/guest")
    public String guest(HttpServletRequest request) {
        request.getSession().removeAttribute("lastUrl");
        return "guest";
    }

    /**
     * 个人中心
     *
     * @return
     */
    @GetMapping("/user")
    public String user() {
        return "user";
    }


}
