package com.hn.cluster.hadoop.rpc.hadoopv2;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

/**
 * 
 * @author : Colonel.Hou
 * @Description:
 * @ClassName :
 * @date : 2015-01-28
 * @version : V1.0
 * 
 */
public class HadoopRpcClient
{

	public static void main(String[] args) throws IOException
	{
		InetSocketAddress addr = new InetSocketAddress(RpcConst.SERVER_ADDRESS,
				RpcConst.SERVER_PORT);
		RpcServerService service = RPC.getProxy(RpcServerService.class,
				RpcServerService.versionID, addr, new Configuration());
		String strServerReturn = service.serverExec("tom", "你给我发后速度快点");
		System.out.println(strServerReturn);
		RPC.stopProxy(service);
	}
}
