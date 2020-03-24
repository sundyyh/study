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

​		**git status** :查看工作区，暂存区状态

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

        good.txt#红色

nothing added to commit but untracked files present (use "git add" to track) #未追踪的文件存在（使用git add命令去追踪它【即包含到暂存区】）


```

​		**git add <file>...**：将工作区的新建/修改添加到暂存区

```shell
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

        new file:   good.txt#绿色


Administrator@DESKTOP-GJAFBAK MINGW64 ~/Desktop/git (master)

```

​		**git rm --cached <file>...** 从暂存区撤回（后悔药------恢复成之前的状态）

​			会从index里面删除该文件，但是本地的文件还是保留

```shell
Administrator@DESKTOP-GJAFBAK MINGW64 ~/Desktop/git (master)
$ git rm --cached good.txt
rm 'good.txt'

Administrator@DESKTOP-GJAFBAK MINGW64 ~/Desktop/git (master)
$ git status
On branch master

No commits yet

Untracked files:
  (use "git add <file>..." to include in what will be committed)

        good.txt

nothing added to commit but untracked files present (use "git add" to track)

```

​		**git commit -m "commit message"  [filename]**	将暂存区的内容提交本地库

```shell
git commit good.txt
```

进入提交备注输入页面（vim编辑器中  ：set nu  显示行号）

```shell
  1
  2 # Please enter the commit message for your changes. Lines starting
  3 # with '#' will be ignored, and an empty message aborts the commit.
  4 #
  5 # On branch master
  6 #
  7 # Initial commit
  8 #
  9 # Changes to be committed:
 10 #       new file:   good.txt
 11 #
~

```
输入完成

```shell
$ git commit
[master (root-commit) 434a94a] My first commit.new file good.txt
 1 file changed, 2 insertions(+)
 create mode 100644 good.txt

Administrator@DESKTOP-GJAFBAK MINGW64 ~/Desktop/git (master)
$ git status
On branch master
nothing to commit, working tree clean

```

修改good.txt加入一行后

```shell
$ git status
On branch master
Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git checkout -- <file>..." to discard changes in working directory)

        modified:   good.txt

no changes added to commit (use "git add" and/or "git commit -a")

```

对已提交（已追踪）文件使用git add <file>...添加到暂存区或使用 git commit -a直接提交

```shell
$ git add good.txt
warning: LF will be replaced by CRLF in good.txt.
The file will have its original line endings in your working directory.

Administrator@DESKTOP-GJAFBAK MINGW64 ~/Desktop/git (master)
$ git status
On branch master
Changes to be committed:
  (use "git reset HEAD <file>..." to unstage)

        modified:   good.txt


```

使用git commit **-m** "My second commit,modify good.txt"使用**-m参数提交**，不需要进入vim编辑器写备注

```shell
$ git commit -m "My second commit,modify good.txt" good.txt
warning: LF will be replaced by CRLF in good.txt.
The file will have its original line endings in your working directory.
[master 760f5d6] My second commit,modify good.txt
 1 file changed, 1 insertion(+)

```

​	**git log**：查看历史记录(最完整，多屏显示控制方式：空格向下翻页，b向上翻页，q退出)

```
$ git log
commit 03a764b442f1be60cd392a490ff674da1741c959 (HEAD -> master)
Author: tom_glb <tom_glb@qq.com>
Date:   Sat Apr 27 15:00:19 2019 +0800

    test history

commit 760f5d6e6771e2ff57a272a50e486c4a4928a7ae
Author: tom_glb <tom_glb@qq.com>
Date:   Sat Apr 27 14:40:51 2019 +0800

    My second commit,modify good.txt

commit 434a94afbbb43d8769845202c35c0dc31a9e0b7d
Author: tom_glb <tom_glb@qq.com>
Date:   Sat Apr 27 14:20:46 2019 +0800

    My first commit.new file good.txt

```

​	**git log --pretty=online**：简洁查看历史记录 使用**--pretty=online**参数单行查看(命令太长可以用下一个)

```
$ git log --pretty=oneline
03a764b442f1be60cd392a490ff674da1741c959 (HEAD -> master) test history
760f5d6e6771e2ff57a272a50e486c4a4928a7ae My second commit,modify good.txt
434a94afbbb43d8769845202c35c0dc31a9e0b7d My first commit.new file good.txt

```

​	**git log --online**：查看历史记录 使用**--online**参数单行查看（该命令只能显示当前版本之前的信息）

```shell
$ git log --oneline
03a764b (HEAD -> master) test history
760f5d6 My second commit,modify good.txt
434a94a My first commit.new file good.txt

