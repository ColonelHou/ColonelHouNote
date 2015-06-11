package com.hn.cluster.hadoop.mrs.MapReduce批量写入Hbase;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

/**
 * hadoop@hadoopMaster:$ hdfsMKDIR /input/batchImportHbase hadoop@hadoopMaster:$
 * hdfsCOPYFROMLOCAL batchImportHbase /input/batchImportHbase/
 * hadoop@hadoopMaster:$ hdfsCAT /input/batchImportHbase/batchImportHbase
 * 1363157985066 13726230503 00-FD-07-A4-72-B8:CMCC 120.196.100.82
 * i02.c.aliimg.com 24 27 2481 24681 200
 * 
 * 1363157995052 13826544101 5C-0E-8B-C7-F1-E0:CMCC 120.197.40.4 4 0 264 0 200
 * 
 * 1363157991076 13926435656 20-10-7A-28-CC-0A:CMCC 120.196.100.99 2 4 132 1512
 * 200
 * 
 * 1363154400022 13926251106 5C-0E-8B-8B-B1-50:CMCC 120.197.40.4 4 0 240 0 200
 * 
 * 
 * create 'wlan_log', 'cf'
 * 
 * @author hadoop
 * 
 */
public class BatchImport
{
	static class BatchImportMapper extends
			Mapper<LongWritable, Text, LongWritable, Text>
	{
		SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyyMMddHHmmss");
		Text v2 = new Text();

		protected void map(LongWritable key, Text value, Context context)
				throws java.io.IOException, InterruptedException
		{
			final String[] splited = value.toString().split("\t");
			try
			{
				final Date date = new Date(Long.parseLong(splited[0].trim()));
				final String dateFormat = dateformat1.format(date);
				String rowKey = splited[1] + ":" + dateFormat;
				v2.set(rowKey + "\t" + value.toString());
				context.write(key, v2);
			} catch (NumberFormatException e)
			{
				final Counter counter = context.getCounter("BatchImport",
						"ErrorFormat");
				counter.increment(1L);
				System.out.println("出错了" + splited[0] + " " + e.getMessage());
			}
		};
	}

	static class BatchImportReducer extends
			TableReducer<LongWritable, Text, NullWritable>
	{
		protected void reduce(LongWritable key,
				java.lang.Iterable<Text> values, Context context)
				throws java.io.IOException, InterruptedException
		{
			for (Text text : values)
			{
				final String[] splited = text.toString().split("\t");

				final Put put = new Put(Bytes.toBytes(splited[0]));
				put.add(Bytes.toBytes("cf"), Bytes.toBytes("date"),
						Bytes.toBytes(splited[1]));
				put.add(Bytes.toBytes("cf"), Bytes.toBytes("msisdn"),
						Bytes.toBytes(splited[2]));
				put.add(Bytes.toBytes("cf"), Bytes.toBytes("apmac"),
						Bytes.toBytes(splited[3]));
				// 省略其他字段，调用put.add(....)即可
				context.write(NullWritable.get(), put);
			}
		};
	}

	public static void main(String[] args) throws Exception
	{

		Configuration conf = new Configuration();
		conf.set("hbase.zookeeper.quorum",
				"hadoopMaster,hadoopSlave1,hadoopSlave2");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		// 设置hbase表名称
		conf.set(TableOutputFormat.OUTPUT_TABLE, "wlan_log");
		// 将该值改大，防止hbase超时退出
		conf.set("dfs.socket.timeout", "180000");

		final Job job = new Job(conf, "HBaseBatchImport");

		job.setMapperClass(BatchImportMapper.class);
		job.setReducerClass(BatchImportReducer.class);
		// 设置map的输出，不设置reduce的输出类型
		job.setMapOutputKeyClass(LongWritable.class);
		job.setMapOutputValueClass(Text.class);

		job.setInputFormatClass(TextInputFormat.class);
		// 不再设置输出路径，而是设置输出格式类型
		job.setOutputFormatClass(TableOutputFormat.class);

		FileInputFormat.setInputPaths(job,
				"hdfs://hadoopMaster:9000/input/batchImportHbase/");

		job.waitForCompletion(true);
	}
}
