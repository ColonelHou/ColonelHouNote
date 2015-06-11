package com.hn.db.mysql.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Test
{
	public static void main(String[] args)
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String url = null;
		String user = null;
		String password = null;
		String sql = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver"); // 加载mysq驱动
		} catch (ClassNotFoundException e)
		{
			System.out.println("驱动加载错误");
			e.printStackTrace();// 打印出错详细信息
		}
		
		try
		{
			
			url = "jdbc:mysql://192.1168.1.12:3306/hiveDB";// 简单写法：url
			user = "root";
			password = "root";
			System.out.println("开始获取连接...");
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e)
		{
			System.out.println("数据库链接错误");
			e.printStackTrace();
		}
		try
		{
			stmt = conn.createStatement();
			sql = "select * from TBLS";// dept这张表有deptno，deptname和age这三个字段
			rs = stmt.executeQuery(sql);// 执行sql语句
			while (rs.next())
			{
				System.out.print(rs.getInt("OWNER") + "   ");
				System.out.print(rs.getString("TBL_NAME") + "   ");
			}
		} catch (SQLException e)
		{
			System.out.println("数据操作错误");
			e.printStackTrace();
		}
		// 关闭数据库
		try
		{
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (stmt != null)
			{
				stmt.close();
				stmt = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
		} catch (Exception e)
		{
			System.out.println("数据库关闭错误");
			e.printStackTrace();
		}
	}
}



