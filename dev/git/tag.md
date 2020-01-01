1.列出已有的tag
```shell
git tag
```
2.加上-l命令可以使用通配符来过滤tag
```shell
git tag -l "v3.3.*"
```
3.新建tag
```shell
git tag v1.0
```
还可以加上-a参数来创建一个带备注的tag，备注信息由-m指定。如果你未传入-m则创建过程系统会自动为你打开编辑器让你填写备注信息
```shell
git tag -a tagName -m "my tag"
```
4.查看tag详细信息(git show命令可以查看tag的详细信息，包括commit号等。)
```shell
git show tagName
```
5.将tag同步到远程服务器
> 同提交代码后，使用git push来推送到远程服务器一样，tag也需要进行推送才能到远端服务器。

> 使用git push origin [tagName]推送单个分支 
```shell
git push origin v1.0
```
推送本地所有tag，使用git push origin --tags

6.切换到某个tag
+ 跟分支一样，可以直接切换到某个tag去。这个时候不位于任何分支，处于游离状态，可以考虑基于这个tag创建一个分支

git checkout v0.9

7.删除某个tag
+ 本地删除(git tag -d v0.1.2 )
+ 远端删除(git push origin :refs/tags/<tagName>)