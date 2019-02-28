package com.q.blog.website.myblog.service;

import com.github.pagehelper.PageInfo;
import com.q.blog.website.myblog.model.Vo.ContentVo;

public interface IContentService {

    /**
    * 方法实现说明
    * @author      qj
    *查询文章返回多条数据
    * @param p 当前页
    * @param limit 每页条数
    * @return ContentVo
    * @date        2019/2/25 8:23 PM
    */
    PageInfo<ContentVo> getContents(Integer p, Integer limit);
}
