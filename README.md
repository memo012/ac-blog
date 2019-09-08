## 适合学生搭建的个人博客：[www.lqnb.xyz](http://www.lqnb.xyz)
# 注意:
#### 博客网站后台技术更新,采用**springboot2.1.7+mybatis-plus**
#### **springboot1.x+mybatis**以前版本也在，供大家选择，GitHub地址为:[https://github.com/memo012/people-blog](https://github.com/memo012/people-blog)
##### 坚持不易，各位朋友如果觉得项目还不错的话可以给项目一个 star 吧，也是对我一直更新代码的一种鼓励啦，谢谢各位的支持。
![](https://github.com/memo012/people-blog/blob/master/images/star.png)
## 项目架构
```
|--- pom                                        // acblog配置文件
|--- blog-plus.sql                              // acblog数据库设计
|--- src                                        // 源代码
|--- |--- main                                  // 代码页
          |--- Java                             // 后台代码
             |--- common                        // 公共类
                   |--- config                  // 配置类(以.config后缀结尾)
                   |--- utils                   // 工具类
                        |--- phoneVerify        // 腾讯云短信验证代码
         |--- modules                           // 服务端代码
                  |--- controller               // 表现层
                  |--- dao                      // 持久层
                  |--- entity                   // 实体层
                  |--- service                  // 业务逻辑层
                  |--- shiro                    // shiro配置类
                  |--- AcblogApplication        // spring boot启动类
|--- |--- resources                             // 资源
              |--- mappering                    // 持久层xml文件
              |--- static                       // 静态文件
              |--- templates                    // 前端页面
              |--- application.properties       // 全局配置类
              |--- application.yml              // 全局配置类
              |--- application-dev.yml          // 全局配置类(开发者模式)
              |--- application-test.yml         // 全局配置类(测试者模式)
              |--- application-prod.yml         // 全局配置类(生产者模式)
```

## 前言
   正如你们所见，我就是这个简陋网站的维护人，一个普普通通的在校大二学生，不对，应该说马上就要大三了，唉唉，大学已经过去了一半了，想想自己的困境，头就要炸，既要应对学业上的问题，又要面临实习找工作，做准备工作，能怎么办？扛着吧！！！  
  众所周知，大学的专业知识（核心）到了大三才学，大三，既要面临找工作，又要加强自己的专业知识水平，难受，辛亏我在大二上半年时加入一个实验室，在这个实验室中慢慢找到自己的方向，非常感谢实验室，让我找到了方向，使自己对自己的人生有了一种定位，不再那么迷茫，那么无助。  
  说到实验室，非常感谢我的老大（鑫哥），具体名字就不说了，他帮助了我们许多，他把他所见，所感，对这个行业的认知，给我们吐露出来，让我们提前了解这个行业，更加清楚自己所处的位置。  
  好了，就说这些吧!!!  
## 英雄帖
人无完人，项目也是，总存在着隐藏bug，各位前辈有好的想法，可以call我，共同进步。
## 技术能力
  我是一个后台开发人（Java）,对前台不是那么的了解，单单只是能搭出来，效果的话，我已经尽我洪荒之力了，就搭出这个水平，希望不要见怪。
### 项目介绍
1. 博客项目对于初学spring boot的人是个不错的练手项目，即不容易，又不复杂，体验一下做项目的感受。  
2. 该项目为开源项目，代码已上传到我的GitHub上[https://github.com/memo012/ac-blog](https://github.com/memo012/ac-blog)，  欢迎**star**。  
3. 该网站已完成基本功能，后续不断更新修改。  
4. 在文章，评论等处添加缓存，提高性能。  
4. 使用nginx反向代理部署。  
## 技术展示
### 后台：
项目构建：Maven  
web框架： spring boot  
数据持久层： mybatis-plus  
安全框架： shiro  
搜索引擎： elasticSearch  
缓存：redis  
数据库：Mysql  
### 前台
前台框架：[layui框架](https://www.layui.com/ "layui框架") [amazeui框架](https://amazeui.clouddeep.cn/ "amazeui框架")  
前端模板： thymeleaf  
### 部署
docker镜像  
服务器： 腾讯云（centos7）  

#### 一些细小的框架，就不一一列举了

## 页面展示
### 关于我页面
![](https://img-blog.csdnimg.cn/20190807130402926.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxMDY2MDY2,size_16,color_FFFFFF,t_70)
### 编辑页面
![](https://img-blog.csdnimg.cn/20190807130338920.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxMDY2MDY2,size_16,color_FFFFFF,t_70)
### 个人中心
![](https://img-blog.csdnimg.cn/20190807130417457.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxMDY2MDY2,size_16,color_FFFFFF,t_70)
### 后台管理界面
![](https://img-blog.csdnimg.cn/20190807130429505.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxMDY2MDY2,size_16,color_FFFFFF,t_70)
## 项目概要
### 项目需求
  博客项目对于spring boot初学者来说，是一个不错的项目体验，从零搭建项目，从无到有，体验项目的完整性，锻炼自己做项目的能力，全面发展。
### 业务设计
#### 打包上传部署
- 使用jar包部署方式，使用守护进程方式部署，`nohup java -jar blog.jar >temp.txt &`
### 总结
#### 开发中遇到难点
- 首先肯定是前端搭建，多亏了我俩个前端大佬，帮助我解决了诸多小儿科问题。
- 使用shiro做安全框架，自己以前也了解过，感觉自己没啥问题，但一用到项目中，傻眼了，只能重新学习了解，发现前后端分离必须使用session管理个人信息，在这儿就耗了好长时间，但功夫不负有心人，victory.
- 打包部署，以前自己只做过部署后台（以war包形式），没真正意思上部署过一个真正项目，认为不太难，往往认为不难的东西，到最终都是花费了大量时间来弄，非常感谢我老大（鑫哥），给我指点了指点，让我少走弯路。
#### 项目优缺点
- 后端代码分工明细，有利于项目的理解和维护。
- 在文章，评论等处添加缓存，提高性能。
- 由于该项目使用两个前台框架，造成代码混乱，但不影响阅读源码。
## 最后想说
> 忍受别人忍受不了的忍受
> 享受别人享受不了的享受

## 关于网站
- 本人秉着开源风格，已把代码上传到GitHub上（[https://github.com/memo012/people-blog](https://github.com/memo012/ac-blog)），可自行下载学习
- 客官觉得不错的话，给个**star**就行。


## 更新记录
### 2019/9/02
添加背景线条和鼠标手势
### 2019/8/20
网站后台技术更新(springboot2.x+mybatis-plus)
### 2019/8/7
网站正式上线
### 2019/7/24
ElasticSearch实现搜索
### 2019/7/21
shiro实现登录权限管理
### 2019/7/13
个人中心页面搭建完毕    
浏览博客一系列操作前后台交互完毕
### 2019/7/9跟新
文章详情前后台交互完毕
### 2019/7/7更新
更新和归档页面搭成
## 2019/7/2更新
#### 编辑页面更新
### 2019/7/1更新
页面大改，前台页面重新搭建
### 2019/6/27更新
前端主页面基本搭成
