# 官方tarball方式：
http://sourceforge.net/projects/pdsh/
wget http://cdnetworks-kr-2.dl.sourceforge.net/project/pdsh/pdsh/pdsh-2.26/pdsh-2.26.tar.bz2
tar -jxf pdsh-2.26.tar.bz2 && cd pdsh-2.26
./configure --with-ssh --without-rsh && make && make install

#yum安装（32位机器我没有找到源，64为机器可以使用rpmforce的源）
yum install pdsh 

/usr/bin/pdsh -R ssh -w node_[1-9] hostname
 
-R：指定传输方式，默认为rsh，本例为ssh，如果希望ssh传输需要另行安装pdsh-rcmd-ssh，如果希望ssh无密码访问需要提前配置好。
-w：指定待执行命令host，也可以这样配置，node_[1-9, 35,22]
command：本例为hostname

pdsh软件包还包括一个pdcp命令，可以将文件拷贝到一组机器上，用法如下:
pdsh -w [SSH_OR_RSH]:[USERNAME]@nodesrv[1,2-4,5] SRCFILE DES 
想将文件test.docx拷贝到上述机器的/doc当中，可执行下列命令:
pdcp -w ssh:root@srv[1,2-4,5] test.docx /doc