package com.hn.cluster.hadoop.rpc.hadoopv1;

import java.net.InetSocketAddress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

public class MyClient {
	/** * RPC客户端 */
	public static void main(String[] args) throws Exception {
		// RPC.getProxy(),该方法有四个参数,第一个参数是被调用的接口类,
		// 第二个是客户端版本号,第三个是服务端地址。返回的代理对象,
		// 就是服务端对象的代理,内部就是使用 java.lang.Proxy 实现的。
		final MyBizable proxy = (MyBizable) RPC.getProxy(MyBizable.class,
				MyBiz.BIZ_VERSION, new InetSocketAddress(
						MyServer.SERVER_ADDRESS, MyServer.SERVER_PORT),
				new Configuration());
		// 调用接口中的方法
		final String result = proxy.hello("world");
		// 打印返回结果，然后关闭网络连接
		System.out.println(result);
		RPC.stopProxy(proxy);
	}
}
