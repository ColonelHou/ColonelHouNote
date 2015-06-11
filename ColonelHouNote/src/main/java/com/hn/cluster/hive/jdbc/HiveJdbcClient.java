package com.hn.cluster.hive.jdbc;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class HiveJdbcClient
{
	private static String driverName = "org.apache.hadoop.hive.jdbc.HiveDriver";

	/**
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException
	{
		/*Properties p = new Properties();
		p.setProperty("http.proxyHost", "proxy.com");
		p.setProperty("http.proxyPort", "8080");
		System.getProperties().setProperty("socksProxyHost", "proxy.com");
		System.getProperties().setProperty("socksProxyPort", "8080");*/
		try
		{
			/*System.getProperties().setProperty("http.proxyHost", "proxy.com");
			System.getProperties().setProperty("http.proxyPort", "8080");*/
			Class.forName(driverName);
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
//		InetSocketAddress addr = new InetSocketAddress("proxy.com",8080);            
//		Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理            
//		
//		Connection con = DriverManager.getConnection("jdbc:hive://192.1168.1.12:10000/x", p);
//		URLConnection conn = url.openConnection(proxy);            
		Connection con = DriverManager.getConnection(
				"jdbc:hive://hadoopMaster:10000/default");
		
		Statement stmt = con.createStatement();
		//String tableName = "hivetbl";
		// stmt.executeQuery("drop table " + tableName);
		// ResultSet res = stmt.executeQuery("create table " + tableName +
		// " (key int, value string)");
		// show tables
		String sql = "select hivetbl.course name, sum(hivetbl.val) val from hivetbl group by hivetbl.course";
		//String sql = "SELECT * FROM hivetbl";
		System.out.println("Running: " + sql);
		ResultSet res = stmt.executeQuery(sql);
		while (res.next())
		{
			System.out.println(res.getString(1) + "\t" + res.getString(2));
			//System.out.println(res.getString(1) + "\t" + res.getString(2) + "\t" + res.getString(3) + "\t" + res.getString(4));
		}
		// describe table
		/*sql = "describe " + tableName;
		System.out.println("Running: " + sql);
		res = stmt.executeQuery(sql);
		while (res.next())
		{
			System.out.println(res.getString(1) + "\t" + res.getString(2));
		}
		
		
		sql = "select * from hivetbl";
		System.out.println("Running: " + sql);
		res = stmt.executeQuery(sql);
		while (res.next())
		{
			System.out.println(res.getString(1) + "\t" + res.getString(2) + "\t" + res.getString(3) + "\t" + res.getString(4) + "\t");
		}*/
	}
}
