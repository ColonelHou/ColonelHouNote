配置：
nginx:192.168.11.131
webserver1:192.168.11.135
webserver2:192.168.11.132
flume1:192.168.11.133
flume2:192.168.11.137
flume3:192.168.11.136

[hadoop@hadoopMasterTest /opt/wksp/tmp/data]$sudo vim /etc/nginx/nginx.conf
    upstream webservers{
        #server 192.168.11.135:8080 weight=1 max_fails=2 fail_timeout=2;
        #server 192.168.11.132:8080 weight=1 max_fails=2 fail_timeout=2;
        server 192.168.11.135:8080 weight=1;
        server 192.168.11.132:8080 weight=1;
    }
    server {
        location / {
            proxy_pass   http://webservers;
            #proxy_set_header X-Real-IP    $remote_addr;
            proxy_set_header  X-Real-IP  $remote_addr;
            #proxy_cache webserver;
            #proxy_cache_valid 200 10m;
        }
[hadoop@hadoopMasterTest /opt/wksp/tmp/data]$sudo /etc/init.d/nginx reload
shell-init: error retrieving current directory: getcwd: cannot access parent directories: No such file or directory
job-working-directory: error retrieving current directory: getcwd: cannot access parent directories: No such file or directory
job-working-directory: error retrieving current directory: getcwd: cannot access parent directories: No such file or directory
nginx: the configuration file /etc/nginx/nginx.conf syntax is ok
nginx: configuration file /etc/nginx/nginx.conf test is successful
Reloading nginx: job-working-directory: error retrieving current directory: getcwd: cannot access parent directories: No such file or directory
                                                           [  OK  ]

server1
1.flume-ng-log4jappender-1.5.2-jar-with-dependencies.jar到WEB项目lib下
log4j.logger.com.flume = info,flume
#-----\u6216\u8005\u4e3a log4j.logger.com.trace. = info,flume \uff0c\u8303\u56f4\u6269\u5927\u5230\u5305
log4j.appender.flume=org.apache.flume.clients.log4jappender.Log4jAppender
log4j.appender.flume.layout = org.apache.log4j.PatternLayout
log4j.appender.flume.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %p [%c{2}] - %m%n
#--\u63a5\u6536\u6570\u636e\u7684\u8fdc\u7a0b\u4e3b\u673aIP\uff0c\u6b64\u5904\u4e3a\u865a\u62df\u673a\u7684ip\u5730\u5740
log4j.appender.flume.Hostname=192.168.11.133
#--\u76d1\u542c\u6570\u636e\u53d1\u9001\u7684\u8fdc\u7a0b\u4e3b\u673a\u7aef\u53e3\u53f7\uff0c\u9700\u5f00\u901a\uff08web\u670d\u52a1\u5668\u7aef\u548c\u6570\u636e\u63a5\u6536\u670d\u52a1\u5668\u7aef\u8981\u5f00\u901a\uff09
log4j.appender.flume.Port=41414

server2.同一

flume1:
+------------------------------------------------------+
#name the  components on this agent
a1.sources  = r1
a1.sinks =  k1
a1.channels  = c1

#  Describe/configure the source
#//这里的数据源设置成netcat，后面将通过telnet传送信息
a1.sources.r1.type  = avro
a1.sources.r1.bind  = 192.168.11.133
a1.sources.r1.port  = 41414


# Use a  channel which buffers events in memory
a1.channels.c1.type  = memory
a1.channels.c1.capacity  = 1000
a1.channels.c1.transactionCapacity  = 100

# Describe  the sink
a1.sinks.k1.type  =avro
#//collect 的IP地址，注意
a1.sinks.k1.hostname=192.168.11.136
#//这里的接口要与collect source的接口一致。
a1.sinks.k1.port=60000 
# Bind the  source and sink to the channel
a1.sources.r1.channels  = c1
a1.sinks.k1.channel  = c1
+------------------------------------------------------+

flume2:
+------------------------------------------------------+
name the  components on this agent
a1.sources  = r1
a1.sinks =  k1
a1.channels  = c1

#  Describe/configure the source
#//这里的数据源设置成netcat，后面将通过telnet传送信息
a1.sources.r1.type  = avro
a1.sources.r1.bind  = 192.168.11.137
a1.sources.r1.port  = 41414


# Use a  channel which buffers events in memory
a1.channels.c1.type  = memory
a1.channels.c1.capacity  = 1000
a1.channels.c1.transactionCapacity  = 100

# Describe  the sink
a1.sinks.k1.type  =avro
#//collect 的IP地址，注意
a1.sinks.k1.hostname=192.168.11.136
#//这里的接口要与collect source的接口一致。
a1.sinks.k1.port=60000
# Bind the  source and sink to the channel
a1.sources.r1.channels  = c1
a1.sinks.k1.channel  = c1
+------------------------------------------------------+


flume3:
+------------------------------------------------------+
agent1.channels = ch1 ch2  
agent1.sources = source1  
agent1.sinks = hdfssink1 sink2  
#agent1.sinks = sink2  
agent1.source.source1.selector.type = replicating  

# Define a memory channel called ch1 on agent1  
agent1.channels.ch1.type = memory  
agent1.channels.ch1.capacity = 1000000  
agent1.channels.ch1.transactionCapacity = 1000000  
agent1.channels.ch1.keep-alive = 10  

agent1.channels.ch2.type = memory  
agent1.channels.ch2.capacity = 1000000  
agent1.channels.ch2.transactionCapacity = 100000  
agent1.channels.ch2.keep-alive = 10  

# Define an Avro source called avro-source1 on agent1 and tell it  
# to bind to 0.0.0.0:41414. Connect it to channel ch1.  
agent1.sources.source1.channels = ch1 ch2  
agent1.sources.source1.type = avro  
agent1.sources.source1.bind = 192.168.11.136
agent1.sources.source1.port = 60000
agent1.sources.source1.threads = 5  

# Define a logger sink that simply logs all events it receives  
# and connect it to the other end of the same channel.  
agent1.sinks.hdfssink1.channel = ch1  
agent1.sinks.hdfssink1.type = hdfs  
agent1.sinks.hdfssink1.hdfs.path = hdfs://hadoopMasterTest:9000/data/flume/ 
agent1.sinks.hdfssink1.hdfs.filePrefix = Terminal-log-%H-%M-%S  
agent1.sinks.hdfssink1.hdfs.useLocalTimeStamp = true  
agent1.sinks.hdfssink1.hdfs.writeFormat = Text  
agent1.sinks.hdfssink1.hdfs.fileType = DataStream  
agent1.sinks.hdfssink1.hdfs.rollInterval = 1800  
agent1.sinks.hdfssink1.hdfs.rollSize = 5073741824  
agent1.sinks.hdfssink1.hdfs.batchSize = 10000  
agent1.sinks.hdfssink1.hdfs.rollCount = 0  
agent1.sinks.hdfssink1.hdfs.round = true  
agent1.sinks.hdfssink1.hdfs.roundValue = 60  
agent1.sinks.hdfssink1.hdfs.roundUnit = minute  


#agent1.sinks.sink2.type = logger  
#agent1.sinks.sink2.sink.batchSize=10000  
#agent1.sinks.sink2.sink.batchTimeout=600000  
agent1.sinks.sink2.sink.rollInterval = 200
agent1.sinks.sink2.sink.directory=/usr/local/flume/data/
agent1.sinks.sink2.sink.fileName=accesslog  
agent1.sinks.sink2.type = file_roll
agent1.sinks.sink2.channel = ch2
+---------------------------------------------------------------------------------+