package com.hn.java.六大法则.单一职责.v2;

/**
 * 
 * @author Colonel.Hou 我们发现这样修改花销很大， 除了把原来的类分解之外， 还需要修改客户端
 * 
 */
public class Client
{
	public static void main(String[] args)
	{
		Terrestrial terrestrial = new Terrestrial();
		terrestrial.breathe("牛");
		terrestrial.breathe("羊");
		terrestrial.breathe("猪");

		Aquatic aquatic = new Aquatic();
		aquatic.breathe("鱼");
	}
}
