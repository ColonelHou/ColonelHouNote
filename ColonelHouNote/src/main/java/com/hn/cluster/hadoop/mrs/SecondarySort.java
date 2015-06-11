/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hn.cluster.hadoop.mrs;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * This is an example Hadoop Map/Reduce application. It reads the text input
 * files that must contain two integers per a line. The output is sorted by the
 * first and second number and grouped on the first number.
 * 
 * 
 * 所谓对于key值的排序，其实是在Map阶段进行的，而Rduce阶段所做的工作是对各个Map任务的结果进行Merge工作
 * 
 * 因为MR中有个基于键的排序过程 WritableComparable和RawComparator两个接口的区别是：
 * writableComparable是需要把数据流反序列化为对象后，然后做对象之间的比较，
 * 而RawComparator是直接比较数据流的数据，不需要数据流反序列化成对象，省去了新建对象的开销。
 * 虽然我们知道是compareTo这个方法实现Key的排序，但其实我们在使用Hadoop的基本数据类型时不需要关注这个排序如何实现，
 * 因为Hadoop的框架会自动调用compareTo这个方法实现key的排序。但是这个排序只是局限在map或者reduce内部。
 * 针对于map与map，reduce与reduce之间的排序compareTo就管不着了，虽然这种情况不常出现，
 * 但是确实存在这种问题的，而且确实有适用场景，比如说全排序。
 * 
 * To run: bin/hadoop jar build/hadoop-examples.jar secondarysort <i>in-dir</i>
 * <i>out-dir</i>
 */
public class SecondarySort
{

	/**
	 * Define a pair of integers that are writable. They are serialized in a
	 * byte comparable format.
	 */
	public static class IntPair implements WritableComparable<IntPair>
	{

		private int first = 0;
		private int second = 0;

		/**
		 * Set the left and right values.
		 */
		public void set(int left, int right)
		{
			first = left;
			second = right;
		}

		public int getFirst()
		{
			return first;
		}

		public int getSecond()
		{
			return second;
		}

		/**
		 * Read the two integers. Encoded as: MIN_VALUE -> 0, 0 -> -MIN_VALUE,
		 * MAX_VALUE-> -1
		 */
		@Override
		public void readFields(DataInput in) throws IOException
		{
			first = in.readInt() + Integer.MIN_VALUE;
			second = in.readInt() + Integer.MIN_VALUE;
		}

		@Override
		public void write(DataOutput out) throws IOException
		{
			out.writeInt(first - Integer.MIN_VALUE);
			out.writeInt(second - Integer.MIN_VALUE);
		}

		@Override
		public int hashCode()
		{
			return first * 157 + second;
		}

		@Override
		public boolean equals(Object right)
		{
			if (right instanceof IntPair)
			{
				IntPair r = (IntPair) right;
				return r.first == first && r.second == second;
			} else
			{
				return false;
			}
		}

		/** A Comparator that compares serialized IntPair. */
		public static class Comparator extends WritableComparator
		{
			public Comparator()
			{
				super(IntPair.class);
			}

			public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2,
					int l2)
			{
				return compareBytes(b1, s1, l1, b2, s2, l2);
			}
		}

		static
		{ // register this comparator
			WritableComparator.define(IntPair.class, new Comparator());
		}

