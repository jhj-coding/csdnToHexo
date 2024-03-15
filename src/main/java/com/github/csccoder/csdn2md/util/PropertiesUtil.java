package com.github.csccoder.csdn2md.util;

import com.github.csccoder.csdn2md.Main;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	public static String getProperties(String key){
		String value = null;
		try {
			Properties pp = new Properties();

			//通过idea运行程序
			InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("config.properties");

			//通过java -jar运行程序
			// String filePath = System.getProperty("user.dir")+ "/config.properties";
			// InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));

			pp.load(inputStream);
			value= (String) pp.get(key);
			return value;
		} catch (IOException e) {
			e.printStackTrace();
			return value;
		}
	}
}
