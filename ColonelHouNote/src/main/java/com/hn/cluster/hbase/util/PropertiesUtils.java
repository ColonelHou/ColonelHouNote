package com.hn.cluster.hbase.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 根据资源文件名称获取其内容 ps:资源文件在src目录下
 * 
 * @author hadoop
 * 
 */
public class PropertiesUtils
{
	/**
	 * 日志记录
	 */
	private static final Logger logger = Logger
			.getLogger(PropertiesUtils.class);

	/**
	 * 单实例
	 */
	private static final PropertiesUtils pro = new PropertiesUtils();

	/**
	 * 根据资源文件名称获取资源文件对象
	 * 
	 * @param strProName
	 *            资源文件名称
	 * @return
	 */
	private Properties loadProperties(String strProName)
	{
		InputStream inputStream = null;
		try
		{
			inputStream = this.getClass().getClassLoader()
					.getResourceAsStream(strProName);
			Properties props = new Properties();
			props.load(inputStream);
			return props;
		} catch (IOException e)
		{
			logger.error("Load the properties File stream error.");
			return null;
		} finally
		{
			try
			{
				inputStream.close();
			} catch (IOException e)
			{
				logger.error("Close the properties File stream error.");
			}
		}
	}

	/**
	 * 对外提供获取资源文件接口
	 * 
	 * @param strFileName
	 *            文件名
	 * @return 资源文件对象
	 */
	public static Properties getPro(String strFileName)
	{
		return pro.loadProperties(strFileName);
	}

	/*
	 * public static void main(String[] args) { Properties p =
	 * getPro("hbase.properties"); Set<Object> keySet = p.keySet(); for (Object
	 * obj : keySet) { System.out.println(obj.toString() + ", " +
	 * p.getProperty(obj.toString())); } }
	 */
}
