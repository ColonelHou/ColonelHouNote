package com.hn.java.jdk7.autoClose;

public class Client {

	public static void main(String[] args) {

		try (NewResource res = new NewResource("res1 closing ...")) {
			res.doSomeWork("Listening to podcast");
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage() + "Thrown by "
					+ e.getClass().getSimpleName());
		}
	}
}
