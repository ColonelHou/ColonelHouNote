package com.hn.cluster.hive.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Date;
import java.io.*;
import jline.ConsoleReader;

public class JDBCClient
{
	public static void main(String[] args)
	{
		String url = "jdbc:hive://192.1168.1.12:10000/x";
		String user = "admin";
		String password = "admin";
		if (args.length > 2)
		{ // has user parameters
			if (args.length != 5)
			{
				System.out.println("Syntax Error!");
				return;
			}
			url = args[2];
			user = args[3];
			password = args[4];
		}
		try
		{
			Class.forName("org.apache.hadoop.hive.jdbc.HiveDriver"); // BufferedReader
																		// reader
																		// = new
																		// BufferedReader(new
																		// InputStreamReader(System.in));
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement statement = conn.createStatement();
			while (true)
			{
				System.out.print("HugeQL>");
				ConsoleReader reader = new ConsoleReader();
				reader.setBellEnabled(false);
				String sql = reader.readLine();
				try
				{
					if (sql.length() == 0)
						continue;
					if (sql.endsWith(";"))
					{
						sql = sql.substring(0, sql.length() - 1);
					}
					if (sql.equals("quit") || sql.equals("exit"))
						return;
					Date start = new Date();
					boolean query = statement.execute(sql);
					Date stop = new Date();
					long deltaTime = stop.getTime() - start.getTime();
					System.out.println("execution time: "
							+ (deltaTime / (60 * 1000)) + " min "
							+ ((deltaTime / 1000) % 60) + " sec "
							+ (deltaTime % 1000) + " ms " + " [total "
							+ deltaTime + " ms]");
					if (query == false)
						System.out.println("update count = "
								+ statement.getUpdateCount());
					else
					{
						ResultSet result = statement.getResultSet();
						ResultSetMetaData metadata = result.getMetaData();
						for (int i = 1; i <= metadata.getColumnCount(); ++i)
							System.out.printf("%30s",
									metadata.getColumnLabel(i));
						System.out.println();
						int n = 0;
						while (result.next())
						{
							for (int i = 1; i <= metadata.getColumnCount(); ++i)
								System.out.printf("%30s", result.getString(i));
							n++;
							System.out.println();
						}
						System.out.println("rows: " + n + " execution time: "
								+ (deltaTime / (60 * 1000)) + " min "
								+ ((deltaTime / 1000) % 60) + " sec "
								+ (deltaTime % 1000) + " ms " + " [total "
								+ deltaTime + " ms]");
					}
				} catch (SQLException e)
				{
					System.out.println(e);
				}
			}
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		System.exit(0);
	}
}
