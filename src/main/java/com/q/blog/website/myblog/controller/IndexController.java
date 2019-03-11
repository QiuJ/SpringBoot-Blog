package com.q.blog.website.myblog.controller;

import com.github.pagehelper.PageInfo;
import com.q.blog.website.myblog.constant.WebConst;
import com.q.blog.website.myblog.model.Bo.CommentBo;
import com.q.blog.website.myblog.model.Bo.RestResponseBo;
import com.q.blog.website.myblog.model.Vo.CommentVo;
import com.q.blog.website.myblog.model.Vo.ContentVo;
import com.q.blog.website.myblog.service.ICommentService;
import com.q.blog.website.myblog.service.IContentService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private ICommentService commentService;

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
        completeArticle(request, contents);
        request.setAttribute("article", contents);
        request.setAttribute("is_post", true);
        return this.render("post");
    }

    /**
    * 抽取公共方法
    * @author      qj
    * @date        2019/3/1 6:00 PM
    */
    private void completeArticle(HttpServletRequest request, ContentVo contents){
        if(contents.getAllowComment()){
            String cp = request.getParameter("cp");
            if(StringUtils.isBlank(cp)){
                cp = "1";
            }
            request.setAttribute("cp",cp);
            PageInfo<CommentBo> commentsPaginator = commentService.getComments(contents.getCid(),Integer.parseInt(cp),6);
            request.setAttribute("comments", commentsPaginator);
        }
    }

    /**
    * 评论操作
    * @author      qj
    * @date        2019/3/5 3:40 PM
    */
    @PostMapping("comment")
    @ResponseBody
    public RestResponseBo comment(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam Integer cid, @RequestParam Integer coid,
                                  @RequestParam String author, @RequestParam String mail,
                                  @RequestParam String url, @RequestParam String text, @RequestParam String _csrf_token){
        CommentVo commentVo = new CommentVo();
        commentVo.setCid(cid);
        commentVo.setCoid(coid);
        commentVo.setAuthor(author);
        commentVo.setMail(mail);
        commentVo.setUrl(url);
        commentVo.setContent(text);
        commentVo.setIp(request.getRemoteAddr());
        commentService.insertComment(commentVo);
        return RestResponseBo.ok();
    }
}
