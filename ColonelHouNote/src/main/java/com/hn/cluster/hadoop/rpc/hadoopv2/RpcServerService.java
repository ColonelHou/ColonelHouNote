package com.hn.cluster.hadoop.rpc.hadoopv2;

import org.apache.hadoop.ipc.VersionedProtocol;

/**
 * 
 * @author : Colonel.Hou
 * @Description:
 * @ClassName :
 * @date : 2015-01-28
 * @version : V1.0
 * 
 */
public interface RpcServerService extends VersionedProtocol
{
	
	public static final long versionID = 1L;

	public String serverExec(String strName, String strContent);
}
