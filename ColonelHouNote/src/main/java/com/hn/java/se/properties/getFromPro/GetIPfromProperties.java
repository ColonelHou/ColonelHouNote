package com.hn.java.se.properties.getFromPro;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 资源文件中获取IP地址
 * 
 * @author Colonel.Hou
 * 
 */
public class GetIPfromProperties
{

	public static Map<String, String> getIpsFromProperties()
	{
		Map<String, String> ips = new HashMap<String, String>();
		String fileName = "ip.properties";
		String filePath = GetIPfromProperties.class.getResource("").getPath()
				+ File.separator + fileName;
		Properties p = new Properties();
		try
		{
			p.load(new FileInputStream(new File(filePath)));

			String inputIP = p.getProperty("inputIp");
			String outputIP = p.getProperty("outputIp");
			String port = p.getProperty("port");
			ips.put("inputIp", inputIP);
			ips.put("outputIp", outputIP);
			ips.put("port", port);
			// System.out.println(inputIP + ", " + outputIP);
		} catch (IOException e)
		{
			System.out.println("载入资源文件错误!!");
			return null;
		}
		return ips;
	}
}
