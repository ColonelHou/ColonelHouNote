package com.hn.cluster.hbase.conn.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import com.hn.cluster.hbase.conn.HBaseFactory;
import com.hn.cluster.hbase.util.HBaseConst;
import com.hn.cluster.hbase.util.PropertiesUtils;

/**
 * HBase数据操作实现类
 * 
 * @author hadoop
 * 
 */
public class HBaseFactoryImpl implements HBaseFactory
{
	/**
	 * 日志记录
	 */
	private static final Logger logger = Logger
			.getLogger(HBaseFactoryImpl.class);

	private static HBaseFactoryImpl hbaseConn = new HBaseFactoryImpl();

	private static HTablePool hTablePool = null;

	private static HBaseAdmin hBaseAdmin = null;

	/**
	 * HBase配置
	 */
	private static Configuration conf = HBaseConfiguration.create();

	/**
	 * 读取hbase.properties文件设置到conf中
	 */
	static
	{
		logger.debug("Begin load properies file and setting conf Obj.");
		Properties pro = PropertiesUtils
				.getPro(HBaseConst.STR_HBASE_PROPERTIES_FILE);
		if (null != pro)
		{
			Set<Object> keySet = pro.keySet();
			String strKey = null;
			for (Object obj : keySet)
			{
				strKey = obj.toString();
				conf.set(strKey, pro.getProperty(strKey));
			}
		}
		try
		{
			int maxSize = Integer.parseInt(pro
					.getProperty("HTablePool.maxSize"));
			hTablePool = new HTablePool(conf, maxSize);
			hBaseAdmin = new HBaseAdmin(conf);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			logger.error("New HBaseAdmin Object error!");
		}
		logger.debug("End load properies file and setting conf Obj.");
	}

	private HBaseFactoryImpl()
	{
		super();
	}

	/**
	 * 通过 tableName 来获取这个 Table
	 */
	@Override
	public HTableInterface getHTable(String tableName)
	{
		return hTablePool.getTable(tableName);
	}

	/**
	 * 获取HBase连接单实例类
	 * 
	 * @return
	 */
	public static HBaseFactoryImpl getHBaseConnInstance()
	{
		return hbaseConn;
	}

	/**
	 * 创建数据库表
	 * 
	 * @param tableName
	 *            数据表名
	 * @param columnFamilys
	 *            列族数组
	 * @throws Exception
	 */
	public void createTable(String tableName, String[] columnFamilys)
			throws Exception
	{

		if (hBaseAdmin.tableExists(tableName))
		{
			logger.error(tableName + " is exists.");
			return;
		} else
		{
			// 新建一个 scores 表的描述
			HTableDescriptor tableDesc = new HTableDescriptor(tableName);
			// 在描述里添加列族
			for (String columnFamily : columnFamilys)
			{
				tableDesc.addFamily(new HColumnDescriptor(columnFamily));
			}
			// 根据配置好的描述建表
			hBaseAdmin.createTable(tableDesc);
			logger.info("Create table : " + tableName + " Successful.");
		}
	}

	/**
	 * 获取表的描述
	 * 
	 * @param tableName
	 * @return
	 */
	public HTableDescriptor describeTable(String tableName)
	{
		try
		{
			return hBaseAdmin.getTableDescriptor(Bytes.toBytes(tableName));
		} catch (Exception e)
		{
			logger.error("Describe Table error.");
			return null;
		}
	}

	/**
	 * 删除数据表
	 * 
	 * @param tableName
	 *            表名
	 * @throws Exception
	 */
	public void deleteTable(String tableName) throws Exception
	{
		if (hBaseAdmin.tableExists(tableName))
		{
			// 关闭一个表
			hBaseAdmin.disableTable(tableName);
			// 删除一个表
			hBaseAdmin.deleteTable(tableName);
			logger.info("Delete table : " + tableName + "Successful.");

		} else
		{
			logger.error(tableName + " is not exists.");
		}
	}

	/**
	 * 添加一条记录
	 * 
	 * @param strTblName
	 *            表名
	 * @param strRowKey
	 *            rowKey
	 * @param strColFamily
	 *            列族
	 * @param strColumn
	 *            列名
	 * @param strVal
	 *            列值
	 * @throws Exception
	 */
	public void addRow(String strTblName, String strRowKey,
			String strColFamily, String strColumn, String strVal)
			throws Exception
	{
		HTableInterface hTable = getHTable(strTblName);
		// table.setAutoFlush(false);
		Put put = new Put(Bytes.toBytes(strRowKey));
		// 参数出分别：列族、列、值
		put.add(Bytes.toBytes(strColFamily), Bytes.toBytes(strColumn),
				Bytes.toBytes(strVal));
		hTable.put(put);
		hTable.flushCommits();
	}

	/**
	 * 删除一条数据
	 * 
	 * @param strTblName
	 *            表名
	 * @param strRowKey
	 *            rowKey
	 * @throws Exception
	 */
	public void delRow(String strTblName, String strRowKey) throws Exception
	{
		HTableInterface hTable = getHTable(strTblName);
		Delete del = new Delete(Bytes.toBytes(strRowKey));
		hTable.delete(del);
	}

	/**
	 * 删除多条数据
	 * 
	 * @param strTblName
	 *            表名
	 * @param rowkeys
	 *            rowKey
	 * @throws Exception
	 */
	public void delMultiRows(String strTblName, String[] rowkeys)
			throws Exception
	{
		HTableInterface hTable = getHTable(strTblName);
		List<Delete> list = new ArrayList<Delete>();

		for (String row : rowkeys)
		{
			Delete del = new Delete(Bytes.toBytes(row));
			list.add(del);
		}

		hTable.delete(list);
	}

	/**
	 * 获取指定行
	 * 
	 * @param strTblName
	 *            表名
	 * @param rowkey
	 *            rowKey
	 * @throws Exception
	 */
	public void getRow(String strTblName, String rowkey) throws Exception
	{
		HTableInterface hTable = getHTable(strTblName);
		Get get = new Get(Bytes.toBytes(rowkey));
		Result result = hTable.get(get);
		// 输出结果
		for (KeyValue rowKV : result.raw())
		{
		}
	}

	/**
	 * get方式，通过rowKey、column查询
	 * 
	 * @param strTblName
	 *            表名
	 * @param rowKey
	 *            rowKey
	 * @param strFamily
	 *            strFamily
	 * @param column
	 *            列名
	 * @throws IOException
	 */
	public void getByRowKeyColumn(String strTblName, String strRowKey,
			String strFamily, String strCol) throws IOException
	{
		HTableInterface hTable = getHTable(strTblName);
		Get g = new Get(Bytes.toBytes(strRowKey));
		g.addColumn(strFamily.getBytes(), strCol.getBytes());
		Result r = hTable.get(g);
		for (KeyValue kv : r.raw())
		{

		}
	}

	/**
	 * 获取指定表所有记录
	 * 
	 * @param strTblName
	 *            表名
	 * @throws Exception
	 */
	public void getAllRows(String strTblName) throws Exception
	{
		HTableInterface hTable = getHTable(strTblName);
		Scan scan = new Scan();
		ResultScanner results = hTable.getScanner(scan);
		// 输出结果
		for (Result result : results)
		{
			for (KeyValue rowKV : result.raw())
			{
			}
		}
	}
}
