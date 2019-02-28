package com.q.blog.website.myblog.controller;

import com.q.blog.website.myblog.utils.MapCache;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    private static String THEME = "themes/default";

    protected MapCache cache = MapCache.single();

    /**
     * 主页的页面主题
     * @param viewName
     * @return
     */
    public String render(String viewName){
        return THEME + "/" + viewName;
    }

    public BaseController title(HttpServletRequest request, String title){
        request.setAttribute("title",title);
        return this;
    }
}