```

​	**git reflog**：移动指针时使用（HEAD@{移动到当前版本需要多少步}）

```shell
$ git reflog
03a764b (HEAD -> master) HEAD@{0}: commit: test history
760f5d6 HEAD@{1}: commit: My second commit,modify good.txt
434a94a HEAD@{2}: commit (initial): My first commit.new file good.txt

```

###### 前进后退（本质HEAD指针的移动）：三种操作方式

​		A.基于索引值操作[推荐]

```shell
$ git reflog
79f5583 (HEAD -> master) HEAD@{0}: commit: insert mmmmmmm
1982c06 HEAD@{1}: commit: insert lllllll
4e969b6 HEAD@{2}: commit: insert kkkkkkk
16250e0 HEAD@{3}: commit: insert jjjjjjj
82fd4ee HEAD@{4}: commit: insert iiiiiii
41a6357 HEAD@{5}: commit: insert hhhhhhh
56bd65c HEAD@{6}: commit: insert ggggg
e9fcb51 HEAD@{7}: commit: insert fffffff
03a764b HEAD@{8}: commit: test history
760f5d6 HEAD@{9}: commit: My second commit,modify good.txt
434a94a HEAD@{10}: commit (initial): My first commit.new file good.txt

```

​	**git reset --hard 索引值**

​	如果我们想回退到41a6357 HEAD@{5}: commit: insert hhhhhhh位置则使用



```shell
$ git reset --hard 16250e0
HEAD is now at 16250e0 insert jjjjjjj

Administrator@DESKTOP-GJAFBAK MINGW64 ~/Desktop/git (master)
$ git reflog
16250e0 (HEAD -> master) HEAD@{0}: reset: moving to 16250e0
79f5583 HEAD@{1}: commit: insert mmmmmmm
1982c06 HEAD@{2}: commit: insert lllllll
4e969b6 HEAD@{3}: commit: insert kkkkkkk
16250e0 (HEAD -> master) HEAD@{4}: commit: insert jjjjjjj
82fd4ee HEAD@{5}: commit: insert iiiiiii
41a6357 HEAD@{6}: commit: insert hhhhhhh
56bd65c HEAD@{7}: commit: insert ggggg
e9fcb51 HEAD@{8}: commit: insert fffffff
03a764b HEAD@{9}: commit: test history
760f5d6 HEAD@{10}: commit: My second commit,modify good.txt
434a94a HEAD@{11}: commit (initial): My first commit.new file good.txt

```



​		B.使用^符号:只能后退(几个^退几步)

```SHELL
$ git reset --hard HEAD^   
HEAD is now at 82fd4ee insert iiiiiii
$ git reset --hard HEAD^^^
```



​		C.使用~符号：git reset --hard HEAD~n  	表示后退n步

```shell
$ git reset --hard HEAD~4	#~回退步数

```

使用 **git help 命令** 查看文档 ex.	git help reset

**reset**命令的三个参数对比	

​	1.**--soft**：仅仅在本地库移动HEAD指针

​	2.**--mixed**：在本地库移动HEAD指针，重置暂存区

​	3.**--hard**：在本地库移动HEAD指针，重置暂存区，重置工作区

**diff **比较文件差异

​	**git diff [filename]**	：将工作区的文件和暂存区进行比较,不带文件名比较多个文件

​	**git diff [本地库中历史版本] [filename]**	：将工作区的文件和本地库历史记录比较

```shell
git diff HEAD appple.txt
git diff HEAD^ appple.txt
```



##### 	3.分支管理

```shell
$ git branch -v		#查看本地所有分支
$ git branch -a		#查看本地/远程所有分支(结果列表中前面标*号的表示当前使用分支)
$ git branch 		#查看当前使用分支(结果列表中前面标*号的表示当前使用分支)
$ git branch hot_fix	#创建hot_fix分支
$ git checkout remotes/origin/dev
 
```

创建分支：git branch 【分支名】

查看本地分支分支：git branch -v 或 git branch

切换分支：git checkout【分支名】

合并分支：git merge 【分支名】

​	1.切换到接受修改的分支上（被合并，增加新内容）git checkout【被合并分支名】

​	2.执行**merg**e命令：git merge 【有新内容分支名】

​	冲突解决

​		1.编辑文件，删除特殊符号

​		2.把文件修改到满意程度，保存退出

​		3.git add【冲突解决后的文件名】

​		4.git commit -m “日志信息” **此时不能带具体文件名**

##### 远程库操作

1.注册<https://github.com/>账号

2.初始化新的本地库

```shell
mkdir demo
cd demo
git init
vim test.txt
git add "test.txt"
git commit "init commit" test.txt
```

3.创建远程库（不需要和本地库名字一致）

​	登录[github](https://github.com/ ) ->[New repository](<https://github.com/new>)

4.在本地库创建远程库地址别名

​	**git remote -v** 查看有没有地址别名

​	**origin remote add origin** 地址	（创建地址别名）



```shell
$ git remote -v
#什么都没有

