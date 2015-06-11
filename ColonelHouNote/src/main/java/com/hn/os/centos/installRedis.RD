sudo yum install gcc-c++
sudo yum install -y tcl
wget http://download.redis.io/redis-stable.tar.gz
tar xvzf redis-stable.tar.gz
cd redis-stable
make
异常：
zmalloc.h:51:31: error: jemalloc/jemalloc.h: No such file or directory
异常原因：一些编译依赖或原来编译遗留出现的问题
解决方案：make distclean。清理一下，然后再make。


在make成功以后，会在src目录下多出一些可执行文件：redis-server，redis-cli等等。

方便期间用cp命令复制到usr目录下运行。
cp redis-server /usr/local/bin/
cp redis-cli /usr/local/bin/

然后新建目录，存放配置文件
mkdir /etc/redis
mkdir /var/redis
mkdir /var/redis/log
mkdir /var/redis/run
mkdir /var/redis/6379

 

在redis解压根目录中找到配置文件模板，复制到如下位置。
cp redis.conf /etc/redis/6379.conf

通过vim命令修改
daemonize yes
pidfile /var/redis/run/redis_6379.pid
logfile /var/redis/log/redis_6379.log
dir /var/redis/6379

最后运行redis：
$ redis-server /etc/redis/6379.conf
