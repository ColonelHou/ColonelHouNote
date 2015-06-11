  root@debian:~# vim .bashrc
   19 alias l='ls -all'
   20 alias ..='cd ..'
   21 alias ...='cd ../..'
  root@debian:~# source .bashrc
  
  
  
第一：到bin目录下 创建l文件
第二：给l文件添加权限chmod u+x l
第三：编写l文件如下：

  #!/bin/bash
  ls -all
  保存
第四：以后我们执行l命令时相当于执行ls -all命令