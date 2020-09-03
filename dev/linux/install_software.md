[Ubuntu下常用软件安装方法](https://blog.csdn.net/oppo62258801/article/details/78866464)

[在Ubuntu上安装Intellij IDEA并创建桌面快捷方式](https://www.cnblogs.com/zaid/p/11141348.html)

安装vim ：sudo apt install vim
安装git ：apt install git
安装svn ：sudo apt-get install subversion

[Windows10远程桌面Ubuntu优麒麟](https://my.oschina.net/chipo/blog/3111230)

[FileZilla工具window10与Ubuntu之间的文件传输](https://blog.csdn.net/songyunli1111/article/details/79792958)

[liunx 系统 （ubuntu(优麒麟)）如何 配置JDK的方法](https://blog.csdn.net/qq_33716443/article/details/51760650)

### 安装git
```jshelllanguage
sudo apt install git
```
   android studio配置git位置 /usr/lib/git-core/git
###1.Chrome

- 32位
```shell
    wget https://dl.google.com/linux/direct/google-chrome-stable_current_i386.deb
    sudo dpkg -i google-chrome-stable_current_i386.deb
```
- 64位
```shell
    wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
    sudo dpkg -i google-chrome-stable_current_amd64.deb 
```
```shell
    wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
    sudo dpkg -i google-chrome*
    sudo apt-get -f install
```
###2.Intellij IDEA
- [下载--->获取tar.gz包](http://www.jetbrains.com/idea/download/)
- 解压

   tar命令参数：
   +  -c ：create 建立压缩档案的参数；
   +  -x ： 解压缩压缩档案的参数；
   +  -z ： 是否需要用gzip压缩；
   +  -v： 压缩的过程中显示档案；
   +  -f： 置顶文档名，在f后面立即接文件名，不能再加参数
    ```shell
    cd /usr/local
    sudo mkdir idea
    sudo tar -zxvf ~/下载/ideaIU-2019.1.3.tar.gz -C idea/
    ```
- 重命名文件夹
    ```shell
    cd /usr/local/idea
    pwd #使用pwd命令确认当前工作目录为/usr/local/idea
    sudo mv idea-IU-191.7479.19/ ideaIU/
    ```
     如果还想把ideaIU里面的内容转移到/usr/local/ideaIU/下
     ```shell
     sudo mv ideaIU/ /usr/local/ideaIU/
     cd ../
     sudo rm -d idea/
     ```
- 创建桌面快捷方式
    ```shell
    cd ~/桌面
    touch idea.desktop
    sudo vi idea.desktop
    ```
    然后按I开始输入,最后输入完了，按ESC，再输入:wq
    ```text
    [Desktop Entry]
    Name=IntelliJ IDEA
    Comment=IntelliJ IDEA
    Exec=/usr/local/ideaIU/bin/idea.sh
    Icon=/usr/local/ideaIU/bin/idea.png
    Terminal=false
    Type=Application
    Categories=Developer;
    ```
    允许这个文件可执行，需要用到chmod 命令    **sudo chmod +x idea.desktop**