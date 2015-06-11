package com.hn.cluster.hadoop.rpc.hadoopv2;

import java.io.IOException;

import org.apache.hadoop.ipc.ProtocolSignature;

/**
 * 
 * @author : Colonel.Hou
 * @Description:
 * @ClassName :
 * @date : 2015-01-28
 * @version : V1.0
 * 
 */
public class RpcServerServiceImpl implements RpcServerService
{

	/**
	 * 返回versionID，保证服务器和客户端请求版本一致
	 */
	@Override
	public long getProtocolVersion(String protocol, long clientVersion)
			throws IOException
	{
		return RpcServerService.versionID;
	}

	@Override
	public ProtocolSignature getProtocolSignature(String protocol,
			long clientVersion, int clientMethodsHash) throws IOException
	{
		return new ProtocolSignature(RpcServerService.versionID, null);
	}

	@Override
	public String serverExec(String strName, String strContent)
	{
		System.out.println("此处验证得到是在server端执行的过程");
		return strName + "说 ：" + strContent;

	}

}
