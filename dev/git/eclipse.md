### Eclipse操作

1.工程初始化为本地库

​	工程->右键->Team->Share Project->Git->勾选Use create repository in parent folder project->选中工程名称->Create Repository ->Finish

2.工程级别设置签名

​	Preferences->Team->Git->Configuration->Add Entry->key输入user.name	value输入user->Add Entry->key输入user.email 	value

输入sun@qq.com

3.文件图标Preferences->Team->Git->Lab Decorations

4.Eclipse中忽略文件（eclipse为了管理我们创建的工程而维护的文件，和开发的代码没有直接关系。最好不要在Git中进行追踪，也就是把它们忽略）

​	Eclipse特定文件

​	**.classpath文件**

​	**.project文件**

​	**.settings目录下所有文件**

​	同一个团队很难保证大家使用相同的IDE工具，而IDE工具不同时，相关的工程特定文件就有可能不同。

5.GitHub官网样例文件

​	https://github.com/github/gitignore

​	https://github.com/github/gitignore/blob/master/Java.gitignore

6.在家目录创建java.gitignore文件

​	.classpath

​	.project

​	.settings

​	target

7.在~/.gitconfig文件中引入上述文件（在git->Configuration中就可以看到excludesfile）

​	[core]

​		excludesfile=C:**/**Users/Administrator/Java.gitignore(一定使用正斜线，linux使用正斜线处理路径)

8.推送到远程

​	Team->Remote->Push->URI输入远程地址....输入用户名和密码->Next->Add All Branches Spec->Next

9.克隆工程

​	Import-> Git->Projects from Git->Next->Clone URI->URI输入地址，输入用户和密码->Next->Local Destination界面修改工作区目录->Browse修改后保存->Next（Select a wizard to use importing projects）->选择importas general project->Next->Finish

​	项目右键 configure->Convert to Maven Project

