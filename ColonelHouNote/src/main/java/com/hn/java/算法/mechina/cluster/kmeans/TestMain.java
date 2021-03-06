package com.hn.java.算法.mechina.cluster.kmeans;

import java.util.ArrayList;
import java.util.List;

/**
 * http://duyunfei.iteye.com/blog/1494015
 * 举个例子，某某教练在得到全队的数据后，想把这些球员自动分成不同的组别，你得问教练需要分成几个组，
 * 他回答你k个，ok可以开始了，在解决这个问题之前有必要详细了解自己需要达到的目的：根据教练给出的k值，
 * 呈现出k个组，每个组的队员是相似的。
 * @author 123
 *
 */
public class TestMain
{

	public static void main(String[] args)
	{
		List<Player> listPlayers = new ArrayList<Player>();

		for (int i = 0; i < 15; i++)
		{

			Player p1 = new Player();
			p1.setName("afei-" + i);
			p1.setAssists(i);
			p1.setBackboard(i);

			// p1.setGoal(new Random(100*i).nextDouble());
			p1.setGoal(i * 10);
			p1.setSteals(i);
			// listPlayers.add(p1);
		}

		Player p1 = new Player();
		p1.setName("afei1");
		p1.setGoal(1);
		p1.setAssists(8);
		listPlayers.add(p1);

		Player p2 = new Player();
		p2.setName("afei2");
		p2.setGoal(2);
		listPlayers.add(p2);

		Player p3 = new Player();
		p3.setName("afei3");
		p3.setGoal(3);
		listPlayers.add(p3);

		Player p4 = new Player();
		p4.setName("afei4");
		p4.setGoal(7);
		listPlayers.add(p4);

		Player p5 = new Player();
		p5.setName("afei5");
		p5.setGoal(8);
		listPlayers.add(p5);

		Player p6 = new Player();
		p6.setName("afei6");
		p6.setGoal(25);
		listPlayers.add(p6);

		Player p7 = new Player();
		p7.setName("afei7");
		p7.setGoal(26);
		listPlayers.add(p7);

		Player p8 = new Player();
		p8.setName("afei8");
		p8.setGoal(27);
		listPlayers.add(p8);

		Player p9 = new Player();
		p9.setName("afei9");
		p9.setGoal(28);
		listPlayers.add(p9);
		
		

		Kmeans<Player> kmeans = new Kmeans<Player>(listPlayers, 3);
		List<Player>[] results = kmeans.comput();
		for (int i = 0; i < results.length; i++)
		{
			System.out.println("===========类别" + (i + 1) + "================");
			List<Player> list = results[i];
			for (Player p : list)
			{
				System.out.println("姓名:" + p.getName() + ", 得分:" + p.getGoal() + ", 助攻"
						+ p.getAssists() + ", 抢断" + p.getSteals() + ", 篮板"
						+ p.getBackboard());
			}
		}

	}

}
