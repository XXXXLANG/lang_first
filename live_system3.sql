/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50733
 Source Host           : localhost:3306
 Source Schema         : live_system

 Target Server Type    : MySQL
 Target Server Version : 50733
 File Encoding         : 65001

 Date: 13/05/2022 18:43:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for chat_history
-- ----------------------------
DROP TABLE IF EXISTS `chat_history`;
CREATE TABLE `chat_history`  (
  `CH_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CH_GroupID` int(11) NULL DEFAULT NULL COMMENT '群ID',
  `CH_FriendID` int(11) NULL DEFAULT NULL COMMENT '好友ID',
  `CH_UserID` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `CH_CreateTime` datetime NULL DEFAULT NULL COMMENT '聊天时间',
  `CH_Top` int(1) NULL DEFAULT NULL COMMENT '置顶（1：是，0：否）',
  PRIMARY KEY (`CH_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '聊天记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city`  (
  `C_ID` int(11) NOT NULL,
  `C_Name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市',
  `C_ProvinceID` int(11) NULL DEFAULT NULL COMMENT '所属省份ID',
  PRIMARY KEY (`C_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for friend_request
-- ----------------------------
DROP TABLE IF EXISTS `friend_request`;
CREATE TABLE `friend_request`  (
  `FR_ID` int(11) NOT NULL AUTO_INCREMENT,
  `FR_User` int(11) NULL DEFAULT NULL COMMENT '对象用户',
  `FR_Group` int(11) NULL DEFAULT NULL COMMENT '对象班级群',
  `FR_Applicant` int(11) NOT NULL COMMENT '申请人',
  `FR_Intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请简介',
  `FR_AliasFriend` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '好友昵称（申请人对好友的备注）',
  `FR_CreateTime` datetime NOT NULL COMMENT '发送时间',
  `FR_state` int(1) NOT NULL COMMENT '是否接受（1：接受，0：未处理，2：拒绝）',
  PRIMARY KEY (`FR_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '交友请求表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for friends
-- ----------------------------
DROP TABLE IF EXISTS `friends`;
CREATE TABLE `friends`  (
  `F_ID` int(11) NOT NULL AUTO_INCREMENT,
  `F_UserID` int(11) NOT NULL COMMENT '自己的ID',
  `F_FriendID` int(11) NOT NULL COMMENT '朋友的ID',
  `F_AliasUser` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '好友备注(USER_ID对FRIEND_ID的备注)',
  `F_AliasFriend` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '好友备注(FRIEND_ID对USER_ID的备注)',
  PRIMARY KEY (`F_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for interaction
-- ----------------------------
DROP TABLE IF EXISTS `interaction`;
CREATE TABLE `interaction`  (
  `I_ID` int(11) NOT NULL AUTO_INCREMENT,
  `I_Pattern` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '互动方式',
  `I_UserID` int(11) NULL DEFAULT NULL COMMENT '发送者',
  `I_CreateTime` datetime NULL DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`I_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '互动方式' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for nation
-- ----------------------------
DROP TABLE IF EXISTS `nation`;
CREATE TABLE `nation`  (
  `N_ID` int(11) NOT NULL,
  `N_Name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国家全名',
  `N_Short` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简称',
  PRIMARY KEY (`N_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for onlinestate
-- ----------------------------
DROP TABLE IF EXISTS `onlinestate`;
CREATE TABLE `onlinestate`  (
  `O_ID` int(11) NOT NULL AUTO_INCREMENT,
  `O_UserID` int(11) NOT NULL COMMENT '群组ID',
  `O_Client` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端地址（rtmp://...）',
  `O_State` int(1) NOT NULL COMMENT '直播状态（1：在线，0：离线）',
  `O_Datatime` datetime NULL DEFAULT NULL COMMENT '最近登录时间',
  PRIMARY KEY (`O_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for position
-- ----------------------------
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position`  (
  `SP_ID` int(11) NOT NULL,
  `SP_Name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职位名称',
  PRIMARY KEY (`SP_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for province
-- ----------------------------
DROP TABLE IF EXISTS `province`;
CREATE TABLE `province`  (
  `P_ID` int(11) NOT NULL,
  `P_Name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份',
  `P_NationID` int(11) NULL DEFAULT NULL COMMENT '国家ID',
  PRIMARY KEY (`P_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for school
-- ----------------------------
DROP TABLE IF EXISTS `school`;
CREATE TABLE `school`  (
  `S_ID` int(11) NOT NULL,
  `S_Name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学校名称',
  PRIMARY KEY (`S_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `U_ID` int(11) NOT NULL AUTO_INCREMENT,
  `U_LoginID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `U_NickName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `U_PassWord` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `U_SignaTure` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个性签名',
  `U_Sex` int(1) NULL DEFAULT NULL COMMENT '性别（1：男，0：女）',
  `U_Birthday` datetime NULL DEFAULT NULL COMMENT '生日',
  `U_Sno` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学号',
  `U_Telephone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号码',
  `U_Email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `U_HeadPortrait` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `U_SchoolTag` int(20) NULL DEFAULT NULL COMMENT '学校',
  `U_position` int(11) NULL DEFAULT NULL COMMENT '学校职位（也包括“学生”）',
  `U_NationID` int(11) NULL DEFAULT NULL COMMENT '国家',
  `U_ProvinceID` int(11) NULL DEFAULT NULL COMMENT '省份',
  `U_CityID` int(11) NULL DEFAULT NULL COMMENT '城市',
  PRIMARY KEY (`U_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_groups
-- ----------------------------
DROP TABLE IF EXISTS `user_groups`;
CREATE TABLE `user_groups`  (
  `UG_ID` int(11) NOT NULL AUTO_INCREMENT,
  `UG_Name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '群名称',
  `UG_GroupNum` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '班级账号（自动生成）',
  `UG_CreateTime` datetime NOT NULL COMMENT '创建时间',
  `UG_AdminID` int(11) NOT NULL COMMENT '群主',
  `UG_ICon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '群图标',
  `UG_Notice` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公告',
  `UG_Intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
  `UG_Verification` int(1) NOT NULL COMMENT '进群验证（1：是，0：否）',
  PRIMARY KEY (`UG_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_groupsmsgcontent
-- ----------------------------
DROP TABLE IF EXISTS `user_groupsmsgcontent`;
CREATE TABLE `user_groupsmsgcontent`  (
  `GM_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `GM_FromID` int(11) NULL DEFAULT NULL COMMENT '发送者ID',
  `GM_Content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息内容',
  `GM_CreateTime` datetime NULL DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`GM_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 125 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '群消息内容表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_groupsmsgtouser
-- ----------------------------
DROP TABLE IF EXISTS `user_groupsmsgtouser`;
CREATE TABLE `user_groupsmsgtouser`  (
  `GM_ID` int(11) NOT NULL AUTO_INCREMENT,
  `GM_UserID` int(11) NULL DEFAULT NULL COMMENT '接收群ID',
  `GM_GroupMessageID` int(11) NULL DEFAULT NULL COMMENT '消息ID',
  PRIMARY KEY (`GM_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 125 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '群消息接收表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_groupstouser
-- ----------------------------
DROP TABLE IF EXISTS `user_groupstouser`;
CREATE TABLE `user_groupstouser`  (
  `UGT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `UGT_UserID` int(11) NOT NULL COMMENT '用户ID',
  `UGT_GroupID` int(11) NOT NULL COMMENT '群ID',
  `UGT_GroupNick` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '群内用户昵称',
  `UGT_Reminding` int(1) NOT NULL COMMENT '用户是否消息免打扰（1：是；0：否）',
  PRIMARY KEY (`UGT_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '群内各用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_msgusertouser
-- ----------------------------
DROP TABLE IF EXISTS `user_msgusertouser`;
CREATE TABLE `user_msgusertouser`  (
  `M_ID` int(11) NOT NULL AUTO_INCREMENT,
  `M_FromUserID` int(11) NULL DEFAULT NULL COMMENT '发送者',
  `M_ToUserID` int(11) NULL DEFAULT NULL COMMENT '接收者',
  `M_MSGContent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容',
  `M_State` int(1) NULL DEFAULT NULL COMMENT '接收状态(1：已读，0：未读)',
  `M_CreateTime` datetime NULL DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`M_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 113 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '私聊消息关联表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
