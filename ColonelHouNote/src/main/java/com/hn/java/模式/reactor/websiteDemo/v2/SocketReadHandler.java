package com.hn.java.模式.reactor.websiteDemo.v2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class SocketReadHandler implements Runnable{

	SelectionKey sk = null;
	ServerSocketChannel socket;
	static final int READING = 0, SENDING = 1;
	int state = READING;
	public SocketReadHandler(Selector sel, ServerSocketChannel c) throws IOException {
		super();
		this.socket = c;
		socket.configureBlocking(false);
		sk = socket.register(sel, 0);
		
		//将SelectionKey绑定为本Handler 下一步有事件触发时，将调用本类的run方法。
		//参看dispatch(SelectionKey k)
		sk.attach(this);

		//同时将SelectionKey标记为可读，以便读取。
		sk.interestOps(SelectionKey.OP_READ);
		sel.wakeup();
	}



	@Override
	public void run() {
		readRequest() ;
	}


	/**
	 * 处理读取data
	 */
	private void readRequest() {
		// TODO Auto-generated method stub
		ByteBuffer input = ByteBuffer.allocate(1024);
		input.clear();
		int bytesRead = socket.read(input);
		//激活线程池 处理这些request
		requestHandle(new Request(socket,btt));
	}

}
