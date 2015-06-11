今天在测试环境使用yum安装，遇到一个问题：

Error: Cannot retrieve metalink for repository: epel. Please verify its path and try again


处理很简单，修改文件“/etc/yum.repos.d/epel.repo”， 将baseurl的注释取消， mirrorlist注释掉。即可。


参考URL： http://www.netpc.com.cn/593.html