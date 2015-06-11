package com.hn.cluster.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

/**
 * 1、修改hbase的BufferSize，并禁用hbase的自动提交功能 2、禁用hbase的预写日志功能（WAL）
 * 
 * @author hadoop
 * 
 */
public class 入库效率提升
{

	public static void main(String[] args)
	{
		Configuration conf = HBaseConfiguration.create();

	}
}
