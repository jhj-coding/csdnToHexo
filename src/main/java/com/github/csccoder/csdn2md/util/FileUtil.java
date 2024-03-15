package com.github.csccoder.csdn2md.util;

import com.github.csccoder.csdn2md.model.Article;
import com.github.csccoder.csdn2md.util.html2markdown.HTML2Md;


import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

public class FileUtil {
    private static String dir;
    private static String htmlDir;
    private static String mdDir;
    private static String imgDir;
    private static boolean img;
    private static Pattern FilePattern = Pattern.compile("[\\s\\\\/:\\*\\?\\\"<>\\|]");

    public FileUtil(String path, boolean imgSwitch) {
        if (path == null) return;
        dir = path;
        String html_path = PropertiesUtil.getProperties("html_path");
        String image_path = PropertiesUtil.getProperties("image_path");
        String md_path = PropertiesUtil.getProperties("md_path");
        htmlDir = path + "/" + html_path + "/";
        mdDir = path + "/" + md_path + "/";
        imgDir = path + "/" + image_path + "/";
        if (img) img = false;
        else img = imgSwitch;
        judeDirExists(dir, htmlDir, mdDir, imgDir);
    }

    private void save(String content, String filePath) {
        File file = new File(filePath);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void saveHtml(Article article) {
        String fileName = article.getTitle();
        if ("".equals(fileName)) return;
        fileName = (fileName == null ? null : FilePattern.matcher(fileName).replaceAll("")); //过滤文件名特殊字符
        String filePath = htmlDir + fileName;
        save(article.getContent(), filePath);

    }

    private void saveHexomd(Article article) {
        String mdhead = "";
        Boolean head = Boolean.valueOf(PropertiesUtil.getProperties("head"));
        if (head) {
            mdhead = HexoMdUtil.getHeader(article);
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fileName = "";
            String MdFileName = "";
            fileName = article.getTitle();
            fileName = (fileName == null ? null : FilePattern.matcher(fileName).replaceAll("")); //过滤文件名特殊字符
            String mdContent = HTML2Md.convertFile(new File(htmlDir + fileName), "utf-8");
            String realContent = mdhead + mdContent;

            String MdFileName_type = PropertiesUtil.getProperties("MdFileName_type");
            if (MdFileName_type.equals("date")) {
                MdFileName = dateFormat.format(article.getDate()).replace(" ", "-").replaceAll(":", "-");
            } else {
                MdFileName = article.getTitle();
            }
            String filePath = mdDir + MdFileName + ".md";
            save(realContent, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void html2HexoMd(Article article) {
        saveHtml(article);
        saveHexomd(article);
    }

    public static String getPicture(String url) {
        String fix = null;
        if (img && !url.equals("")) {
            URL ur;
//            https://img-blog.csdnimg.cn/20190316212631882.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RhdGFpeWFuZ3U=,size_16,color_FFFFFF,t_70
            BufferedInputStream in;
            ByteArrayOutputStream outStream;
            try {
                // fix = url.substring(url.lastIndexOf("img-blog.csdnimg.cn") + 20, url.length());
                // fix = fix.replace("/", "-");
                // fix = fix.replace(".png", "-");
                // fix = fix.replace("?", "-");
                // fix = fix + ".png";

                // 直接uuid，csdn的太长
                fix = UUID.randomUUID() + ".png";

                String fileName = UUID.randomUUID().toString();
                ur = new URL(url);
                in = new BufferedInputStream(ur.openStream());
                outStream = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int length = 0;
                while ((length = in.read(buf, 0, buf.length)) != -1) {
                    outStream.write(buf, 0, length);
                }
                byte[] bytes = outStream.toByteArray();
                if (!"".equals(fix)) {
                    if (!fix.contains(".")) {
                        fix = fix + "." + GetFileSuffix(bytes);
                    }
                }


                File fileOut = new File(imgDir + fix);
                FileOutputStream op = new FileOutputStream(fileOut);

                op.write(bytes);
                op.close();
                in.close();
                outStream.close();
                return fix;
            } catch (Exception e) {
                e.printStackTrace();
                return fix;
            }
        }
        return fix;
    }

    private static String GetFileSuffix(byte[] fileData) {
        if (fileData == null || fileData.length < 10) {
            return null;
        }

        if (fileData[0] == 'G' && fileData[1] == 'I' && fileData[2] == 'F') {
            return "gif";
        } else if (fileData[1] == 'P' && fileData[2] == 'N' && fileData[3] == 'G') {
            return "png";
        } else if (fileData[6] == 'J' && fileData[7] == 'F' && fileData[8] == 'I' && fileData[9] == 'F') {
            return "jpg";
        } else if (fileData[0] == 'B' && fileData[1] == 'M') {
            return "bmp";
        } else {
            return null;
        }
    }

    // 判断文件夹是否存在
    public static void judeDirExists(String dirRoot, String htmlDirPath, String mdDirPath, String imgDirPath) {
        try {
            File file = new File(dirRoot);
            if (!file.exists()) {
                if (!file.isDirectory()) {
                    File dirR = new File(dirRoot);
                    if (dirR.mkdirs()) {
                        System.out.println("已创建根目录！");
                    }
                }
            }

            File htmlDirP = new File(htmlDirPath);
            File mdDirP = new File(mdDirPath);
            File imgDirP = new File(imgDirPath);
            if (htmlDirP.mkdirs() && mdDirP.mkdirs() && imgDirP.mkdirs()) {
                System.out.println("目录创建成功！");
            } else {
                System.out.println(htmlDirP.mkdirs());
                System.out.println("目录已经存在，如果还是不行请尝试删掉根目录下所有文件");
            }

        } catch (Exception e) {
            e.printStackTrace();

        }


    }


}
