/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.162
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : 192.168.1.162
 Source Database       : lmt_user

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : utf-8

 Date: 07/30/2018 18:39:12 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_account`
-- ----------------------------
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `account_name` varchar(20) NOT NULL DEFAULT '' COMMENT '登陆账号(用户自定义)',
  `password` varchar(255) DEFAULT NULL,
  `manager_type` int(11) NOT NULL DEFAULT '0' COMMENT '管理员类别(0普通管理员 1超级管理员)',
  `master` int(11) NOT NULL DEFAULT '0' COMMENT '是否被授权管理员(0否，1是)',
  `register_type` int(11) NOT NULL DEFAULT '0' COMMENT '注册类型（0前台注册 1后台创建 2用户中心创建）',
  `lock_times` int(11) NOT NULL DEFAULT '0' COMMENT '锁定次数',
  `is_lock` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否被锁定',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT '状态(0正常 1禁用)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `accountName` (`account_name`)
) ENGINE=MyISAM AUTO_INCREMENT=130 DEFAULT CHARSET=utf8 COMMENT='账号表';

-- ----------------------------
--  Table structure for `t_account_name`
-- ----------------------------
DROP TABLE IF EXISTS `t_account_name`;
CREATE TABLE `t_account_name` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `account_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '账号id',
  `account_name` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `typ` int(1) NOT NULL DEFAULT '0' COMMENT '类型（0主账号，1手机，2邮箱）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_name` (`account_name`)
) ENGINE=MyISAM AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='账号名称表';

-- ----------------------------
--  Table structure for `t_account_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_account_role`;
CREATE TABLE `t_account_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `account_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '账号id',
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_id_role_id` (`account_id`,`role_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='账号角色表';

-- ----------------------------
--  Table structure for `t_data_resources`
-- ----------------------------
DROP TABLE IF EXISTS `t_data_resources`;
CREATE TABLE `t_data_resources` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `icon` varchar(255) DEFAULT '' COMMENT '图标',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '资源名称',
  `uri` varchar(255) NOT NULL DEFAULT '' COMMENT '资源定位',
  `code` varchar(20) NOT NULL DEFAULT '' COMMENT '编码',
  `parent_code` varchar(20) NOT NULL DEFAULT '' COMMENT '父级编码',
  `description` varchar(50) DEFAULT '' COMMENT '描述',
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '状态(0正常 1禁用)',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='数据资源表';

-- ----------------------------
--  Table structure for `t_group`
-- ----------------------------
DROP TABLE IF EXISTS `t_group`;
CREATE TABLE `t_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '父部门ID/父公司ID',
  `group_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '顶级公司ID',
  `code` varchar(20) NOT NULL DEFAULT '' COMMENT '部门编码(公司类型可为空)',
  `code_path` varchar(50) NOT NULL DEFAULT '' COMMENT '当前级+父级编码组合（|01|0101|）',
  `organization_code` varchar(20) DEFAULT '' COMMENT '组织机构代码',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称(公司或部门名称)',
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '状态(0正常 1禁用)',
  `typ` int(1) NOT NULL DEFAULT '0' COMMENT '组类型（0内部组 1非内部组）',
  `grade` int(1) NOT NULL DEFAULT '0' COMMENT '级别（0公司 1部门）',
  `audit_state` int(1) NOT NULL DEFAULT '0' COMMENT '审核状态（0待审核 1审核通过2审核拒绝）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='部门/公司表';

-- ----------------------------
--  Table structure for `t_group_img`
-- ----------------------------
DROP TABLE IF EXISTS `t_group_img`;
CREATE TABLE `t_group_img` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` bigint(20) NOT NULL COMMENT '组ID',
  `type` int(11) NOT NULL COMMENT '证件类型(0三证合一 1营业执照 2经营许可证 3税务登记证 4管理员授权书 5法人身份证）',
  `img_url` varchar(100) NOT NULL DEFAULT '' COMMENT '证件图片地址',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='组图片表';

-- ----------------------------
--  Table structure for `t_group_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_group_info`;
CREATE TABLE `t_group_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `group_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '组织ID',
  `legal_person` varchar(20) NOT NULL DEFAULT '' COMMENT '拥有者/法人',
  `typ` int(11) NOT NULL DEFAULT '0' COMMENT '公司类型（0个人独资 1有限责任公司 2股份制公司 3集团公司 4联营企业 5外商投资企业 6中外合资经营企业 7国有 8私营企业 9全民所有制 10集体所有制...）',
  `validity` varchar(25) NOT NULL DEFAULT '' COMMENT '经营期限',
  `business_scope` varchar(100) NOT NULL DEFAULT '' COMMENT '经营范围',
  `address` varchar(100) NOT NULL DEFAULT '' COMMENT '注册地',
  `capital` varchar(10) NOT NULL DEFAULT '' COMMENT '注册资金',
  `phone` varchar(30) NOT NULL DEFAULT '' COMMENT '公司电话',
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '状态(0正常 1禁用)',
  `create_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='组信息表';

