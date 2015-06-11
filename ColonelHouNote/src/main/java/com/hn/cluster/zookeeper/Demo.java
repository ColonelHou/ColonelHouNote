package com.hn.cluster.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * ZooKeeper API 共包含 5 个包， 分别为： org.apache.zookeeper ， org.apache.zookeeper.data
 * ， org.apache.zookeeper.server ， org.apache.zookeeper.server.quorum 和
 * org.apache.zookeeper.server.upgrade 。
 * 
 * 其中 org.apache.zookeeper 包含 ZooKeeper 类，它我们编程时最常用的类文件。
 * 
 * 
 * 提升ZOOKEEPER集群的性能
 * 由于ZooKeeper的写入首先需要通过Leader，然后这个写入的消息需要传播到半数以上的Fellower通过才能完成整个写入。
 * 所以整个集群写入的性能无法通过增加服务器的数量达到目的，相反，整个集群中Fellower数量越多， 整个集群写入的性能越差。
 * 
 * @author hadoop
 * 
 */
public class Demo
{

	// 会话超时时间，设置为与系统默认时间一致
	private static final int SESSION_TIMEOUT = 10000;

	/**
	 * 客户端库的主要类文件 创建 ZooKeeper 实例
	 */
	ZooKeeper zk;

	// 创建 Watcher 实例
	Watcher wh = new Watcher()
	{
		public void process(org.apache.zookeeper.WatchedEvent event)

		{
			System.out.println("Watcher event : " + event.toString());
		}

	};

	/**
	 * 初始化 ZooKeeper 实例
	 * 
	 * 一旦客户端和 ZooKeeper 服务建立起连接， ZooKeeper 系统将会分配给此连接回话一个 ID 值，
	 * 并且客户端将会周期地向服务器发送心跳来维持会话的连接。 只要连接有效， 客户端就可以调用 ZooKeeper API 来做相应的处理。
	 * 
	 * 每实例化一个ZooKeeper客户端，就开启了一个Session。ZooKeeper客户端是线程安全的， 也可以认为它实现了连接池.
	 * 因此，每一个应用只需要实例化一个ZooKeeper客户端即可，
	 * 同一个ZooKeeper客户端实例可以在不同的线程中使用。除非你想同一个应用中开启多个Session，
	 * 使用不同的Watcher，在这种情况下，才需要实例化多个ZooKeeper客户端。
	 * 
	 * 对ZNode的大小做了限制，最大不能超过1M (ZNODE数据存储在内存中)
	 * 
	 * ZNode的过大:读写某一个ZNode将造成不确定的延时/将过快地耗尽ZooKeeper服务器的内存
	 * 
	 * @throws IOException
	 */
	private void createZKInstance() throws IOException

	{
		zk = new ZooKeeper("hadoopMaster:2181", Demo.SESSION_TIMEOUT, this.wh);
	}

	private void ZKOperations() throws IOException, InterruptedException,
			KeeperException

	{
		System.out
				.println("/n1. 创建 ZooKeeper 节点 (znode ： zoo2, 数据： myData2 ，权限： OPEN_ACL_UNSAFE ，节点类型： Persistent");
		zk.create("/zoo2", "myData2".getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL);
		// CreateMode.EPHEMERAL 客户端断开后就会把目录删除的
		System.out.println("/n2. 查看是否创建成功： ");
		System.out.println(new String(zk.getData("/zoo2", false, null)));
		System.out.println("/n3. 修改节点数据 ");
		zk.setData("/zoo2", "ColonelHou".getBytes(), -1);
		System.out.println("/n4. 查看是否修改成功： ");
		System.out.println(new String(zk.getData("/zoo2", false, null)));

		// System.out.println("/n5. 删除节点 ");
		// zk.delete("/zoo2", -1);
		// System.out.println("/n6. 查看节点是否被删除： ");
		// System.out.println(" 节点状态： [" + zk.exists("/zoo2", false) + "]");
	}

	private void ZKClose() throws InterruptedException

	{
		Thread.sleep(15000);

		zk.close();
	}

	public static void main(String[] args) throws IOException,
			InterruptedException, KeeperException
	{

		Demo dm = new Demo();
		dm.createZKInstance();
		dm.ZKOperations();
		dm.ZKClose();
	}

}
