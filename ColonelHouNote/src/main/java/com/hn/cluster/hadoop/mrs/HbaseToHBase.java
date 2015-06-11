package com.hn.cluster.hadoop.mrs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;

public class HbaseToHBase
{
	private static final String sourceTable = "student";
	private static final String targetTable = "students";
	static Configuration config = HBaseConfiguration.create();

	public static void createTable(String tablename, String[] cfs)
			throws IOException
	{
		HBaseAdmin admin = new HBaseAdmin(config);
		if (admin.tableExists(tablename))
		{
			System.out.println("table already exists");
		} else
		{
			HTableDescriptor tableDesc = new HTableDescriptor(tablename);
			for (int i = 0; i < cfs.length; i++)
			{
				tableDesc.addFamily(new HColumnDescriptor(cfs[i]));
			}
			admin.createTable(tableDesc);
			System.out.println("create table successly");
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException,
			InterruptedException, ClassNotFoundException
	{
		// TODO Auto-generated method stub
		String[] cfs = { "course" };
		createTable(targetTable, cfs);
		Job job = new Job(config, "test");
		job.setJarByClass(HbaseToHBase.class);
		Scan scan = new Scan();
		scan.setCaching(1024);
		scan.setCacheBlocks(false);
		TableMapReduceUtil.initTableMapperJob(sourceTable, scan, Mapper1.class,
				Text.class, IntWritable.class, job);
		TableMapReduceUtil
				.initTableReducerJob(targetTable, Reducer1.class, job);
		boolean b = job.waitForCompletion(true);
		if (!b)
		{
			throw new IOException("error");
		}
	}

	public static class Mapper1 extends TableMapper<Text, IntWritable>
	{
		private final IntWritable ONE = new IntWritable(1);
		private Text text = new Text();

		public void map(ImmutableBytesWritable row, Result value,
				Context context) throws IOException, InterruptedException
		{
			String id = new String(value.getValue(Bytes.toBytes("course"),
					Bytes.toBytes("chinese")));
			text.set(id);
			context.write(text, ONE);
		}
	}

	public static class Reducer1 extends
			TableReducer<Text, IntWritable, ImmutableBytesWritable>
	{
		public void reduce(Text key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException
		{
			int i = 0;
			for (IntWritable val : values)
			{
				i += val.get();
			}
			Put put = new Put(Bytes.toBytes(key.toString()));
			put.add(Bytes.toBytes("a"), Bytes.toBytes("c"), Bytes.toBytes(i));
			context.write(null, put);
		}
	}
}
