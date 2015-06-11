http://www.centoscn.com/image-text/config/2013/0805/1071.html 
可使用命令：
#yum install xinetd (注意在root下安装）
#yum install telnet-server(注意在root下安装）
#yum install telnet(注意在root下安装）
直接修改配置文件
vi /etc/xinetd.d/telnet
一般是这样子的：
# default: yes
# description: The telnet server servestelnet sessions; it uses \
#      unencrypted username/password pairs for authentication.
service telnet
{
       flags           = REUSE
       socket_type     = stream
       wait            = no
       user            = root
       server          =/usr/sbin/in.telnetd
       log_on_failure  += USERID
       disable         = yes
}
 
只需要将”disable= yes”改成” disable=no”
service xinetd restart