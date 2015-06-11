package com.hn.java.se.集合;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
 * Q 57 用两个栈实现队列
 */

public class QueueImplementByTwoStacks {

	private Stack<Integer> stack1;
	private Stack<Integer> stack2;

	QueueImplementByTwoStacks() {
		stack1 = new Stack<Integer>();
		stack2 = new Stack<Integer>();
	}

	public Integer poll() {
		Integer re = null;
		if (!stack2.empty()) {
			re = stack2.pop();
		} else {
			while (!stack1.empty()) {// move to stack2 to make stack1 have only
										// one element.Then pop this element.
				re = stack1.pop();
				stack2.push(re);
			}
			if (!stack2.empty()) {
				re = stack2.pop();
			}
		}
		return re;
	}

	public Integer offer(int o) {
		stack1.push(o);
		return o;
	}

	public static void main(String[] args) {
		QueueImplementByTwoStacks queue = new QueueImplementByTwoStacks();
		List<Integer> re = new ArrayList<Integer>();
		queue.offer(1);
		queue.offer(2);
		queue.offer(3);
		re.add(queue.poll());
		queue.offer(4);
		re.add(queue.poll());
		queue.offer(5);
		re.add(queue.poll());
		re.add(queue.poll());
		re.add(queue.poll());
		System.out.println(re.toString());
	}

}
