package com.qiang.modules.sys.controller;

import com.qiang.common.utils.BlogJSONResult;
import com.qiang.common.utils.CommonUtils;
import com.qiang.common.utils.Constant;
import com.qiang.common.utils.RedisOperator;
import com.qiang.modules.sys.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.controller
 * @Description: 首页
 * @Date: 2019/8/3 0003 10:43
 **/
@RestController
public class IndexController {

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private IndexService indexService;


    /**
     * 文章总数
     * @return
     */
    @GetMapping("myArticlesCount")
    public BlogJSONResult myArticlesCount(){
        Long count = 0L;
        if(redisOperator.hasKey(Constant.BLOG_DETAIL)){
            count = redisOperator.hsize(Constant.BLOG_DETAIL);
        }else{
            count = indexService.myArticlesCount();
        }
        return BlogJSONResult.ok(count);
    }

    /**
     * 标签总数
     * @return
     */
    @GetMapping("myLabelsCount")
    public BlogJSONResult myLabelsCount(){
        Integer count = 0;
        if(redisOperator.hasKey(Constant.LABEL_ALL_COUNT)){
            count = (int)redisOperator.get(Constant.LABEL_ALL_COUNT);
        }else{
            count = indexService.myLabelsCount();
        }
        return BlogJSONResult.ok(count);
    }


    /**
     * 评论总数
     * @return
     */
    @GetMapping("myReportsCount")
    public BlogJSONResult myReportsCount(){
        Integer count = 0;
        if(redisOperator.hasKey(Constant.BLOG_REPORT_COUNT)){
            count = (Integer) redisOperator.get(Constant.BLOG_REPORT_COUNT);
        }else{
            count = indexService.myReportsCount();
        }

        return BlogJSONResult.ok(count);
    }

    /**
     * 留言总数
     * @return
     */
    @GetMapping("myGuestCount")
    public BlogJSONResult myGuestCount(){
        Integer count = 0;
        if(redisOperator.hasKey(Constant.BLOG_GUEST_COUNT)){
            count = (Integer)redisOperator.get(Constant.BLOG_GUEST_COUNT);
        }else{
            count = indexService.myGuestCount();
        }
        return BlogJSONResult.ok(count);
    }

    /**
     * 访客量
     * @return
     */
    @GetMapping("visitCount")
    public BlogJSONResult visitCount(@RequestParam("pageName") String pageName){
        Integer count = 0;
        // 有N个页面不需要添加访问量
        if(CommonUtils.pageNameIsAdd(pageName)) {
            if (redisOperator.hasKey(Constant.BLOG_VISIT_COUNT)) {
                count = (Integer)redisOperator.get(Constant.BLOG_VISIT_COUNT);
            }else {
                count = indexService.myWebCount();
            }
        }else {
            if (redisOperator.hasKey(Constant.BLOG_VISIT_COUNT)) {
                redisOperator.incr(Constant.BLOG_VISIT_COUNT, 1);
                count = (Integer) redisOperator.get(Constant.BLOG_VISIT_COUNT);
            }else {
                count = indexService.myWebCount();
            }
        }
        return BlogJSONResult.ok(count);
    }
    


}
