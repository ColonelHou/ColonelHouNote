package com.hn.cluster.hadoop.mrs.合并文件内容MR;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.contrib.utils.join.DataJoinMapperBase;
import org.apache.hadoop.contrib.utils.join.DataJoinReducerBase;
import org.apache.hadoop.contrib.utils.join.TaggedMapOutput;

import com.hn.java.se.properties.getFromPro.GetIPfromProperties;

/**
 * file01.txt 20081401,math,90,201106 20081402,history,93,201106
 * 20081403,english,87,201106 20081404,chinese,89,201106 20081405,some,45,201106
 * file02.txt 20081401,tom,00000-11111-2222 20081402,jim,00000-11111-3333
 * 20081403,lily,00000-11111-4444 20081404,lucy,00000-11111-5555
 * 20081405,john,00000-11111-6666
 * 
 * 查看： hadoop@hadoopMaster:/opt/hadoop/hadoopTestData/join$ hdfsCAT
 * /output/myJoin/part-00000 20081401,math,90,201106,tom,00000-11111-2222
 * 20081402,history,93,201106,jim,00000-11111-3333
 * 20081403,english,87,201106,lily,00000-11111-4444
 * 20081404,chinese,89,201106,lucy,00000-11111-5555
 * 20081405,some,45,201106,john,00000-11111-6666
 * 
 * @author hadoop
 * 
 */
public class DataJoin extends Configured implements Tool
{
	public static class MapClass extends DataJoinMapperBase
	{
		protected Text generateInputTag(String inputFile)
		{
			String datasource = inputFile.split("-")[0];
			return new Text(datasource);
		}

		protected Text generateGroupKey(TaggedMapOutput aRecord)
		{
			String line = ((Text) aRecord.getData()).toString();
			String[] tokens = line.split(",");
			String groupKey = tokens[0];
			return new Text(groupKey);
		}

		protected TaggedMapOutput generateTaggedMapOutput(Object value)
		{
			TaggedWritable retv = new TaggedWritable((Text) value);
			retv.setTag(this.inputTag);
			return retv;
		}
	}

	public static class Reduce extends DataJoinReducerBase
	{
		protected TaggedMapOutput combine(Object[] tags, Object[] values)
		{
			if (tags.length < 2)
				return null;
			String joinedStr = "";
			for (int i = 0; i < values.length; i++)
			{
				if (i > 0)
					joinedStr += ",";
				TaggedWritable tw = (TaggedWritable) values[i];
				String line = ((Text) tw.getData()).toString();
				String[] tokens = line.split(",", 2);
				joinedStr += tokens[1];
			}
			TaggedWritable retv = new TaggedWritable(new Text(joinedStr));
			retv.setTag((Text) tags[0]);
			return retv;
		}
	}

	public static class TaggedWritable extends TaggedMapOutput
	{
		private Writable data;

		public TaggedWritable()
		{
			this.tag = new Text();
		}

		public TaggedWritable(Writable data)
		{
			this.tag = new Text("");
			this.data = data;
		}

		public Writable getData()
		{
			return data;
		}

		public void setData(Writable data)
		{
			this.data = data;
		}

		public void write(DataOutput out) throws IOException
		{
			this.tag.write(out);
			out.writeUTF(this.data.getClass().getName());
			this.data.write(out);
		}

		public void readFields(DataInput in) throws IOException
		{
			this.tag.readFields(in);
			String dataClz = in.readUTF();
			if (this.data == null
					|| !this.data.getClass().getName().equals(dataClz))
			{
				try
				{
					this.data = (Writable) ReflectionUtils.newInstance(
							Class.forName(dataClz), null);
				} catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
			this.data.readFields(in);
		}
	}

	public int run(String[] args) throws Exception
	{
		for (String string : args)
		{
			System.out.println(string);
		}
		Configuration conf = getConf();
		JobConf job = new JobConf(conf, DataJoin.class);
		Path in = new Path(args[0]);
		Path out = new Path(args[1]);
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);
		job.setJobName("DataJoin");
		job.setMapperClass(MapClass.class);
		job.setReducerClass(Reduce.class);
		job.setInputFormat(TextInputFormat.class);
		job.setOutputFormat(TextOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(TaggedWritable.class);
		job.set("mapred.textoutputformat.separator", ",");
		JobClient.runJob(job);
		return 0;
	}

	public static void main(String[] args) throws Exception
	{
		String strIp = GetIPfromProperties.getIpsFromProperties()
				.get("inputIp");
		String[] arg = { "hdfs://" + strIp + ":9000/input/myJoin",
				"hdfs://" + strIp + ":9000/output/myJoin" };
		int res = ToolRunner.run(new Configuration(), new DataJoin(), arg);
		System.exit(res);
	}
}
