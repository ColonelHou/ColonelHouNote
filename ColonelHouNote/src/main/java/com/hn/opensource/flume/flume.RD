1):下载
	wget http://archive.apache.org/dist/flume/1.5.2/apache-flume-1.5.2-bin.tar.gz
	tar -xvf apache-flume-1.5.2-bin.tar.gz /usr/local/
2):添加环境变量
	vim /etc/profile
	#apache-flume-1.5.2-bin
	export FLUME_HOME=/usr/local/apache-flume-1.5.2-bin
	export FLUME_CONF_DIR=$FLUME_HOME/conf
	export PATH=$FLUME_HOME/bin:$PATH
3):生成配置文件等
	mv /usr/local/apache-flume-1.5.2-bin/conf/flume-conf.properties.templates flume-conf.properties
	mv /usr/local/apache-flume-1.5.2-bin/conf/flume-env.sh.templates flume-env.sh
4):配置Flume环境变量
	vim /usr/local/apache-flume-1.5.2-bin/conf/flume-env.sh
	JAVA_HOME=/usr/local/jdk
	FLUME_CLASSPATH=/usr/local/apache-flume-1.5.2-bin
5):配置Flume配置文件：
	vim /usr/local/apache-flume-1.5.2-bin/conf/flume-conf.properties
	agent1.sources = avro-source1    #agent1是agent名字
	agent1.channels = ch1
	agent1.sinks = sink1
	agent1.sources.avro-source1.channels = ch1
	#--source的类型avro表示以avro协议接收数据，且数据由avro客户端事件驱动
	agent1.sources.avro-source1.type = avro
	#--source绑定的数据源主机地址。web端的日志已由log4j以RPC协议发送到数据接收服务器的41414端口，那么数据接收服务器中flume agent的source只需绑定本>机的ip，并接收41414端口过来的数据（这里很重要，否则会接收不到数据，web端也可能无法发送）。
	agent1.sources.avro-source1.bind = 192.168.11.135  #引处配置的是flume所在机器的IP地址
	agent1.sources.avro-source1.port = 41414

	#agent1.sources.avro-source1.interceptors = i1
	#agent1.sources.avro-source1.interceptors.i1.type = timestamp

	agent1.channels.ch1.type = memory
	#--配置sink,将接收到的数据保存到指定地点。
	agent1.sinks.sink1.channel = ch1
	#agent1.sinks.sink1.type = logger
	#--sink的type=file_roll表示以滚动文件的形式将数据保存到文件中。这里有个问题，如果不做其他设置，flume默认30秒生成一个新文件（即使没有数据）
	agent1.sinks.sink1.type = file_roll
	#--设置shik组件将文件sink的路径。
	agent1.sinks.sink1.sink.directory = /usr/local/apache-flume-1.5.2-bin/logs  #自己创建
	#此处配置的，应该是每隔24秒，生成一个flume日志数据文件。此处如果不配置，默认是300秒（数字的单位是秒）
	agent1.sinks.sink1.sink.rollInterval=20
