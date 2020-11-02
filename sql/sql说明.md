## 数据库创与说明
数据库名字是acblog, 在application-dev.yml, application-prod.yml, application-test.yml这三个配置文件之中设置, 可以自行修改


### 所有的数据库表
`blog`, `comment`, `commentlikes`, `friendlikes`, `friendlink`, `friendurl`, `guest`, `guestlikes`, `label
`, `permission`, `permission_role`, `repfriend`, `repguest`, `reportcomment`, `roles`, `users`, `web`


### 没有外键的数据库表
`blog`, `friendlink`, `friendurl`, `label`, `permission`, `permission_role`, `roles`, `users`, `web`


### 有外键依赖的数据库表
`comment`, `commentlikes`, `friendlikes`, `guest`, `guestlikes`, `repfriend`, `repguest`, `reportcomment`


### 创建数据库表的顺序
由于有依赖, 如果直接使用`acblog.sql`创建, 可能导致外键依赖不完整, 或者创建使用, 所以可以按照`没有外键的数据库表`然后`有外键依赖的数据库表`这个顺序
来创建, 也可以直接使用`create-acblog.sql`来创建