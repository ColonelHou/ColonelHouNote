package com.hn.work.sendRequest;

import java.io.IOException;
import java.util.Calendar;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpHost;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

public class HttpClientSendRequest
{

	private static Logger log = Logger.getLogger(HttpClientSendRequest.class);

	// private static String WINDOWS_ONLINE_TEST_LOG =
	// "C:\\Users\\Colonel.Hou\\Desktop\\online_history.log";

	public static void main(String args[])
	{
		Monitor m = new Monitor("tes");
		m.run();
	}

	public static boolean testCheck()
	{
		boolean result = false;
		// log.info("broadCastToService start");
		// String url = "http://192.1168.1.12:8080/WebPro/accept";
		// String url = "http://192.1168.1.12:8080/WebPro/accept";
		String url = "http://localhost:8080/WebPro/accept";
		// String url =
		// "http://192.1168.1.12:8080/WebPro/accept";
		// String url =
		// "http://192.1168.1.12:8080/WebPro/accept";
		HttpClient client = new HttpClient();
		// 设置代理开始。如果代理服务器需要验证的话，可以修改用户名和密码
		// client.getCredentialsProvider().setCredentials(new
		// AuthScope("proxy.com",8080), new UsernamePasswordCredentials("",
		// ""));

		// client.getHostConfiguration().setProxy("proxy.com", 8080);
		// HttpHost proxy = new HttpHost("proxy.com", 8080);

		PostMethod postRequest = new PostMethod(url);

		// \"52\":\"appStore\",
		postRequest
				.setParameter("josn");
		try
		{
			client.executeMethod(postRequest);
		} catch (Exception e)
		{
			log.error("broadCastToService io execetion ");
			e.printStackTrace();
		}
		// 200 is ok
		if (postRequest.getStatusCode() == 200)
		{
			log.info("connected");
			result = true;
			postRequest.releaseConnection();
		} else
		{
			log.info("fail");
			log.info("Status code is : " + postRequest.getStatusCode());
			try
			{
				log.info(postRequest.getResponseBodyAsString());
			} catch (Exception e)
			{
				log.info("Get response code error");
				// e.printStackTrace();
			}
			result = false;
			postRequest.releaseConnection();
		}

		return result;
	}

	public static class Monitor extends Thread
	{
		Monitor(String name)
		{
			super(name);// 调用父类带参数的构造方法
		}

		public void run()
		{
			boolean lastTest = false;
			boolean connected = false;
			int conLostTick = 0;
			String tip = "连接消失";
			while (true)
			{
				// File his_file = new File(WINDOWS_ONLINE_TEST_LOG);
				try
				{
					// if(!his_file.exists())
					// {
					// his_file.createNewFile();
					// }

					// FileWriter fw = new FileWriter(WINDOWS_ONLINE_TEST_LOG ,
					// true);
					long time = DateUtilities.Calendar2timeString(Calendar
							.getInstance());
					time = time / 100000;
					connected = testCheck();
					conLostTick++;
					// System.out.println(conLostTick);
					// fw.write(time+ " : "+ connected + " lost conn : "
					// +conLostTick +"m");
					// fw.write("\r\n");
					// fw.close();
				} catch (Exception e)
				{
					e.printStackTrace();
					System.out.println("error");
				}
				// 两次发送设定间隔
				try
				{
					this.sleep(2000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}