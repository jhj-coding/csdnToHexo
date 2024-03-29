package com.github.csccoder.csdn2md.paser;

import com.github.csccoder.csdn2md.model.Article;
import com.github.csccoder.csdn2md.util.PropertiesUtil;
import com.github.csccoder.csdn2md.util.RegexUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ArticlePaser {
    //2017-10-07 23:13
    private static final SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static Article parseArticle(String url){
        Document document=CorePaser.getDocument(url);
        Article article = new Article();

        String articleId=parseArticleId(url);
        String articleTitle=document.select(".article-title-box>h1").text().trim();
        String articleContent=document.select("#article_content").html();
        String[] tags =document.select(".blog-tags-box a").first().text().split(" ");
        String[] category = document.select(".blog-tags-box a").last().text().split(" ");
        String author = PropertiesUtil.getProperties("author");
        article.setAuthor(author);
        article.setId(Integer.parseInt(articleId));
        article.setTitle(articleTitle);
        article.setContent(articleContent);
        article.setCatagory(category);
        try {
//             Elements element = document.select(".article-bar-top>.time");
            Elements element = document.select(".blog-postTime");
            if(element.attr("data-time").equals("")){
                element = document.select(".bar-content .time");
                article.setDate(dateFormat.parse(element.text().substring(2,21)));
            }else {
                article.setDate(dateFormat.parse(element.attr("data-time")));
            }
        } catch (Exception e) {
            System.out.println("应该是被反爬虫了,换个wifi或者网络试试~~~~~~~~~~~~~~~~~~~~~~~");
            e.printStackTrace();
        }
        article.setTags(tags);
        return article;
    }

    private static String parseCata(Document document) {
        Elements elements = document.select(".tags-box>a");
        for(Element element:elements){
            String url=element.attr("href");
            if(url != null && url.trim().length() != 0){
                if(url.lastIndexOf("category") != -1){
                    return element.select(".tag-link").text().trim();
                }
            }
        }
        return null;
    }

    public static String parseArticleId(String uri){
        return RegexUtil.match(".*/(\\d*)",uri,1);
    }

    public static String[] parseTags(Document document){
        ArrayList<String> list = new ArrayList<String>();
        Elements elements = document.select(".artic-tag-box>a");
        for(Element element:elements){
            String text=element.select(".tag-link").text().trim();
            list.add(text);
        }
        return list.toArray(new String[0]);
    }
}
