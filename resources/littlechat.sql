/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50527
 Source Host           : localhost:3306
 Source Schema         : littlechat

 Target Server Type    : MySQL
 Target Server Version : 50527
 File Encoding         : 65001

 Date: 07/12/2019 16:34:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
CREATE DATABASE littlechat;
USE littlechat;
-- ----------------------------
-- Table structure for chatuser
-- ----------------------------
DROP TABLE IF EXISTS `chatuser`;
CREATE TABLE `chatuser`  (
  `username` int(11) NOT NULL,
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nickname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '佚名',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'm',
  `age` int(11) NULL DEFAULT NULL,
  `image` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `signature` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NOT NULL COMMENT '0:不在线\n1:在线',
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of chatuser
-- ----------------------------
INSERT INTO `chatuser` VALUES (111, '111', '雷军', 'm', 0, 'images/135.gif', '小米科技，天下第一', 0);
INSERT INTO `chatuser` VALUES (123, '123', '杰克乔斯', 'm', 0, 'images/135.gif', '国家富强民族振兴人民幸福', 0);
INSERT INTO `chatuser` VALUES (222, '222', '任正非', 'm', 0, 'images/130.gif', '华为国货之光', 0);
INSERT INTO `chatuser` VALUES (234, '234', '艾丽丝', 'f', 0, 'images/100.gif', '奉献友爱互助进步', 0);
INSERT INTO `chatuser` VALUES (333, '333', '董明珠', 'f', 0, 'images/111.gif', '格力制造中国制造', 0);
INSERT INTO `chatuser` VALUES (345, '345', '胡歌', 'm', 0, 'images/120.gif', '去看南方车站的聚会', 0);
INSERT INTO `chatuser` VALUES (456, '456', '王子文', 'f', 0, 'images/111.gif', '我是王子文，我为自己代言', 0);
INSERT INTO `chatuser` VALUES (567, '567', '谢娜', 'f', 0, 'images/122.gif', '菠萝菠萝蜜', 0);
INSERT INTO `chatuser` VALUES (666, '666', '666', 'f', 0, 'images/100.gif', '666', 0);
INSERT INTO `chatuser` VALUES (678, '678', '马爸爸', 'm', 0, 'images/120.gif', '我是你爸爸', 0);
INSERT INTO `chatuser` VALUES (789, '789', '马化腾', 'm', 0, 'images/102.gif', '我就是马化腾，想咋地', 0);

-- ----------------------------
-- Table structure for friend
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend`  (
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `friendType` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `friendname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of friend
-- ----------------------------
INSERT INTO `friend` VALUES ('111', '家人', '222');
INSERT INTO `friend` VALUES ('222', '家人', '111');
INSERT INTO `friend` VALUES ('111', '朋友', '123');
INSERT INTO `friend` VALUES ('123', '朋友', '111');
INSERT INTO `friend` VALUES ('111', '同学', '234');
INSERT INTO `friend` VALUES ('234', '朋友', '111');
INSERT INTO `friend` VALUES ('111', '朋友', '345');
INSERT INTO `friend` VALUES ('345', '同学', '111');
INSERT INTO `friend` VALUES ('123', '家人', '222');
INSERT INTO `friend` VALUES ('123', '朋友', '234');
INSERT INTO `friend` VALUES ('222', '朋友', '345');
INSERT INTO `friend` VALUES ('222', '同学', '234');
INSERT INTO `friend` VALUES ('222', '朋友', '456');
INSERT INTO `friend` VALUES ('333', '朋友', '111');
INSERT INTO `friend` VALUES ('333', '同学', '222');
INSERT INTO `friend` VALUES ('333', '家人', '234');
INSERT INTO `friend` VALUES ('111', '朋友', '333');
INSERT INTO `friend` VALUES ('222', '朋友', '333');
INSERT INTO `friend` VALUES ('234', '家人', '333');

SET FOREIGN_KEY_CHECKS = 1;
