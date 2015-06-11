package com.hn.java.se.io.DataOutputStream;


import java.io.*;

public class DataStreamDemo 
{
	public static void main(String[] args) 
	{
		String strFile = DataStreamDemo.class.getResource("").getPath();
		strFile = strFile + "file.data";
		Member[] members = {new Member("Justin",90),
			                new Member("momor",95),
			                new Member("Bush",88)};
        try
		{
			writeData(strFile, members);

            readData(strFile, members);
		}
		catch(IOException e)
		{
            e.printStackTrace();
		}
	}

	private static void readData(String strFile, Member[] members)
			throws FileNotFoundException, IOException {
		DataInputStream dataInputStream = new DataInputStream(new FileInputStream(strFile));

		//读出数据并还原为对象
		for(int i=0;i<members.length;i++)
		{
			//读出UTF字符串
			String name = dataInputStream.readUTF();
			//读出int数据
			int score = dataInputStream.readInt();
			members[i] = new Member(name,score);
		}

		//关闭流
		dataInputStream.close();

		//显示还原后的数据
		for(Member member : members)
		{
			System.out.printf("%s\t%d%n",member.getName(),member.getAge());
		}
	}

	private static void writeData(String strFile, Member[] members)
			throws FileNotFoundException, IOException {
		DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(strFile));

		for(Member member:members)
		{
		    //写入UTF字符串
		    dataOutputStream.writeUTF(member.getName());
			//写入int数据
			dataOutputStream.writeInt(member.getAge());
		}

		//所有数据至目的地
		dataOutputStream.flush();
		//关闭流
		dataOutputStream.close();
	}
}
