package com.github.csccoder.csdn2md.paser;

import com.github.csccoder.csdn2md.model.Article;
import com.github.csccoder.csdn2md.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.util.ArrayList;
import java.util.List;


public class CorePaser {


	/**
	 * 解析博客的入口函数
	 *
	 * @param host   csdn域名
	 * @param author csdn账号
	 */
	public void parse(String host, String author, String path, boolean img) {
		Document document;
		FileUtil fileUtil = new FileUtil(path, img);
		int recordCount = 1;
		int pageCount = 1;
		List<String> uris = new ArrayList<String>();
		while (pageCount==1) {




			System.out.println("正在爬取第" + pageCount + "页");
			try {
				int url_way = Integer.parseInt(PropertiesUtil.getProperties("url_way"));

				String url_url_way_1 = PropertiesUtil.getProperties("url_way_1");
				String url_url_way_2 = PropertiesUtil.getProperties("url_way_2");
				String url_url_way_3 = PropertiesUtil.getProperties("url_way_3");
				String url_url_way_4 = PropertiesUtil.getProperties("url_way_4");
				String url_url_way_5 = PropertiesUtil.getProperties("url_way_5");

				switch (url_way) {
					case 1:
						document = getDocument(host + "/" + author + url_url_way_1 + pageCount);
						uris = parseArticleURIs(document);
						break;
					case 2:
						document = getDocument(host + "/" + author + url_url_way_2);
						uris = parseArticleURIsOfZhuanLan(document);
						break;
					case 3:
						uris.add(host + "/" + author + url_url_way_3);
						recordCount=1;
						break;
					case 4:
						document = getDocument(host + "/" + author + url_url_way_4);
						uris = parseArticleURIs(document);
						break;
					case 5:
						document = getDocument(host + "/" + author + url_url_way_5);
						uris = parseArticleURIsOfCategory(document);
						break;
					default:
						break;
				}

				if (uris.size() == 0) {
					break;
				}
				for (String uri : uris) {
					if (uri.indexOf(author) <= 0) {
						System.out.println("网址中没有包含用户名");
						continue;
					}
					// 核心
					Article article = ArticlePaser.parseArticle(uri);
					System.out.println("第" + recordCount + "篇  =>" + article.getId() + "  " + article.getTitle());
					// 核心
					fileUtil.html2HexoMd(article);
					recordCount++;
				}
				pageCount++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}


	public static Document getDocument(String url) {
		String content = HttpClientUtil.get(url);
		Document document = Jsoup.parse(content);
		return document;
	}


	public ArrayList<String> parseArticleURIs(Document document) {
		ArrayList<String> ids = new ArrayList();
		Elements elements = document.select(".article-item-box>h4>a");
		for (Element element : elements) {
			ids.add(element.attr("href"));
		}
		return ids;
	}

	public ArrayList<String> parseArticleURIsOfZhuanLan(Document document) {
		ArrayList<String> ids = new ArrayList();
		Elements elements = document.select(".column_article_list>li>a");
		for (Element element : elements) {
			ids.add(element.attr("href"));
		}
		return ids;
	}

	//	这里其实和上面的parseArticleURIs 实现是一样的，为了区分，重新写一个方法把，只是名字不一样而已。
	public ArrayList<String> parseArticleURIsOfCategory(Document document) {
		ArrayList<String> ids = new ArrayList();
		Elements elements = document.select(".column_article_list a");
		for (Element element : elements) {
			ids.add(element.attr("href"));
		}
		return ids;
	}

	public int getRecordCount(String papeList) {
		String value = RegexUtil.match("\\s*?(\\d*)条.*", papeList, 1);
		return value == null ? 0 : Integer.parseInt(value);
	}

	public int getPageCount(String pageList) {
		String value = RegexUtil.match(".*共(\\d*)页", pageList, 1);
		return value == null ? 0 : Integer.parseInt(value);
	}




}
