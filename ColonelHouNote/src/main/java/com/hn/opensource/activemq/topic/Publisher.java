package com.hn.opensource.activemq.topic;

import java.util.Date;

import javax.jms.Connection;

import javax.jms.Destination;

import javax.jms.MapMessage;

import javax.jms.MessageProducer;

import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Publisher
{

	public static void main(String[] arg)
	{

		String user = ActiveMQConnection.DEFAULT_USER;

		String password = ActiveMQConnection.DEFAULT_PASSWORD;

		String url = "tcp://192.1168.1.12:61616";

		String subject = "mq.topic";

		ActiveMQConnectionFactory amcf = new ActiveMQConnectionFactory(user,
				password, url);

		try
		{

			Connection conn = amcf.createConnection();

			conn.start();

			Session session = conn.createSession(Boolean.TRUE,
					Session.AUTO_ACKNOWLEDGE);

			Destination d = session.createTopic(subject);

			MessageProducer producer = session.createProducer(d);

			for (int i = 0; i <= 5; i++)
			{

				MapMessage message = session.createMapMessage();

				Date date = new Date();

				message.setLong("count", date.getTime());

				Thread.sleep(1000);

				producer.send(message);

				System.out.println("--发送消息：" + date);

			}

			session.commit();

			session.close();

			conn.close();

		} catch (Exception e)
		{

			e.printStackTrace();

		}

	}

}
