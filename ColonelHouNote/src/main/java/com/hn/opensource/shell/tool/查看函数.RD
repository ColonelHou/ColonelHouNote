下载taglist_16.zip http://download.csdn.net/detail/mybelief321/5683477
cd ./vim
unzip taglist_16.zip
vim
":helptags ~/.vim/doc/"
下载ctags-5.5.tar.gz http://download.chinaunix.net/down.php?id=3567&ResourceID=1919&site=1
tar -xvf ctags-5.5.tar.gz
cd ctags-5.5
sudo  ./configure
sudo make
sudo make install
然后可以使用如下vim命令：
:Tlist
CTRL+WW切换窗口


在taglist窗口中，可以使用下面的快捷键：
<CR>          跳到光标下tag所定义的位置，用鼠标双击此tag功能也一样
o             在一个新打开的窗口中显示光标下tag
<Space>       显示光标下tag的原型定义
u             更新taglist窗口中的tag
s             更改排序方式，在按名字排序和按出现顺序排序间切换
x             taglist窗口放大和缩小，方便查看较长的tag
+             打开一个折叠，同zo
-             将tag折叠起来，同zc
*             打开所有的折叠，同zR
=             将所有tag折叠起来，同zM
[[            跳到前一个文件
]]            跳到后一个文件
q             关闭taglist窗口
<F1>          显示帮助 