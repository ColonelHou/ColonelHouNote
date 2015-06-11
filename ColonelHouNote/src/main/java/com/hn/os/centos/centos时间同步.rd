修改同步服务器的配置文件sudo /etc/ntp.conf ，删除所有的内容，添加
restrict default nomodify
server  127.127.1.0     # local clock
fudge   127.127.1.0 stratum 8
重启ntpd: sudo /etc/init.d/ntpd restart
等待ntp服务器自身同步完成
执行：watch ntpq -p后观察这个reach需要超过17在其他机器ntpdate同步
Every 2.0s: ntpq -p                                   Thu Sep 18 22:55:51 2014

     remote           refid      st t when poll reach   delay   offset  jitter
==============================================================================
*LOCAL(0)        .LOCL.           8 l   40   64  377    0.000    0.000   0.000
在其它机器上关闭ntp后执行同步:
sudo /etc/init.d/ntpd stop
[hadoop@hadoop ~]$sudo ntpdate -q 192.168.1.75
或者
[hadoop@hadoop ~]$sudo ntpdate 192.168.1.75
server 10.3.1.75, stratum 9, offset -0.003526, delay 0.02568
18 Sep 22:55:20 ntpdate[13907]: adjust time server 10.3.1.75 offset -0.003526 sec

