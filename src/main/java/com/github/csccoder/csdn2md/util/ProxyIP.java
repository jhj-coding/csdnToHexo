package com.github.csccoder.csdn2md.util;

import java.util.Properties;

public class ProxyIP {
	public static void daili(String ip, String dk) {
		Properties prop = System.getProperties();
		// 设置http访问要使用的代理服务器的地址
		prop.setProperty("http.proxyHost", ip);
		// 设置http访问要使用的代理服务器的端口
		prop.setProperty("http.proxyPort", dk);
		// 设置不需要通过代理服务器访问的主机，可以使用*通配符，多个地址用|分隔
		prop.setProperty("http.nonProxyHosts", "localhost|192.168.168.*");
		// 设置安全访问使用的代理服务器地址与端口
		// 它没有https.nonProxyHosts属性，它按照http.nonProxyHosts 中设置的规则访问
		prop.setProperty("https.proxyHost", ip);
		prop.setProperty("https.proxyPort", dk);
		// 使用ftp代理服务器的主机、端口以及不需要使用ftp代理服务器的主机
		prop.setProperty("ftp.proxyHost", ip);
		prop.setProperty("ftp.proxyPort", dk);
		prop.setProperty("ftp.nonProxyHosts", "localhost|192.168.168.*");
		// socks代理服务器的地址与端口
		prop.setProperty("socksProxyHost", ip);
		prop.setProperty("socksProxyPort", dk);
		System.out.println("即将开始代理进行访问 ip:" + ip + " port:" + dk);
	}
	public static String[] dl = PropertiesUtil.getProperties("IP_And_Port").split(",");


}
