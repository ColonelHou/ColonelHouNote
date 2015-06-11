package com.hn.cluster.hadoop.rpc.hadoopv2;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.Server;

/**
 * 
 * @author : Colonel.Hou
 * @Description:
 * @ClassName :
 * @date : 2015-01-28
 * @version : V1.0
 * 
 */
public class HadoopRpcServer
{

	public static void main(String[] args) throws IOException
	{

		/**
		 * Class to construct instances of RPC server with specific options.
		 * 使用Builder构造RPC Server实例;
		 * param1:setProtocol设置的值是服务端提供的对象必须是一个接口并继承VersioinedProtocal
		 * VersioinedProtocal:HadoopRPC接口,所有的 RPC 通信必须实现这个一接口,用于保证客户端和服务端的端口一致
		 * param2:setInstance服务端需要调用的业务类 param3:setBindAddress 设置开启服务的IP
		 * param4:setPort 设置开启服务的端口号
		 * 
		 * 最后调用build函数创建RPC Server实例
		 */
		Server server = new RPC.Builder(new Configuration())
				.setProtocol(RpcServerService.class)
				.setInstance(new RpcServerServiceImpl())
				.setBindAddress(RpcConst.SERVER_ADDRESS)
				.setPort(RpcConst.SERVER_PORT).build();
		server.start();
	}
}
