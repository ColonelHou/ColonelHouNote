package com.hn.java.模式.reactor.websiteDemo.v1;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;



/**
 * http://www.blogjava.net/baoyaer/articles/87514.html
 * 
 * 但这种模式在用户负载增加时，性能将下降非常的快。
 * 我们需要重新寻找一个新的方案，保持数据处理的流畅，很显然，
 * 事件触发机制是最好的解决办法，当有事件发生时，会触动handler,然后开始数据的处理。
 * Reactor模式类似于AWT中的Event处理：
 * @author John
 *
 */
public class NBTest {

	public NBTest() {

	}

	public void startServer() throws IOException {
		int channels = 0;
		int nKeys = 0;
		int currentSelector = 0;

		Selector selector = Selector.open();
		// 建立Channel
		ServerSocketChannel ssc = ServerSocketChannel.open();
		InetSocketAddress address = new InetSocketAddress(
				"127.0.0.1", 9000);
		ssc.socket().bind(address);

		// 使用non-blocking的方式
		ssc.configureBlocking(false);

		// 向Selector注册Channel及我们有兴趣的事件
		SelectionKey s = ssc.register(selector, SelectionKey.OP_ACCEPT);
		printKeysInfo(s);

		// 不断的轮询
		while (true) {
			debug("NBTest: Starting select");

			// Selector通过select方法通知我们我们感兴趣的事件发生了。
			nKeys = selector.select();

			// 如果有我们注册的事情发生了，它的传回值就会大于0
			if (nKeys > 0) {
				debug("NBTest: Number of keys after select operation: " + nKeys);
				//Selector传回一组SelectionKeys
				//我们从这些key中的channel()方法中取得我们刚刚注册的channel。
				Set selectedKeys = selector.selectedKeys();
				Iterator i = selectedKeys.iterator();
				while(i.hasNext()){
					 s = (SelectionKey) i.next();
					 printKeysInfo(s);
				 debug("NBTest: Nr Keys in selector: " +selector.keys().size());

				//一个key被处理完成后，就都被从就绪关键字（ready keys）列表中除去
				 i.remove();
				 if(s.isAcceptable()){
					 // 从channel()中取得我们刚刚注册的channel。
				 Socket socket = ((ServerSocketChannel)s.channel()).accept().socket();
				 SocketChannel sc = socket.getChannel();

				 sc.configureBlocking(false);
				 sc.register(selector, SelectionKey.OP_READ |SelectionKey.OP_WRITE);
				 System.out.println(++channels);
				 }else{
					 debug("NBTest: Channel not acceptable");
				 }
				}
			}else{
				debug("NBTest: Select finished without any keys.");
			}
		}
	}

	private static void debug(String s) {
		System.out.println(s);
	}

	public void printKeysInfo(SelectionKey sk) {
		StringBuffer sb = new StringBuffer();
		sb.append("Att: " + (sk.attachment() == null ? "no" : "yes"));
		sb.append(", Read: " + sk.isReadable());
		sb.append(", Acpt: " + sk.isAcceptable());
		sb.append(", Cnct: " + sk.isConnectable());
		sb.append(", Wrt: " + sk.isWritable());
		sb.append(", Valid: " + sk.isValid());
		sb.append(", Ops: " + sk.interestOps());
		debug(sb.toString());
	}
	
	public static void main(String[] args) {
		NBTest nbTest = new NBTest();
		try {
			nbTest.startServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
