### 1.体统查看版本
```
lsb_release -a
//显示linux的内核版本和系统是多少位的：X86_64代表系统是64位的
uname -a
```

### 2.ifconfig 在20.4 不存在使用  ip addr


### 3.远程登录（ssh–secure shell，ssh是一种安全协议，主要用于给远程登录会话数据进行加密，保证数据传输的安全）
    
##### 0. SSH分客户端openssh-client和openssh-server
    登陆别的机器的SSH只需要安装openssh-client（ubuntu有默认安装，如果没有则sudo apt-get install openssh-client），如果要使本机开放SSH服务就需要安装openssh-server
    
##### 1. 查看当前的ubuntu是否安装了ssh-server服务。默认只安装ssh-client服务。
>dpkg -l | grep ssh

##### 2. 安装ssh-server服务

    sudo apt-get install openssh-server
    再次查看安装的服务：dpkg -l | grep ssh
    
    
    然后确认ssh-server是否启动了：
    >ps -e | grep ssh
    
    如果看到sshd那说明ssh-server已经启动了
    
    如果没有则可以这样启动：sudo /etc/init.d/ssh start或sudo service ssh start
    
    配置相关：
    ssh-server配置文件位于/etc/ssh/sshd_config，在这里可以定义SSH的服务端口，默认端口是22，你可以自己定义成其他端口号，如222。（或把配置文件中的”PermitRootLogin without-password”加一个”#”号,把它注释掉，再增加一句”PermitRootLogin yes”）
    然后重启SSH服务：
    sudo /etc/init.d/ssh stop
    sudo /etc/init.d/ssh start
    
#####  3. 登陆SSH（Linux）
    ssh username@192.168.1.103
    其中，username为192.168.1.103机器上的用户，需要输入密码。
    断开连接：exit