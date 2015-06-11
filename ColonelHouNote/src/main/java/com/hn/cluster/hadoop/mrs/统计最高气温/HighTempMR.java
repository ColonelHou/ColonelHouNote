package com.hn.cluster.hadoop.mrs.统计最高气温;

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

/**
 * 统计最高气温 简单的实现了一下
 * m输入 k:文件偏移量 v :行内容  输出：k是年份 , v是对应气温  (2013, 45)
 * r输入 k:年份  v :年对应的所有气温 (2013, [34, 54, 23, 45, 32])
 * 
 * @author Colonel.Hou
 * 
 */
public class HighTempMR
{

	/**
	 * 把日志中的年以及对应的温度取出来
	 * 
	 * @author Colonel.Hou
	 * 
	 */
	public static class TempMapper extends
			Mapper<LongWritable, Text, Text, LongWritable>
	{
		private final LongWritable t = new LongWritable();
		private Text word = new Text();

		public void map(LongWritable key, Text value, Context context)
		{
			String strLine = value.toString();
			String strYear = strLine.substring(14, 18);
			String strTemp = strLine.substring(23, 24);
			word.set(strYear);
			t.set(Long.parseLong(strTemp));
			try
			{
				context.write(word, t);
			} catch (IOException | InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 算出每一年的最高气温
	 * 
	 * @author Colonel.Hou
	 * 
	 */
	public static class TempReduce extends
			Reducer<Text, LongWritable, Text, LongWritable>
	{
		public void reduce(Text key, Iterable<LongWritable> values, Context context)
		{
			Iterator<LongWritable> iter = values.iterator();
			long maxVal = Integer.MIN_VALUE;
			while(iter.hasNext())
			{
				maxVal = Math.max(maxVal, iter.next().get());
			}
			try
			{
				context.write(key, new LongWritable(maxVal));
			} catch (IOException | InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception
	{
		Configuration conf = new Configuration();
		Job job = new Job(conf, "high temperature");

		job.setJarByClass(HighTempMR.class);

		job.setMapperClass(TempMapper.class);
		job.setCombinerClass(TempReduce.class);
		job.setReducerClass(TempReduce.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		FileInputFormat.addInputPath(job, new Path(
				"hdfs://hadoopMaster:9000/input/temperature.log"));
		FileOutputFormat.setOutputPath(job, new Path(
				"hdfs://hadoopMaster:9000/output/IterTemperature/"));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
