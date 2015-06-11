package com.hn.cluster.hadoop.mrs;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * "PATENT","GYEAR","GDATE","APPYEAR","COUNTRY","POSTATE","ASSIGNEE","ASSCODE",
 * "CLAIMS","NCLASS","CAT","SUBCAT","CMADE","CRECEIVE","RATIOCIT","GENERAL","O
 * RIGINAL","FWDAPLAG","BCKGTLAG","SELFCTUB","SELFCTLB","SECDUPBD","SECDLWBD"
 * 3070801,1963,1096,,"BE","",,1,,269,6,69,,1,,0,,,,,,,
 * 3070802,1963,1096,,"US","TX",,1,,2,6,63,,0,,,,,,,,,
 * 3070803,1963,1096,,"US","IL",,1,,2,6,63,,9,,0.3704,,,,,,,
 * 3070804,1963,1096,,"US","OH",,1,,2,6,63,,3,,0.6667,,,,,,,
 * 3070805,1963,1096,,"US","CA",,1,,2,6,63,,1,,0,,,,,,,
 * 3070806,1963,1096,,"US","PA",,1,,2,6,63,,0,,,,,,,,,
 * 3070807,1963,1096,,"US","OH",,1,,623,3,39,,3,,0.4444,,,,,,,
 * 3070808,1963,1096,,"US","IA",,1,,623,3,39,,4,,0.375,,,,,,,
 * 3070809,1963,1096,,"US","AZ",,1,,4,6,65,,0,,,,,,,,,
 * 3070810,1963,1096,,"US","IL",,1,,4,6,65,,3,,0.4444,,,,,,,
 * 3070811,1963,1096,,"US","CA",,1,,4,6,65,,8,,0,,,,,,,
 * 3070812,1963,1096,,"US","LA",,1,,4,6,65,,3,,0.4444,,,,,,,
 * 3070813,1963,1096,,"US","NY",,1,,5,6,65,,2,,0,,,,,,,
 * 3070814,1963,1096,,"US","MN",,2,,267,5,59,,2,,0.5,,,,,,,
 * 3070815,1963,1096,,"US","CO",,1,,7,5,59,,1,,0,,,,,,,
 * 3070816,1963,1096,,"US","OK",,1,,114,5,55,,4,,0,,,,,,,
 * 3070817,1963,1096,,"US","RI",,2,,114,5,55,,5,,0.64,,,,,,,
 * 3070818,1963,1096,,"US","IN",,1,,441,6,69,,4,,0.625,,,,,,,
 * 3070819,1963,1096,,"US","TN",,4,,12,6,63,,0,,,,,,,,,
 * 3070820,1963,1096,,"GB","",,2,,12,6,63,,0,,,,,,,,,
 * 3070821,1963,1096,,"US","IL",,2,,15,6,69,,1,,0,,,,,,,
 * 3070822,1963,1096,,"US","NY",,2,,401,1,12,,4,,0.375,,,,,,,
 * 3070823,1963,1096,,"US","MI",,1,,401,1,12,,8,,0.6563,,,,,,,
 * 3070824,1963,1096,,"US","IL",,1,,401,1,12,,5,,0.48,,,,,,,
 * 3070825,1963,1096,,"US","IL",,1,,401,1,12,,7,,0.6531,,,,,,,
 * 3070826,1963,1096,,"US","IA",,1,,401,1,12,,1,,0,,,,,,,
 * 3070827,1963,1096,,"US","CA",,4,,401,1,12,,2,,0.5,,,,,,,
 * 3070828,1963,1096,,"US","CT",,2,,16,5,59,,4,,0.625,,,,,,,
 * 3070829,1963,1096,,"FR","",,3,,16,5,59,,5,,0.48,,,,,,,
 * 3070830,1963,1096,,"US","NH",,2,,16,5,59,,0,,,,,,,,,
 * 3070831,1963,1096,,"US","CT",,2,,16,5,59,,0,,,,,,,,,
 * 
 * @author hadoop
 * 
 */
public class AveragingWithCombiner
{

	public static class MapperTest extends
			Mapper<LongWritable, Text, Text, Text>
	{
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException
		{
			String fields[] = value.toString().split(",", -20);
			String country = fields[4];
			String numClaims = fields[8];
			if (numClaims.length() > 0 && !numClaims.startsWith("\""))
			{
				context.write(new Text(country), new Text(numClaims + ",1"));
			}
		}
	}

	public static class Combine extends Reducer<Text, Text, Text, Text>
	{
		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException
		{
			double sum = 0;
			int count = 0;
			Iterator<Text> it = values.iterator();
			while (it.hasNext())
			{
				String fields[] = it.next().toString().split(",");
				sum += Double.parseDouble(fields[0]);
				count += Integer.parseInt(fields[1]);
			}

			context.write(key, new Text(sum + "," + count));
		}
	}

	public static class ReduceTest extends
			Reducer<Text, Text, Text, DoubleWritable>
	{
		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException
		{
			double sum = 0;
			int count = 0;
			Iterator<Text> it = values.iterator();
			while (it.hasNext())
			{
				String fields[] = it.next().toString().split(",");
				sum += Double.parseDouble(fields[0]);
				count += Integer.parseInt(fields[1]);
			}

			context.write(key, new DoubleWritable(sum / count));
		}
	}

	public static void main(String[] args) throws IOException,
			ClassNotFoundException, InterruptedException
	{
		Configuration conf = new Configuration();
		Job job = new Job(conf, "Word Count Into HBase");
		job.setJarByClass(AveragingWithCombiner.class);

		job.setMapperClass(MapperTest.class);
		job.setCombinerClass(Combine.class);
		job.setReducerClass(ReduceTest.class);

		// job.setInputFormatClass(TextInputFormat.class);
		// job.setOutputFormatClass(TextOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.addInputPath(job, new Path(
				"hdfs://192.1168.1.12:9000/input/input/apat63_99.txt"));
		FileOutputFormat.setOutputPath(job, new Path(
				"hdfs://192.1168.1.12:9000/output/average/"));

		System.out.println(job.waitForCompletion(true));
	}
}
