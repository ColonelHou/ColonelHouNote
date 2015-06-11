package com.hn.cluster.hadoop;

/**
 * 加载core-site.xml/core-default.xml配置文件,49３行
 * 提供了大量getter/setter函数,用于获取或者设置某种数据类型属性的属性值
 * writeXML函数：该函数能够将当前Configuration对象中所有属性及属性值保存到一个XML文件中,以便节点之间传输
 */

import org.apache.hadoop.conf.Configuration;

//网络传输与持久化
import org.apache.hadoop.io.WritableComparable;

//提供给应用程序的工具,应用程序可以使用Reporter中的方法报告完成进度,设定状态消息以及更新计数器
import org.apache.hadoop.mapred.Reporter;
/**
 * 主要用于描述输入数据格式,提供二个功能：
 *   １.数据切分；按照某个策略将输入数据切分成若干个split，以便确定MapTask个数以及对应的split
 *    2.为mapper提供输入数据;给定某个split能将其解析成一个个key/value对
 * 　　　　两个方法：
 * 　　　　getSplits：完成数据切分的功能,它会尝试着将输入数据切分成numSplits个inputSplit
 * 　　　　           inputSplit二个特点：
 * 　　　　           　　　　　逻辑分片:只是在逻辑上对输入数据进行分片,并不会在磁盘上将其切分成分片进行存储;
 * 　　　　                            只记录分片的元数据信息;eg:s始位置/长度/所在结点列表
 * 　　　　           　　　　　可序列化:进程间通信和永久存储;这里主要为了进程间通信
 * 　　　　createRecordReader:返回一个RecordReader对象，该对象可将输入的InputSplit解析成若干k/v对。
 * 为方便用户编写MR程序,Hadoop自带了一些针对ＤＢ和文件的InputFormat
 * 下面重点介绍TextInputFormat/SequenceFileInputFormat
 * 
 */
import org.apache.hadoop.mapreduce.InputFormat;
/**
 * FileInputFormat:重要功能是为各种InputFormat提供统一的getSplits函数。
 * 实现最核心的两人个算法是文件切分算法与host选择算法
 * 文件切分算法：goalSize:根据用户期望的InputSplit数目计算出来的,即totalSize(文件总大小)/numSplits(用户设定的MapTask个数,默认是１)
 *             minSize:InputSplit的最小值,由配置参数mapred.min.split.size确定，默认是1
 *             blockSize:文件在HDFS中存储的block大小,不同文件可能不同,默认是64MB
 *             以上三个参数共同决定InputSplit的最终大小splitSize=max{minSize,min{goalSize,blockSize}}
 *             .新版API有一改动，InputSplit划分算法不再考虑用户设定的MapTask个数，而用mapred.max.split.size代替
 *             公式变为:splitSize=max{minSize, min(maxSize, blockSize)}
 * host选择算法:切分完成后,下一步确定每个InputSplit元数据信息(文件,起始位置,长度,以及所在host列表).
 */
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
/**
 * 
 */
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
/**
 * 主要用于描述输出数据的格式,它能将用户提供的k/v对写入特定的文件中.
 * 三个函数:
 * 		checkOutputSpecs:检查输出目录是否合法
 * 		getOutputCommitter:
 * 		RecordWriter:
 */
import org.apache.hadoop.mapreduce.OutputFormat;

/**
 * 同时具有了作用配置和作业提交功能
 */
import org.apache.hadoop.mapreduce.Job;

/**
 * MapperReducer提供了很多Mapper/Reducer实现,但大部分功能比较简单,
 * ChainMapper/ChainReducer:用于支持链式作业
 * IdentityMapper/IdentityReducer:用于输入k/v不进行任何处理
 * InverseMapper：交换k/v位置
 * RegexMapper:正则表达式字符串匹配
 * TokenMapper:将字符串分割成若干个token(单词),可用作WordCount的Mapper
 * LongSumReducer:以key为组,对long类型的value求累加和
 * 
 * 一个MR应用程序,不一定非要存在Mapper
 */
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.chain.ChainReducer;
import org.apache.hadoop.mapred.lib.IdentityMapper;
import org.apache.hadoop.mapred.lib.IdentityReducer;
import org.apache.hadoop.mapreduce.lib.map.InverseMapper;
import org.apache.hadoop.mapreduce.lib.map.RegexMapper;
import org.apache.hadoop.mapreduce.lib.reduce.LongSumReducer;
import org.apache.hadoop.mapred.MapRunnable;

