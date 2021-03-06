<http://www.runoob.com/linux/linux-vim.html>

1.安装vim

```shell
yum install vim
sudo apt-get install vim
```

2.打开文件 vim text

3.模式

​	

## 一般模式可用的光标移动

| h 或 向左箭头键(←) | 光标向左移动一个字符                                         |
| ------------------ | ------------------------------------------------------------ |
| j 或 向下箭头键(↓) | 光标向下移动一个字符                                         |
| k 或 向上箭头键(↑) | 光标向上移动一个字符                                         |
| l 或 向右箭头键(→) | 光标向右移动一个字符                                         |
| [Ctrl] + [f]       | 屏幕『向下』移动一页，相当于 [Page Down]按键 (常用)          |
| [Ctrl] + [b]       | 屏幕『向上』移动一页，相当于 [Page Up] 按键 (常用)           |
| [Ctrl] + [d]       | 屏幕『向下』移动半页                                         |
| [Ctrl] + [u]       | 屏幕『向上』移动半页                                         |
| +                  | 光标移动到非空格符的下一行                                   |
| -                  | 光标移动到非空格符的上一行                                   |
| n<space>           | 那个 n 表示『数字』，例如 20 。按下数字后再按空格键，光标会向右移动这一行的 n 个字符。例如 20<space> 则光标会向后面移动 20 个字符距离。 |
| 0,^ 或功能键[Home]  | 这是数字『 0 』：移动到这一行的最前面字符处 (常用)           |
| b                  | 将光标定位到光标所在单词起始处          |
| e                  | 将光标定位到光标所在单词结尾处          |
| w                  | 将光标定位到下一个所在单词起始处          |
| $ 或功能键[End]    | 移动到这一行的最后面字符处(常用)                             |
| H	                 | 光标移动到这个屏幕的最上方那一行的第一个字符                 |
| M	                 | 光标移动到这个屏幕的中央那一行的第一个字符                   |
| L	                 | 光标移动到这个屏幕的最下方那一行的第一个字符                 |
| **G**	             | 移动到这个档案的最后一行(常用)                               |
| nG	             | n 为数字。移动到这个档案的第 n 行。例如 20G 则会移动到这个档案的第 20 行(可配合 :set nu) |
| **gg**	         | 移动到这个档案的第一行，相当于 1G 啊！ (常用)                |
| n<Enter>	         | n 为数字。光标向下移动 n 行(常用)                            |

**移动都指定行号**  ①.显示行号：set nu ；②输入数字（去的行号）；③输入shift+g

## 删除命令	

|                    |                                                              |
| ------------------ | ------------------------------------------------------------ |
| d0                 | 删除光标从当前位置(不包含)到该行行首的所有字符               |
| d^                 | 同上                                                         |
| d$                 | 删除光标从当前位置(包含)到该行行尾的所有字符                 |
| db                 | 删除光标从当前位置(不包含)到单词起始处的所有字符             |
| de                 | 删除光标从当前位置(包含)到单词末尾处的所有字符               |
| dw                 | 删除光标从当前位置(包含)到下一个单词起始处的所有字符         |
| dh                 | 删除光标前一个字符                                           |
| dl                 | 删除光标指定的字符                                           |
| dj                 | 删除光标所在行以及下一行的所有字符                           |
| dk                 | 删除光标所在行以及上一行的所有字符                           |
| dd                 | 删除光标所在行的字符、删除当前行向下的5行5dd(包含该当前行)   |
| dgg                | 删除光标所在行到文件开头的所有字符                           |
| dG                 | 删除光标所在行到文件末尾的所有字符                           |


## 拷贝
 1.拷贝当前行yy，拷贝当前行向下5行 5yy，并粘贴（P）

## 搜索
```TEXT
1.在文件中查找某个单词[命令模式下/关键字，回车查找，输入n就是查找下一个]
search hit BOTTOM, continuing at TOP(已查找到文件结尾，再从开头继续查找)
```

## 设置行号

​	设置行号	**set nu**

​	取消行号	**set nonu**

## 撤销

- u 表示撤销最后一次修改
- U 表示撤销对整行的修改
- Ctrl+r 快捷键可以恢复撤销的内容


### 命令模式：

用户刚刚启动 vi/vim，便进入了命令模式。

此状态下敲击键盘动作会被Vim识别为命令，而非输入字符。比如我们此时按下i，并不会输入一个字符，i被当作了一个命令。

以下是常用的几个命令：

- **i** 切换到输入模式，以输入字符。
- **x** 删除当前光标所在处的字符。
- **:** 切换到底线命令模式，以在最底一行输入命令。

