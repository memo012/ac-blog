/*
Navicat MySQL Data Transfer

Source Server         : leiqiang
Source Server Version : 80003
Source Host           : localhost:3306
Source Database       : blog-plus

Target Server Type    : MYSQL
Target Server Version : 80003
File Encoding         : 65001

Date: 2019-08-25 13:57:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` bigint(20) NOT NULL COMMENT '标识符',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `text` longtext NOT NULL COMMENT '正文',
  `labelValues` varchar(255) NOT NULL COMMENT '标签',
  `selectType` varchar(10) NOT NULL COMMENT '文章类型',
  `selectCategories` varchar(25) NOT NULL COMMENT '博客分类',
  `selectGrade` int(7) NOT NULL COMMENT '文章等级',
  `originalAuthor` varchar(10) DEFAULT NULL COMMENT '原文章作者',
  `message` varchar(2) NOT NULL COMMENT '文章（0-公开  1-私密）',
  `createTime` varchar(11) NOT NULL COMMENT '创建时间',
  `likes` int(9) NOT NULL DEFAULT '0' COMMENT '点赞',
  `name` varchar(20) NOT NULL COMMENT '作者名字',
  `articleTabloid` varchar(255) NOT NULL COMMENT '文章摘要',
  `look` int(10) NOT NULL DEFAULT '0' COMMENT '浏览次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES ('1565487430', '测试', '## 告诉\n#### 嗯刚刚', '1', '原创', '程序录', '5', '', '0', '2019-08-11', '0', '强子', '告诉嗯刚刚...', '1');
INSERT INTO `blog` VALUES ('1565493925', '正式测试', '## 正式测试\n> 电话JFK垃圾\n> jefkljfk \ndkjfkljfkljklf\njfkljfkljfkl\n\njfklsdjfklsdjfkl黑发hi哦阿姐', '23,34', '原创', '程序录', '5', '', '0', '2019-08-11', '0', '强子', '正式测试电话JFK垃圾jefkljfkdkjfkljfkljklfjfkljfkljfkljfklsdjfklsdjfkl黑发hi哦阿姐...', '2');
INSERT INTO `blog` VALUES ('1565494331', '客人JFK六角恐龙', '>jklfrjf\njfkjklfj\njfkrjlkfj', '665', '原创', '程序录', '5', '', '0', '2019-08-11', '0', '强子', 'jklfrjfjfkjklfjjfkrjlkfj...', '3');
INSERT INTO `blog` VALUES ('1565495378', '的空间克隆的分解开来', '伺服电机考虑到实际付款链接\n# 角度考虑JFK了', '4565', '原创', '面试', '3', '', '0', '2019-08-11', '0', '强子', '伺服电机考虑到实际付款链接角度考虑JFK了...', '6');

-- ----------------------------
-- Table structure for commentlikes
-- ----------------------------
DROP TABLE IF EXISTS `commentlikes`;
CREATE TABLE `commentlikes` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `blogId` bigint(20) NOT NULL COMMENT '博客id',
  `commentId` bigint(20) NOT NULL COMMENT '评论id',
  `likeName` varchar(60) NOT NULL COMMENT '点赞人',
  `likeTime` varchar(60) NOT NULL COMMENT '点赞时间',
  `isRead` int(1) NOT NULL DEFAULT '1' COMMENT '1 -- 未读 0 -- 已读',
  PRIMARY KEY (`id`),
  KEY `blogId` (`blogId`),
  KEY `commentId` (`commentId`),
  CONSTRAINT `commentlikes_ibfk_1` FOREIGN KEY (`blogId`) REFERENCES `blog` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `commentlikes_ibfk_2` FOREIGN KEY (`commentId`) REFERENCES `comment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of commentlikes
-- ----------------------------

-- ----------------------------
-- Table structure for guest
-- ----------------------------
DROP TABLE IF EXISTS `guest`;
CREATE TABLE `guest` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `userId` varchar(60) NOT NULL COMMENT '留言者id',
  `message` varchar(255) NOT NULL COMMENT '内容',
  `createTime` varchar(60) NOT NULL COMMENT '创建时间',
  `likes` int(10) NOT NULL COMMENT '点赞数',
  `isRead` int(1) NOT NULL DEFAULT '1' COMMENT '1  -- 未读  0 -- 已读',
  `authorName` varchar(60) NOT NULL COMMENT '留言名称',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  CONSTRAINT `guest_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of guest
-- ----------------------------

-- ----------------------------
-- Table structure for guestlikes
-- ----------------------------
DROP TABLE IF EXISTS `guestlikes`;
CREATE TABLE `guestlikes` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `guestId` bigint(20) NOT NULL COMMENT '留言id',
  `likeName` varchar(60) NOT NULL COMMENT '点赞用户',
  PRIMARY KEY (`id`),
  KEY `guest_likes_id` (`guestId`),
  CONSTRAINT `guest_likes_id` FOREIGN KEY (`guestId`) REFERENCES `guest` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of guestlikes
-- ----------------------------

-- ----------------------------
-- Table structure for label
-- ----------------------------
DROP TABLE IF EXISTS `label`;
CREATE TABLE `label` (
  `id` varchar(60) NOT NULL COMMENT '标识符',
  `labelName` varchar(60) NOT NULL COMMENT '标签姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of label
-- ----------------------------
INSERT INTO `label` VALUES ('0022fb44-235a-48dd-ac77-fc86cc19ea7f', '4');
INSERT INTO `label` VALUES ('0c1ed914-c22e-42ba-b98e-bb052a91d379', '1');
INSERT INTO `label` VALUES ('168fc78b-0969-4160-866a-6e58e40f2be5', '5');
INSERT INTO `label` VALUES ('1b9ee54d-fa74-4aca-8588-3af2e07b02f1', '1224');
INSERT INTO `label` VALUES ('26539cc9-72a7-4b06-85de-ac08b2db6b81', '地方');
INSERT INTO `label` VALUES ('3b0724be-f77f-491d-9a1a-7361dae0afa5', '665');
INSERT INTO `label` VALUES ('408a3373-b931-46e7-89f3-1ae85c719fa6', '785');
INSERT INTO `label` VALUES ('5a43d7b4-1e96-461e-88d8-7d9e3b588215', '45');
INSERT INTO `label` VALUES ('6418a069-2d08-44e5-81ab-c66424c7b087', '134');
INSERT INTO `label` VALUES ('7ed58a18-988f-4538-b63c-6290af25ef71', '12345');
INSERT INTO `label` VALUES ('9365bf1b-50eb-48c4-9325-015560a892dd', '56');
INSERT INTO `label` VALUES ('938ea8b7-bce8-478a-b7af-8d7f60775471', '方法');
INSERT INTO `label` VALUES ('ac4c267b-b361-4aba-b117-93868541fff3', '23');
INSERT INTO `label` VALUES ('ace1a047-b70e-4b04-b77b-8dbcd12052c9', '4554');
INSERT INTO `label` VALUES ('b23d3f1e-5717-4c0b-a245-c7f4dd4bf916', '34');
INSERT INTO `label` VALUES ('c72fb5a7-5078-466b-a382-5a23842c7a7d', 'regt');
INSERT INTO `label` VALUES ('ccd25a46-e6c7-4ac3-ab47-06afac3045c9', '测试');
INSERT INTO `label` VALUES ('d373d55e-5a76-4551-866b-4d6af0ba5a80', '6方法');
INSERT INTO `label` VALUES ('f8ebfbda-cd3a-40b7-a0d6-1622b80922e2', '4565');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `pid` int(5) NOT NULL AUTO_INCREMENT,
  `pname` varchar(255) NOT NULL DEFAULT '' COMMENT '权限名(写博客)',
  `url` varchar(255) DEFAULT '',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', 'editor', '');
INSERT INTO `permission` VALUES ('2', 'manage', '');
INSERT INTO `permission` VALUES ('3', 'comment', '');

-- ----------------------------
-- Table structure for permission_role
-- ----------------------------
DROP TABLE IF EXISTS `permission_role`;
CREATE TABLE `permission_role` (
  `rid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  KEY `idx_rid` (`rid`),
  KEY `idx_pid` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission_role
-- ----------------------------
INSERT INTO `permission_role` VALUES ('1', '2');
INSERT INTO `permission_role` VALUES ('2', '3');
INSERT INTO `permission_role` VALUES ('3', '1');

-- ----------------------------
-- Table structure for repguest
-- ----------------------------
DROP TABLE IF EXISTS `repguest`;
CREATE TABLE `repguest` (
  `rid` bigint(20) NOT NULL COMMENT '主键',
  `guestId` bigint(20) NOT NULL COMMENT '留言条id',
  `repMess` varchar(255) NOT NULL COMMENT '正文',
  `rguestId` varchar(60) NOT NULL COMMENT '评论者id',
  `rcreateTime` varchar(60) NOT NULL COMMENT '创建时间',
  `risRead` int(1) NOT NULL DEFAULT '1' COMMENT '1 - 未读  0 - 已读',
  `repName` varchar(60) NOT NULL COMMENT '评论名称',
  `guestName` varchar(60) NOT NULL COMMENT '被评论名称',
  PRIMARY KEY (`rid`),
  KEY `guestId` (`guestId`),
  CONSTRAINT `repguest_ibfk_1` FOREIGN KEY (`guestId`) REFERENCES `guest` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of repguest
-- ----------------------------

-- ----------------------------
-- Table structure for reportcomment
-- ----------------------------
DROP TABLE IF EXISTS `reportcomment`;
CREATE TABLE `reportcomment` (
  `rid` bigint(20) NOT NULL COMMENT '回复评论id',
  `commentId` bigint(20) NOT NULL COMMENT '评论文章id',
  `repMess` varchar(255) NOT NULL COMMENT '内容',
  `reportedId` varchar(60) NOT NULL COMMENT '回复者id',
  `rcreateTime` varchar(60) NOT NULL COMMENT '创建时间',
  `risRead` int(1) NOT NULL DEFAULT '1' COMMENT '1 -- 未读  0  -- 已读',
  `repName` varchar(60) NOT NULL COMMENT '回复者id',
  `comName` varchar(60) NOT NULL COMMENT '被评论者名字',
  PRIMARY KEY (`rid`),
  KEY `commentId` (`commentId`),
  KEY `reportedId` (`reportedId`),
  CONSTRAINT `reportcomment_ibfk_1` FOREIGN KEY (`commentId`) REFERENCES `comment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `reportcomment_ibfk_2` FOREIGN KEY (`reportedId`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of reportcomment
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `rid` int(1) NOT NULL COMMENT '标识符',
  `rname` varchar(10) NOT NULL COMMENT '角色',
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('1', 'admin');
INSERT INTO `roles` VALUES ('2', 'user');
INSERT INTO `roles` VALUES ('3', 'partner');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` varchar(60) NOT NULL COMMENT '标识符',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(60) NOT NULL COMMENT '密码',
  `phone` varchar(18) NOT NULL COMMENT '手机号',
  `sex` int(1) NOT NULL COMMENT '性别',
  `last_time` varchar(30) NOT NULL COMMENT '最后一次登录时间',
  `role_id` int(2) NOT NULL COMMENT '角色(1-超级管理员 2-普通用户)',
  `realname` varchar(60) DEFAULT NULL COMMENT '真实姓名',
  `qq` varchar(11) NOT NULL COMMENT 'qq',
  `email` varchar(20) NOT NULL COMMENT 'email邮箱',
  `intro` varchar(255) NOT NULL COMMENT '个人简历',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('56db1007-5b9f-44f9-a73b-fc64e4e92a8c', 'dong', 'bd0596c7bfd4a4ea8c36a9695ef8d77e', '15383466858', '0', '2019-08-17', '2', '', '', '', '');
INSERT INTO `users` VALUES ('63e09177-338e-47a3-a8db-f1dd2e19e70e', '强子', 'ae1289da5dea6047a009ac1c417babf1', '15383466854', '0', '2019-08-06', '1', '1', '1', '1', '1');
INSERT INTO `users` VALUES ('dfdf', '12', 'efaef', '12344', '0', 'efsdf', '1', 'df', '2', '3', 'dfe');

-- ----------------------------
-- Table structure for web
-- ----------------------------
DROP TABLE IF EXISTS `web`;
CREATE TABLE `web` (
  `id` varchar(60) NOT NULL COMMENT '主键',
  `visitor` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of web
-- ----------------------------
INSERT INTO `web` VALUES ('89357yeru', '1282');