6):启动：
	[hadoop@hadoopSlave1Test /usr/local/apache-flume-1.5.2-bin]$flume-ng agent -n agent1 -c conf -f conf/flume-conf.properties
	Info: Sourcing environment configuration script /usr/local/apache-flume-1.5.2-bin/conf/flume-env.sh
	Info: Including Hadoop libraries found via (/usr/local/hadoop/bin/hadoop) for HDFS access
	Info: Excluding /usr/local/hadoop/share/hadoop/common/lib/slf4j-api-1.7.5.jar from classpath
	Info: Excluding /usr/local/hadoop/share/hadoop/common/lib/slf4j-log4j12-1.7.5.jar from classpath
	...
	:/usr/local/hadoop/share/hadoop/mapreduce/lib-examples:/usr/local/hadoop/share/hadoop/mapreduce/sources:/usr/local/hadoop/contrib/capacity-scheduler/*.jar' -Djava.library.path=:/usr/local/hadoop/lib/native org.apache.flume.node.Application -n agent1 -f conf/flume-conf.properties
7):配置WEB：
src下log4j.properties：
	#定义
	log4j.rootCategory=INFO

	log4j.logger.com.flume = info,flume
	#-----或者为 log4j.logger.com.trace. = info,flume ，范围扩大到包
	log4j.appender.flume=org.apache.flume.clients.log4jappender.Log4jAppender
	log4j.appender.flume.layout = org.apache.log4j.PatternLayout
	log4j.appender.flume.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %p [%c{2}] - %m%n
	#--接收数据的远程主机IP，此处为虚拟机的ip地址
	log4j.appender.flume.Hostname=192.168.11.135
	#--监听数据发送的远程主机端口号，需开通（web服务器端和数据接收服务器端要开通）
	log4j.appender.flume.Port=41414
8):拷贝JAR到WEB项目下：
   [hadoop@hadoopMasterTest ~]$cp /.../apache-flume-1.5.2-bin/lib/* /.../webapps/log/WEB-INF/lib/
   web代码：
    @WebServlet("/GenerateLog")
	public class GenerateLog extends HttpServlet {
		Logger logger = Logger.getLogger(GenerateLog.class);
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		while(true)
		{
			for (int i = 0; i < 100; i++)
			{
	        	logger.debug("Here is DEBUG messgae " + i);
	        	logger.info("Here is INFO message " + i);
	        	logger.warn("Here is WARN message " + i);
	        	logger.error("Here is ERROR message  " + i);
				....
   启动WEB项目
    INFO: Starting ProtocolHandler ["http-bio-8080"]
	Apr 03, 2015 1:59:52 AM org.apache.coyote.AbstractProtocol start
	INFO: Starting ProtocolHandler ["ajp-bio-8009"]
	Apr 03, 2015 1:59:52 AM org.apache.catalina.startup.Catalina start
	INFO: Server startup in 1665 ms
	log4j:WARN No appenders could be found for logger (org.apache.flume.api.NettyAvroRpcClient).
	log4j:WARN Please initialize the log4j system properly.
	log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
9):查看Flume下生成的：
	[hadoop@hadoopSlave1Test /usr/local/apache-flume-1.5.2-bin/logs]$l
	total 39964
	drwxrwxrwx 2 hadoop hadoop   4096 Apr  3 02:18 .
	drwxrwxrwx 8 hadoop hadoop   4096 Apr  3 01:46 ..
	-rw-rw-r-- 1 hadoop hadoop      0 Apr  3 01:59 1428051597978-1
	-rw-rw-r-- 1 hadoop hadoop 739960 Apr  3 02:03 1428051597978-10
	-rw-rw-r-- 1 hadoop hadoop 711500 Apr  3 02:03 1428051597978-11
	-rw-rw-r-- 1 hadoop hadoop 739960 Apr  3 02:03 1428051597978-12
	-rw-rw-r-- 1 hadoop hadoop 711500 Apr  3 02:04 1428051597978-13
	-rw-rw-r-- 1 hadoop hadoop 739960 Apr  3 02:04 1428051597978-14
	...
	-rw-rw-r-- 1 hadoop hadoop   5724 Apr  3 02:00 flume.log
	[hadoop@hadoopSlave1Test /usr/local/apache-flume-1.5.2-bin/logs]$vim 1428051597978-58
	2015-04-03 02:18:58 INFO [log.GenerateLog] - Here is INFO message 75

	2015-04-03 02:18:58 WARN [log.GenerateLog] - Here is WARN message 75

	2015-04-03 02:18:58 ERROR [log.GenerateLog] - Here is ERROR message  75
	...



实现agent到collect的连接，并能发送日志。

一：假设：现有两台机子，命名为：agent，collect。agent IP地址为：192.168.11.131，collect为192.168.11.132
二：要求：实现agent到collect的连接，并能向collect发送日志。
三：agent配置：
	#name the  components on this agent
	a1.sources  = r1
	a1.sinks =  k1
	a1.channels  = c1
	#  Describe/configure the source
	a1.sources.r1.type  = netcat
	#//这里的数据源设置成netcat，后面将通过telnet传送信息
	a1.sources.r1.bind  = 0.0.0.0
	# //建议  
	a1.sources.r1.port  = 44444
	# Describe  the sink
	a1.sinks.k1.type  =avro
	a1.sinks.k1.hostname=192.168.11.132  
	#//collect 的IP地址，注意
	a1.sinks.k1.port=60000
	#//这里的接口要与collect source的接口一致。
	# Use a  channel which buffers events in memory
	a1.channels.c1.type  = memory
	a1.channels.c1.capacity  = 1000
	a1.channels.c1.transactionCapacity  = 100
	# Bind the  source and sink to the channel
	a1.sources.r1.channels  = c1
	a1.sinks.k1.channel  = c1
四：collect配置：
	# Name the  components on this agent
	a1.sources  = r1
	a1.sinks =  k1
	a1.channels  = c1
	#  Describe/configure the source
	a1.sources.r1.type  = avro
	a1.sources.r1.bind  = 192.168.11.132
	#//绑定本机的IP的地址
	a1.sources.r1.port  = 60000
	#//接口要与agent sink的port一致
	# Describe  the sink
	a1.sinks.k1.type  = logger
	# Use a  channel which buffers events in memory
	a1.channels.c1.type  = memory
	a1.channels.c1.capacity  = 1000
	a1.channels.c1.transactionCapacity  = 100
	# Bind the  source and sink to the channel
	a1.sources.r1.channels  = c1
	a1.sinks.k1.channel  = c1
五：collect启动：
	[hadoop@hadoopSlave2Test /usr/local/Flume/conf]$flume-ng agent -n a1 -c conf -f flume-conf.properties 
	Info: Including Hadoop libraries found via (/usr/local/hadoop/bin/hadoop) for HDFS access
	Info: Excluding /usr/local/hadoop/share/hadoop/common/lib/slf4j-api-1.7.5.jar from classpath
	Info: Excluding /usr/local/hadoop/share/hadoop/common/lib/slf4j-log4j12-1.7.5.jar from classpath
	。。。
	15/04/08 00:30:59 INFO instrumentation.MonitoredCounterGroup: Component type: CHANNEL, name: c1 started
	15/04/08 00:30:59 INFO node.Application: Starting Sink k1
	15/04/08 00:30:59 INFO node.Application: Starting Source r1
	15/04/08 00:30:59 INFO source.AvroSource: Starting Avro source r1: { bindAddress: 192.168.11.132, port: 60000 }...
	15/04/08 00:31:00 INFO instrumentation.MonitoredCounterGroup: Monitored counter group for type: SOURCE, name: r1: Successfully registered new MBean.
	15/04/08 00:31:00 INFO instrumentation.MonitoredCounterGroup: Component type: SOURCE, name: r1 started
	15/04/08 00:31:00 INFO source.AvroSource: Avro source r1 started.
六：agent启动：
	[hadoop@hadoopSlave1Test /usr/local/Flume/conf]$flume-ng  agent -c conf -f flume-conf.properties -n a1 -Dflume.root.logger=INFO,console
	Info: Including Hadoop libraries found via (/usr/local/hadoop/bin/hadoop) for HDFS access
	Info: Excluding /usr/local/hadoop/share/hadoop/common/lib/slf4j-api-1.7.5.jar from classpath
	Info: Excluding /usr/local/hadoop/share/hadoop/common/lib/slf4j-log4j12-1.7.5.jar from classpath
	。。。
	15/04/08 00:31:21 INFO instrumentation.MonitoredCounterGroup: Component type: SINK, name: k1 started
	15/04/08 00:31:21 INFO source.NetcatSource: Source starting
	15/04/08 00:31:21 INFO sink.AbstractRpcSink: Rpc sink k1: Building RpcClient with hostname: 192.168.11.132, port: 60000
	15/04/08 00:31:21 INFO sink.AvroSink: Attempting to create Avro Rpc client.
	15/04/08 00:31:21 INFO source.NetcatSource: Created serverSocket:sun.nio.ch.ServerSocketChannelImpl[/0:0:0:0:0:0:0:0:44444]
	15/04/08 00:31:21 WARN api.NettyAvroRpcClient: Using default maxIOWorkers
	15/04/08 00:31:21 INFO sink.AbstractRpcSink: Rpc sink k1 started.
七：agent另开一个 terminal,输入 telnet 127.0.0.1 44444 后:
	[hadoop@hadoopSlave1Test ~]$telnet 127.0.0.1 44444 
	Trying 127.0.0.1...
	Connected to 127.0.0.1.
	Escape character is '^]'.
	Hello World
	OK
八：collect控制台显示：
	15/04/08 00:31:23 INFO ipc.NettyServer: [id: 0x2928052c, /192.168.11.135:44370 => /192.168.11.132:60000] OPEN
	15/04/08 00:31:23 INFO ipc.NettyServer: [id: 0x2928052c, /192.168.11.135:44370 => /192.168.11.132:60000] BOUND: /192.168.11.132:60000
	15/04/08 00:31:23 INFO ipc.NettyServer: [id: 0x2928052c, /192.168.11.135:44370 => /192.168.11.132:60000] CONNECTED: /192.168.11.135:44370
	15/04/08 00:33:08 INFO sink.LoggerSink: Event: { headers:{} body: 48 65 6C 6C 6F 20 57 6F 72 6C 64 0D             Hello World. }

