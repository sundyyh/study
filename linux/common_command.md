##  关机&重启命令

​	shutdown -h now  	表示立即关机

​	shutdown  -h 1		一分钟后关机

​	shutdown  -r		立即重启

​	halt	直接使用，等效于关机

​	reboot	现在重启计算机

​	sync	把内存的数据同步到磁盘

​	注意：当我们关机或者重启是，都应该先执行以下sync命令，把内存的数据写入磁盘，防止数据丢失

## 用户登录和注销

​	登录时尽量少的用root账号登录，因为它是系统管理员，最大的权限，避免操作失误，可以利用普通用户登录，登录后再用“**su** -用户名”命令来切换成系统管理员

​	在提示符下输入logout即可注销用户

​	**logout**   退出（logout在图像运行级别无效）

## 用户管理

​	说明：

​		1.Linux系统是一个多用户多任务的操作系统，任何一个要使用系统资源的用户，都必选首先向系统管理员申请一个账号，然后以这个账号的身份进入系统

​		2.Linux的用户至少属于一个组

​	**添加用户**

```shell
$ useradd [选项] 用户名 #中括号可以不写
```

​	user sundy：会创建一个和sundy同名的组，并将sundy添加到sundy组中，同时会自动创建和用户名同名的家目录，可以通过useradd -d 指定目录 新的用户名，给新创建的用户指定家目录

​	**指定或修改密码**：

```shell
$ passwd 用户名
```

​	**删除用户**

```shell
userdel 用户名 #该方法会保留家目录
userdel -r 用户名 #同时删除家目录
```

​	**查询用户信息**

```shell
$ id 用户名
[root@localhost home]# id sundy
uid=1000(sundy) gid=1000(sundy) groups=1000(sundy)

```

uid=用户id号 ；gid=所在组的id号；groups=组名称

​	**切换用户**

```shell
$ su - 用户名 （-可以不用写）
```

​	返回到原来用户：**exit**

## 用户组

​	介绍：类似于角色，系统可以对有共性的多个用户进行统一的管理

​	**添加组**

```shell
groupadd 组名 #添加组
groupdel 组名 #删除组
```

​	**添加用户时直接添加上组**

```shell
$ useradd -g 用户组 用户名
```

​	**修改用户组**

```shell
$ usermod -g 用户组 用户名
```

**用户和组的相关文件**

​	**/etc/passwd文件**：用户（user）的配置文件，记录用户的各种信息

​		每行的含义->用户名：口令：用户标识号：组标识号：注释性描述：主目录：登录Shell

​	/etc/shadow:口令的配置文件（密码和登录信息，是加密）

​		每行含义->登录名：加密口令：最后一次修改时间：最小时间间隔：最大时间间隔：警告时间：不活动时间：失效时间：标志

​	**/etc/group文件**：组（group）的配置文件，记录linux包含的组信息

​		每行含义->组名：口令：组标识号：组内用户列表

#### 指定运行级别

0： 关机

1： 单用户【找回丢失密码,因为该级别不需要密码进入】

2： 多用户状态无网络服务

**3： 多用户状态有网络服务  命令行模式**

4： 系统未使用保留给用户

5： GUI（图形桌面 模式）

6 ： 重启

​	常用运行级别是3和5，要修改默认的运行级别可修改文件：/etc/inittab的 **id：5：initdefault**

​	**查看运行级别**

​	1.who -r ： 显示当前运行级别以及系统当前时间 

​	2.runlevel ： 显示前一个运行级别（无则显示”N”） 、 当前运行级别

```shell
[sundy@localhost ~]$ who -r
         run-level 3  2019-04-30 01:50
[sundy@localhost ~]$ runlevel
N 3
```

​	切换运行级别

```shell
$ init 3  #[0123456]
```

​	案例：通过init来切换到不同级别，比如从5切换到3，然后关机

​		①init 3 ；②init 5；③init 0

**如何找回root密码**（进入到单用户模式，然后修改密码，因为单用户模式root不需要密码）

​	1.首先，打开centos7，在选择进入系统的界面按“e”进入编辑页面

​	2.然后按向下键，找到以“Linux16”开头的行，在该行的最后面输入“init=/bin/sh”

​	3.接下来按“ctrl+X”组合键进入单用户模式

#### 帮助命令

​	**man** 获得帮助信息

​	语法：man [命令或配置文件] （功能描述：获取帮助信息）

```shell
$ man ls #获取ls命令的帮助信息
```

​	**help** 指令

​		语法：help 命令 （功能描述：获取**shell**内置命令的帮助信息）

```shell
$ help cd
```

#### 文件目录类

###### 	pwd指令

​		基本语法：pwd	（功能描述：显示当前工作目录的绝对路径Print the name of the current working directory）