| 命令 | 作用                                                         |
| :--- | ------------------------------------------------------------ |
| i, I | 进入输入模式(Insert mode)i 为『从目前光标所在处输入』，I 为『在目前所在行的第一个非空格符处开始输入』 |
| a,A  | 进入输入模式(Insert mode)a 为『从目前光标所在的下一个字符处开始输入』， A 为『从光标所在行的最后一个字符处开始输入』 |
| o,O  | 进入输入模式(Insert mode)这是英文字母 o 的大小写。o 为『在目前光标所在的下一行处输入新的一行』； O 为在目前光标所在处的上一行输入新的一行！(常用) |
| s,S  | 小写s删除光标指定的字符并进入插入模式，大写S将光标所在行删除并进入插入模式 |
| ESC  | 退出编辑模式，回到一般模式中                                 |

### 输入模式

在命令模式下按下i就进入了输入模式。

在输入模式中，可以使用以下按键：

- **字符按键以及Shift组合**，输入字符
- **ENTER**，回车键，换行
- **BACK SPACE**，退格键，删除光标前一个字符
- **DEL**，删除键，删除光标后一个字符
- **方向键**，在文本中移动光标
- **HOME**/**END**，移动光标到行首/行尾
- **Page Up**/**Page Down**，上/下翻页
- **Insert**，切换光标为输入/替换模式，光标将变成竖线/下划线
- **ESC**，退出输入模式，切换到命令模式

### 底线命令模式

在命令模式下按下:（英文冒号）就进入了底线命令模式。

底线命令模式可以输入单个或多个字符的命令，可用的命令非常多。

在底线命令模式中，基本的命令有（已经省略了冒号）：

- q 退出程序
- w 保存文件

按ESC键可随时退出底线命令模式。

​	

​	注意一下啊，那个惊叹号 (!) 在 vi 当中，常常具有『强制』的意思～

## 一般模式切换到编辑模式的可用的按钮说明

| :w                  | 将编辑的数据写入硬盘档案中(常用)                             |
| ------------------- | ------------------------------------------------------------ |
| :w!                 | 若文件属性为『只读』时，强制写入该档案。不过，到底能不能写入， 还是跟你对该档案的档案权限有关啊！ |
| :q                  | 离开 vi (常用)                                               |
| :q!                 | 若曾修改过档案，又不想储存，使用 ! 为强制离开不储存档案。    |
| :wq                 | 储存后离开，若为 :wq! 则为强制储存后离开 (常用)              |
| ZZ                  | 这是大写的 Z 喔！若档案没有更动，则不储存离开，若档案已经被更动过，则储存后离开！ |
| :w [filename]       | 将编辑的数据储存成另一个档案（类似另存新档）                 |
| :r [filename]       | 在编辑的数据中，读入另一个档案的数据。亦即将 『filename』 这个档案内容加到游标所在行后面 |
| :n1,n2 w [filename] | 将 n1 到 n2 的内容储存成 filename 这个档案。                 |
| :! command          | 暂时离开 vi 到指令行模式下执行 command 的显示结果！例如 『:! ls /home』即可在 vi 当中察看 /home 底下以 ls 输出的档案信息！ |
| :set nu             | 显示行号，设定之后，会在每一行的前缀显示该行的行号           |
| :set nonu           | 与 set nu 相反，为取消行号！                                 |
|                     |                                                              |
|                     |                                                              |
|                     |                                                              |
|                     |                                                              |
|                     |                                                              |
|                     |                                                              |

## Linux中.swp 文件的产生与解决方法

​	**.swp简介**：vim中的swp即swap（交换分区）的简写，在编辑文件时产生，它是隐藏文件。这个文件是一个临时交换文件，用来备份缓冲区中的内容。类似于Windows的虚拟内存，就是当内存不足时候,把一部分硬盘空间虚拟成内存使用,从而解决内存容量不足的情况。

1.多个程序编辑同一个文件时选择readonly

2.非常规退出时：当强行关闭vi时，比如电源突然断掉或者你使用了Ctrl+ZZ，vi自动生成一个.swp文件，下次再编辑时就会出现一些提示

​	解决方法：如果你正常退出，那么这个这个swp文件将会自动删除(vim编辑器要正常退出可以使用Shift-ZZ)。

用vi -r  xxx(recover  恢复)

 ```shell
vi -r 文件名
 ```

来恢复文件，然后用

```shell
rm -rf xxx.swp
```

删除swp文件，不然每一次编辑时总是有这个提示

如果你不想产生交换文件，可以在vim配置中 (/etc/vimrc) 添加

```shell
set noswapfile
```

命令禁止产生交换文件

也可以用添加

```shell
set swapfile
```

这条命令设置生成交换文件