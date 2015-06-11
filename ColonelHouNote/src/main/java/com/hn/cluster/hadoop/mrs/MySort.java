package com.hn.cluster.hadoop.mrs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MySort
{

	public static class MapperSort extends
			Mapper<LongWritable, Text, LongWritable, LongWritable>
	{
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException
		{
			Integer val = Integer.parseInt(new Text(value).toString());
			context.write(new LongWritable(val), key);
		}
	}

	public static class ReduceSort extends
			Reducer<LongWritable, LongWritable, LongWritable, String>
	{
		public void reduce(LongWritable key, LongWritable value, Context context)
				throws IOException, InterruptedException
		{
			context.write(value, "");
		}
	}

	public static void main(String[] args) throws IOException,
			ClassNotFoundException, InterruptedException
	{
		Configuration conf = new Configuration();
		Job job = new Job(conf);
		job.setMapperClass(MapperSort.class);
		job.setReducerClass(ReduceSort.class);
		job.setJarByClass(MySort.class);
		job.setMapOutputKeyClass(LongWritable.class);
		job.setMapOutputValueClass(LongWritable.class);
		job.setOutputKeyClass(LongWritable.class);
		job.setOutputValueClass(String.class);
		job.setInputFormatClass(TextInputFormat.class);
		FileInputFormat.setInputPaths(job, new Path(
				"hdfs://192.168.1.12:9000/input/mysort/mysort"));
		FileOutputFormat.setOutputPath(job, new Path(
				"hdfs://192.168.1.12:9000/output/mysort/"));
		System.out.println(job.waitForCompletion(true));
	}
}
