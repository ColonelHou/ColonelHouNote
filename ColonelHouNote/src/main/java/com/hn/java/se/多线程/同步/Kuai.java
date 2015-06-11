package com.hn.java.se.多线程.同步;

public class Kuai
{
	String name;
	boolean enable = true;

	public Kuai(String name)
	{
		super();
		this.name = name;
	}

	public synchronized void pickUp()
	{
		try
		{
			while (this.enable == false)
			{
				this.wait();
			}
			this.enable = false;
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

	}

	public synchronized void putdown()
	{
		this.enable = true;
		this.notifyAll();
	}

	public static void main(String[] args)
	{
		Kuai k1 = new Kuai("筷子1号");
		Kuai k2 = new Kuai("筷子2号");
		Kuai k3 = new Kuai("筷子3号");
		Kuai k4 = new Kuai("筷子4号");
		Kuai k5 = new Kuai("筷子5号");

		People p1 = new People("哲学家1", k5, k1);
		People p2 = new People("哲学家2", k1, k2);
		People p3 = new People("哲学家3", k2, k3);
		People p4 = new People("哲学家4", k3, k4);
		People p5 = new People("哲学家5", k4, k5);

		p1.start();
		p2.start();
		p3.start();
		p4.start();
		p5.start();
	}

}

class People extends Thread
{
	String name;
	Kuai left;
	Kuai right;

	public People(String name, Kuai left, Kuai right)
	{
		super();
		this.name = name;
		this.left = left;
		this.right = right;
	}

	public void run()
	{
		left.pickUp();
		System.out.println(name + " 眼明手快， 拿起 " + left.name);
		right.pickUp();
		System.out.println(name + " 眼明手快， 拿起 " + right.name);
		System.out.println(name + " 已拿起左右手筷子， 狼吞虎咽起来");

		try
		{
			Thread.sleep(2000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		System.out.println(name + " 囫囵吞枣的吃完饭， 舒服的放下 " + left.name + " 和 "
				+ right.name + " 筷子 ");
		left.putdown();
		right.putdown();
	}
}
