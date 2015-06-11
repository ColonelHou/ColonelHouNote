package com.hn.cluster.hadoop.rpc.hadoopv1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.Server;

public class MyServer {
	//定义final类型服务器地址和端口	
	public static final String SERVER_ADDRESS = "localhost";	
	public static final int SERVER_PORT = 1234;	
	/**	 * RPC是远程过程调用（Remote Procedure Call）	 */	
	public static void main(String[] args) throws Exception {		
		Configuration conf = new Configuration();		
		//重点RPC.getServer方法，该方法有四个参数,第一个参数是被调用的 java对象,		
		//第二个参数是服务器的地址,第三个参数是服务器的端口。获得服务器对象后,		
		//启动服务器。这样,服务器就在指定端口监听客户端的请求。		
		final Server server = RPC.getServer(new MyBiz(), SERVER_ADDRESS, SERVER_PORT, conf);		
		server.start();	
	}
}