-- ----------------------------
--  Table structure for `t_group_member`
-- ----------------------------
DROP TABLE IF EXISTS `t_group_member`;
CREATE TABLE `t_group_member` (
  `id` bigint(20) NOT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `member_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_group_resources`
-- ----------------------------
DROP TABLE IF EXISTS `t_group_resources`;
CREATE TABLE `t_group_resources` (
  `id` bigint(20) NOT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `uri` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_group_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_group_role`;
CREATE TABLE `t_group_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` bigint(20) NOT NULL COMMENT '组ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_id_role_id` (`group_id`,`role_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='组角色关联表';

-- ----------------------------
--  Table structure for `t_group_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_group_user`;
CREATE TABLE `t_group_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '公司ID或当前部门ID',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `code_path` varchar(50) NOT NULL DEFAULT '' COMMENT '冗余组父级所有code，英文|分割',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_id_user_id` (`group_id`,`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='组用户关联表';

-- ----------------------------
--  Table structure for `t_member`
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member` (
  `id` bigint(20) NOT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_permission`
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `icon` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常，1禁用）',
  `code` varchar(50) NOT NULL COMMENT '编码',
  `parent_code` varchar(50) NOT NULL COMMENT '父级编码',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
--  Table structure for `t_permission_resources`
-- ----------------------------
DROP TABLE IF EXISTS `t_permission_resources`;
CREATE TABLE `t_permission_resources` (
  `id` bigint(20) NOT NULL,
  `permission_id` bigint(20) DEFAULT NULL,
  `resources_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission_id_resources_id` (`permission_id`,`resources_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_resources`
-- ----------------------------
DROP TABLE IF EXISTS `t_resources`;
CREATE TABLE `t_resources` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `resources_id` bigint(20) DEFAULT NULL COMMENT '资源id',
  `path` varchar(20) DEFAULT NULL COMMENT '资源路径',
  `uri` varchar(50) DEFAULT NULL COMMENT '资源uri',
  `name` varchar(20) DEFAULT NULL COMMENT '资源名称',
  `typ` varchar(1) DEFAULT NULL COMMENT '资源类型（0URL资源 1图片资源 2数据资源）',
  `state` int(1) DEFAULT NULL COMMENT '状态(0正常 1禁用)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='资源表';

-- ----------------------------
--  Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `icon` varchar(100) DEFAULT NULL COMMENT '角色对应的图标地址',
  `name` varchar(20) NOT NULL COMMENT '角色名称',
  `state` int(1) NOT NULL COMMENT '状态(0正常 1禁用)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
--  Table structure for `t_accredit`
-- ----------------------------
DROP TABLE IF EXISTS `t_accredit`;
CREATE TABLE `t_accredit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission_id_role_id` (`permission_id`,`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='角色权限关联表';

-- ----------------------------
--  Table structure for `t_url_resources`
-- ----------------------------
DROP TABLE IF EXISTS `t_url_resources`;
CREATE TABLE `t_url_resources` (
  `id` bigint(20) NOT NULL COMMENT 'id主键',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `name` varchar(20) NOT NULL COMMENT '资源名称',
  `uri` varchar(100) NOT NULL COMMENT '资源路径',
  `code` varchar(20) NOT NULL COMMENT '编码',
  `parent_code` varchar(20) NOT NULL COMMENT '父级编码',
  `description` varchar(50) DEFAULT NULL COMMENT '描述',
  `sort` int(11) DEFAULT NULL COMMENT '顺序',
  `state` int(1) NOT NULL COMMENT '状态(0正常 1禁用)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='URL资源表';

-- ----------------------------
--  Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '姓名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `domicile` varchar(200) DEFAULT NULL COMMENT '户籍地',
  `id_card` varchar(30) DEFAULT NULL COMMENT '身份证号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `qq` varchar(20) DEFAULT NULL COMMENT 'QQ',
  `sex` int(11) DEFAULT NULL COMMENT '性别(0男 1女)',
  `phone` varchar(30) DEFAULT NULL COMMENT '座机',
  `is_supplement` tinyint(1) DEFAULT NULL COMMENT '是否已经补全信息',
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '状态(0正常 1禁用)',
  `typ` int(1) NOT NULL DEFAULT '0' COMMENT '用户类型（0非内部用户 1内部用户 2内部用户兼外部用户）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=94 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
--  Table structure for `t_user_account`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_account`;
CREATE TABLE `t_user_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `account_id` bigint(20) NOT NULL COMMENT '账号ID',
  `user_id` varchar(255) NOT NULL COMMENT '用户ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_id_user_id` (`account_id`,`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='用户账号关联表';

-- ----------------------------
--  Table structure for `t_user_address`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_address`;
CREATE TABLE `t_user_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `address` varchar(100) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL COMMENT '电话',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `sort` int(11) DEFAULT NULL COMMENT '排序优先级',
  `state` int(1) DEFAULT NULL COMMENT '状态(0正常 1禁用)',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户地址信息表';

