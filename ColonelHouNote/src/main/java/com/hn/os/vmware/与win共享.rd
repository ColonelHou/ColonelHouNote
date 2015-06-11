第一步：cp  :/run/media/root/VMware Tools/VMwareTools-9.6.2-1688356.tar.gz  /tmp，把这个文件拷贝到tmp下

第二步：cd  /tmp进入临时目录，ls 查看刚才的文件是否在这个目录下
第三步：tar  zxvf  VMwareTools-5.5.0-18463.tar.gz 解压这个文件
第四步：cd  vmware-tools-distrib进行vmware-tools-distrib目录
第五步：./vmware-install.pl 执行这个文件，出现提示就回车