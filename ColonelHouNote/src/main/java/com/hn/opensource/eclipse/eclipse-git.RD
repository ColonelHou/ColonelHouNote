安装git for eclipse
http://marketplace.eclipse.org/content/egit-git-team-provider#.VFs0ctx9d6c
2、在Eclipse中生public key, 并添加到GitHub Repository中。

在菜单栏依次打开window → preference → general → network connection → SSH2 → Key Management → generate RSA Key... → apply → save private key...
生成 SSH 的 public key在GitHub中通过：edit your profile -> ssh key -> Add SSH Key 添加SSH Key, 把上面生成的 public key 拷贝到这里，保存。


本地安装maven并在eclipse里配置到安装目录   指定user setting到maven的conf目录下的setting.xml文件并update
新建maven项目  点击team→share project，-->create repository
在项目上点右键–>team–>commit。commit是commit到本机的git库，而push才是同步到github。
在弹出窗口输入你的GitHub 用户名和邮箱
提交文件到本地，并推送到服务器
然后在github网址上新建此名称的项目
https://github.com/ColonelHou/GitHub.git

再回到eclipse 项目-Team-remote-push-refs/heads/master->Add spec ->force Update -finish  输入github用户名与密码


导出仓库中项目到本机
先在eclipse的仓库中从远程clonel到本地, 然后在eclipse file import git local就可以
更新先执行fetch然后执行rebase就完成了更新


C:\Users\Colonel.Hou\.gitconfig配置如下：
[user]
	name = Colonel.Hou
	email = edric_net_hou2008@163.com
[core]
    repositoryformatversion = 0
    filemode = false
    logallrefupdates = true
[branch "master"] 
	remote = origin 
	merge = refs/heads/master 
[remote "origin"]
	url = https://github.com/ColonelHou/GitHub.git
    fetch = +refs/heads/*:refs/remotes/origin/*
    
[http] 　　

    proxy = 127.0.0.1:8087 sslVerify = false

[https] 　　

    proxy = 127.0.0.1:8087 sslVerify = false
