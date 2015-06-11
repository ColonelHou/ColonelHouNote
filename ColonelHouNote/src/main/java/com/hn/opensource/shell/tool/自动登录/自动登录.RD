


#前提要安装了sudo apt-get install expect
centos:
把expect-5.43.0.tar.gz和tcl8.4.11-src.tar.gz复制到soft目录下, 
       解压tcl8.4.11-src.tar.gz 
       tar –xvf tcl8.4.11-src.tar.gz 
       解压后将创建tcl8.4.11 文件夹 

       解压expect-5.43.0.tar.gz 
       tar –xvf expect-5.43.0.tar.gz 
第4步：安装tcl 
       进入/soft/tcl8.4.11/unix 目录 
        a.执行sed -i "s/relid'/relid/" configure 
        b.执行./configure --prefix=/expect 
        c.执行make 
        d.执行make install 
        e.执行mkdir -p /tools/lib 
        f.执行cp tclConfig.sh /tools/lib/ 
        g. 将/tools/bin目录export到环境变量 
           tclpath=/tools/bin 
           export tclpath 

第5步：安装Expect 
        进入/soft/expect-5.43目录 
        执行./configure --prefix=/tools --with-tcl=/tools/lib --with-x=no 
        如果最后一行提示： 
        configure: error: Can't find Tcl private headers 
        需要添加一个头文件目录参数 
        --with-tclinclude=../tcl8.4.11/generic,即 
        ./configure --prefix=/tools --with-tcl=/tools/lib --with-x=no --with-tclinclude=../tcl8.4.11/generic 
        ../tcl8.4.11/generic 就是tcl解压安装后的路径，一定确保该路径存在 
        执行make 
        执行make install 
        编译完成后会生在/tools/bin内生成expect命令 
        执行/tools/bin/expect出现expect1.1>提示符说明expect安装成功. 

第6步：创建一个符号链接 
        ln -s /tools/bin/expect /usr/bin/expect 
        查看符号连接 
        ls -l /usr/bin/expect 
        lrwxrwxrwx 1 root root 17 06-09 11:38 /usr/bin/expect -> /tools/bin/expect 

        这个符号链接将在编写expect脚本文件时用到，例如在expect文件头部会指定用于执行该脚本的shell 
        #!/usr/bin/expect 





http://bluethink.iteye.com/blog/1079044
#!/usr/bin/expect
set password root
spawn mysql -uroot -p
expect "password:"
send  "$password\r"
#登录进去后不退出/交互模式
interact
#不交互/非交互模式
expect eof
exit 
#！/bin/bash
xxxx
xxx
绝对路经cp.exp param1 param2
xxx

cp.exp
#!/usr/bin/expect -f
set ip 192.168.13.99
set password 123456
set timeout 10
 set ip [lindex $argv 0 ]    //接收第一个参数,并设置IP
 set password [lindex $argv 1 ]  //接收第二个参数,并设置密码
spawn ssh hadoop@$ip
expect {
"*yes/no" { send "yes\r"; exp_continue}
"*password:" { send "$password\r" }
}
interact

《Expect 教程中文版 [较好的交互式工具] 》
问题：expect在crontab下执行失败的问题
http://blog.csdn.net/huli870715/article/details/8036380
