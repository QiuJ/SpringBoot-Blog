package com.q.blog.website.myblog.service;

import com.github.pagehelper.PageInfo;
import com.q.blog.website.myblog.model.Bo.CommentBo;
import com.q.blog.website.myblog.model.Vo.CommentVo;

public interface ICommentService {


    /**
     * 保存对象
     * @param commentVo
     */
    String insertComment(CommentVo commentVo);

    /**
    * 方法实现说明
    * @author      qj
    * @param cid
    * @return
    * @exception
    * @date        2019/3/4 10:57 AM
    */
    PageInfo<CommentBo> getComments(Integer cid, int page, int limit);
}
