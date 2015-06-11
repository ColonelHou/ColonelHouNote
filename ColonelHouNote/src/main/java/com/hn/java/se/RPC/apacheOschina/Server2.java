package com.hn.java.se.RPC.apacheOschina;

import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

public class Server2
{
	private static final int port = 8005;

	public static void main(String[] args) throws Exception
	{
		WebServer webServer = new WebServer(port);
		XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
		PropertyHandlerMapping phm = new PropertyHandlerMapping();
		phm.addHandler("HelloHandler2", HelloHandler2.class);
		xmlRpcServer.setHandlerMapping(phm);
		XmlRpcServerConfigImpl config = (XmlRpcServerConfigImpl) xmlRpcServer
				.getConfig();
		config.setEnabledForExtensions(true);
		config.setContentLengthOptional(false);
		webServer.start();
	}
}
