/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80003
Source Host           : localhost:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 80003
File Encoding         : 65001

Date: 2019-09-26 18:36:12
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
  `label_values` varchar(255) NOT NULL COMMENT '标签',
  `select_type` varchar(10) NOT NULL COMMENT '文章类型',
  `select_categories` varchar(25) NOT NULL COMMENT '博客分类',
  `select_grade` int(7) NOT NULL COMMENT '文章等级',
  `original_author` varchar(10) DEFAULT NULL COMMENT '原文章作者',
  `message` varchar(2) NOT NULL COMMENT '文章（0-公开  1-私密）',
  `create_time` varchar(11) NOT NULL COMMENT '创建时间',
  `likes` int(9) NOT NULL DEFAULT '0' COMMENT '点赞',
  `name` varchar(20) NOT NULL COMMENT '作者名字',
  `article_tabled` varchar(255) NOT NULL COMMENT '文章摘要',
  `look` int(10) NOT NULL DEFAULT '0' COMMENT '浏览次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES ('1566276948', 'springboot', 'cesiyixa\n> 不能\nfg\n\n![](/63e09177-338e-47a3-a8db-f1dd2e19e70e/face/1566276932547_158.png)', '1,2', '原创', '程序录', '0', '', '0', '2019-08-20', '0', '强子', 'cesiyixa不能fg...', '3');
