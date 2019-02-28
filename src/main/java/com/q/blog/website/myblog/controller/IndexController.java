package com.q.blog.website.myblog.controller;

import com.github.pagehelper.PageInfo;
import com.q.blog.website.myblog.constant.WebConst;
import com.q.blog.website.myblog.model.Vo.ContentVo;
import com.q.blog.website.myblog.service.IContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
* @Description:    首页
* @Author:         qj
* @CreateDate:     2019/2/25 4:09 PM
* @Version:        1.0
*/
@Controller
public class IndexController extends BaseController{
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IContentService contentService;

    /**
     * 首页
     * @return
     */
    @GetMapping(value = "/")
    public String index(HttpServletRequest request,
                        @RequestParam(value = "limit" , defaultValue = "12") int limit){

        return this.index(request,1,limit);
    }

    /**
     *
     * @param request
     * @param p       第几页
     * @param limit   每页大小
     * @return 主页
     */
    @GetMapping(value = "page/{p}")
    public String index(HttpServletRequest request,
                        @PathVariable int p,
                        @RequestParam(value = "limit" , defaultValue = "12") int limit){
        p = p < 0 || p > WebConst.MAX_PAGE ? 1 : p;
        PageInfo<ContentVo> articles = contentService.getContents(p,limit);
        request.setAttribute("articles",articles);
        if(p > 1){
            this.title(request,"第" + p + "页");
        }
        return this.render("index");
    }
}
