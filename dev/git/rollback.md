###分支回退
> 如提交时发现多提交一些文件，需要撤销提交
- 如果只是提交到本地，还未提交到服务器
    - IDE(AS) VCS->Git->Reset HEAD->弹框(Reset Type 选择Mixed，To Commit输入框中HEAD添加^符号，几个^退几步,也可以使用~1)
    - 命令 -> git reset --hard HEAD^  或git reset --hard HEAD~1
- 如果提交到远程也是和上面一样    
    
    
    
reset命令的三个参数对比

​ 1.--soft：仅仅在本地库移动HEAD指针

​ 2.--mixed：在本地库移动HEAD指针，重置暂存区

​ 3.--hard：在本地库移动HEAD指针，重置暂存区，重置工作区 