$ origin remote add origin https://github.com/sundyyh/study
$ git remote -v
origin  https://github.com/sundyyh/study (fetch)
origin  https://github.com/sundyyh/study (push)
```

5.推送

​	**git push origin** master（指定分支名）

​	弹出登录页面登录

6.克隆（自动执行下面3个步骤）

```shell
git clone 项目地址
```

​	1.完整的将远程库下载到本地

​	2.创建origin远程地址别名

​	3.初始化本地库

6.加入项目

​	在仓库项目（Code   Issues Pull requests   Projects Wiki Insights Settings）下 --->Settings--->Collaborations--->输入被邀请人账号--->Add collaborator--->被邀请人接受

新加入项目的人修改文件后提交本地仓库后，使用**git push origin master**  推送到远程仓库如果没加入项目会如下提示：

 git push https://github.com/sundyyh/study.git master 

remote: Permission to sundyyh/study.git denied to sundyyh. fatal: unable to access https://github.com/sundyyh/study.git: The requested URL returned error: 403



​	win10系统加入登录后提交就不需要账号密码（win10 帮我们记住密码了，如果想换账号密码： **控制面板\所有控制面板项\凭据管理器--->Windows 凭据--->普通凭证--->删除对应**）；

7.远程修改的拉取（fetch+merge）

​	**pull=fetch+merge**

​	**git fetch [远程库地址别名] [远程分支名]****

​	**git merge[远程库地址别名/远程分支名]**

​	fetch和pull对应远程都是读操作（不需要登录，和验证身份）

​	fetch后查看本地文件并没有改变，只是把远程的内容下载到本地，并没有改本地工作区的文件

```shell
git fetch origin master
```

​	**查看下载后的内容：**

```shell
git checkout origin/master#切换到下载后的master分支内容
git checkout master#切换到本地master分支
git merge origin/master#将远程master合并到本地master
```

8.远程修改的拉取（pull）

```shell
git pull origin master
```

9.[跨团队协作操作](https://www.bilibili.com/video/av49085612/?p=41)

#### 4.SSH免密登陆

​	win10 系统凭据管理帮我们保存用户名和密码，如果没有这个功能（如win7，其它系统）基于https的模式，每次操作都需要提供用户名和密码非常麻烦，使用SSH方式可以避免（局限只能为一个账号设置）

```shell
cd ~   #进入家目录
rm -r :ssh/  #删除以前创建的ssh目录删除
ssh-keygen -t rsa -C sun@qq.com #-C 后面为邮箱；后面后很多确认全部确认就行，这样就生成了一个.ssh目录
$ cd .ssh/

Administrator@DESKTOP-GJAFBAK MINGW64 ~/.ssh
$ ll
total 12
-rw-r--r-- 1 Administrator 197121 3243 7月  27  2018 id_rsa
-rw-r--r-- 1 Administrator 197121  742 7月  27  2018 id_rsa.pub
$ cat id_rsa.pub
ssh-rsa AAAAB3NzaC1yc2EAAnADAQABAAABAQC5X0IJ43hV5sdMT9WbitldbaR6DZ5KH3PQPuhVavqjUxGAZFxaCwX2SywNA9a95IR6sqgqkfN0Us8xYFJAl5FrBTn5i5jl8GrZS7z0sbVCyZ9xEyqFm+cidVPOpjhy9W0V4hCLPGiuHw6awFno84FMfB7X//BxD/+fdLkraBYaMgkF/fRo914h4EcqSPahQ1/WApgo9jnMeHo1/O2D2rpAV8c4oCBT2kXYAgmTPk3Sl+DB3NXj9jJjRj/fRG57Mu5qce/Fk sun@qq.com

```

 复制id_rsa.pub中内容

加入到[github](https://github.com/settings/ssh/new)(setting-->SSH and GPG Keys-->New SSH key)

 ```shell
git remote add origin_ssh git@github.com:sundyyh/study.git    #使用SSH创建远程库地址别名
git remote -v
origin_ssh git@github.com:sundyyh/study.git(fetch)
origin_ssh git@github.com:sundyyh/study.git(push)

git push origin_ssh master
 ```

