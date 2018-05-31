/**
 * 描述:读取资源文件内容
 */
package com.dayuan.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesInfo {
	/**
	 * PropertiesInfo实例
	 */
	private static PropertiesInfo pi = null;
	/**
	 *  获取一个PropertiesInfo实例
	 * @return PropertiesInfo
	 */
	public static synchronized PropertiesInfo getInstance() {
		if(pi == null) {
			pi = new PropertiesInfo();
		}
		return pi;
	}
	/**
	 * 资源文件名
	 * 该资源文件应该放置在classpath下
	 */
	private final String propertiesFile = "config.properties";
	/**
	 * 资源文件对象
	 */
	private Properties p = new Properties();
	/**
	 * 构建资源文件读取对象
	 *
	 */
	private PropertiesInfo() {
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream(propertiesFile);
			p.load(is);
			is.close();
		}catch(Exception e) {
			System.out.println("资源文件加载失败! [" + propertiesFile + "]");
			System.out.println("---------------------------------------------------------");
			e.printStackTrace();
			System.out.println("---------------------------------------------------------");
		}
	}
	/**
	 * 根据资源文件的key读取值
	 * @param key
	 * @return String
	 */
	public String getKeyValue(String key) {
		String keyValue;
		if(key == "" || key.equals("")) {
			keyValue = "null";
		}else {
			keyValue = p.getProperty(key, "null");
		}
		return keyValue;
	}
	/**
	 * 设置值
	 * @param key
	 * @param value
	 */
	public void setKeyValue(String key, String value) {
		p.setProperty(key, value);
		OutputStream os = null;
		try {
			os = new FileOutputStream(new File(getClass().getClassLoader().getResource(propertiesFile).toURI()));
			p.store(os, null);
			os.flush();
			os.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}