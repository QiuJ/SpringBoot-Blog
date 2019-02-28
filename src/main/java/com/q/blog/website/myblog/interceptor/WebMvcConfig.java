package com.q.blog.website.myblog.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class WebMvcConfig extends WebMvcConfigurerAdapter{

    @Autowired
    private BaseInterceptor baseInterceptor;

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(baseInterceptor);
    }
}
