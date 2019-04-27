## 安装

1.官网地址：<https://git-scm.com/>

2.下载安装

​	选择不带中文和空格的目录

​	1).双击安装程序

​	2).Select Destination Location选择程序的安装目录

​	3).Select Comonents

​	![](https://github.com/sundyyh/study/blob/master/imgs/git_install_select_components.jpg)

​		说明：

​		（1）图标组件(Addition icons) : 选择是否创建桌面快捷方式。

​		（2）桌面浏览(Windows Explorer integration) : 浏览源码的方法，使用bash 或者 使用Git GUI工具。

​		（3）关联配置文件 : 是否关联 git 配置文件, 该配置文件主要显示文本编辑器的样式。

​		（4）关联shell脚本文件 : 是否关联Bash命令行执行的脚本文件。

​		（5）使用TrueType编码 : 在命令行中是否使用TruthType编码, 该编码是微软和苹果公司制定的通用编码。

​	4).Select Start Menu Folder:开始菜单快捷方式目录：设置开始菜单中快捷方式的目录名称, 也可以选择不在开始菜单中创建快捷方式

​	5). Choosing the default editor used by Git:选择一个编辑器作为Git的默认编辑器，选择一个你自己正在使用的编辑器，我使用的是Notepad++，如果没有的话，自己下一个，编辑器安装还是很简单的(Use Vim-----)。

​	6).**设置环境变量**

​		选择使用什么样的命令行工具，一般情况下我们默认使用Git Bash即可：

​		（1）Git自带：使用Git自带的Git Bash命令行工具。

​		（2）系统自带CMD：使用Windows系统的命令行工具。

​		（3）二者都有：上面二者同时配置，但是注意，这样会将windows中的find.exe 和 sort.exe工具覆盖，如果不懂这些尽量不要选择。

![](https://github.com/sundyyh/study/blob/master/imgs/git-install-path-environment.jpg)

​	7).Choosing HTTPS transport backend 选择HTTPS连接库，这里可以选择默认，然后Next

​	8).Configuring the line ending conversions

![](https://github.com/sundyyh/study/blob/master/imgs/git_install_configuring_the_line_ending_conversions.png.jpg)
		配置行结束符，选择第一项，意思是"取出文件时使用windows风格，提交文件时使用unix风格"

​		选择提交的时候换行格式

​		（1）检查出windows格式转换为unix格式：将windows格式的换行转为unix格式的换行再进行提交。

​		（2）检查出原来格式转为unix格式：不管什么格式的，一律转为unix格式的换行再进行提交。

​		（3）不进行格式转换 : 不进行转换，检查出什么，就提交什么。

​	9）Configuring the terminal emulator to use with Git Bash选择终端模拟器，选择第一个选项，然后点击Next，这样配置后git bash的终端比较易用。(默认 Use-MinTTY)

​	10）.Configuring extra options (使用默认)

## 工作区（Working Directory）

就是你在电脑里能看到的目录

## 暂存区（缓存区）

英文叫stage, 或index。一般存放在".git目录下" 下的index文件（**.git/index**）中，所以我们把暂存区有时也叫作索引（index）

## 版本库

工作区有一个隐藏目录.git，这个不算工作区，而是Git的版本库。**git中的.git/refs/heads/master是分支，是版本**
![版本库图片](https://github.com/sundyyh/study/blob/master/imgs/git_branch_info.jpg)

下面这个图展示了工作区、版本库中的暂存区和版本库之间的关系：
![工作区、版本库中的暂存区和版本库之间的关系](https://github.com/sundyyh/study/blob/master/imgs/git_install_configuring_the_line_ending_conversions.png)

图中左侧为工作区，右侧为版本库。在版本库中标记为"index" 的区域是暂存区（stage,index），标记为 "master"的是 master 分支所代表的目录树。

在工作区新建文件   ----->git add提交到暂存区------>git commint 到本地库



## git命令行操作

	##### 本地库操作

##### 	1.本地初始化

​		**git init**

​		使用 Git Bash Here打开git命令行工具进入要git管理的目录使用git init，成功后会在改目录下创建.git目录（在linux中以.开头的文件和目录是隐藏的资源，使用ls -lA 命令查看，）

​	.git 目录中存放的是本地库相关的子目录和文件，不要删除和随便修改

##### 	2.设置签名

​		形式：

​			用户名：tom

​			Email地址：sundy@qq.com

​		作用：区分不同开发人员的身份

​		辨析：这里设置的签名和登录远程库（代码托管中心）的账号、密码没有任何关系

​		命令：

​			项目级别/仓库级别： 仅在当前本地库范围内有效

​				git **config** user.name tom_pro

​				git **config** user.email sun@qq.com

​			系统用户级别：登录当前操作系统的用户范围

​				git **config** **--global** user.name tom_glb

​				git **config** **--global** user.email sun@qq.com

​			项目级别查看设置好后的签名：cat  .git/config

```shell
$ cat config
[core]
        repositoryformatversion = 0
        filemode = false
        bare = false
        logallrefupdates = true
        symlinks = false
        ignorecase = true
[user]
        name = tom_pro
        email = sun@qq.com

```



```shell
cd ~：回到用户家目录
ls -lA|less :查看隐藏参数 保存在~/.gitconfig文件
cat .gitconfig
[user]
        name = tom_glb
        email = sun_glb@qq.com
```



​			优先级：就近原则（项目级别优先于系统用户级别，二者都有采用项目级别的签名），如果只有系统用户级别的签名，就以系统用户级别的签名为准，二者都没有不允许

##### 	2.基本操作

​		**git status**

```shell
$ git status
On branch master

No commits yet

nothing to commit (create/copy files and use "git add" to track)
$ vim good.txt	#创建文件

Administrator@DESKTOP-GJAFBAK MINGW64 ~/Desktop/git (master)
$ git status
On branch master

No commits yet

Untracked files:
  (use "git add <file>..." to include in what will be committed)

         <font color=#f00>good.txt</font>

nothing added to commit but untracked files present (use "git add" to track) #未追踪的文件存在（使用git add命令去追踪它【即包含到暂存区】）

Administrator@DESKTOP-GJAFBAK MINGW64 ~/Desktop/git (master)
$ git add good.txt
warning: LF will be replaced by CRLF in good.txt. #CRLF是Carriage-Return Line-Feed的缩写，意思是回车换行，就是回车(CR, ASCII 13, \r) 换行(LF, ASCII 10, \n)
The file will have its original line endings in your working directory.

Administrator@DESKTOP-GJAFBAK MINGW64 ~/Desktop/git (master)
$ git status
On branch master

No commits yet

Changes to be committed:
  (use "git rm --cached <file>..." to unstage)

        <font color=#0f0>new file:   good.txt</font>


Administrator@DESKTOP-GJAFBAK MINGW64 ~/Desktop/git (master)

```



##### 	3.分支管理

##### 远程库操作

