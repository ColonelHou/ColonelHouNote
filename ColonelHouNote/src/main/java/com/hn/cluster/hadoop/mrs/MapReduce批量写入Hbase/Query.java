package com.hn.cluster.hadoop.mrs.MapReduce批量写入Hbase;

import java.io.IOException;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import com.hn.cluster.hbase.conn.impl.HBaseFactoryImpl;

public class Query
{

	public static void main(String[] args) throws IOException
	{
		scan("wlan_log");
		scanPeriod("wlan_log");
	}

	// 查询手机13450456688的所有上网记录
	public static void scan(String tableName) throws IOException
	{
		HBaseFactoryImpl hbaseFac = HBaseFactoryImpl.getHBaseConnInstance();
		HTableInterface table = hbaseFac.getHTable(tableName);

		Scan scan = new Scan();
		scan.setStartRow(Bytes.toBytes("13926251106:/"));
		scan.setStopRow(Bytes.toBytes("13926251106::"));
		ResultScanner scanner = table.getScanner(scan);
		int i = 0;
		for (Result result : scanner)
		{
			System.out.println("Scan:" + i++ + " " + result);
		}
	}

	// 查询134号段的所有上网记录
	public static void scanPeriod(String tableName) throws IOException
	{
		HBaseFactoryImpl hbaseFac = HBaseFactoryImpl.getHBaseConnInstance();
		HTableInterface table = hbaseFac.getHTable(tableName);

		Scan scan = new Scan();
		scan.setStartRow(Bytes.toBytes("136/"));
		scan.setStopRow(Bytes.toBytes("136:"));
		scan.setMaxVersions(1);
		ResultScanner scanner = table.getScanner(scan);
		int i = 0;
		for (Result result : scanner)
		{
			System.out.println("Scan:" + i++ + " " + result);
		}
	}
}
