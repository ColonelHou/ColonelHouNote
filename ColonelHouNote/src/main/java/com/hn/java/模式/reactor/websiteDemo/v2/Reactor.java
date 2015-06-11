package com.hn.java.模式.reactor.websiteDemo.v2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Reactor implements Runnable{

	Selector selector = null;
	ServerSocketChannel serversocket;
	
	
	
	
	public Reactor(int port) throws IOException {
		super();
		selector = Selector.open();
		
		serversocket = ServerSocketChannel.open();
		InetSocketAddress address = new InetSocketAddress("127.0.0.1", 9000);
		serversocket.socket().bind(address);
		serversocket.configureBlocking(false);
		
		//向selector注册Channel
		SelectionKey sk = serversocket.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("-->Start serverSocket.register!");
		
		//利用sk的attache功能绑定Acceptor 如果有事情，触发Acceptor
		sk.attach(new Acceptor());
		System.out.println("-->attach(new Acceptor()!");
	}




	@Override
	public void run() {
		try {
			while (!Thread.interrupted())
			{
			selector.select();
			Set selected = selector.selectedKeys();
			Iterator it = selected.iterator();
			//Selector如果发现channel有OP_ACCEPT或READ事件发生，下列遍历就会进行。
			while (it.hasNext())
			//来一个事件 第一次触发一个accepter线程
			//以后触发SocketReadHandler
			dispatch((SelectionKey)(it.next()));
			selected.clear();
		　　　　　　}
		　　　　}catch (IOException ex) {
			logger.debug("reactor stop!"+ex);
		　　　　}
	}
	//运行Acceptor或SocketReadHandler
	void dispatch(SelectionKey k) {
		Runnable r = (Runnable)(k.attachment());
		if (r != null){
		　　　　　　// r.run();
			}
　　}
	
	class Acceptor implements Runnable{

		@Override
		public void run() {
			try {
				System.out.println("-->ready for accept!");
				SocketChannel c = serversocket.accept();
				if(null != c){
					//调用Handler来处理channel
					new SocketReadHandler(selector, c);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
