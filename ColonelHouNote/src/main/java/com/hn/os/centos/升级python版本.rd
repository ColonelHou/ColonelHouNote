http://www.jb51.net/article/51800.htm
首先下载源tar包
可利用linux自带下载工具wget下载，如下所示：
wget http://www.python.org/ftp/python/2.7.3/Python-2.7.3.tgz
下载完成后到下载目录下，解压
tar -zxvf Python-2.7.3.tgz
进入解压缩后的文件夹
cd Python-2.7.3
在编译前先在/usr/local建一个文件夹python27（作为python的安装路径，以免覆盖老的版本）
mkdir /usr/local/python2.7.3
在解压缩后的目录下编译安装
sudo ./configure --prefix=/usr/local/python2.7.3
sudo make
sudo make install
此时没有覆盖老版本，再将原来/usr/bin/python链接改为别的名字
sudo mv /usr/bin/python /usr/bin/python_old
再建立新版本python的链接
sudo ln -s /usr/local/python2.7.3/bin/python2.7 /usr/bin/python
这个时候输入
python
就会显示出python的新版本信息
Python 2.7.3 (default, Sep 29 2013, 11:05:02)
[GCC 4.1.2 20080704 (Red Hat 4.1.2-54)] on linux2
Type "help", "copyright", "credits" or "license" for more information.
>>>
第5步：修改yum配置文件 

#vim /usr/bin/yum 
把文件头部的#!/usr/bin/python改成#!/usr/bin/python2.4 //改为之前的老版本

以上改的好像动态库有问题了， 看一下以下的源码 编译安装
http://www.jb51.net/article/34012.htm
在64位系统中加如下进行配置
CFLAGS="-O3 -fPIC" ./configure 