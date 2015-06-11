package com.hn.java.se.代理;

import java.util.Properties;

/**
 * http://llying.iteye.com/blog/465253
 * @author hadoop
 *
 */
public class Proxy
{

	public static void main(String[] args)
	{
		Properties prop = System.getProperties();
        // HTTP代理的IP设置
        prop.setProperty("http.proxyHost", "192.1168.1.12");
        // HTTP代理的端口设置
        prop.setProperty("http.proxyPort", "80");
        //这里也可以设置不需要使用代理的地址
        prop.setProperty("http.nonProxyHosts", "localhost|192.1168.1.12.*");
        //设置HTTPS安全访问的代理服务器地址与端口
        prop.setProperty("https.proxyHost", "192.1168.1.12");
        prop.setProperty("https.proxyPort", "443");
	//对于安全访问的过滤地址属性同样是http.nonProxyHosts并没有https.nonProxyHosts

        //FTP的代理设置入下
        prop.setProperty("ftp.proxyHost", "192.1168.1.12");
        prop.setProperty("ftp.proxyPort", "2121");
        prop.setProperty("ftp.nonProxyHosts", "localhost|192.168.0.*");
        //SOCKS的代理设置
        prop.setProperty("socksProxyHost", "192.1168.1.12");
        prop.setProperty("socksProxyPort", "8000");

	}
}
