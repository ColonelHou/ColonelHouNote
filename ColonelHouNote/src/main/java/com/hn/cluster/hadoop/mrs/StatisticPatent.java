package com.hn.cluster.hadoop.mrs;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * 统计专利
 * 
 * @author Colonel.Hou
 * 
 */
public class StatisticPatent extends Configured implements Tool
{
	/**
	 * 取出每条专利数据,把名字与专利号输出
	 * eg:5762253,".ANG.berg","Kjell","","","","Sandefjord","","NO","",1
	 * .ANG.berg 5762253
	 * 
	 * @author Colonel.Hou
	 * 
	 */
	public static class MapClass extends Mapper<LongWritable, Text, Text, Text>
	{
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException
		{
			/*
			 * String[] citation = value.toString().split(",");
			 * context.write(new Text(citation[1]), new Text(citation[0]));
			 */
			context.write(value, new Text(key + ""));
		}
	}

	/**
	 * 把莫个人下所有专利输出 eg : ".ANG.berg" 5762253,5749991,5539654
	 * 
	 * @author Colonel.Hou
	 * 
	 */
	public static class Reduce extends Reducer<Text, Text, Text, Text>
	{
		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException
		{
			String csv = "";
			for (Text val : values)
			{
				if (csv.length() > 0)
					csv += ",";
				csv += val.toString();
			}
			context.write(key, new Text(csv));
		}
	}

	public int run(String[] args) throws Exception
	{
		Configuration conf = getConf();
		Job job = new Job(conf, "MyJob");
		job.setJarByClass(StatisticPatent.class);
		Path in = new Path(args[0]);
		Path out = new Path(args[1]);
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);
		job.setMapperClass(MapClass.class);
		job.setReducerClass(Reduce.class);
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		return 0;
	}

	public static void main(String[] args) throws Exception
	{
		String[] arg = {
				"hdfs://192.1168.1.12:9000/input/input/usData/cite75_99.txt",
				"hdfs://192.1168.1.12:9000/output/usData/cite/" };
		int res = ToolRunner.run(new Configuration(), new StatisticPatent(),
				arg);
		System.exit(res);
	}
}
