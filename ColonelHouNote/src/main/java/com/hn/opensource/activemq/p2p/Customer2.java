package com.hn.opensource.activemq.p2p;

import java.util.Date;

import javax.jms.Connection;

import javax.jms.ConnectionFactory;

import javax.jms.Destination;

import javax.jms.JMSException;

import javax.jms.MapMessage;

import javax.jms.Message;

import javax.jms.MessageConsumer;

import javax.jms.MessageListener;

import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;

import org.apache.activemq.ActiveMQConnectionFactory;

/*

 *第二个消费者

 */

public class Customer2
{

	public static void main(String[] args)
	{

		String user = ActiveMQConnection.DEFAULT_USER;
		String password = ActiveMQConnection.DEFAULT_PASSWORD;
		String url = ActiveMQConnection.DEFAULT_BROKER_URL;
		url = "tcp://192.1168.1.12:61616";
		String subject = "test.queue";
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				user, password, url);
		Connection connection;

		try
		{
			connection = connectionFactory.createConnection();
			connection.start();
			final Session session = connection.createSession(Boolean.TRUE,
					Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(subject);
			MessageConsumer message = session.createConsumer(destination);
			message.setMessageListener(new MessageListener()
			{
				public void onMessage(Message msg)
				{
					MapMessage message = (MapMessage) msg;
					try
					{
						System.out.println("--收到消息2："
								+ new Date(message.getLong("count")));
						session.commit();
					} catch (JMSException e)
					{
						e.printStackTrace();
					}

				}

			});
			Thread.sleep(30000);
			session.close();
			Thread.sleep(30000);
			connection.close();
			Thread.sleep(30000);
		} catch (JMSException e)
		{
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
