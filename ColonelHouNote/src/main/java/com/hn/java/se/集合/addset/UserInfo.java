package com.hn.java.se.集合.addset;

public class UserInfo
{
	private String userName;
	private int userAge;

	public UserInfo(String userName, int userAge)
	{
		super();
		this.userName = userName;
		this.userAge = userAge;
	}

	@Override
	public int hashCode()
	{
		final int offer = 31;

		return userAge * userName.hashCode() * offer;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;
		UserInfo userinfo = (UserInfo) obj;
		if (userName.equals(userinfo.getUserName())
				&& userAge == userinfo.getUserAge())
			return true;
		return false;
	}

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		// return super.toString();
		return "UserInfo UserName:" + getUserName() + "UserAge:" + getUserAge();
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public int getUserAge()
	{
		return userAge;
	}

	public void setUserAge(int userAge)
	{
		this.userAge = userAge;
	}
}
