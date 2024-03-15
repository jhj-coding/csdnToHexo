package com.github.csccoder.csdn2md.util;

import com.github.csccoder.csdn2md.model.Article;
import com.github.csccoder.csdn2md.util.html2markdown.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import static java.io.File.separator;

public class HexoMdUtil {
    private static final SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     *
     * 适配hexo头部
     *
     ---
     title: hexo deploy时重复输入用户名密码的问题
     date: 2017-12-12 19:17:34
     tags: hexo
     ---
     */

    public static String getHeader(Article article){
        String separator = System.getProperty("line.separator");
        String article_title = article.getTitle();
        String[] article_tags = article.getTags();
        StringBuilder hexo_tags = new StringBuilder(separator);
        String[] article_catagory = article.getCatagory();
        StringBuilder hexo_categories = new StringBuilder(separator);
        for (String tag : article_tags) {
            hexo_tags.append("       - ").append(tag).append(separator);
        }
        for (String category : article_catagory) {
            hexo_categories.append("       - ").append(category).append(separator);
        }
        String title = PropertiesUtil.getProperties("title");
        if (!("").equals(title)){
            article_title = title;
        }

        StringBuilder sb=new StringBuilder();
        sb.append("---\n").
                append(String.format("title: %s\n",article_title)).
                append(String.format("author: %s\n",article.getAuthor())).
                append("tags: "+hexo_categories+"\n").
                append("category: "+hexo_tags+"\n").
                append(String.format("date: %s\n",dateFormat.format(article.getDate()))).
                append("---\n");
        return sb.toString();
    }

    public static String array2String(String[] array){
        String str="";
        for(String temp:array){
            str+=temp;
        }
        return str;
    }



}
