package com.hn.cluster.hadoop.mrs.join;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
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

/**
 * "ID","NAME","SEX" "1","user1","0" "2","user2","0" "3","user3","0"
 * "4","user4","1" "5","user5","0" "6","user6","0" "7","user7","1"
 * "8","user8","0" "9","user9","0"
 * 
 * order.csv文件：
 * 
 * "USER_ID","NAME" "1","order1" "2","order2" "3","order3" "4","order4"
 * "7","order7" "8","order8" "9","order9"
 * 
 * @author hadoop
 * 
 */
public class ReduceJoin
{
	public static class MapClass extends Mapper<LongWritable, Text, Text, Text>
	{

		// 最好在map方法外定义变量，以减少map计算时创建对象的个数
		private Text key = new Text();
		private Text value = new Text();
		private String[] keyValue = null;

		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException
		{
			// 采用的数据输入格式是TextInputFormat，
			// 文件被分为一系列以换行或者制表符结束的行，
			// key是每一行的位置（偏移量,LongWritable类型），
			// value是每一行的内容,Text类型，所有我们要把key从value中解析出来
			keyValue = value.toString().split(",", 2);
			this.key.set(keyValue[0]);
			this.value.set(keyValue[1]);
			System.out.println("map: " + keyValue[0] + "  ---->   "
					+ keyValue[1]);
			context.write(this.key, this.value);
		}

	}

	public static class Reduce extends Reducer<Text, Text, Text, Text>
	{

		// 最好在reduce方法外定义变量，以减少reduce计算时创建对象的个数
		private Text value = new Text();

		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException
		{
			StringBuilder valueStr = new StringBuilder();

			// values中的每一个值是不同数据文件中的具有相同key的值
			// 即是map中输出的多个文件相同key的value值集合
			for (Text val : values)
			{
				valueStr.append(val);
				valueStr.append(",");
			}

			this.value.set(valueStr.deleteCharAt(valueStr.length() - 1)
					.toString());
			System.out.println("reduce:" + key + "  ---> "
					+ valueStr.deleteCharAt(valueStr.length() - 1).toString());
			context.write(key, this.value);
		}

	}

	public static void main(String[] args) throws Exception
	{
		Configuration conf = new Configuration();
		Job job = new Job(conf, "MyJoin");

		job.setJarByClass(ReduceJoin.class);
		job.setMapperClass(MapClass.class);
		job.setReducerClass(Reduce.class);
		// job.setCombinerClass(Reduce.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		// 分别采用TextInputFormat和TextOutputFormat作为数据的输入和输出格式
		// 如果不设置，这也是Hadoop默认的操作方式
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path(
				"hdfs://192.1168.1.12:9000/mr/join/*.txt"));
		FileOutputFormat.setOutputPath(job, new Path(
				"hdfs://192.1168.1.12:9000/mr/join/out/"));

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
