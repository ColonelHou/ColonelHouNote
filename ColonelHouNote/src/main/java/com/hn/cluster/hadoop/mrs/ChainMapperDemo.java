package com.hn.cluster.hadoop.mrs;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ChainMapperDemo
{

	public static class Map00 extends
			Mapper<LongWritable, Text, LongWritable, Text>
	{
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException
		{
			Text ft = new Text("100");
			if (!key.equals(ft))
			{
				context.write(key, value);
			}
		}
	}

	public static class Map01 extends
			Mapper<LongWritable, Text, LongWritable, Text>
	{
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException
		{
			Text ft = new Text("101");
			if (!key.equals(ft))
			{
				context.write(key, value);
			}
		}
	}

	public static class Reduce01 extends
			Reducer<LongWritable, Text, LongWritable, Text>
	{
		public void reduce(LongWritable key, Iterable<Text> values,
				Context context) throws IOException, InterruptedException
		{
			Iterator<Text> iter = values.iterator();
			while (iter.hasNext())
			{
				context.write(key, iter.next());
			}
		}
	}

	public static void main(String ages[]) throws IOException,
			ClassNotFoundException, InterruptedException
	{
		Configuration conf = new Configuration();
		Job job = new Job(conf, "chain mappre");
		job.setJarByClass(ChainMapperDemo.class);
		// 如果有两个mapper的话， 最新版本中是怎么处理的
		job.setMapperClass(Map00.class); // 为job设置Mapper类
		job.setMapperClass(Map01.class); // 为job设置Mapper类
		job.setReducerClass(Reduce01.class); // 为job设置Reduce类

		job.setOutputKeyClass(LongWritable.class); // 设置输出key的类型
		job.setOutputValueClass(Text.class);// 设置输出value的类型
		FileInputFormat.addInputPath(job, new Path(
				"hdfs://192.1168.1.12:9000/input/input/ChainMapper.txt")); // 为map-reduce任务设置InputFormat实现类
		// 设置输入路径

		FileOutputFormat.setOutputPath(job, new Path(
				"hdfs://192.1168.1.12:9000/output/chain/"));// 为map-reduce任务设置OutputFormat实现类
		// 设置输出路径
		System.out.println(job.waitForCompletion(true));
	}
}
