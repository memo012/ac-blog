package com.qiang.modules.sys.controller;

import com.qiang.common.utils.BlogJSONResult;
import com.qiang.common.utils.PagedResult;
import com.qiang.common.utils.TransCodingUtil;
import com.qiang.modules.sys.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.controller
 * @Description: 文章归类
 * @Date: 2019/7/10 0010 16:42
 **/
@RestController
public class CategoriesController {

    @Autowired
    private ArticleService articleService;

    /**
     * 文章归类查询
     * @param pageSize
     * @param pageNum
     * @param categorie
     * @return
     */
    @GetMapping("/getCategories")
    public BlogJSONResult getCategories(@RequestParam("pageSize") int pageSize
                                        , @RequestParam("pageNum") int pageNum
                                        , @RequestParam("categorie") String categorie){

        String categories = TransCodingUtil.unicodeToString(categorie);
        PagedResult byCategories = articleService.findByCategories(pageNum, pageSize, categories);
        return BlogJSONResult.ok(byCategories);
    }
}