##### ls指令

​	语法： ls [选项]	[目录或文件]

​	常用参数

​		-a：显示当前目录所有的文件和目录，包括隐藏的（all）

​		-l：以列表的方式显示信息（use a long listing format）

```shell
[sundy@localhost ~]$ ls -l
total 1
-rw-rw-r--. 1 sundy sundy 326 Apr 29 22:39 Test.java
```

#### 	cd命令

​		语法：cd [参数]	（功能描述：切换到指定目录）

​		常用参数

​		**cd ~**  或**cd**     回到自己的家目录

​		**cd ..**       回到当前目录的上一级目录

​		**绝对路径**：即从根目录（/）开始定位

​		**相对路径**：从当前目录开始定位到需要的目录去

​		如当前目录为/root，去/home目录可使用 ../home或者/home

```shell
$ cd ../../root
$ cd /home/sundy
$ cd #回家目录
$ cd ~ #回家目录
$ cd .. #上级目录
```

#### mkdir（make directories创建目录）

​	语法：mkdir 【选项】 要创建的目录

​	常用选项

​		-p ：创建多级目录（parents）

```shell
$ mkdir -p a/b/c
```

#### rmdir(remove empty directories)

​	语法：rmdir 【选项】要清除的空目录（目录下有内容时无法删除）

```shell
$ rmdir dog
```

​	**删除非空目录使用rm -rf /home/dog**

#### touch指令（创建空文件）

​	语法：touch 文件名称

```shell
$ touch hello.txt
 touch 1.txt 2.txt  #同时创建两个文件
```

#### cp指令（copy files and directories）

​	语法：**cp 【选项】 source dest**

​	常用选项

​		-r：递归复制整个文件夹 --recursive【copy directories recursively】

```shell
$ cp aaa.txt bbb/ #将当前目录下aaa.txt文件拷贝到当前面目录的bbb这个目录下，有相同文件会提示是否覆盖
$ cp -r test/ dog/
$ \cp -r test/ dog/ #第二次copy，会提示是否覆盖，使用反斜杠 \强制覆盖不提示
```

#### rm指令（remove files or directories）

​	语法：rm 【选项】 要删除的文件或目录

​	常用选项

​		-r：递归删除整个文件夹

		-f：强制删除不提示（--force； ignore nonexistent files and arguments, never prompt）
```shell
$ rm hello.txt
$ rm -rf  bbb/ #删除目录

```

#### mv指令（移动文件与目录或重命名）

​	语法：

​		mv oldNameFile newNameFile（重命名）

​		mv /temp/movefile /targetFolder(移动文件)

```shell
$ mv aaa.txt bbb.txt #重命名
$ mv a.txt /home/sundy #将当前目录a.txt文件移动到...
```

#### cat 指令（concatenate files and print on the standard output只读方式查看文件内容）

​	语法：cat 【选项】 要查看的文件

​	常用选项

​		-n ：显示行号

```shell
$ cat -n /etc/profile
$ cat -n /etc/profile | more #|管道命令加more 分页显示
```

#### more指令

​	more指令是一个基于VI编辑器的文本过滤器，它以全屏幕的方式按页显示文本文件的内容，more命令中内置了若干快捷键

​	语法：more 要查看的文件

| 操作            | 功能说明                         |
| --------------- | -------------------------------- |
| 空格键（space） | 向下翻一页                       |
| Enter           | 向下翻（一行）                   |
| q               | 立刻离开more，不再显示该文件内容 |
| ctrl+F          | 向下翻动一屏                     |
| ctrl+B          | 返回上一屏                       |
| =               | 输出当前的行号                   |
| ：f             | 输出文件名和当前行号             |

##### less指令

​	less指令用来分屏查看文件内容，它和more指令相似，但是比more指令更加强大，支持各种显示终端。less指令在显示文件内容时，并不是一次将整个文件加载之后才显示，而是根据显示需要加载内容，**对于显示大型文件具有较高的销量**

​	语法：less 要查看的文件

| 操作            | 功能说明                                         |
| --------------- | ------------------------------------------------ |
| 空格键（space） | 向下翻一页                                       |
| Enter           | 向下翻（一行）             	                     |
| [pagedown]      | 向下翻一页                                       |
| [pageup]        | 向上翻一页                                       |
| /字串           | 向下搜寻【字串】的功能，n：向下查找；N：向上查找 |
| ?字串           | 向上搜寻【字串】的功能，n：向下查找；N：向上查找 |
| q               | 离开less这个程序                                 |

##### >指令和>>指令

​	**>**输出重定向（会将原来的文件的内容覆盖）和**>>**追加（不会覆盖原来文件的内容，而是追加到文件的尾部）

​	基本语法

