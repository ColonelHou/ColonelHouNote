package com.hn.opensource.activemq.p2p;

import java.util.Date;

import javax.jms.Connection;

import javax.jms.ConnectionFactory;

import javax.jms.Destination;

import javax.jms.JMSException;

import javax.jms.MapMessage;

import javax.jms.MessageProducer;

import javax.jms.Session;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer
{

	public static void main(String[] args)
	{
		String user = ActiveMQConnection.DEFAULT_USER;
		String password = ActiveMQConnection.DEFAULT_PASSWORD;
		String url = ActiveMQConnection.DEFAULT_BROKER_URL;
		url = "tcp://192.1168.1.12:61616";
		String subject = "test.queue";
		ConnectionFactory contectionFactory = new ActiveMQConnectionFactory(
				user, password, url);
		try
		{
			Connection connection = contectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(Boolean.TRUE,
					Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(subject);
			MessageProducer producer = session.createProducer(destination);
			for (int i = 0; i <= 5; i++)
			{
				MapMessage message = session.createMapMessage();
				Date date = new Date();
				message.setLong("count", date.getTime());
				Thread.sleep(1000);
				producer.send(message);
				System.out.println("--发送消息：" + date);
			}
			Thread.sleep(2000);
			session.commit();
			session.close();
			connection.close();
		} catch (JMSException e)
		{
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
