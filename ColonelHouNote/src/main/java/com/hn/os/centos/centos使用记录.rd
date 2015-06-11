安装centos时，出现安装菜单时输入a， 然后输入linux nodmraid加车就可以安装了
===========================查看==========================================
查看版本cat /etc/redhat-release
cat /proc/meminfo 内存信息 
cat /proc/cpuinfo CPU信息 
free -m 内存占用信息 
sar 1 10 显示每秒钟显示，共10次CPU占用情况
===========================查看==========================================
配置代理：sudo vi yum.conf 加入： proxy = http://[]:[] 注意等号前后的空格。
挂载U盘：fdisk -l 看看U盘的设备 mount  /dev/sdg1   /mnt/usb_disk
//如果卸载不了:lsof | grep /mnt查看到bash的端口号kill -9杀掉
gcc安装：http://www.cnblogs.com/zhangtingkuo/archive/2013/04/06/3002982.html
查看centos版本： lsb_release -a
查看网卡信息 :  # dmesg | grep -i eth
＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
http://blog.csdn.net/huang_pin_fu/article/details/6857758
使用RAR：
wget http://www.rarsoft.com/rar/rarlinux-4.0.1.tar.gz 
tar -zxvf rarlinux-4.0.1.tar.gz
cd rar 
make
mkdir -p /usr/local/bin
mkdir -p /usr/local/lib
cp rar unrar /usr/local/bin
cp rarfiles.lst /etc
cp default.sfx /usr/local/lib
 
但是在运行命令rar时,出现下面这个问题,
rar: /lib/i686/nosegneg/libc.so.6: version `GLIBC_2.7' not found (required by rar)
解决办法：
cp rar_static /usr/local/bin/rar
先记住两个常用命令吧：
rar x yhcsh.rar //解压 yhcsh.rar 到当前目录
rar yhcsh.rar ./yhcsh/  //将 yhcsh 目录打包为 yhcsh.rar
================================================================
第一光驱弹出光盘：eject 
第一光驱装入光盘：eject -t
第二光驱弹出光盘：eject /dev/cdrom1
第二光驱装入光盘：eject /dev/cdrom1 -t
=================================================================
centos挂载DVD为源：
[root@hadoop~]# mount -t /dev/cdrom /media/cdrom
[root@hadoop /etc/yum.repos.d/]# mv CentOS-Base.repo CentOS-Base.repo.bak
[root@hadoop~]# vim /etc/yum.repos.d/CentOS-Media.repo
[c6-media]
name=CentOS-$releasever- Media
baseurl=file:///media/cdrom/
gpgcheck=1
enabled=1
:wq保存退出
清除原有的yum信息
[root@hadoop~]# yum clean all
[root@hadoop yum.repos.d]# yum list
===========================================配置代理上网=======================
编辑/etc/yum.conf，在最后加入proxy=http://username:password@proxy_ip:port/
Wget的代理设置 编辑/etc/wgetrc，在最后加入
http_proxy=http://username:password@proxy_ip:port/
ftp_proxy=http://username:password@proxy_ip:port/
=============================================================================
命令行提示符完全显示完整的工作目录名称 export PS1="[\u@\h \w]\$"  
===============================yum groupinstall 'Development Tools'==========
=> flex
=> gcc
=> redhat-rpm-config
=> strace
=> rpm-build
=> make
=> pkgconfig
=> gettext
=> automake
=> strace64
=> gdb
=> bison
=> libtool
=> autoconf
=> gcc-c++
=> binutils and all dependencies.
=============================================================================
查找并卸载java
[root@hadoop ~]$rpm -qa | grep java
[root@hadoop ~]$rpm -e --nodeps sun-javadb-common.....i386
查看端口进程：netstat -nap | grep 99000

-------------------
添加端口：
vim /etc/sysconfig/iptables
添加-A INPUT -m state --state NEW -m tcp -p tcp --dport 5432 -j ACCEPT
[hadoop@hadoop ~]$sudo service iptables restart
iptables：将链设置为政策 ACCEPT：filter                    [确定]
iptables：清除防火墙规则：                                 [确定]
iptables：正在卸载模块：                                   [确定]
iptables：应用防火墙规则：                                 [确定]
[hadoop@hadoop ~]$sudo service iptables save
iptables：将防火墙规则保存到 /etc/sysconfig/iptables：      [确定]
取消屏保：/etc/bashrc加入:setterm -blank 0
=======================================
sudo service network restart  重启网络
查看现在已经安装了那些软件包
rpm -qa
rpm -qa | wc -l
====================================
使用CentOS常用命令查看当前linux的版本
more /etc/redhat-release
================================
查看在线终端：w
查看用户登录历史：last
踢出用户：
=============设置代理====
YUM代理设置
编辑/etc/yum.conf，在最后加入
# Proxy
proxy=http://username:password@proxy_ip:port/
－－－－－－－－
Wget的代理设置
编辑/etc/wgetrc，在最后加入
# Proxy
http_proxy=http://username:password@proxy_ip:port/
ftp_proxy=http://username:password@proxy_ip:port/
－－－－－－－－－
系统全局代理
如果需要为某个用户设置一个系统级的代理，可以在~/.bash_profile中设置：
http_proxy="http://username:password@proxy_ip:port"
export_http_proxy
＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
修改hostname: /etc/sysconfig/network
添加用户useradd hadoop    
改密码passwd hadoop
添加sudo hadoop可执行权限  chmod u+w /etc/sudoers  添加hadoop  ALL=(ALL)       ALL
.bashrc
alias l='ls -all'
alias ..='cd ..'
alias ...='cd ../..'
export PS1="[\u@\h \w]\$"
===============================
tar xvJf  ***.tar.xz来解压

