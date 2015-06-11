package com.hn.cluster.hadoop.mrs.join.单表关联;

import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
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
 * 
 * 给出：
 * child parent
 * Tom Lucy
 * Tom Jack
 * Jone Lucy
 * Jone Jack
 * Lucy Mary
 * Lucy Ben
 * Jack Alice
 * Jack Jesse
 *  
 * 输出：
 * Tom Alice
 * Tom Jesse
 * Jone Alice
 * Jone Jesse
 * Tom Mary
 * Tom Ben
 * Jone Mary
 * Jone Ben
 * @author 123
 *
 */
public class SimpleTblJoin extends Configured implements Tool
{
	public static class MapClass extends Mapper<LongWritable, Text, Text, Text>
	{
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException
		{
			String childName = new String();
			String parentName = new String();
			String relationType = new String();
			String line = value.toString();
			String[] values = line.split(" ");
			if (values[0].compareTo("child") != 0)
			{
				childName = values[0];
				parentName = values[1];
				relationType = "1";// 左表标志
				context.write(new Text(parentName), new Text(relationType + " "
						+ childName));
				relationType = "2";// 右表标志
				context.write(new Text(childName), new Text(relationType + " "
						+ parentName));
			}
		}
	}

	public static class ReduceClass extends Reducer<Text, Text, Text, Text>
	{
		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException
		{
			String[] grandChild = new String[10];// 存放孙子的数组
			int grandChildNum = 0;
			String[] grandParent = new String[10];
			int grandParentNum = 0;
			Iterator<Text> it = values.iterator();
			while (it.hasNext())
			{
				String[] record = it.next().toString().split(" ");
				if (record.length == 0)
					continue;
				if (record[0].equals("1"))
				{// 孙子放到一个数组里
					grandChild[grandChildNum] = record[1];
					grandChildNum++;
				} else
				{// 祖辈放到另外一个数组中
					grandParent[grandParentNum] = record[1];
					grandParentNum++;
				}
			}
			if (grandChildNum != 0 && grandParentNum != 0)
			{// 两个数组的X值为grandChild-grandParent关系
				for (int i = 0; i < grandChildNum; i++)
				{
					for (int j = 0; j < grandParentNum; j++)
					{
						context.write(new Text(grandChild[i]), new Text(
								grandParent[j]));
					}
				}
			}
		}
	}

	@Override
	public int run(String[] args) throws Exception
	{
		Configuration conf = getConf();
		Job job = new Job(conf, "SingletonTableJoinJob02");
		job.setJarByClass(SimpleTblJoin.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.setMapperClass(MapClass.class);
		// job.setCombinerClass(ReduceClass.class);
		job.setReducerClass(ReduceClass.class);
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		return 0;
	}

	public static void main(String[] args) throws Exception
	{
		int res = ToolRunner.run(new Configuration(),
				new SimpleTblJoin(), args);
		System.exit(res);
	}
}
