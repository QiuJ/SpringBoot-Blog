package com.q.blog.website.myblog.model.Bo;

import com.q.blog.website.myblog.model.Vo.ContentVo;

import java.io.Serializable;
import java.util.List;

/**
* @Description:    存档
* @Author:         qj
* @CreateDate:     2019/2/25 4:18 PM
* @Version:        1.0
*/
public class ArchiveBo implements Serializable{

    private String date;
    private String count;
    List<ContentVo> list;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<ContentVo> getList() {
        return list;
    }

    public void setList(List<ContentVo> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ArchiveBo{" +
                "date='" + date + '\'' +
                ", count='" + count + '\'' +
                ", list=" + list +
                '}';
    }
}
