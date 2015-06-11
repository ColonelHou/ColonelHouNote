package com.hn.java.se.tools;

public class 打印调用栈 {
	public 打印调用栈() {
	}

	private static void printStackTrace() {
		
		StackTraceElement[] stackElements = Thread.currentThread().getStackTrace();
		if (stackElements != null) {
			for (int i = 0; i < stackElements.length; i++) {
				System.out.println("" + stackElements[i]);
			}
		}
	}

	public void methodA() {
		methodB();
	}

	public void methodB() {
		printStackTrace();
	}

	public static void main(String[] argv) {
		打印调用栈 a = new 打印调用栈();
		a.methodA();
	}
}
