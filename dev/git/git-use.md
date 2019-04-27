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
		配置行结束符，选择第一项，意思是“取出文件时使用windows风格，提交文件时使用unix风格

​		选择提交的时候换行格式

​		（1）检查出windows格式转换为unix格式：将windows格式的换行转为unix格式的换行再进行提交。

​		（2）检查出原来格式转为unix格式：不管什么格式的，一律转为unix格式的换行再进行提交。

​		（3）不进行格式转换 : 不进行转换，检查出什么，就提交什么。

​	9）Configuring the terminal emulator to use with Git Bash选择终端模拟器，选择第一个选项，然后点击Next，这样配置后git bash的终端比较易用。(默认 Use-MinTTY)

​	10）.Configuring extra options (使用默认)