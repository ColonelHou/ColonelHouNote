package com.hn.cluster.hadoop.rpc.hadoopv1;

import org.apache.hadoop.ipc.VersionedProtocol;

public interface MyBizable extends VersionedProtocol{
	//定义抽象类方法hello	
	public abstract String hello(String name);
}
