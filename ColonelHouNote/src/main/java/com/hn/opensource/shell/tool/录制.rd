录制屏幕
script -t 2> timing.log -a execute.log
回放
[hadoop@hadoop ]$scriptreplay timing.log execute.log

广播视频会话：
Terminal1：
mkfifo scriptfifo
Terminal2:
cat scriptfifo
Terminal1:
script -f scriptfifo
commands
不管你在Terminal1输入什么命令都会在Terminal2实时显示