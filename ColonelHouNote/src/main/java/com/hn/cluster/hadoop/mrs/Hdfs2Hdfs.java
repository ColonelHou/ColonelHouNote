package com.hn.cluster.hadoop.mrs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class Hdfs2Hdfs
{
	public static final String NAME = "ImportFromFile";

	public enum Counters
	{
		LINES
	}

	static class ImportMapper extends
			Mapper<LongWritable, Text, ImmutableBytesWritable, Comparable>
	{
		private byte[] family = null;
		private byte[] qualifier = null;

		@Override
		protected void setup(Context context) throws IOException,
				InterruptedException
		{
			String column = context.getConfiguration().get("conf.column");
			byte[][] colkey = KeyValue.parseColumn(Bytes.toBytes(column));
			family = colkey[0];
			if (colkey.length > 1)
			{
				qualifier = colkey[1];
			}
		}

		@Override
		public void map(LongWritable offset, Text line, Context context)
				throws IOException
		{
			try
			{
				String[] lineArr = line.toString().split("\t");
				Put put = new Put(Bytes.toBytes(offset + ""));
				put.add(family, Bytes.toBytes("time"),
						Bytes.toBytes(lineArr[lineArr.length - 1]));
				context.write(
						new ImmutableBytesWritable(Bytes.toBytes(offset + "")),
						put);
				context.getCounter(Counters.LINES).increment(1);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception
	{
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum",
				"hadoopMaster,hadoopSlave1,hadoopSlave2");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		conf.set("conf.column", "cf");
		Job job = new Job(conf, "hdfsToHBase");

		job.setJarByClass(Hdfs2Hdfs.class);
		job.setMapperClass(ImportMapper.class);
		job.setOutputFormatClass(TableOutputFormat.class);
		job.getConfiguration().set(TableOutputFormat.OUTPUT_TABLE,
				"hdfsToHBase");
		job.setOutputKeyClass(ImmutableBytesWritable.class);
		job.setOutputValueClass(Writable.class);
		job.setNumReduceTasks(0);
		FileInputFormat.addInputPath(job, new Path(
				"hdfs://hadoopMaster:9000/input/input/hdfsToHBase"));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
