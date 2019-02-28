package com.q.blog.website.myblog.utils;

/**
* @Description:    map实现缓存
* @Author:         qj
* @CreateDate:     2019/2/25 5:47 PM
* @Version:        1.0
*/
public class MapCache {

    private static final MapCache INS = new MapCache();

    public static MapCache single(){
        return INS;
    }
}
