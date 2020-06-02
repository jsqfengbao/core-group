/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : jeecg-boot

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2020-03-04 20:57:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for fund_budget
-- ----------------------------
DROP TABLE IF EXISTS `fund_budget`;
CREATE TABLE `fund_budget` (
  `id` varchar(36) NOT NULL,
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  `sys_org_code` varchar(64) DEFAULT NULL COMMENT '所属部门',
  `fund_type_id` varchar(4) NOT NULL COMMENT '资金类型ID',
  `fund_type_name` varchar(32) NOT NULL COMMENT '资金类型名',
  `money` decimal(8,2) NOT NULL COMMENT '预算金额',
  `month_num` int(11) NOT NULL COMMENT '预算月份',
  `year_num` int(11) NOT NULL COMMENT '预算年份',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fund_budget
-- ----------------------------

-- ----------------------------
-- Table structure for fund_family
-- ----------------------------
DROP TABLE IF EXISTS `fund_family`;
CREATE TABLE `fund_family` (
  `id` varchar(36) NOT NULL,
  `fund_family_name` varchar(50) NOT NULL COMMENT '家庭名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `sys_org_code` varchar(32) DEFAULT NULL COMMENT '所属部门',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fund_family
-- ----------------------------
INSERT INTO `fund_family` VALUES ('1203676212019171330', 'test', '2019-12-08 00:00:00', '2019-12-08 22:06:55', 'admin', 'admin', 'A01');

-- ----------------------------
-- Table structure for fund_type
-- ----------------------------
DROP TABLE IF EXISTS `fund_type`;
CREATE TABLE `fund_type` (
  `id` varchar(36) NOT NULL,
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  `sys_org_code` varchar(64) DEFAULT NULL COMMENT '所属部门',
  `type` varchar(4) DEFAULT NULL COMMENT '类型',
  `name` varchar(16) DEFAULT NULL COMMENT '类型名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fund_type
-- ----------------------------
INSERT INTO `fund_type` VALUES ('1203683647870021634', 'admin', '2019-12-08 22:32:00', 'admin', '2020-02-07 18:47:20', 'A01', '0', '工资');
INSERT INTO `fund_type` VALUES ('1225731142770769922', 'admin', '2020-02-07 18:40:52', null, null, 'A01', '0', '生活费');
INSERT INTO `fund_type` VALUES ('1225732740804788226', 'admin', '2020-02-07 18:47:13', null, null, 'A01', '0', '红包');
SET FOREIGN_KEY_CHECKS=1;