​	

| 语法              | 功能描述                        |
| ----------------- | ------------------------------- |
| ls -l >文件       | 列表的内容写入文件a.txt(覆盖写) |
| ls -al >>文件     | 列表的内容追加到文件a.txt的末尾 |
| cat 文件1>文件2   | 将文件1的内容覆盖到文件2        |
| echo "内容">>文件 |                                 |

```shell
$ ls -l > a.txt #将ls -l的显示的内容覆盖写入到a.txt文件，如果a.txt不存在就创建该文件
$ ls -al >>b.txt #将ls -al显示的内容追加到后面的文件中去
$ cal >>/home/mycal #将当前日历信息追加到/home/mycal文件中
```

#### echo指令

​	echo输出内容到控制台

​	语法：echo  [选项] [输出内容]

```shell
$ echo $PATH
/usr/local/bin:/usr/bin:/usr/local/sbin:/usr/sbin:/home/sundy/.local/bin:/home/sundy/bin

$ echo $PATH #输出环境变量
$ echo "Hello,world"#输出Hello,world
```

##### head指令

​	head用于显示文件的开头部分内容，默认情况head指令显示文件的前10行内容

​	语法：**head 文件** (查看文件头10行内容)

​	 	  **head -n 5 文件** (查看文件头5行内容，5可以是任意行数)

##### tail指令

tail用于输出文件中尾部的内容，默认情况下tail指令显示文件的前10行内容。
• 基本语法
	1) tail 文件 （功能描述：查看文件头10行内容）
	2) tail -n 5 文件 （功能描述：查看文件头5行内容， 5可以是任意行数）
	**3) tail -f 文件** （功能描述：实时追踪该文档的所有更新，**常用**）
• 应用实例
案例1: 查看/etc/profile 最后5行的代码
案例2: 实时监控 mydate.txt , 看看到文件有变化时，是否看到， 实时的追加日期 

```shell
$ tail -f mydate.txt
```

##### ln 指令

​	软链接也成为符号链接，类似于**windows里的快捷方式**， 主要存放了链接其他文件的路径
​	• 基本语法
​		ln -s [原文件或目录] [软链接名] （功能描述：给原文件创建一个软链接）
​	• 应用实例
​		案例1: 在/home 目录下创建一个软连接 linkToRoot，连接到 /root 目录
​		案例2: 删除软连接 linkToRoot 

```shell
[root@localhost home]$  ln -s /root linkToRoot
lrwxrwxrwx. l root root 6 3月   7 01：28 linkToRoot -> /root/
$ rm -rf linkToRoot #不要带斜杠,如linkToRoot/
```

​	• 细节说明
​		当我们使用pwd指令查看目录时，仍然看到的是软链接所在目录。 

#### history指令

​	查看已经执行过历史命令,也可以执行历史指令
​		• 基本语法
​			history （功能描述：查看已经执行过历史命令）
​		• 应用实例
​			案例1: 显示所有的历史命令
​			案例2: 显示最近使用过的10个指令。
​			案例3：执行历史编号为5的指令 

```shell
$ history
$ history 10
$ !5 #执行历史编号为5的指令
```

##### date指令-显示当前日期

• 基本语法
	1) date （功能描述：显示当前时间）
	2) date +%Y （功能描述：显示当前年份）
	3) date +%m （功能描述：显示当前月份）
	4) date +%d （功能描述：显示当前是哪一天）
	5) date "+%Y-%m-%d %H:%M:%S"（功能描述：显示年月日时分秒）
• 应用实例
	案例1: 显示当前时间信息
	案例2: 显示当前时间年月日
	案例3: 显示当前时间年月日时分秒 

```shell
[root@localhost home]# date +%Y
2019
[root@localhost home]# date +%m
05
[root@localhost home]# date +%d
06
[root@localhost home]# date "+%Y-%m-%d %H:%M:%S"    #+号不能少
2019-05-06 16:20:49
```

date指令-设置日期
	• 基本语法
		date -s 字符串时间
	• 应用实例
		案例1: 设置系统当前时间 ， 比如设置成 2019-05-06 15:35:40

```shell
[root@localhost home]# date -s "2019-05-06 15:35:40"
Mon May  6 15:35:40 EDT 2019
```

##### cal指令

​	查看日历指令
​	• 基本语法
​		cal [选项] （功能描述：不加选项，显示本月日历）
​	• 应用实例
​		案例1: 显示当前日历
​		案例2: 显示2020年日历 

```shell
[root@localhost home]# cal
      May 2019      
Su Mo Tu We Th Fr Sa
          1  2  3  4
 5  6  7  8  9 10 11
12 13 14 15 16 17 18
19 20 21 22 23 24 25
26 27 28 29 30 31

```

