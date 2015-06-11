查看在线用户
[root@hadoopSlave1Test /var/log/httpd]$w
 00:38:20 up 6 days,  1:03,  3 users,  load average: 0.11, 0.03, 0.01
USER     TTY      FROM              LOGIN@   IDLE   JCPU   PCPU WHAT
hadoop   tty1     :0               31Mar15  6days  1:07   0.01s pam: gdm-password
hadoop   pts/2    hadoopmastertest Wed23    0.00s 15:22   0.01s sshd: hadoop [priv]
hadoop   pts/3    hadoopmastertest 23:24   29:13   0.17s  0.17s -bash
踢出用户：
[root@hadoopSlave1Test /var/log/httpd]$pkill -KILL -t pts/2
Hangup
[root@hadoopSlave1Test /var/log/httpd]$Connection to hadoopSlave1Test closed.

查看用户登录历史：
[hadoop@hadoopMasterTest ~]$last
hadoop   pts/4        :0.0             Thu Apr  2 01:01   still logged in   
hadoop   pts/3        :0.0             Wed Apr  1 23:24   still logged in   
hadoop   pts/2        :0.0             Wed Apr  1 23:24   still logged in   
hadoop   pts/1        :0.0             Wed Apr  1 23:23   still logged in   
hadoop   pts/1        hadoopslave1test Wed Apr  1 23:21 - 23:21  (00:00)    
hadoop   pts/0        :0.0             Wed Apr  1 23:18   still logged in   
