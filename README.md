

# 功能
概述：利用爬虫爬取指定用户的CSDN博客文章转为md格式，目的是完成博客迁移

## 爬取的方式：
       1 默认轮询从第一页开始往后
       2 专栏方式
       3 指定某篇文章
       4 指定页数
       5 分类
## 设置生成的md文件命名规则：
    可选值:date title ，date根据日期命名，title根据文章名命名

## 设置md文件的头部信息
title=
author=
tags=
categories=
## 是否显示csdn中的锚点"文章目录"字样，以及下面具体的锚点 默认false(因为csdn中是集成了[toc]功能的，hexo并没有集成)
anchor=false
## 是否开启版权声明 默认false(csdn文章头部有我们自定的版权声明，false即为去掉。)
copyright=false

# 工具
[html2markdown](https://github.com/pnikosis/jHTML2Md)
Jsoup

# 提示
- 运行过程中可能出现异常（小概率）： 因为csdn有反爬虫机制，如果迁移过程中出现”应该是被反爬虫了,换个wifi或者网络试试~~~~~~~~~~~~~~~~~~~~~~~ “，那你就照着我打的这个日志去做吧，哈哈
- 运行./start.sh 之前需要给这个脚本赋予权限，执行 `chmod 777 ./start.shg`
- 需要jdk1.8

# 用法：
## 方式一：
git clone 到本地，进入target目录，修改config.properties中的某些配置（视自己情况而修改）
运行
```js
java -jar csdn2hexo-1.0-SNAPSHOT.jar
```
## 方式二：
git clone 到本地，进入target目录，修改config.properties中的某些配置（视自己情况而修改）
运行 ./start.sh a,b,c  1,2,3 articleName    tips：这里的a,b,c 是hexo标签，逗号隔开 1,2,3是hexo分类，逗号隔开 articleName是文章标题，不写的话默认标题是csdn的文章标题。这三个参数如果不写的话默认是配置文件中的内容
## 方式三：
git clone 到本地，通过idea import本project，
修改src/main/resource目录中的config.properties文件（视自己情况而修改）
修改读取配置文件的路径：找到com.github.csccoder.csdn2md.util.PropertiesUtil
```js
package com.github.csccoder.csdn2md.util;

public class PropertiesUtil {
	public static String getProperties(String key){
		String value = null;
		try {
			Properties pp = new Properties();

			//通过idea运行程序
			InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("config.properties");
			//通过java -jar 或者 ./start.sh运行程序
			//String filePath = System.getProperty("user.dir")+ "/config.properties";
			//InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));

			pp.load(inputStream);
			value= (String) pp.get(key);
			return value;
		} catch (IOException e) {
			e.printStackTrace();
			return value;
		}
	}
}
```
找到 Main 这个类运行即可
# 展示

详细效果展示：[利用爬虫爬取指定用户的CSDN博客文章转为md格式，目的是完成博客迁移博文到Hexo等金静态博客](https://blog.csdn.net/dataiyangu/article/details/88637312)


查看头部信息
![image.png](https://upload-images.jianshu.io/upload_images/11496534-c09953e83e0a9172.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


查看头部信息显示效果
![image.png](https://upload-images.jianshu.io/upload_images/11496534-e8d82c5291a5d012.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image.png](https://upload-images.jianshu.io/upload_images/11496534-2806b5fa05c16500.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
# 配置文件信息展示
```js
# md文章头部配置
#分类和标签逗号隔开
title=
author=Leesin.Dong
tags=a,b,c
categories=a,b,c

# 文件命名规则  可选值:date title ，date根据日期命名，title根据文章名命名
MdFileName_type=date


# csdn host 这里如果没有自定义域名的话不用改
csdn_host=https://blog.csdn.net
# csdn用户名
casn_name=dataiyangu


# 文件保存的绝对路径，即img html post这三个文件夹的父文件夹
file_Path=/Users/leesin/Desktop/hexo_blog_md
# 设置下载的文件夹名字
html_path=html
image_path=images
md_path=_posts


# 设置下载的方式，默认是从第一页往后不断的下载的
#可选的方式：
#       1 默认轮询从第一页开始往后
#       2 专栏方式
#       3 指定某篇文章
#       4 指定页数
#       5 分类
url_way=2
# 具体的五种抓取方法的地址填写（视自己情况而定）
# 比如 我的： https://blog.csdn.net/dataiyangu/article/category/8118370
# 下面的几种情况只写 https://blog.csdn.net/dataiyangu 后面的内容即可，后面写什么自己手动点到相应的页面粘贴过来就行了
url_way_1=/article/list/
url_way_2=/column/info/32118
url_way_3=/article/details/88525801
url_way_4=/article/list/2
url_way_5=/article/category/8118370

#是否显示csdn中的锚点"文章目录"字样，以及下面具体的锚点 默认false
anchor=false
#是否开启版权声明 默认false
copyright=false
```
