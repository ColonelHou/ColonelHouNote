NameNode HA如何实现及关键技术难题？
1.保持主备状态同步: StandBy在Active挂掉后迅速提供服务, 
                                 信息同步 = 加载FSImage+EditLog + DN心跳BlockReport。
2.脑裂：高可用(HA)系统中,两个联系着的结点断开后,本是整体系统分裂为两个独立节点,
             这时两个节点开始争抢资源，结果导致系统混乱,数据损坏.
3.NameNode切换对外透明,切换过程中不应该导致正在连接客户端失败。


================================ZKFC的设计======================
1. FailoverController实现下述几个功能
  (a) 监控NN的健康状态
  (b) 向ZK定期发送心跳，使自己可以被选举。
  (c) 当自己被ZK选为主时，active FailoverController通过RPC调用使相应的NN转换为active。
2. 为什么要作为一个deamon进程从NN分离出来
  (1) 防止因为NN的GC失败导致心跳受影响。
  (2) FailoverController功能的代码应该和应用的分离，提高的容错性。
  (3) 使得主备选举成为可插拔式的插件。
3. FailoverController主要包括三个组件，
  (1) HealthMonitor 监控NameNode是否处于unavailable或unhealthy状态。当前通过RPC调用NN相应的方法完成。
  (2) ActiveStandbyElector 管理和监控自己在ZK中的状态。
  (3) ZKFailoverController 它订阅HealthMonitor 和ActiveStandbyElector 的事件，并管理NameNode的状态。
==============================QJM的设计==========================
1.Namenode记录了HDFS的目录文件等元数据，客户端每次对文件的增删改等操作，Namenode都会记录一条日志，叫做editlog，而元数据存储在fsimage中。为了保持Stadnby与active的状态一致，standby需要尽量实时获取每条editlog日志，并应用到FsImage中。这时需要一个共享存储，存放editlog，standby能实时获取日志。这有两个关键点需要保证， 共享存储是高可用的，需要防止两个NameNode同时向共享存储写数据导致数据损坏。
2.是什么，Qurom Journal Manager，基于Paxos（基于消息传递的一致性算法）。这个算法比较难懂，简单的说，Paxos算法是解决分布式环境中如何就某个值达成一致，（一个典型的场景是，在一个分布式数据库系统中，如果各节点的初始状态一致，每个节点都执行相同的操作序列，那么他们最后能得到一个一致的状态。为保证每个节点执行相同的命令序列，需要在每一条指令上执行一个"一致性算法"以保证每个节点看到的指令一致）
3.如何实现，
(1) 初始化后，Active把editlog日志写到2N+1上JN上，每个editlog有一个编号，每次写editlog只要其中大多数JN返回成功（即大于等于N+1）即认定写成功。

(2) Standby定期从JN读取一批editlog，并应用到内存中的FsImage中。

(3) 如何fencing： NameNode每次写Editlog都需要传递一个编号Epoch给JN，JN会对比Epoch，如果比自己保存的Epoch大或相同，则可以写，JN更新自己的Epoch到最新，否则拒绝操作。在切换时，Standby转换为Active时，会把Epoch+1，这样就防止即使之前的NameNode向JN写日志，也会失败。

(4) 写日志：

  (a) NN通过RPC向N个JN异步写Editlog，当有N/2+1个写成功，则本次写成功。

  (b) 写失败的JN下次不再写，直到调用滚动日志操作，若此时JN恢复正常，则继续向其写日志。

  (c) 每条editlog都有一个编号txid，NN写日志要保证txid是连续的，JN在接收写日志时，会检查txid是否与上次连续，否则写失败。

(5) 读日志：

  (a) 定期遍历所有JN，获取未消化的editlog，按照txid排序。

  (b) 根据txid消化editlog。

(6) 切换时日志恢复机制

  (a) 主从切换时触发

  (b) 准备恢复（prepareRecovery），standby向JN发送RPC请求，获取txid信息，并对选出最好的JN。

  (c) 接受恢复（acceptRecovery），standby向JN发送RPC，JN之间同步Editlog日志。

  (d) Finalized日志。即关闭当前editlog输出流时或滚动日志时的操作。

  (e) Standby同步editlog到最新

(7) 如何选取最好的JN

  (a) 有Finalized的不用in-progress

  (b) 多个Finalized的需要判断txid是否相等

  (c) 没有Finalized的首先看谁的epoch更大

  (d) Epoch一样则选txid大的。
  
http://www.cnblogs.com/shenh062326/p/3870219.html