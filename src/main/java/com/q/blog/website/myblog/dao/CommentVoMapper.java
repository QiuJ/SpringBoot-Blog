package com.q.blog.website.myblog.dao;

import com.q.blog.website.myblog.model.Vo.CommentVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CommentVoMapper {

    List<CommentVo> selectByCidAndStatus(@Param("cid") String cid, @Param("status") String status);

    int insertSelective(CommentVo record);
}
