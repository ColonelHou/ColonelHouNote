package com.hn.cluster.hbase.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;

/**
 * 
 * 
 * 统计年龄在某一区间内的人名字
 * hbase(main):023:0> scan 'students_age'
 * ROW                                          COLUMN+CELL
 *  27                                          column=basicInfo:Peter, timestamp=1420017939148, value=Peter
 *  27                                          column=basicInfo:Tom, timestamp=1420017939148, value=Tom
 *  28                                          column=basicInfo:Jack, timestamp=1420017939148, value=Jack
 *  28                                          column=basicInfo:Jim, timestamp=1420017939148, value=Jim
 * @author John
 * 
 */
public class HBaseMR {

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

	public HBaseMR(String rootDir, String zkServer, String port) {
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

	public static void main(String[] args) throws IOException,
			ClassNotFoundException, InterruptedException {
		//initHbaseData();
		
		//initHbaseOutData();
		
		submitJob();
	}

	public static void submitJob() throws ClassNotFoundException, IOException,
			InterruptedException {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum",
				"192.168.13.17,192.168.13.18,192.168.13.19");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		Scan scan = new Scan();
		scan.setCaching(1000);

		Job job = new Job(conf);
		// 输入/出
		TableMapReduceUtil.initTableMapperJob(Bytes.toBytes("students"), scan,
				MyMapper.class, Text.class, Text.class, job);

		TableMapReduceUtil.initTableReducerJob("students_age", MyReducer.class,
				job);

		job.waitForCompletion(true);
	}

	/**
	 * students Jack, Jim, Peter, Tom basicInfo:age moreInfo:tel
	 */
	public static void initHbaseData() {
		String tableName = "students";
		hbaseMrCreateTbl(tableName, "basicInfo", "moreInfo");
		hbaseMrPutData(tableName);
	}
	
	/**
	 * 创建MR输入表
	 */
	public static void initHbaseOutData() {
		String tableName = "students_age";
		hbaseMrCreateTbl(tableName, "basicInfo");
	}
	

	/**
	 * 创建表
	 * 
	 * @param tableName
	 */
	public static void hbaseMrCreateTbl(String tableName, String ...fams) {
		List<String> colFam = new ArrayList<String>();
		for (int i = 0; i < fams.length; i++) {
			colFam.add(fams[i]);
		}
		conn.createTable(tableName, colFam);
	}

	/**
	 * Jack 28 119 Jim 28 112 Peter 27 110 Tom 27 111
	 * 
	 * @param tableName
	 */
	public static void hbaseMrPutData(String tableName) {
		List<Put> puts = new LinkedList<Put>();
		Put put1 = new Put(Bytes.toBytes("Jack"));
		put1.add(Bytes.toBytes("basicInfo"), Bytes.toBytes("age"),
				Bytes.toBytes("28"));
		put1.add(Bytes.toBytes("moreInfo"), Bytes.toBytes("tel"),
				Bytes.toBytes("119"));

		Put put2 = new Put(Bytes.toBytes("Jim"));
		put2.add(Bytes.toBytes("basicInfo"), Bytes.toBytes("age"),
				Bytes.toBytes("28"));
		put2.add(Bytes.toBytes("moreInfo"), Bytes.toBytes("tel"),
				Bytes.toBytes("112"));

		Put put3 = new Put(Bytes.toBytes("Peter"));
		put3.add(Bytes.toBytes("basicInfo"), Bytes.toBytes("age"),
				Bytes.toBytes("27"));
		put3.add(Bytes.toBytes("moreInfo"), Bytes.toBytes("tel"),
				Bytes.toBytes("110"));

		Put put4 = new Put(Bytes.toBytes("Tom"));
		put4.add(Bytes.toBytes("basicInfo"), Bytes.toBytes("age"),
				Bytes.toBytes("27"));
		put4.add(Bytes.toBytes("moreInfo"), Bytes.toBytes("tel"),
				Bytes.toBytes("111"));
		puts.add(put1);
		puts.add(put2);
		puts.add(put3);
		puts.add(put4);
		conn.saveData(tableName, puts);
	}
}

class MyMapper extends TableMapper<Text, Text> {

	/**
	 * key rowkey result: record
	 */
	@Override
	protected void map(ImmutableBytesWritable key, Result value, Context context)
			throws IOException, InterruptedException {

		Text rowkey = new Text(Bytes.toString(key.get()));

		Text recordValAge = new Text(value.getValue(Bytes.toBytes("basicInfo"),
				Bytes.toBytes("age")));
		context.write(recordValAge, rowkey);

	}
}

class MyReducer extends TableReducer<Text, Text, ImmutableBytesWritable> {

	protected void reduce(Text keyAge, Iterable<Text> nameValues,
			Context context) throws IOException, InterruptedException {

		Put put = new Put(Bytes.toBytes(keyAge.toString()));
		for (Text nameVal : nameValues) {
			put.add(Bytes.toBytes("basicInfo"),
					Bytes.toBytes(nameVal.toString()),
					Bytes.toBytes(nameVal.toString()));
		}

		context.write(null, put);
	}

}
