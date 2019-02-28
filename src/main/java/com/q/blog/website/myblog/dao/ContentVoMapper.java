package com.q.blog.website.myblog.dao;

import com.q.blog.website.myblog.model.Vo.ContentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContentVoMapper {

    List<ContentVo> selectContentVoByTypeAndStatus(@Param("type") String type, @Param("status") String status);
}
