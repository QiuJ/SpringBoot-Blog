package com.q.blog.website.myblog.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.q.blog.website.myblog.constant.WebConst;
import com.q.blog.website.myblog.dao.CommentVoMapper;
import com.q.blog.website.myblog.model.Bo.CommentBo;
import com.q.blog.website.myblog.model.Vo.CommentVo;
import com.q.blog.website.myblog.model.Vo.ContentVo;
import com.q.blog.website.myblog.service.ICommentService;
import com.q.blog.website.myblog.service.IContentService;
import com.q.blog.website.myblog.utils.DateKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService{

    @Autowired
    private CommentVoMapper commentVoMapper;

    @Autowired
    private IContentService contentService;

    @Autowired
    private CommentVoMapper commentDao;

    @Override
    @Transactional
    public String insertComment(CommentVo comments) {
        ContentVo contents = contentService.getContents(String.valueOf(comments.getCid()));
        if(null == contents){
            return "不存在的文章";
        }
        comments.setOwnerId(contents.getAuthorId());
        comments.setStatus("not_audit");// todo 枚举
        comments.setCreated(DateKit.getCurrentUnixTime());
        commentDao.insertSelective(comments);

        ContentVo contentVoTemp = new ContentVo();
        contentVoTemp.setCid(contents.getCid());
        contentVoTemp.setCommentsNum(contents.getCommentsNum() + 1);
        contentService.updateContentByCid(contentVoTemp);
        return WebConst.SUCCESS_RESULT;
    }

    @Override
    public PageInfo<CommentBo> getComments(Integer cid, int page, int limit) {
        if(null != cid){
            PageHelper.startPage(page,limit);
            List<CommentVo> parents = commentVoMapper.selectByCidAndStatus(String.valueOf(cid),"approved");
            PageInfo<CommentVo> commentPaginator = new PageInfo<>(parents);
            PageInfo<CommentBo> returnBo = copyPageInfo(commentPaginator);
            if(parents.size() != 0){
                List<CommentBo> comments = new ArrayList<>(parents.size());
                parents.forEach(parent -> {
                    CommentBo comment = new CommentBo(parent);
                    comments.add(comment);
                });
                returnBo.setList(comments);
            }
            return returnBo;
        }
        return null;
    }

    /**
     * copy原有的分页信息，除数据
     *
     * @param ordinal
     * @param <T>
     * @return
     */
    private <T> PageInfo<T> copyPageInfo(PageInfo ordinal) {
        PageInfo<T> returnBo = new PageInfo<T>();
        returnBo.setPageSize(ordinal.getPageSize());
        returnBo.setPageNum(ordinal.getPageNum());
        returnBo.setEndRow(ordinal.getEndRow());
        returnBo.setTotal(ordinal.getTotal());
        returnBo.setHasNextPage(ordinal.isHasNextPage());
        returnBo.setHasPreviousPage(ordinal.isHasPreviousPage());
        returnBo.setIsFirstPage(ordinal.isIsFirstPage());
        returnBo.setIsLastPage(ordinal.isIsLastPage());
        returnBo.setNavigateFirstPage(ordinal.getNavigateFirstPage());
        returnBo.setNavigateLastPage(ordinal.getNavigateLastPage());
        returnBo.setNavigatepageNums(ordinal.getNavigatepageNums());
        returnBo.setSize(ordinal.getSize());
        returnBo.setPrePage(ordinal.getPrePage());
        returnBo.setNextPage(ordinal.getNextPage());
        return returnBo;
    }
}
