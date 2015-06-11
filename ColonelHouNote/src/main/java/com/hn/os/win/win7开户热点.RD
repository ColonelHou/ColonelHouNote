管理员身份运行cmd -> netsh wlan set hostednetwork mode=allow ssid=mywifi key=g12345678
->
承载网络模式已设置为允许。
已成功更改承载网络的 SSID。
已成功更改托管网络的用户密钥密码。
->
然后再打开“网络和共享中心”--“更改适配器设置”看看是不是多了一项，
若果有多出的这一项“Microsoft Virtual WiFi Miniport Adapter”，那么说明你前边的设置是真确的。
->
设置Internet连接共享：在“网络连接”窗口中，右键单击已连接到Internet的网络连接，选择“属性”→“共享”，
勾上“允许其他······连接(N)”并选择“虚拟WiFi”
--确定之后，提供共享的网卡图标旁会出现“共享的”字样，表示“宽带连接”已共享至“虚拟WiFi。
开启无线网络：继续在命令提示符中运行：netsh wlan start hostednetwork
当然可以新建一个记事本文件复制：netsh wlan start hostednetwork然后另存为：启动WIFI热点.bat
下次开机的时候只需要以“管理员身份运行”就启动了。
附：显示无线网络信息命令：netsh wlan show hostednetwork