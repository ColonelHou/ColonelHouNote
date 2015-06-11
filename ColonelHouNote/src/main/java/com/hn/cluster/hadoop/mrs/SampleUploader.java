package com.hn.cluster.hadoop.mrs;

import java.io.IOException;
import java.math.BigInteger;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableExistsException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.log4j.Logger;

/**
 * Sample Uploader MapReduce
 * <p>
 * This is EXAMPLE code. You will need to change it to work for your context.
 * <p>
 * Uses {@link TableReducer} to put the data into HBase. Change the InputFormat
 * to suit your data. In this example, we are importing a CSV file.
 * <p>
 * 
 * <pre>
 * row,family,qualifier,value
 * </pre>
 * <p>
 * The table and columnfamily we're to insert into must preexist.
 * <p>
 * There is no reducer in this example as it is not necessary and adds
 * significant overhead. If you need to do any massaging of data before
 * inserting into HBase, you can do this in the map as well.
 * <p>
 * Do the following to start the MR job:
 * 
 * <pre>
 * ./bin/hadoop org.apache.hadoop.hbase.mapreduce.SampleUploader /tmp/input.csv TABLE_NAME
 * </pre>
 * <p>
 * This code was written against HBase 0.21 trunk.
 */
public class SampleUploader
{

	public static Logger loger = Logger.getLogger(SampleUploader.class);

	private static final String NAME = "SampleUploader";

	static class Uploader extends
			Mapper<LongWritable, Text, ImmutableBytesWritable, Put>
	{

		private long checkpoint = 100;
		private long count = 0;

		@Override
		public void map(LongWritable key, Text line, Context context)
				throws IOException
		{

			// Input is a CSV file
			// Each map() is a single line, where the key is the line number
			// Each line is comma-delimited; row,family,qualifier,value

			// Split CSV line
			String[] values = line.toString().split(",");
			if (values.length != 4)
			{
				return;
			}

			// Extract each value
			byte[] row = Bytes.toBytes(values[0]);
			byte[] family = Bytes.toBytes(values[1]);
			byte[] qualifier = Bytes.toBytes(values[2]);
			byte[] value = Bytes.toBytes(values[3]);
			loger.info(values[0] + ":" + values[1] + ":" + values[2] + ":"
					+ values[3]);

			// Create Put
			Put put = new Put(row);
			put.add(family, qualifier, value);

			// Uncomment below to disable WAL. This will improve performance but
			// means
			// you will experience data loss in the case of a RegionServer
			// crash.
			// put.setWriteToWAL(false);

			try
			{
				context.write(new ImmutableBytesWritable(row), put);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
				loger.error("write到hbase 异常:", e);
			}

			// Set status every checkpoint lines
			if (++count % checkpoint == 0)
			{
				context.setStatus("Emitting Put " + count);
			}
		}
	}

	/**
	 * Job configuration.
	 */
	public static Job configureJob(Configuration conf, String[] args)
			throws IOException
	{
		Path inputPath = new Path(args[0]);
		String tableName = args[1];
		Job job = new Job(conf, NAME + "_" + tableName);
		job.setJarByClass(Uploader.class);
		FileInputFormat.setInputPaths(job, inputPath);
		job.setInputFormatClass(TextInputFormat.class);
		job.setMapperClass(Uploader.class);
		// No reducers. Just write straight to table. Call initTableReducerJob
		// because it sets up the TableOutputFormat.
		loger.error("TableName:" + tableName);
		TableMapReduceUtil.initTableReducerJob(tableName, null, job);
		job.setNumReduceTasks(0);
		return job;
	}

	/**
	 * Main entry point.
	 * 
	 * @param args
	 *            The command line parameters.
	 * @throws Exception
	 *             When running the job fails.
	 */
	public static void main(String[] args) throws Exception
	{
		Configuration conf = HBaseConfiguration.create();
		String[] otherArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();
		if (otherArgs.length != 2)
		{
			System.err
					.println("Wrong number of arguments: " + otherArgs.length);
			System.err.println("Usage: " + NAME + " <input> <tablename>");
			System.exit(-1);
		}
		Job job = configureJob(conf, otherArgs);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

	public static boolean createTable(HBaseAdmin admin, HTableDescriptor table,
			byte[][] splits) throws IOException
	{
		try
		{
			admin.createTable(table, splits);
			return true;
		} catch (TableExistsException e)
		{
			// logger.info("table " + table.getNameAsString() +
			// " already exists");
			// the table already exists...
			return false;
		}
	}

	public static byte[][] getHexSplits(String startKey, String endKey,
			int numRegions)
	{
		byte[][] splits = new byte[numRegions - 1][];
		BigInteger lowestKey = new BigInteger(startKey, 16);
		BigInteger highestKey = new BigInteger(endKey, 16);
		BigInteger range = highestKey.subtract(lowestKey);
		BigInteger regionIncrement = range.divide(BigInteger
				.valueOf(numRegions));
		lowestKey = lowestKey.add(regionIncrement);
		for (int i = 0; i < numRegions - 1; i++)
		{
			BigInteger key = lowestKey.add(regionIncrement.multiply(BigInteger
					.valueOf(i)));
			byte[] b = String.format("%016x", key).getBytes();
			splits[i] = b;
		}
		return splits;
	}
}
