package com.q.blog.website.myblog.utils;

import com.q.blog.website.myblog.constant.WebConst;
import com.q.blog.website.myblog.model.Vo.ContentVo;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
* @Description:    主题公共函数
* @Author:         qj
* @CreateDate:     2019/2/26 5:03 PM
* @Version:        1.0
*/
@Component
public class Commons {

    public static String THEME = "themes/default";

    /**
    * 网站链接
    * @author      qj
    * @date        2019/2/26 5:10 PM
    */
    public static String site_url(){
        return site_url("/");
    }

    /**
    * 返回网站链接下的全地址
    * @author      qj
    * @date        2019/2/26 5:10 PM
    */
    public static String site_url(String sub){
        return site_option("site_url") + sub;
    }

    /**
    * 网站标题
    * @author      qj
    * @date        2019/2/27 3:31 PM
    */
    public static String site_title() {
        return site_option("site_title");
    }

    /**
    * 网站配置项
    * @author      qj
    * @date        2019/2/26 5:54 PM
    */
    public static String site_option(String key){
        return site_option(key,"");
    }

    /**
    * 网站配置项
    * @author      qj
    * @date        2019/2/26 5:12 PM
    */
    public static String site_option(String key,String defaultValue){
        if(StringUtils.isBlank(key)){
            return "";
        }
        String str = WebConst.initConfig.get(key);
        if(StringUtils.isNotBlank(str)){
            return str;
        }else {
            return defaultValue;
        }
    }

    /**
    * 返回文章链接地址
    * @author      qj
    * @date        2019/2/27 5:12 PM
    */
    public static String permalink(ContentVo contentVo){
        return permLink(contentVo.getCid(), contentVo.getSlug());
    }

    /**
     * 返回文章链接地址
     * @author      qj
     * @date        2019/2/27 5:12 PM
     */
    public static String permLink(Integer cid, String slug){
        return site_url("/article/" + (StringUtils.isNotBlank(slug) ? slug : cid.toString()));
    }

    /**
    * 方法实现说明
    * @author      qj
    * @return
    * @exception
    * @date        2019/3/1 10:17 AM
    */
    public static String fmtdata(Integer unixTime){
        return fmtdata(unixTime,"yyyy-MM-DD");
    }

    public static String fmtdata(Integer unixTime, String patten){
        if(null != unixTime && StringUtils.isNotBlank(patten)){
            return DateKit.formatDateByUnixTime(unixTime,patten);
        }
        return "";
    }

    /**
     * 显示文章缩略图，顺序为：文章第一张图 -> 随机获取
     *
     * @return
     */
    public static String show_thumb(ContentVo contents) {
        int cid = contents.getCid();
        int size = cid % 20;
        size = size == 0 ? 1 : size;
        return "/user/img/rand/" + size + ".jpg";
    }

    /**
    * 显示分类
    * @author      qj
    * @date        2019/2/27 3:48 PM
    */
    public static String show_categories(String categories) throws UnsupportedEncodingException {
        if(StringUtils.isNotBlank(categories)){
            String[] attr = categories.split(",");
            StringBuffer sb = new StringBuffer();
            for (String s : attr){
                sb.append("<a href=\"/category/" + URLEncoder.encode(s, "UTF-8") + "\">" + s + "</a>");
            }
            return sb.toString();
        }
        return show_categories("默认分类");
    }

    /**
    * 显示标签
    * @author      qj
    * @return
    * @date        2019/3/1 11:16 AM
    */
    public static String show_tags(String tags) throws UnsupportedEncodingException {
        if(StringUtils.isNotBlank(tags)){
            String[] attr = tags.split(",");
            StringBuffer sb = new StringBuffer();
            for (String c : attr){
                sb.append("<a href=\"/tag/" + URLEncoder.encode(c, "UTF-8") + "\">" + c + "</a>");
            }
            return sb.toString();
        }
        return "";
    }

    /**
    * 显示文章内容，转换markdown为html
    * @author      qj
    * @exception
    * @date        2019/3/1 11:31 AM
    */
    public static String article(String value){
        if (StringUtils.isNotBlank(value)) {
            value = value.replace("<!--more-->", "\r\n");
            return TaleUtils.mdToHtml(value);
        }
        return "";
    }

    /**
     * An :grinning:awesome :smiley:string &#128516;with a few :wink:emojis!
     * <p>
     * 这种格式的字符转换为emoji表情
     *
     * @param value
     * @return
     */
    public static String emoji(String value) {
        return EmojiParser.parseToUnicode(value);
    }

    private static final String[] ICONS = {"bg-ico-book", "bg-ico-game", "bg-ico-note", "bg-ico-chat", "bg-ico-code", "bg-ico-image", "bg-ico-web", "bg-ico-link", "bg-ico-design", "bg-ico-lock"};

    /**
     * 显示文章图标
     *
     * @param cid
     * @return
     */
    public static String show_icon(int cid) {
        return ICONS[cid % ICONS.length];
    }

    /**
    * 获取社交网站外链
    * @author      qj
    * @date        2019/2/27 3:05 PM
    */
    public static Map<String, String> social() {
        final String prefix = "social_";
        Map<String, String> map = new HashMap<>();
        map.put("weibo", WebConst.initConfig.get(prefix + "weibo"));
        map.put("zhihu", WebConst.initConfig.get(prefix + "zhihu"));
        map.put("github", WebConst.initConfig.get(prefix + "github"));
        map.put("twitter", WebConst.initConfig.get(prefix + "twitter"));
        return map;
    }

}
