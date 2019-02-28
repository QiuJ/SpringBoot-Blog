package com.q.blog.website.myblog.interceptor;

import com.q.blog.website.myblog.utils.Commons;
import com.q.blog.website.myblog.utils.MapCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
* @Description:    自定义拦截器
* @Author:         qj
* @CreateDate:     2019/2/26 5:29 PM
* @Version:        1.0
*/
@Component
public class BaseInterceptor implements HandlerInterceptor{

    private static final Logger log = LoggerFactory.getLogger(BaseInterceptor.class);
    private static final String USER_AGENT = "user_agent";


    private MapCache cache = MapCache.single();

    @Resource
    private Commons commons;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception{
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView){
        request.setAttribute("commons",commons);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){

    }

}
