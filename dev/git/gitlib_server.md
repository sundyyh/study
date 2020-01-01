#### CentOS7 Gitlab服务器搭建

1.官网地址

​	首页：https://about.gitlab.com

​	安装说明：https://about.gitlab.com/install/

2.安装命令摘录

​	A,安装并配置必要的依赖项

​		CentOS 7.x开始，CentOS开始使用systemd服务来代替daemon，原来管理系统启动和管理系统服务的相关命令全部由systemctl命令来代替

​	

```shell
sudo yum install -y curl policycoreutils-python openssh-server##step1:安装基础包
sudo systemctl enable sshd ##step1:启动sshd ①设定服务开机启动
sudo systemctl start sshd ##②开启服务
sudo firewall-cmd --permanent --add-service=http
sudo systemctl reload firewalld
##安装postfix(发邮件)
sudo yum install postfix
sudo systemctl enable postfix
sudo systemctl start postfix
##添加GitLab包存储库(是一个可执行的脚本默认是ee 我们可以改成ce)
curl https://packages.gitlab.com/install/repositories/gitlab/gitlab-ee/script.rpm.sh | sudo bash
#接下来，安装GitLab包。将https://gitlab.example.com更改为您要访问GitLab实例的URL。安装将自动配置并启动该URL的GitLab或者直接运行 yum install gitlab-ce
sudo EXTERNAL_URL="https://gitlab.example.com" yum install -y gitlab-ee   ##默认是ee 我们可以改成ce


```

​	实际问题：yum 安装gitlab-ee（或ce  [*GitLab* 社区版(*CE*)和企业版(*EE*)]）时，需要联网下载几百M的安装文件，非常耗时，所以应提前把所需RPM包下载并安装好

下载地址为：https://packages.gitlab.com/gitlab/gitlab-ce/packages/el/7/gitlab-ce-10.8.2-ce.0.el7.x86_64.rpm   右上角 [download](https://packages.gitlab.com/gitlab/gitlab-ce/packages/el/7/gitlab-ce-10.8.2-ce.0.el7.x86_64.rpm/download.rpm)

下载gitlab-ce安装步骤

```shell
sudo rpm -ivh /opt/gitlab-ce-10.8.2.ce.0.el7.x86_64.rpm
sudo lokkit -s http -s ssh
sudo yum install postfix
sudo service postfix start
sudo chkconfig postfix on
curl https://packages.gitlab.com/install/repositories/gitlab/gitlab-ce/script.rpm.sh | sudo bash
sudo EXTERNAL_URL="https://gitlab.example.com" yum install -y gitlab-ce 
```

​	*RPM*是Red-Hat Package Manager（*RPM*软件包管理器）的缩写



​	安装完成：重启

```
reboot
```



## 防火墙

[firewalld](https://www.itgank.com/archives/3064) 自身并不具备防火墙的功能，而是和 iptables 一样需要通过内核的 netfilter 来实现，也就是说 firewalld 和 iptables 一样，他们的作用都是用于维护规则，而真正使用规则干活的是内核的 netfilter，只不过 firewalld 和 iptables 的结构以及使用方法不一样罢了。

​	**firewalld 的配置模式**（firewalld 的配置文件以 xml 格式为主（主配置文件 firewalld.conf 例外），他们有两个存储位置）

​		1、/etc/firewalld/ 用户配置文件

​		2、/usr/lib/firewalld/ 系统配置文件，预置文件

**firewall-cmd **是 firewalld 的字符界面管理工具，如果需要使用图形化配置工具还需要安装 firewall-config，firewalld 是 centos7 的一大特性，最大的好处有两个：支持动态更新，不用重启服务；第二个就是加入了防火墙的“zone”概念。

##### 查看firewall是否运行,下面两个命令都可以

```shell
systemctl status firewalld.service
firewall-cmd --state
```

>##### 查看当前开了哪些端口 
>
>​	其实一个服务对应一个端口，每个服务对应/usr/lib/firewalld/services下面一个xml文件
>
>```shell
>firewall-cmd --list-services
>```
>
>##### 添加一个服务到firewalld
>
>```shell
>firewall-cmd --get-services
>```
>
>##### 添加一个服务到firewalld
>
>```shell
>firewall-cmd --add-service=http ##http换成想要开放的service
>```
>
>​	这样添加的service当前立刻生效，但系统下次启动就失效，可以测试使用。要永久打开一个service，加上 --permanent
>
>```shell
>firewall-cmd --permanent --add-service=http
>```
>
>

#### 3.gitlib服务操作

​	**初始化配置gitlab**(这个操作非常耗时，需要耐心等待，直到看到下一个命令提示符)

```shell
gitlab-ctl reconfigure
```

​	**启动gitlab服务**

```shell
gitlab-ctl start
```

4.浏览器访问

​	访问linux服务器ip地址即可，如果想访问external_url指定的域名还需要配置域名服务器或本地hosts文件

​	初次登录时需要为gitlab的root用户设置密码

​	如果无法访问需要停止防火墙服务**service firewalld stop**

​	