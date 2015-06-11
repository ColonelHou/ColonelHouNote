package com.hn.cluster.hbase.conn;

import java.io.IOException;

import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HTableInterface;

/**
 * HBase数据操作接口
 * 
 * @author hadoop
 * 
 */
public interface HBaseFactory
{
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
			throws Exception;

	/**
	 * 通过 tableName 来获取这个 Table
	 */
	HTableInterface getHTable(String tableName);

	/**
	 * 删除数据表
	 * 
	 * @param tableName
	 *            表名
	 * @throws Exception
	 */
	public void deleteTable(String tableName) throws Exception;

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
			throws Exception;

	/**
	 * 删除一条数据
	 * 
	 * @param strTblName
	 *            表名
	 * @param strRowKey
	 *            rowKey
	 * @throws Exception
	 */
	public void delRow(String strTblName, String strRowKey) throws Exception;

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
			throws Exception;

	/**
	 * 获取指定行
	 * 
	 * @param strTblName
	 *            表名
	 * @param rowkey
	 *            rowKey
	 * @throws Exception
	 */
	public void getRow(String strTblName, String rowkey) throws Exception;

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
			String strFamily, String strCol) throws IOException;

	/**
	 * 获取指定表所有记录
	 * 
	 * @param strTblName
	 *            表名
	 * @throws Exception
	 */
	public void getAllRows(String strTblName) throws Exception;

	/**
	 * 获取表的描述
	 * 
	 * @param tableName
	 * @return
	 */
	public HTableDescriptor describeTable(String tableName);
}
