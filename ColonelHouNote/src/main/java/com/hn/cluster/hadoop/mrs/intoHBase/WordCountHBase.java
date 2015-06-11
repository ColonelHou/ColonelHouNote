package com.hn.cluster.hadoop.mrs.intoHBase;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

/**
 * 使用mapreduce统计单词, 然后把统计的结果插入到hbase数据库中
 * 
 * @author hadoop
 * 
 */
public class WordCountHBase
{

	public static void main(String[] args)
	{
		String tableName = "mapreduce";
		// 第一步：创建数据库表
		WordCountHBase.createHBaseTable(tableName);

		// 第二步：进行 MapReduce 处理
		// 配置 MapReduce
		Configuration conf = new Configuration();
		// 这几句话很关键
		conf.set("mapred.job.tracker", "hadoopMaster:9001");
		conf.set("hbase.zookeeper.quorum",
				"hadoopMaster,hadoopSlave1,hadoopSlave2...");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		conf.set(TableOutputFormat.OUTPUT_TABLE, tableName);

		try
		{
			Job job = new Job(conf, "Word Count Into HBase");
			job.setJarByClass(WordCountHBase.class);

			// 设置 Map 和 Reduce 处理类
			job.setMapperClass(Map.class);
			job.setReducerClass(Reduce.class);

			// 设置输出类型
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(IntWritable.class);

			// 设置输入和输出格式
			job.setInputFormatClass(TextInputFormat.class);
			job.setOutputFormatClass(TableOutputFormat.class);

			// 设置输入目录
			FileInputFormat.addInputPath(job, new Path(
					"hdfs://hadoopMaster:9000/input/input/*.txt"));
			System.exit(job.waitForCompletion(true) ? 0 : 1);
		} catch (Exception e)
		{
			System.out.println("任务失败...");
		}

	}

	/**
	 * Map实现类
	 * 
	 * @author hadoop
	 * 
	 */
	public static class Map extends
			Mapper<LongWritable, Text, Text, IntWritable>
	{
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException
		{
			StringTokenizer itr = new StringTokenizer(value.toString());
			while (itr.hasMoreTokens())
			{
				word.set(itr.nextToken());
				context.write(word, one);
			}
		}
	}

	/**
	 * Reduce实现类
	 * 
	 * @author hadoop
	 */
	public static class Reduce extends
			TableReducer<Text, IntWritable, NullWritable>
	{

		public void reduce(Text key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException
		{

			int sum = 0;

			Iterator<IntWritable> iterator = values.iterator();
			while (iterator.hasNext())
			{
				sum += iterator.next().get();
			}

			// Put 实例化，每个词存一行
			Put put = new Put(Bytes.toBytes(key.toString()));
			// 列族为 content，列修饰符为 count，列值为数目
			put.add(Bytes.toBytes("content"), Bytes.toBytes("count"),
					Bytes.toBytes(String.valueOf(sum)));

			context.write(NullWritable.get(), put);
		}
	}

	/**
	 * 创建 HBase 数据表
	 * 
	 * @param tableName
	 *            数据表名
	 * @throws IOException
	 */
	public static void createHBaseTable(String tableName)
	{
		// 创建表描述
		HTableDescriptor htd = new HTableDescriptor(tableName);

		// 创建列族描述
		HColumnDescriptor col = new HColumnDescriptor("content");
		htd.addFamily(col);

		// 配置 HBase
		Configuration conf = HBaseConfiguration.create();

		conf.set("hbase.zookeeper.quorum",
				"hadoopMaster,hadoopSlave1,hadoopSlave2...");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		try
		{
			HBaseAdmin hAdmin = new HBaseAdmin(conf);
			if (hAdmin.tableExists(tableName))
			{
				System.out.println("该数据表已经存在，正在重新创建...");
				hAdmin.disableTable(tableName);
				hAdmin.deleteTable(tableName);
			}

			System.out.println("创建表：" + tableName);
			hAdmin.createTable(htd);
		} catch (IOException e)
		{
			System.out.println("创建表失败, 程序推出!!");
			System.exit(0);
		}
	}
}