		@Override
		public int compareTo(IntPair o)
		{
			if (first != o.first)
			{
				return first < o.first ? -1 : 1;
			} else if (second != o.second)
			{
				return second < o.second ? -1 : 1;
			} else
			{
				return 0;
			}
		}
	}

	/**
	 * Partition based on the first part of the pair.
	 * 对map端输出的数据key作一个散列，使数据能够均匀分布在各个reduce上进行后续操作，避免产生热点区。
	 * Hadoop默认使用的分区函数是Hash Partitioner
	 * 大部分情况下，我们都会使用默认的分区函数，但有时我们又有一些，特殊的需求，而需要定制Partition来完成我们的业务，案例如下：
	 * 对如下数据，按字符串的长度分区，长度为1的放在一个，2的一个，3的各一个。 河南省;1 河南;2 中国;3 中国人;4 大;1 小;3 中;11
	 * 这时候，我们使用默认的分区函数，就不行了，所以需要我们定制自己的Partition，
	 * 首先分析下，我们需要3个分区输出，所以在设置reduce的个数时，一定要设置为3，
	 * 其次在partition里，进行分区时，要根据长度具体分区，而不是根据字符串的hash码来分区。
	 */
	public static class FirstPartitioner extends
			Partitioner<IntPair, IntWritable>
	{
		/**
		 * getPartition()函数返回一个0到Reducer数目之间的int值来确定将<key,value>键值对送到哪一个Reducer中
		 */
		@Override
		public int getPartition(IntPair key, IntWritable value,
				int numPartitions)
		{
			return Math.abs(key.getFirst() * 127) % numPartitions;
		}
	}

	/**
	 * Compare only the first part of the pair, so that reduce is called once
	 * for each value of the first part.
	 */
	public static class FirstGroupingComparator implements
			RawComparator<IntPair>
	{
		@Override
		public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2)
		{
			return WritableComparator.compareBytes(b1, s1, Integer.SIZE / 8,
					b2, s2, Integer.SIZE / 8);
		}

		@Override
		public int compare(IntPair o1, IntPair o2)
		{
			int l = o1.getFirst();
			int r = o2.getFirst();
			return l == r ? 0 : (l < r ? -1 : 1);
		}
	}

	/**
	 * Read two integers from each line and generate a key, value pair as
	 * ((left, right), right).
	 */
	public static class MapClass extends
			Mapper<LongWritable, Text, IntPair, IntWritable>
	{

		private final IntPair key = new IntPair();
		private final IntWritable value = new IntWritable();

		/**
		 * map输出 a#1 1 z#3 3 b#2 2 a#100 100 a#3 3 b#1 1
		 */
		@Override
		public void map(LongWritable inKey, Text inValue, Context context)
				throws IOException, InterruptedException
		{
			StringTokenizer itr = new StringTokenizer(inValue.toString());
			int left = 0;
			int right = 0;
			if (itr.hasMoreTokens())
			{
				left = Integer.parseInt(itr.nextToken());
				if (itr.hasMoreTokens())
				{
					right = Integer.parseInt(itr.nextToken());
				}
				key.set(left, right);
				value.set(right);
				context.write(key, value);
			}
		}
	}

	public static class Reduce extends
			Reducer<IntPair, IntWritable, Text, IntWritable>
	{
		private static final Text SEPARATOR = new Text(
				"------------------------------------------------");
		private final Text first = new Text();

		@Override
		public void reduce(IntPair key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException
		{
			context.write(SEPARATOR, null);
			first.set(Integer.toString(key.getFirst()));
			for (IntWritable value : values)
			{
				context.write(first, value);
			}
		}
	}

	public static void main(String[] args) throws Exception
	{
		// 读取hadoop配置
		Configuration conf = new Configuration();
		// 实例化一道作业
		Job job = new Job(conf, "secondary sort");
		job.setJarByClass(SecondarySort.class);
		// Mapper类型
		job.setMapperClass(MapClass.class);
		// Reducer类型
		job.setReducerClass(Reduce.class);

		// 分区函数
		job.setPartitionerClass(FirstPartitioner.class);
		// 分组函数
		job.setGroupingComparatorClass(FirstGroupingComparator.class);

		// map 输出Key的类型
		job.setMapOutputKeyClass(IntPair.class);
		// map输出Value的类型
		job.setMapOutputValueClass(IntWritable.class);

		// rduce输出Key的类型，是Text，因为使用的OutputFormatClass是TextOutputFormat
		job.setOutputKeyClass(Text.class);
		// rduce输出Value的类型
		job.setOutputValueClass(IntWritable.class);

		/**
		 * 将输入的数据集分割成小数据块splites，同时提供一个RecordReder的实现。
		 * 他提供的RecordReder会将文本的一行的行号作为key，这一行的文本作为value
		 * 这就是自定义Map的输入是<LongWritable, Text>的原因
		 * 调用自定义Map的map方法，将一个个<LongWritable, Text>对输入给Map的map方法
		 * 最终是生成一个List<IntPair, IntWritable>
		 * 在map阶段的最后，会先调用job.setPartitionerClass对这个List进行分区，每个分区映射到一个reducer
		 */
		job.setInputFormatClass(TextInputFormat.class);
		// 提供一个RecordWriter的实现，负责数据输出。
		job.setOutputFormatClass(TextOutputFormat.class);

		// 输入hdfs路径
		FileInputFormat.addInputPath(job, new Path(
				"hdfs://192.1168.1.12:9000/input/input/soso.txt"));
		// 输出hdfs路径
		FileOutputFormat.setOutputPath(job, new Path(
				"hdfs://192.1168.1.12:9000/output/sort/"));
		// 提交job
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
