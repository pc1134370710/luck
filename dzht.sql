/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : dzht

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 17/03/2023 23:49:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ht
-- ----------------------------
DROP TABLE IF EXISTS `ht`;
CREATE TABLE `ht`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `htname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同名称',
  `userEmail` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同发起人邮箱',
  `qsuserEmail` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同签署人邮箱',
  `hturl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同虚拟路径',
  `htpath` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同真实路径',
  `starttime` datetime(0) NULL DEFAULT NULL COMMENT '合同开始时间',
  `endtime` datetime(0) NULL DEFAULT NULL COMMENT '合同到期时间',
  `qsjztime` datetime(0) NULL DEFAULT NULL COMMENT '签署截止时间',
  `qstime` datetime(0) NULL DEFAULT NULL COMMENT '签署时间',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `state` int(1) NULL DEFAULT 0 COMMENT '0待签署，1已签署，2已拒签，3已撤销，4已逾期，5已过期',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发起人姓名',
  `qsusername` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签署人姓名',
  `is_del` int(1) NULL DEFAULT 0 COMMENT '1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ht
-- ----------------------------
INSERT INTO `ht` VALUES (9, 'ggg', '1134370710@qq.com', '1134370710@qq.com', '/ht/1618042553513MySQL学习笔记.pdf', 'D:/dzht/ht/1618042553513MySQL学习笔记.pdf', '2021-04-10 16:16:08', '2021-04-30 00:00:00', '2021-04-17 00:00:00', '2021-04-10 18:32:11', '2021-04-10 16:16:07', 5, '臭猪猪', '侧翻时', 0);
INSERT INTO `ht` VALUES (10, '2312', '1134370710@qq.com', '1134370710@qq.com', '/ht/1618050814491MySQL学习笔记.pdf', 'D:/dzht/ht/1618050814491MySQL学习笔记.pdf', '2021-04-10 18:33:45', '2021-04-24 00:00:00', '2021-04-16 00:00:00', '2021-04-10 18:38:29', '2021-04-10 18:33:44', 5, '臭猪猪', '侧翻时', 0);
INSERT INTO `ht` VALUES (20, 'asd', '1134370710@qq.com', '1924873998@qq.com', '/ht/1634805492473invoice.pdf', '/home/dzht/ht/1634805492473invoice.pdf', '2021-10-21 16:38:19', '2021-10-30 00:00:00', '2021-10-22 00:00:00', '2021-10-21 19:05:01', '2021-10-21 16:38:18', 3, '臭猪猪', 'ccc', 0);
INSERT INTO `ht` VALUES (21, '汽车租赁合同', '1134370710@qq.com', '1134370710@qq.com', '/ht/1634811426148汽车租赁合同_20211021181532.pdf', '/home/dzht/ht/1634811426148汽车租赁合同_20211021181532.pdf', '2021-10-21 18:17:25', '2021-10-28 00:00:00', '2021-10-24 00:00:00', '2021-10-21 18:21:42', '2021-10-21 18:17:25', 5, '臭猪猪', '侧翻时', 0);
INSERT INTO `ht` VALUES (22, '对对对', '1134370710@qq.com', '1134370710@qq.com', '/ht/16348231060321134370710.pdf', '/home/dzht/ht/16348231060321134370710.pdf', '2021-10-21 21:32:00', '2021-10-30 00:00:00', '2021-10-28 00:00:00', NULL, '2021-10-21 21:32:00', 9, '臭猪猪', '臭猪猪', 1);
INSERT INTO `ht` VALUES (23, '1134370710@qq.com', '1134370710@qq.com', '1134370710@qq.com', '/ht/16348234771291134370710.pdf', '/home/dzht/ht/16348234771291134370710.pdf', '2021-10-21 21:38:06', '2021-10-27 00:00:00', '2021-10-22 00:00:00', NULL, '2021-10-21 21:38:06', 9, '臭猪猪', '臭猪猪', 1);
INSERT INTO `ht` VALUES (24, '合同', '1134370710@qq.com', '1134370710@qq.com', '/ht/1634832299600汇聚融合同.pdf', '/home/dzht/ht/1634832299600汇聚融合同.pdf', '2021-10-22 00:06:16', '2021-10-23 00:00:00', '2021-10-22 00:00:00', NULL, '2021-10-22 00:06:15', 9, '臭猪猪', '臭猪猪', 1);
INSERT INTO `ht` VALUES (25, '大萨达', '1134370710@qq.com', '1134370710@qq.com', '/ht/1634832474041汇聚融合同.pdf', '/home/dzht/ht/1634832474041汇聚融合同.pdf', '2021-10-22 00:08:06', '2021-10-29 00:00:00', '2021-10-28 00:00:00', '2021-10-22 00:09:41', '2021-10-22 00:08:05', 5, '臭猪猪', '侧翻时', 0);
INSERT INTO `ht` VALUES (26, '1134370710@qq.com', '1134370710@qq.com', '1134370710@qq.com', '/ht/1634862995900汇聚融合同(1)-已转档.pdf', '/home/dzht/ht/1634862995900汇聚融合同(1)-已转档.pdf', '2021-10-22 08:36:42', '2021-10-28 00:00:00', '2021-10-22 00:00:00', NULL, '2021-10-22 08:36:41', 9, '臭猪猪', '臭猪猪', 1);
INSERT INTO `ht` VALUES (27, '1134370710@qq.com', '1134370710@qq.com', '1134370710@qq.com', '/ht/1634863430287汇聚融合同(1)-已转档.pdf', '/home/dzht/ht/1634863430287汇聚融合同(1)-已转档.pdf', '2021-10-22 08:43:56', '2021-10-29 00:00:00', '2021-10-22 00:00:00', NULL, '2021-10-22 08:43:56', 9, '臭猪猪', '臭猪猪', 1);
INSERT INTO `ht` VALUES (28, '1134370710@qq.com', '1134370710@qq.com', '1134370710@qq.com', '/ht/1634889220920contract_40425738_99b9571730514b66979ef261d6a5973b.pdf', '/home/dzht/ht/1634889220920contract_40425738_99b9571730514b66979ef261d6a5973b.pdf', '2021-10-22 15:53:52', '2021-10-29 00:00:00', '2021-10-28 00:00:00', NULL, '2021-10-22 15:53:51', 9, '臭猪猪', '臭猪猪', 1);
INSERT INTO `ht` VALUES (29, '1134370710@qq.com', '1134370710@qq.com', '1134370710@qq.com', '/ht/1635254603125好签小程序手册.pdf', '/home/dzht/ht/1635254603125好签小程序手册.pdf', '2021-10-26 21:23:46', '2021-10-30 21:23:26', '2021-10-27 21:23:27', '2021-10-26 21:25:41', '2021-10-26 21:23:46', 5, '臭猪猪', '侧翻时', 0);
INSERT INTO `ht` VALUES (30, '租证协议书', '1134370710@qq.com', '1134370710@qq.com', '/ht/1635755041557租证协议书.pdf', '/home/dzht/ht/1635755041557租证协议书.pdf', '2021-11-01 16:25:13', '2021-12-31 00:00:00', '2021-11-01 16:17:12', NULL, '2021-11-01 16:25:13', 9, '臭猪猪', '臭猪猪', 1);
INSERT INTO `ht` VALUES (31, '11222', '1134370710@qq.com', '1134370710@qq.com', '/ht/1635931130777二次认证排期.pdf', '/home/dzht/ht/1635931130777二次认证排期.pdf', '2021-11-03 17:19:21', '2021-11-03 17:19:06', '2021-11-03 00:00:00', NULL, '2021-11-03 17:19:20', 5, '臭猪猪', '侧翻时', 0);
INSERT INTO `ht` VALUES (32, 'ttttttt', '1134370710@qq.com', '1134370710@qq.com', '/ht/1635988759304工作记录.pdf', '/home/dzht/ht/1635988759304工作记录.pdf', '2021-11-04 09:19:43', '2021-11-26 00:00:00', '2021-11-15 00:00:00', NULL, '2021-11-04 09:19:42', 9, '臭猪猪', '臭猪猪', 1);
INSERT INTO `ht` VALUES (33, '啊实打实D', '1134370710@qq.com', '1134370710@qq.com', '/ht/1636029865357MySQL学习笔记.pdf', '/home/dzht/ht/1636029865357MySQL学习笔记.pdf', '2021-11-04 20:44:36', '2021-11-27 00:00:00', '2021-11-17 00:00:00', NULL, '2021-11-04 20:44:36', 9, '臭猪猪', '侧翻时', 0);
INSERT INTO `ht` VALUES (34, '6666', '1134370710@qq.com', '1134370710@qq.com', '/ht/1636158008166P020160118318054552431.pdf', '/home/dzht/ht/1636158008166P020160118318054552431.pdf', '2021-11-06 08:20:21', '2021-11-30 00:00:00', '2021-11-06 00:00:00', NULL, '2021-11-06 08:20:20', 9, '臭猪猪', '侧翻时', 0);
INSERT INTO `ht` VALUES (35, '555', '1134370710@qq.com', '1134370710@qq.com', '/ht/1636220183320P020160118318054552431.pdf', '/home/dzht/ht/1636220183320P020160118318054552431.pdf', '2021-11-07 01:38:42', '2021-12-10 00:00:00', '2021-11-25 00:00:00', '2021-11-07 01:41:57', '2021-11-07 01:38:42', 5, '臭猪猪', '侧翻时', 0);
INSERT INTO `ht` VALUES (36, 'ddd', '1134370710@qq.com', '1134370710@qq.com', '/ht/1636222187401088c05f41074749ee054ac2908aa6f0c.pdf', '/home/dzht/ht/1636222187401088c05f41074749ee054ac2908aa6f0c.pdf', '2021-11-07 02:14:35', '2021-11-29 00:00:00', '2021-11-07 00:00:00', NULL, '2021-11-07 02:14:35', 9, '臭猪猪', '臭猪猪', 1);
INSERT INTO `ht` VALUES (37, 'dgfsd', '1134370710@qq.com', '1134370710@qq.com', '/ht/1636223067804088c05f41074749ee054ac2908aa6f0c.pdf', '/home/dzht/ht/1636223067804088c05f41074749ee054ac2908aa6f0c.pdf', '2021-11-07 02:24:40', '2021-11-24 00:00:00', '2021-11-07 00:00:00', NULL, '2021-11-07 02:24:40', 9, '臭猪猪', '臭猪猪', 1);
INSERT INTO `ht` VALUES (38, '5555555555', '1134370710@qq.com', '1134370710@qq.com', '/ht/1636223613408P020160118318054552431.pdf', '/home/dzht/ht/1636223613408P020160118318054552431.pdf', '2021-11-07 02:33:53', '2021-11-16 00:00:00', '2021-11-07 00:00:00', NULL, '2021-11-07 02:33:52', 9, '臭猪猪', '臭猪猪', 1);
INSERT INTO `ht` VALUES (39, 'cccccccc', '1134370710@qq.com', '1134370710@qq.com', '/ht/1636223695567P020160118318054552431.pdf', '/home/dzht/ht/1636223695567P020160118318054552431.pdf', '2021-11-07 02:35:20', '2021-11-30 00:00:00', '2021-11-11 00:00:00', '2021-11-07 02:35:37', '2021-11-07 02:35:20', 3, '臭猪猪', '侧翻时', 0);
INSERT INTO `ht` VALUES (40, 'test', '1134370710@qq.com', '1134370710@qq.com', '/ht/1636639491362MySQL学习笔记.pdf', '/home/dzht/ht/1636639491362MySQL学习笔记.pdf', '2021-11-11 22:05:29', '2021-11-11 22:07:00', '2021-11-11 22:06:00', '2021-11-11 22:05:54', '2021-11-11 22:05:28', 4, '臭猪猪', '侧翻时', 0);
INSERT INTO `ht` VALUES (41, '测试过去', '1134370710@qq.com', '1134370710@qq.com', '/ht/1636639705534MySQL学习笔记.pdf', '/home/dzht/ht/1636639705534MySQL学习笔记.pdf', '2021-11-11 22:08:54', '2021-11-11 22:11:32', '2021-11-11 22:10:46', '2021-11-11 22:09:09', '2021-11-11 22:08:54', 4, '臭猪猪', '侧翻时', 0);
INSERT INTO `ht` VALUES (42, '啊啊实打实', '1134370710@qq.com', '1134370710@qq.com', '/ht/1636640423132MySQL学习笔记.pdf', '/home/dzht/ht/1636640423132MySQL学习笔记.pdf', '2021-11-11 22:20:58', '2021-11-11 22:23:41', '2021-11-11 22:21:47', '2021-11-11 22:21:12', '2021-11-11 22:20:58', 4, '臭猪猪', '侧翻时', 0);
INSERT INTO `ht` VALUES (43, '66666', '1134370710@qq.com', '1134370710@qq.com', '/ht/1636641270162MySQL学习笔记.pdf', '/home/dzht/ht/1636641270162MySQL学习笔记.pdf', '2021-11-11 22:34:48', '2021-11-11 22:36:34', '2021-11-11 22:35:39', '2021-11-11 22:34:58', '2021-11-11 22:34:47', 4, '臭猪猪', '侧翻时', 0);
INSERT INTO `ht` VALUES (44, '3213', '1134370710@qq.com', '1134370710@qq.com', '/ht/1636641597398MySQL学习笔记.pdf', '/home/dzht/ht/1636641597398MySQL学习笔记.pdf', '2021-11-11 22:40:16', '2021-11-11 22:42:02', '2021-11-11 22:41:07', '2021-11-11 22:40:27', '2021-11-11 22:40:16', 4, '臭猪猪', '侧翻时', 0);
INSERT INTO `ht` VALUES (45, '669', '1134370710@qq.com', '1134370710@qq.com', '/ht/1636642297454MySQL学习笔记.pdf', '/home/dzht/ht/1636642297454MySQL学习笔记.pdf', '2021-11-11 22:51:55', '2021-11-11 22:53:41', '2021-11-11 22:52:46', '2021-11-11 22:52:07', '2021-11-11 22:51:55', 4, '臭猪猪', '侧翻时', 0);
INSERT INTO `ht` VALUES (46, 'asdasdasdasd', '1134370710@qq.com', '1134370710@qq.com', '/ht/1636642470723MySQL学习笔记.pdf', '/home/dzht/ht/1636642470723MySQL学习笔记.pdf', '2021-11-11 22:55:02', '2021-11-11 22:55:30', '2021-11-11 22:55:20', '2021-11-11 22:55:12', '2021-11-11 22:55:02', 4, '臭猪猪', '侧翻时', 0);
INSERT INTO `ht` VALUES (47, '少时诵诗书所所所所所所所所', '1134370710@qq.com', '1134370710@qq.com', '/ht/1636642906756MySQL学习笔记.pdf', '/home/dzht/ht/1636642906756MySQL学习笔记.pdf', '2021-11-11 23:02:14', '2021-11-11 23:04:50', '2021-11-11 23:03:40', '2021-11-11 23:02:23', '2021-11-11 23:02:13', 4, '臭猪猪', '侧翻时', 0);
INSERT INTO `ht` VALUES (48, 'gggg', '1134370710@qq.com', '1134370710@qq.com', '/ht/1636686859314微服务入门&Nocas实操.pdf', '/home/dzht/ht/1636686859314微服务入门&Nocas实操.pdf', '2021-11-12 11:14:54', '2021-11-12 11:20:25', '2021-11-12 11:14:41', NULL, '2021-11-12 11:14:54', 9, '臭猪猪', '臭猪猪', 1);
INSERT INTO `ht` VALUES (49, '合同', '1134370710@qq.com', '1134370710@qq.com', '/ht/1637045796273合同.pdf', '/home/dzht/ht/1637045796273合同.pdf', '2021-11-16 14:56:44', '2021-11-30 00:00:00', '2021-11-29 00:00:00', '2021-11-16 16:11:19', '2021-11-16 14:56:44', 4, '臭猪猪', '侧翻时', 0);
INSERT INTO `ht` VALUES (50, '11312313133', '1134370710@qq.com', '1134370710@qq.com', '/ht/1637198328808P2P行业研究报告-360营销研究院.pdf', '/home/dzht/ht/1637198328808P2P行业研究报告-360营销研究院.pdf', '2021-11-18 09:18:50', '2021-11-30 00:00:00', '2021-11-26 00:00:00', NULL, '2021-11-18 09:18:50', 9, '臭猪猪', '侧翻时', 0);
INSERT INTO `ht` VALUES (51, '123', '1134370710@qq.com', '1134370710@qq.com', '/ht/1638333256274测试合同.pdf', '/home/dzht/ht/1638333256274测试合同.pdf', '2021-12-01 12:34:27', '2021-12-22 00:00:00', '2021-12-17 00:00:00', '2021-12-07 14:33:45', '2021-12-01 12:34:27', 4, '臭猪猪', '侧翻时', 0);
INSERT INTO `ht` VALUES (52, 'test', '1134370710@qq.com', '1134370710@qq.com', '/ht/1638344219259东信容器云用户手册_1026.pdf', '/home/dzht/ht/1638344219259东信容器云用户手册_1026.pdf', '2021-12-01 15:37:01', '2021-12-30 00:00:00', '2021-12-01 15:36:42', NULL, '2021-12-01 15:37:00', 9, '臭猪猪', '臭猪猪', 1);
INSERT INTO `ht` VALUES (53, '大范甘迪个', '1134370710@qq.com', '1134370710@qq.com', '/ht/16390394980272021年底即将到期的税收优惠政策汇总（20211203）.pdf', '/home/dzht/ht/16390394980272021年底即将到期的税收优惠政策汇总（20211203）.pdf', '2021-12-09 16:45:00', '2022-12-31 00:00:00', '2021-12-09 00:00:00', NULL, '2021-12-09 16:45:00', 9, '臭猪猪', '臭猪猪', 1);
INSERT INTO `ht` VALUES (54, '飞天', '1134370710@qq.com', '1134370710@qq.com', '/ht/16390398522472021年底即将到期的税收优惠政策汇总（20211203）.pdf', '/home/dzht/ht/16390398522472021年底即将到期的税收优惠政策汇总（20211203）.pdf', '2021-12-09 16:51:13', '2021-12-31 00:00:00', '2021-12-31 00:00:00', '2021-12-09 16:51:49', '2021-12-09 16:51:12', 3, '臭猪猪', '侧翻时', 0);
INSERT INTO `ht` VALUES (55, '22222222', '1134370710@qq.com', '1134370710@qq.com', '/ht/1640842310348222222222.pdf', '/home/dzht/ht/1640842310348222222222.pdf', '2021-12-30 13:32:05', '2022-01-01 00:00:00', '2021-12-30 00:00:00', NULL, '2021-12-30 13:32:04', 9, '臭猪猪', '臭猪猪', 1);
INSERT INTO `ht` VALUES (56, '未命名合同', '1134370710@qq.com', '1134370710@qq.com', '/ht/1641362727595未命名1.pdf', '/home/dzht/ht/1641362727595未命名1.pdf', '2022-01-05 14:06:23', '2022-01-06 00:00:00', '2022-01-06 00:00:00', NULL, '2022-01-05 14:06:23', 9, '臭猪猪', '臭猪猪', 1);
INSERT INTO `ht` VALUES (57, '飞飞', '1134370710@qq.com', '1134370710@qq.com', '/ht/1643272271047__.pdf', '/home/dzht/ht/1643272271047__.pdf', '2022-01-27 16:31:36', '2022-05-31 00:00:00', '2022-01-31 00:00:00', NULL, '2022-01-27 16:31:36', 9, '臭猪猪', '臭猪猪', 1);
INSERT INTO `ht` VALUES (58, 'test', '1134370710@qq.com', '1134370710@qq.com', '/ht/1645014054365__.pdf', '/home/dzht/ht/1645014054365__.pdf', '2022-02-16 20:21:12', '2022-02-28 00:00:00', '2022-02-28 00:00:00', NULL, '2022-02-16 20:21:11', 9, '臭猪猪', '侧翻时', 0);
INSERT INTO `ht` VALUES (59, '测试合同', '1134370710@qq.com', '1134370710@qq.com', '/ht/1645522021937rbac接口文档.pdf', '/home/dzht/ht/1645522021937rbac接口文档.pdf', '2022-02-22 17:35:05', '2029-01-01 00:00:00', '2022-02-22 17:35:03', NULL, '2022-02-22 17:35:04', 9, '侧翻时', '侧翻时', 1);
INSERT INTO `ht` VALUES (60, 'werwe', '1134370710@qq.com', '1134370710@qq.com', '/ht/1646126573907Java 最常见 200+ 面试题全解析：面试必备.pdf', '/home/dzht/ht/1646126573907Java 最常见 200+ 面试题全解析：面试必备.pdf', '2022-03-01 17:23:03', '2022-03-31 00:00:00', '2022-03-16 00:00:00', NULL, '2022-03-01 17:23:03', 9, '侧翻时', '侧翻时', 1);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `NO` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `birthday` date NULL DEFAULT NULL,
  `profession` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `department` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`NO`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '未认证' COMMENT '用户姓名',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `sfz_url_z` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证正面',
  `sfz_url_f` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证反面',
  `qspassword` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签署合同密码',
  `start` int(1) NULL DEFAULT 0 COMMENT '用户状态，0未认证，1审核中，2不通过，3已认证',
  `salt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加密盐',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像路径',
  `type` int(1) NULL DEFAULT 0 COMMENT '1为管理员，0位普通用户，2超级管路',
  `sfz` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10007 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (10003, '张三', '1134370710@qq.com', '00619e7902344270d20f9b80d82c5470', 'http://106.52.48.21/dzht/user/sfz/WechatIMG15.png', 'http://106.52.48.21/dzht/user/sfz/WechatIMG15.png', '00619e7902344270d20f9b80d82c5470', 3, '67738ed9ceee407ca118', 'http://106.52.48.21/dzht/user/img/2DDE889CD74D3DBE8891AF2C0E042A18.png', 1, '431100211204512014');
INSERT INTO `user` VALUES (10004, 'ccc', '1924873998@qq.com', 'cdd727615af19ab4864b58669953af1d', 'http://106.52.48.21/dzht/user/img/2DDE889CD74D3DBE8891AF2C0E042A18.png', 'http://106.52.48.21/dzht/user/img/2DDE889CD74D3DBE8891AF2C0E042A18.png', 'cdd727615af19ab4864b58669953af1d', 3, 'cc8ef1d67e5c40da9de5', NULL, 1, '123456');
INSERT INTO `user` VALUES (10005, '未认证', '59010986@qq.com', 'fe12f9bdce170a1de16745c7ac616b52', NULL, NULL, NULL, 3, '2a928db4d4184623b7ae', NULL, 0, NULL);
INSERT INTO `user` VALUES (10006, '未认证', 'bach1100@qq.com', 'a0762f64c1c1f30b3e430229e207fcad', NULL, NULL, NULL, 3, 'f9c8e77abe90456fb416', NULL, 0, NULL);

SET FOREIGN_KEY_CHECKS = 1;
