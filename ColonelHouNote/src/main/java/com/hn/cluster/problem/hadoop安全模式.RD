安全模式主要是为了系统启动的时候检查各个DataNode上数据块的有效性，同时根据策略必要的复制或者删除部分数据块。

只要在Hadoop的目录下输入：
 
$bin/hdfs dfsadmin -safemode leave
 
也就是关闭Hadoop的安全模式，这样问题就解决了


enter - 进入安全模式
leave - 强制NameNode离开安全模式
get   - 返回安全模式是否开启的信息
wait  - 等待，一直到安全模式结束。


或者
增加磁盘然后执行wait命令：hdfs dfsadmin -safemode wait