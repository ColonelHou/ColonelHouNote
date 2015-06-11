原来出现这个错误原因（httpd.exe: 
Could not reliably determine the server's fully qualified domain name, 
using 192.168.1.111 for ServerName）

是因为DNS没配置好. 如果不想配置DNS, 就在httpd.conf , 在最前加入 ServerName localhost:80 即可