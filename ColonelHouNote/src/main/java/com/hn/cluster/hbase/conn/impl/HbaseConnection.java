package com.hn.cluster.hbase.conn.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm;
import org.apache.hadoop.hbase.io.encoding.DataBlockEncoding;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.BinaryComparable;

public class HbaseConnection {

	// hdfs://hadoopMaster:9000/hbase
	private String rootDir;
	private String zkServer;
	private String port;
	private Configuration conf;
	private HConnection hConn = null;
	private static HbaseConnection conn = null;
	static {
		String rootDir = "hdfs://hadoopMaster:9000/hbase";
		String zkServer = "192.168.13.17,192.168.13.18,192.168.13.19";
		String port = "2181";
		conn = new HbaseConnection(rootDir, zkServer, port);

	}

	public HbaseConnection(String rootDir, String zkServer, String port) {
		super();
		this.rootDir = rootDir;
		this.zkServer = zkServer;
		this.port = port;
		conf = HBaseConfiguration.create();
		conf.set("hbase.rootdir", rootDir);
		conf.set("hbase.zookeeper.quorum", zkServer);
		conf.set("hbase.zookeeper.property.clientPort", port);
		try {
			hConn = HConnectionManager.createConnection(conf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建表
	 * 
	 * @param tableName
	 * @param cols
	 */
	public void createTable(String tableName, List<String> cols) {

		try {
			HBaseAdmin admin = new HBaseAdmin(conf);
			HTableDescriptor tableDesc = null;
			if (admin.tableExists(tableName)) {
				throw new IOException("table exists");
			} else {
				tableDesc = new HTableDescriptor(tableName);
				for (String col : cols) {
					HColumnDescriptor colDesc = new HColumnDescriptor(col);
					// 是否压缩
					colDesc.setCompressionType(Algorithm.GZ);
					colDesc.setDataBlockEncoding(DataBlockEncoding.DIFF);
					tableDesc.addFamily(colDesc);
				}
			}
			admin.createTable(tableDesc);
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存数据
	 * 
	 * @param tableName
	 * @param puts
	 */
	public void saveData(String tableName, List<Put> puts) {
		try {
			HTableInterface table = hConn.getTable(tableName);
			table.put(puts);
			// 主要是为了IO效率
			table.setAutoFlush(false);
			table.flushCommits();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 根据rowkey查询数据
	 * 
	 * @param tableName
	 * @param rowkey
	 * @return
	 */
	public Result getData(String tableName, String rowkey) {

		try {
			HTableInterface table = hConn.getTable(tableName);
			Get get = new Get(Bytes.toBytes(rowkey));
			return table.get(get);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 返回的结果集格式化
	 * 
	 * @param result
	 */
	public void format(Result result) {
		String rowkey = Bytes.toString(result.getRow());
		KeyValue[] kvs = result.raw();
		for (KeyValue kv : kvs) {
			String family = Bytes.toString(kv.getFamily());
			String qualifier = Bytes.toString(kv.getQualifier());
			System.out.println("rowkey-> " + rowkey + " family->" + family
					+ "  quilfier-> " + qualifier);
		}
	}

	/**
	 * 取HBase中多条记录
	 * 
	 * @param tableName
	 */
	public void hbaseScan(String tableName) {
		Scan scan = new Scan();
		scan.setCaching(2);
		try {
			HTableInterface table = hConn.getTable(tableName);
			ResultScanner rs = table.getScanner(scan);
			for (Result result : rs) {
				format(result);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 过滤rowkey等于jim的
	 * 
	 * @param tableName
	 */
	public void filterTest(String tableName) {
		Scan scan = new Scan();
		scan.setCaching(2);

		/*
		 * 取记录为jim的行
		 * RowFilter filter = new RowFilter(CompareFilter.CompareOp.EQUAL,
				new BinaryComparator(Bytes.toBytes("jim")));*/
		//取首字母为j开头的人
		RowFilter filter = new RowFilter(CompareFilter.CompareOp.EQUAL,
				new RegexStringComparator("j\\w+"));

		PageFilter pf = new PageFilter(3);
		scan.setFilter(pf);
		try {
			HTableInterface table = hConn.getTable(tableName);
			ResultScanner rs = table.getScanner(scan);
			for (Result result : rs) {
				format(result);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 分页
	 * @param tableName
	 */
	public void pageFilterTest(String tableName)
	{
		PageFilter pf = new PageFilter(3);
		byte[] lastRow = null;
		int pageCount = 0;
		
		try {
			HTableInterface table = hConn.getTable(tableName);
			while(++pageCount > 0)
			{
				System.out.println("pageCount = " + pageCount);
				Scan scan = new Scan();
				scan.setFilter(pf);
				if(lastRow != null)
				{
					scan.setStartRow(lastRow);
				}
				ResultScanner result = table.getScanner(scan);
				int count = 0;
				for (Result re : result) {
					lastRow = re.getRow();
					if(++count > 3)
					{
						break;
					}
					format(re);
				}
				if(count < 3)
				{
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		String tableName = "Student";
		
		/*List<String> colFam = new ArrayList<String>();
		colFam.add("info");
		colFam.add("course");
		colFam.add("company");
		conn.createTable(tableName, colFam);*/

		/*List<Put> puts = new LinkedList<Put>();
		Put put1 = new Put(Bytes.toBytes("john"));
		put1.add(Bytes.toBytes("info"), Bytes.toBytes("age"),
				Bytes.toBytes("988"));
		put1.add(Bytes.toBytes("course"), Bytes.toBytes("chinese"),
				Bytes.toBytes("89"));
		put1.add(Bytes.toBytes("company"), Bytes.toBytes("isoftstone"),
				Bytes.toBytes("2"));

		Put put2 = new Put(Bytes.toBytes("same"));
		put2.add(Bytes.toBytes("info"), Bytes.toBytes("age"),
				Bytes.toBytes("26"));
		put2.add(Bytes.toBytes("course"), Bytes.toBytes("chinese"),
				Bytes.toBytes("98"));
		put2.add(Bytes.toBytes("company"), Bytes.toBytes("isoftstone"),
				Bytes.toBytes("3"));

		Put put3 = new Put(Bytes.toBytes("hello"));
		put3.add(Bytes.toBytes("info"), Bytes.toBytes("age"),
				Bytes.toBytes("27"));
		put3.add(Bytes.toBytes("course"), Bytes.toBytes("chinese"),
				Bytes.toBytes("87"));
		put3.add(Bytes.toBytes("company"), Bytes.toBytes("isoftstone"),
				Bytes.toBytes("4"));

		puts.add(put1);
		puts.add(put2);
		puts.add(put3);
		conn.saveData(tableName, puts);*/
		 

		/*
		 * Result result = conn.getData(tableName, "jim"); conn.format(result);
		 */

		// conn.hbaseScan(tableName);

		//conn.filterTest(tableName);
		
		conn.pageFilterTest(tableName);
	}

}
