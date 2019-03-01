package com.q.blog.website.myblog.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.q.blog.website.myblog.dao.ContentVoMapper;
import com.q.blog.website.myblog.dto.Types;
import com.q.blog.website.myblog.model.Vo.ContentVo;
import com.q.blog.website.myblog.service.IContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentServiceImpl implements IContentService {
    private static final Logger log = LoggerFactory.getLogger(ContentServiceImpl.class);

    @Autowired
    private ContentVoMapper contentVoMapper;

    @Override
    public PageInfo<ContentVo> getContents(Integer p, Integer limit) {
        log.info("Enter getContents method");
        PageHelper.startPage(p,limit);
        List<ContentVo> contentVoList = contentVoMapper.selectContentVosByTypeAndStatus(Types.ARTICAL.getType(),Types.PUBLISH.getType());
        PageInfo<ContentVo> pageInfo = new PageInfo<>(contentVoList);
        return pageInfo;
    }

    @Override
    public ContentVo getContents(String id) {
        ContentVo v = contentVoMapper.selectContentVoByPrimaryKey(id);
        return v;
    }
}