INSERT INTO `blog` VALUES ('1566277253', 'springboot实现搭建个人博客（前后端分离）', '![](/63e09177-338e-47a3-a8db-f1dd2e19e70e/face/1566277237445_157.png)', '3', '原创', '程序录', '0', '', '0', '2019-08-20', '0', '强子', '...', '1');
INSERT INTO `blog` VALUES ('1566277542', 'weqrw', 'erqwf', '122', '转载', '程序录', '5', '', '0', '2019-08-20', '0', '强子', 'erqwf...', '0');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL COMMENT '评论id',
  `user_id` varchar(60) NOT NULL COMMENT '博客发布者id',
  `blog_id` bigint(20) NOT NULL COMMENT '博客id',
  `message` varchar(255) NOT NULL COMMENT '内容',
  `create_time` varchar(50) NOT NULL COMMENT '创建时间',
  `likes` int(11) NOT NULL COMMENT '点赞数',
  `is_read` int(1) NOT NULL DEFAULT '1' COMMENT '该条评论是否已读  1--未读   0--已读',
  `author_name` varchar(60) NOT NULL COMMENT '作者',
  PRIMARY KEY (`id`),
  KEY `blogId` (`blog_id`),
  KEY `userId` (`user_id`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('1566276963', '63e09177-338e-47a3-a8db-f1dd2e19e70e', '1566276948', 'dfjksafl', '2019-08-20 12:56:03', '1', '1', '强子');

-- ----------------------------
-- Table structure for commentlikes
-- ----------------------------
DROP TABLE IF EXISTS `commentlikes`;
CREATE TABLE `commentlikes` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `blog_id` bigint(20) NOT NULL COMMENT '博客id',
  `comment_id` bigint(20) NOT NULL COMMENT '评论id',
  `like_name` varchar(60) NOT NULL COMMENT '点赞人',
  `like_time` varchar(60) NOT NULL COMMENT '点赞时间',
  `is_read` int(1) NOT NULL DEFAULT '1' COMMENT '1 -- 未读 0 -- 已读',
  PRIMARY KEY (`id`),
  KEY `blogId` (`blog_id`),
  KEY `commentId` (`comment_id`),
  CONSTRAINT `commentlikes_ibfk_1` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `commentlikes_ibfk_2` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of commentlikes
-- ----------------------------
INSERT INTO `commentlikes` VALUES ('1566276967', '1566276948', '1566276963', '强子', '2019-08-20 12:56:07', '1');

-- ----------------------------
-- Table structure for friendlikes
-- ----------------------------
DROP TABLE IF EXISTS `friendlikes`;
CREATE TABLE `friendlikes` (
  `id` bigint(20) NOT NULL,
  `friend_id` bigint(20) NOT NULL COMMENT '留言id',
  `like_name` varchar(60) NOT NULL COMMENT '点赞用户',
  PRIMARY KEY (`id`),
  KEY `friend_id` (`friend_id`),
  CONSTRAINT `friendlikes_ibfk_1` FOREIGN KEY (`friend_id`) REFERENCES `friendlink` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of friendlikes
-- ----------------------------

-- ----------------------------
-- Table structure for friendlink
-- ----------------------------
DROP TABLE IF EXISTS `friendlink`;
CREATE TABLE `friendlink` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` varchar(60) NOT NULL COMMENT '留言者id',
  `message` varchar(255) NOT NULL COMMENT '内容',
  `create_time` varchar(30) NOT NULL COMMENT '创建时间',
  `likes` int(10) NOT NULL COMMENT '点赞数',
  `is_read` int(1) NOT NULL DEFAULT '1' COMMENT '1  -- 未读  0 -- 已读',
  `author_name` varchar(60) NOT NULL COMMENT '留言名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of friendlink
-- ----------------------------
INSERT INTO `friendlink` VALUES ('1566277015', '63e09177-338e-47a3-a8db-f1dd2e19e70e', 'dfag', '2019-08-20 12:56:55', '0', '1', '强子');

-- ----------------------------
-- Table structure for friendurl
-- ----------------------------
DROP TABLE IF EXISTS `friendurl`;
CREATE TABLE `friendurl` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `friend_name` varchar(255) NOT NULL COMMENT '友链名',
  `friend_url` varchar(255) NOT NULL COMMENT '友链地址',
  `create_time` varchar(60) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of friendurl
-- ----------------------------
INSERT INTO `friendurl` VALUES ('1566224573', '哇哦你刚才', 'http://www.lqnb.xyz', '');
INSERT INTO `friendurl` VALUES ('1566224742', 'lizex', 'http://www.lqnb.xyz', '');
INSERT INTO `friendurl` VALUES ('1566224813', 'dfjkjkl', 'http://www.lqnb.xyz', '');

-- ----------------------------
-- Table structure for guest
-- ----------------------------
DROP TABLE IF EXISTS `guest`;
CREATE TABLE `guest` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` varchar(60) NOT NULL COMMENT '留言者id',
  `message` varchar(255) NOT NULL COMMENT '内容',
  `create_time` varchar(60) NOT NULL COMMENT '创建时间',
  `likes` int(10) NOT NULL COMMENT '点赞数',
  `is_read` int(1) NOT NULL DEFAULT '1' COMMENT '1  -- 未读  0 -- 已读',
  `author_name` varchar(60) NOT NULL COMMENT '留言名称',
  PRIMARY KEY (`id`),
  KEY `userId` (`user_id`),
  CONSTRAINT `guest_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
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
  `guest_id` bigint(20) NOT NULL COMMENT '留言id',
  `like_name` varchar(60) NOT NULL COMMENT '点赞用户',
  PRIMARY KEY (`id`),
  KEY `guest_likes_id` (`guest_id`),
  CONSTRAINT `guest_likes_id` FOREIGN KEY (`guest_id`) REFERENCES `guest` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
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
  `label_name` varchar(60) NOT NULL COMMENT '标签姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of label
-- ----------------------------
INSERT INTO `label` VALUES ('212a7ef6-0f40-4994-911d-d15469415bca', '3');
INSERT INTO `label` VALUES ('3a3a0ecc-6b72-49b1-b575-2521c993e1b2', '2');
INSERT INTO `label` VALUES ('7491742e-4f1d-46c1-9e6f-5e704504391c', '1');
INSERT INTO `label` VALUES ('f7671655-1e73-4428-a830-4e443654c7c7', '122');

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
-- Table structure for repfriend
-- ----------------------------
DROP TABLE IF EXISTS `repfriend`;
CREATE TABLE `repfriend` (
  `rid` bigint(20) NOT NULL COMMENT '主键',
  `friend_id` bigint(20) NOT NULL COMMENT '留言条id',
  `rep_mess` varchar(255) NOT NULL COMMENT '正文',
  `rfriend_id` varchar(60) NOT NULL COMMENT '评论者id',
  `rcreate_time` varchar(60) NOT NULL COMMENT '创建时间',
  `ris_read` int(1) NOT NULL DEFAULT '1' COMMENT '1 - 未读  0 - 已读',
  `rep_name` varchar(60) NOT NULL COMMENT '评论名称',
  `friend_name` varchar(60) NOT NULL COMMENT '被评论名称',
  PRIMARY KEY (`rid`),
  KEY `friend_id` (`friend_id`),
  CONSTRAINT `repfriend_ibfk_1` FOREIGN KEY (`friend_id`) REFERENCES `friendlink` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of repfriend
-- ----------------------------

-- ----------------------------
-- Table structure for repguest
-- ----------------------------
DROP TABLE IF EXISTS `repguest`;
CREATE TABLE `repguest` (
  `rid` bigint(20) NOT NULL COMMENT '主键',
  `guest_id` bigint(20) NOT NULL COMMENT '留言条id',
  `rep_mess` varchar(255) NOT NULL COMMENT '正文',
  `rguest_id` varchar(60) NOT NULL COMMENT '评论者id',
  `rcreate_time` varchar(60) NOT NULL COMMENT '创建时间',
  `ris_read` int(1) NOT NULL DEFAULT '1' COMMENT '1 - 未读  0 - 已读',
  `rep_name` varchar(60) NOT NULL COMMENT '评论名称',
  `guest_name` varchar(60) NOT NULL COMMENT '被评论名称',
  PRIMARY KEY (`rid`),
  KEY `guestId` (`guest_id`),
  CONSTRAINT `repguest_ibfk_1` FOREIGN KEY (`guest_id`) REFERENCES `guest` (`id`) ON DELETE CASCADE
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
  `comment_id` bigint(20) NOT NULL COMMENT '评论文章id',
  `rep_mess` varchar(255) NOT NULL COMMENT '内容',
  `reported_id` varchar(60) NOT NULL COMMENT '回复者id',
  `rcreate_time` varchar(60) NOT NULL COMMENT '创建时间',
  `ris_read` int(1) NOT NULL DEFAULT '1' COMMENT '1 -- 未读  0  -- 已读',
  `rep_name` varchar(60) NOT NULL COMMENT '回复者id',
  `com_name` varchar(60) NOT NULL COMMENT '被评论者名字',
  PRIMARY KEY (`rid`),
  KEY `commentId` (`comment_id`),
  KEY `reportedId` (`reported_id`),
  CONSTRAINT `reportcomment_ibfk_1` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `reportcomment_ibfk_2` FOREIGN KEY (`reported_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
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
INSERT INTO `users` VALUES ('63e09177-338e-47a3-a8db-f1dd2e19e70e', '强子', 'ee663543a50fa1ca6edadcee6a91455d', '15383466854', '0', '2019-08-06', '1', '1', '1', '1', '1');
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
