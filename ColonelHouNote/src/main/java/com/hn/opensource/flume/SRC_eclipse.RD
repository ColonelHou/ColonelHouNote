wget http://archive.apache.org/dist/flume/1.5.2/apache-flume-1.5.2-src.tar.gz
tar -xvf apache-flume-1.5.2-src.tar.gz
cd apache-flume-1.5.2-src
touch eclipse.sh
#!/bin/sh
mvn clean compile
mvn eclipse:clean
mvn eclipse:eclipse
chmod +x eclipse.sh
nohup ./eclipse & 
前提是要配置好maven的



org.apache.flume.sink.hdfs.HDFSEventSink类中
private static final String defaultFileName = "FlumeData";配置默认文件名