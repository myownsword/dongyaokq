/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : dongyao

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 07/10/2019 14:48:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for kq_table
-- ----------------------------
DROP TABLE IF EXISTS `kq_table`;
CREATE TABLE `kq_table`  (
  `id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `kq_time` datetime(0) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
