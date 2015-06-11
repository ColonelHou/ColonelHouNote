[root@~]# rpm -ev mysql-libs-5.1.66-2.el6_3.x86_64
error: Failed dependencies:
 libmysqlclient.so.16()(64bit) is needed by (installed) postfix-2:2.6.6-2.2.el6_1.x86_64
 libmysqlclient.so.16(libmysqlclient_16)(64bit) is needed by (installed) postfix-2:2.6.6-2.2.el6_1.x86_64
 mysql-libs is needed by (installed) postfix-2:2.6.6-2.2.el6_1.x86_64 情况

解决办法：rpm -e --nodeps mysql-libs-5.1.66-2.el6_3.x86_64

--nodeps --force