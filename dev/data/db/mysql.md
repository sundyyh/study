
##mysql8.0  远程访问
```sql
update user set host = "%" where user = "root";
grant all privileges on *.* to root@'%';
```