/**
 * 作用:是对Mapper产生的中间结果进行分片,以便将同一分组的数据交给同一个Reducer处理,它直接影响Reducer阶段的负载均衡
 * MR提供了两个Partitioner实现：
 * HashPartitioner:其中HashPartitioner是默认实现，它实现了一种基于哗然值的分片方法;
 * TotalOrderPartitioner:提供了一种基于区间的分片方法,通常用在数据全排序中。在ＭＲ环境中容易想到的全排序是归并排序，
 * 　　　　　　　　　　　　　即在Map阶段，每个MapTask进行局部排序；在Reducer阶段启动一个ReducerTask进行全局排序。
 * 
 */
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * 
 */
import org.apache.hadoop.mapreduce.Reducer;

/**
 * Hadoop MapReduce对外提供的５个组件(InputFormat, Mapper, Partitioner, Reducer, OutputFormat)
 * 当用户按照约定实现这几个接口后,MapReduce运行时环境会自动调用它们
 * @author hadoop
 *
 */

/**
 * RPC：实际上是分布式计算中客户机/服务器模型的一个应用实例. 
 * 特点:透明性,高性能,可控性
 * Master就是一个RPC Server
 * 四个部分：
 * 		序列化层：序列化层的主要作用是将结构化对象转为字节流以便于在网络进行传输或持久存储
 * 		函数调用层：定位要调用的函数并执行该函数.hadoopRPC采用java反射机制与动态代理实现
 * 		网络传输层：描述client与server之间消息传输方式; hadoop采用基于TCP/IP的Socket机制
 * 		服务器端处理框架：可被抽象为网络I/O模型.常见I/O模型:阻塞式、非阻塞式、事件驱动
 * 主要使用:java反射、NIO、网络编程来实现
 * NIO包含：
 * 		Channel(通道)：类似于原IO中流,用户可以通过它读取和写入数据。
 * 		Buffer(缓冲区)：一块连续的内存区域,一般作为Channel收发数据的载体出现。 
 *                     所有Data都通过Buffer对象处理
 * 		Selector(选择器):提供了监控一个或多个通道当前状态的机制。
 *                      只要Channel向Selector注册了某种特定事件,Selector就会监听这些事件是否会发生，
 *                      一旦发生某个事件，便会通知对应的Channel.
 * RPC是客户端与服务器之间通信接口,它定义了服务器端对外提供的服务接口
 * 
 * RPC对外提供了一些可配置参数,以便于用户根据业务需求和硬件环境对其进行调优：
 * 		Reader线程数目:ipc.server.read.threadpool.size默认１
 * 		每个Handler线程对应的最大Call数目:ipc.server.handler.queue.size指定，默认100
 * 		Handler线程数目:
 * 		客户端最大重试次数：ipc.client.connect.max.retries默认是１０
 * @author hadoop
 *
 */
import java.nio.Buffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

//import org.apache.hadoop.hdfs.protocol.ClientProtocol;
import org.apache.hadoop.mapreduce.protocol.ClientProtocol;
import org.apache.hadoop.yarn.client.api.AMRMClient;
import org.apache.hadoop.yarn.client.api.YarnClient;
/**
 * RPC类实际上是对底层客户机/服务器网络模型的封装,以便程序员提供一套更方便简洁的编程接口
 * hadoopRPC使用了java动态代理完成对远程方法的调用
 */
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.Client;
import org.apache.hadoop.ipc.RpcEngine;
import org.apache.hadoop.ipc.Server;

/**
 * 下载源码http://mirrors.hust.edu.cn/apache/hadoop/common/hadoop-2.2.0/hadoop-2.2.0-src.tar.gz
 * 减压并安装maven与protobuf, 注意版本2.5以下
 * 进入源码执行mvn eclipse:eclipse
 * 导入eclipse中
 * 
 * /
public class SRC
{

	public static void main(String[] args)
	{

	}
}
