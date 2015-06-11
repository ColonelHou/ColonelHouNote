package com.hn.java.se.RPC.apacheOschina;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class Client2
{
	public static void main(String[] args) throws Exception
	{
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL("http://localhost:8005/xmlrpc"));
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
		List<String> list = new ArrayList<String>();
		list.add("feng");
		list.add("helloword");
		String result = (String) client.execute("HelloHandler2.sayHello", list);
		System.out.println(result);
	}
}
