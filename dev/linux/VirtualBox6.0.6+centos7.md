# VirtualBox6.0.6+centos7

## 1.准备

​	虚拟机软件： VirtualBox

​	系统iso版本：CentOS-7-x86_64-Everything-1804

​	虚拟机软件下载地址: https://www.virtualbox.org/wiki/Downloads

​	操作系统下载地址：https://www.centos.org/download/

​	建议下载everything版本，其他的版本请根据自身需要选择。

##### 	A.VirtualBox6.0.6安装

​		虚拟机软件下载地址: https://www.virtualbox.org/wiki/Downloads   本次测试使用6.0.6版本 https://download.virtualbox.org/virtualbox/6.0.6/VirtualBox-6.0.6-130049-Win.exe

##### 	B.CentOS下载

​		（Community Eeterprise Operating System）是Linux发行版之一，它是来自于Red Hat Enterprise Linux依照开放源代码规定释出的源代码所编译而成。由于出自同样的源代码，因此有些要求高度稳定性的服务器以CentOS替代商业版的Red Hat 		Enterprise Linux使用。两者的不同，在于CentOS并不包含封闭源代码软件

​		CentOS下载地址：

​		阿里云开源镜像站：<http://mirrors.aliyun.com/>

​		Linux系统版本：CentOS_7（64位，要和电脑系统的位数保持一致，我这里下载的是：<https://mirrors.aliyun.com/centos/7/isos/x86_64/CentOS-7-x86_64-Minimal-1810.iso>，迷你版，没有桌面系统，纯命令模式；可以根据自己的需求下载对应		版本）

### 2.新建虚拟机

1. 在虚拟机软件界面选择‘新建’
2. 名称随便起，但是建议使用发行版名称 + 版本号 + 用途，例如‘CentOS 7 学习’。
3. 类型选择‘Linux’
4. 版本选择RedHat（根据电脑情况选择32或者64）
5. （内存大小）内存大小，安装用户界面建议2G（2048M），不安装1G（1024M）即可，有其他需要，请自行调节，点击‘下一步’
6. （虚拟硬盘）然后下一步。
7. （虚拟硬盘文件类型）直接点击创建
8. （存储在物理硬盘上）没有特殊需要，直接下一步
9. （文件位置和大小）下一步
10. 选择虚拟磁盘创建位置和虚拟磁盘大小，安装桌面环境，建议最少分配20G，点击创建

## 3.安装系统

1. 单机新建的虚拟机，点击设置按钮

2. 进入 存储->没有碟片，点击‘分配光驱’右侧的碟片图标，选择‘选择一个虚拟光盘文件’，之后选择下载的系统iso文件，之后点击确定回到主界面。

3. 选择新建的虚拟机，点击‘启动’按钮，等待虚拟机开机，会自动进入系统安装界面
4. 用上下选择键选择第一个‘Install CentOS 7’，等待几秒进入安装引导程序。
5. 选择你想要使用的语言，如果可以，请尽量使用英语，教程将使用英语界面，点击‘Continue’
6. 选择‘SOFTWARE SELECTION’
7. 选择’INSTALLATION DESTINATION‘，然后直接’Done‘即可，返回主界面，等待选项恢复正常。
8. 选择INSTALLATION DESTINATION ，然后直接’Done‘即可
9. 选择’Begin Installation'，开始安装。
10. USER SETTINGS这里两个可配置项目，一个是设置root密码，一个是添加用户，建议添加一个非管理账户用来日常使用。

