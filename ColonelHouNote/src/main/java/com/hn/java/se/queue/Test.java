package com.hn.java.se.queue;

public class Test
{

	public static void main(String[] args)
	{
		Queue queue = new Queue();
		for (int i = 0; i < 15; i++)
		{
			queue.enqueue("mr" + i);
		}
		System.out.println(queue);
		System.out.println(queue.getSize());
		System.out.println("----------------------");
		for (int i = 0; i < 10; i++)
		{
			queue.dequeue();
		}
		System.out.println(queue);
		System.out.println(queue.getSize());
		System.out.println("----------------------");
		for (int i = 0; i < 5; i++)
		{
			queue.enqueue("sunhailong" + i);
		}
		System.out.println(queue);
		System.out.println(queue.getSize());
	}
}
