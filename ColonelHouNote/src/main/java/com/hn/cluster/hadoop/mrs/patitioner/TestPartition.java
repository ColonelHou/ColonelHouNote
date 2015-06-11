package com.hn.cluster.hadoop.mrs.patitioner;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DFSClient;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapTask;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 当Mapper处理好数据后，需要使用Partitioner确定怎样合理地将Mapper输出分配到Reducer之中 河南省;1 河南;2 中国;3
 * 中国人;4 大;1 小;3 中;11
 * 
 * @author hadoop
 * 
 */
public class TestPartition
{
	/**
	 * map任务
	 * 
	 * */
	public static class PMapper extends Mapper<LongWritable, Text, Text, Text>
	{

		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException
		{
			// 河南省;1
			String ss[] = value.toString().split(";");

			// 0:河南省 1:1
			context.write(new Text(ss[0]), new Text(ss[1]));
		}
	}

	/**
	 * Partitioner 如何使用Hadoop产生一个全局排序的文件？最简单的方法就是使用一个分区，
	 * 但是该方法在处理大型文件时效率极低，因为一台机器必须处理所有输出文件， 从而完全丧失了MapReduce所提供的并行架构的优势。
	 * 
	 * */
	public static class PPartition extends Partitioner<Text, Text>
	{
		public int getPartition(Text arg0, Text arg1, int arg2)
		{
			/**
			 * 自定义分区，实现长度不同的字符串，分到不同的reduce里面
			 * 
			 * 现在只有3个长度的字符串，所以可以把reduce的个数设置为3 有几个分区，就设置为几
			 * */
			String key = arg0.toString();
			if (key.length() == 1)
			{
				return 1 % arg2;
			} else if (key.length() == 2)
			{
				return 2 % arg2;
			} else if (key.length() == 3)
			{
				return 3 % arg2;
			}
			return 0;
		}
	}

	/***
	 * Reduce任务
	 * 
	 * **/
	public static class PReduce extends Reducer<Text, Text, Text, Text>
	{
		/**
		 * reduce是真正合并Mapper结果的地方，它的输入是key和这个key对应的所有value的一个迭代器
		 */
		@Override
		protected void reduce(Text arg0, Iterable<Text> arg1, Context arg2)
				throws IOException, InterruptedException
		{
			String key = arg0.toString().split(",")[0];
			System.out.println("key==> " + key);
			for (Text t : arg1)
			{
				// System.out.println("Reduce:  "+arg0.toString()+"   "+t.toString());
				arg2.write(arg0, t);
			}
		}
	}

	public static void main(String[] args) throws IOException,
			ClassNotFoundException, InterruptedException
	{
		Configuration conf = new Configuration();
		Job job = new Job(conf);
		job.setJarByClass(TestPartition.class);

		job.setPartitionerClass(PPartition.class);

		job.setNumReduceTasks(3);
		job.setMapperClass(PMapper.class);
		job.setReducerClass(PReduce.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.setInputPaths(job, new Path(
				"hdfs://192.1168.1.12:9000/input/partition/a.txt"));
		FileOutputFormat.setOutputPath(job, new Path(
				"hdfs://192.1168.1.12:9000/output/partition/"));
		System.out.println(job.waitForCompletion(true));
	}

}
