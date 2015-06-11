package com.hn.java.se.io.getpath;

import java.io.File;

public class GetPath
{

	public static void main(String[] args)
	{
		// 类URL，不包括类文件
		System.out.println(GetPath.class.getResource(""));

		// com的父路经
		System.out.println(GetPath.class.getResource("/"));
		System.out.println(Thread.currentThread().getContextClassLoader()
				.getResource(""));
		System.out.println(GetPath.class.getClassLoader().getResource(""));
		System.out.println(ClassLoader.getSystemResource(""));
		System.out.println(new Thread().getContextClassLoader().getClass().getResource("/").getPath());
		// 工各路经
		System.out.println(new File("").getAbsolutePath());
		System.out.println(System.getProperty("user.dir"));

		// web容器
		/**
		 * (1).Tomcat 在类中输出System.getProperty("user.dir");显示的是%Tomcat_Home%/bin
		 * (2).Resin 不是你的JSP放的相对路径,是JSP引擎执行这个JSP编译成SERVLET 的路径为根.比如用新建文件法测试File
		 * f = new File("a.htm"); 这个a.htm在resin的安装目录下 (3).如何读文件
		 * 使用ServletContext.getResourceAsStream()就可以 (4).获得文件真实路径 String
		 * file_real_path=ServletContext.getRealPath("mypath/filename");
		 * 不建议使用request.getRealPath("/");
		 */
	}
}
