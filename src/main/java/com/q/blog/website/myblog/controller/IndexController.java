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
import javax.websocket.server.PathParam;

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
     * 首页
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

    /**
    * 文章详情页
    * @param cid
    * @author      qj
    * @date        2019/2/28 11:24 AM
    */
    @GetMapping(value = {"article/{cid}","article/{cid}.html"})
    public String getArticle(HttpServletRequest request, @PathVariable String cid){
        ContentVo contents = contentService.getContents(cid);
        if(null == contents || "draft".equals(contents.getStatus())){
            return this.render_404();
        }
        request.setAttribute("article", contents);
        request.setAttribute("is_post", true);
        return this.render("post");
    }
}
