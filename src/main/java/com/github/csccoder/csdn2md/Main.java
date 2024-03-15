package com.github.csccoder.csdn2md;

import com.github.csccoder.csdn2md.paser.CorePaser;
import com.github.csccoder.csdn2md.util.PropertiesUtil;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main implements Runnable{

	static ExecutorService service = Executors.newFixedThreadPool(1);

	static String csdn_host = PropertiesUtil.getProperties("csdn_host");
	private static final String HOST = csdn_host;
	private static final String AUTHOR = PropertiesUtil.getProperties("casn_name");
	/**
	 * 文件保存路径（绝对路径）
	 */
	private static final String FILE_PATH = PropertiesUtil.getProperties("file_Path");
	//csdn 用户名

	public static void main(String args[]) throws IOException {
		service.execute(new Main());
	}

	public void run() {
		new CorePaser().parse(HOST,
				AUTHOR,
				FILE_PATH,
				//是否爬取图片 默认false
				true);
	}
}


