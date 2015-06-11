package com.hn.cluster.hadoop.rpc.hadoopv1;

import java.io.IOException;

import org.apache.hadoop.ipc.ProtocolSignature;

public class MyBiz  implements MyBizable{	
	public static long BIZ_VERSION = 123456L;	
	@Override	
	public String hello(String name) 
	{		
		System.out.println("我是ByBiz,我被调用了。");		
		return "hello" + name;	
	}	
	
	@Override	
	public long getProtocolVersion(String protocol, long clientVersion)	throws IOException 
	{		//<span style="color:#FF0000;">返回BIZ_VERSION，保证服务器和客户端请求版本一致</span>		
		return BIZ_VERSION;	
	}

	@Override
	public ProtocolSignature getProtocolSignature(String protocol,
			long clientVersion, int clientMethodsHash) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}