package com.hn.cluster.hadoop.mrs;

import java.io.IOException;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;

/**
 * hbase(main):024:0> scan 'student' ROW COLUMN+CELL jim column=course:chinese,
 * timestamp=1405391091810, value=87 jim column=course:english,
 * timestamp=1405391091810, value=62 jim column=course:math,
 * timestamp=1405391091810, value=73 lily column=course:chinese,
 * timestamp=1405391091810, value=83 lily column=course:english,
 * timestamp=1405391091810, value=65 lily column=course:math,
 * timestamp=1405391091810, value=66 tom column=course:chinese,
 * timestamp=1405391091810, value=63 tom column=course:english,
 * timestamp=1405391091810, value=68 tom column=course:math,
 * timestamp=1405391091810, value=63
 * 
 * @author hadoop
 * 
 */
public class MR查询HBase
{

	public static class MyMapper extends TableMapper<Text, Text>
	{

		public void map(ImmutableBytesWritable row, Result values,
				Context context) throws InterruptedException, IOException
		{
			// process data for the row from the Result instance.
			StringBuilder sb = new StringBuilder();
			for (Entry<byte[], byte[]> value : values.getFamilyMap(
					"course".getBytes()).entrySet())
			{
				byte[] cell = value.getValue();
				if (cell != null)
				{
					sb.append(new Text(value.getKey())).append(":")
							.append(new Text(cell)).append("\t");
				}
			}
			System.out.println(new Text(row.get()).toString() + " -- > "
					+ sb.toString());
		}
	}

	public static void main(String[] args) throws Exception
	{
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum.",
				"hadoopMaster,hadoopSlave1,hadoopSlave2");
		Job job = new Job(conf, "ExampleRead");
		job.setJarByClass(MR查询HBase.class); // class that contains mapper

		Scan scan = new Scan();
		scan.setCaching(500); // 1 is the default in Scan, which will be bad for
								// MapReduce jobs
		scan.setCacheBlocks(false); // don't set to true for MR jobs
		TableMapReduceUtil.initTableMapperJob("student", // input HBase table
															// name
				scan, // Scan instance to control CF and attribute selection
				MyMapper.class, // mapper
				null, // mapper output key
				null, // mapper output value
				job);
		job.setOutputFormatClass(NullOutputFormat.class); // because we aren't
															// emitting anything
															// from mapper
		boolean b = job.waitForCompletion(true);
		if (!b)
		{
			throw new IOException("error with job!");
		}
	}

}
