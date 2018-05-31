/*
MySQL Data Transfer
Source Host: 120.24.239.96
Source Database: dymanager
Target Host: 120.24.239.96
Target Database: dymanager
Date: 2016/11/10 11:35:29
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for sys_right
-- ----------------------------
CREATE TABLE `sys_right` (
  `rightId` varchar(50) NOT NULL COMMENT '限权ID',
  `rightName` varchar(50) DEFAULT NULL COMMENT '限权名称',
  PRIMARY KEY (`rightId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
CREATE TABLE `sys_user` (
  `userName` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `name` varchar(50) NOT NULL COMMENT '真实姓名',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `state` int(1) DEFAULT '0' COMMENT '状态 1登录 ,0注销',
  `department` varchar(50) NOT NULL COMMENT '所属部门',
  `superAdmin` int(1) DEFAULT '0' COMMENT '是否为超级管理员0=不是,1=是',
  `rightList` varchar(100) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `comment` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`userName`),
  KEY `fk_rightList` (`rightList`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_certificate
-- ----------------------------
CREATE TABLE `tbl_business_certificate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sapNo` varchar(100) NOT NULL COMMENT '仪器SAP代码',
  `calibrationCertificate` varchar(200) NOT NULL COMMENT '证书名称',
  `certificateType` int(11) NOT NULL COMMENT '书证类型',
  `qualificationAttribution` varchar(50) NOT NULL COMMENT '资质归属',
  `calibStartTime` date DEFAULT NULL COMMENT '证书有效开始日期',
  `calibEndTime` date DEFAULT NULL COMMENT '书证有效束结时间',
  `reviser` varchar(50) DEFAULT NULL COMMENT '修订者',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `fk_cerTisapNo` (`sapNo`),
  CONSTRAINT `tbl_business_certificate_ibfk_1` FOREIGN KEY (`sapNo`) REFERENCES `tbl_business_instrumentinfo` (`sapNo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_certificatetype
-- ----------------------------
CREATE TABLE `tbl_business_certificatetype` (
  `certificateNo` int(11) NOT NULL AUTO_INCREMENT COMMENT '证书编号',
  `certificateName` varchar(50) NOT NULL COMMENT '证书类型',
  PRIMARY KEY (`certificateNo`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_circuitboard
-- ----------------------------
CREATE TABLE `tbl_business_circuitboard` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sapNo` varchar(100) NOT NULL COMMENT '器仪sap代码',
  `circuitBoardSapNo` varchar(100) DEFAULT NULL COMMENT '电路板sap代码',
  `circuitBoardName` varchar(200) DEFAULT NULL COMMENT '电路板名称',
  `circuitBoardVersion` varchar(50) NOT NULL COMMENT '电路板BOM单版次',
  `createPerson` varchar(50) DEFAULT NULL COMMENT '创建者',
  `updateTime` datetime DEFAULT NULL COMMENT '创建,修改时间',
  `revisedRecord` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `fk_circuitSapNo` (`sapNo`),
  CONSTRAINT `fk_circuitSapNo` FOREIGN KEY (`sapNo`) REFERENCES `tbl_business_instrumentinfo` (`sapNo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_circuitboarddetail
-- ----------------------------
CREATE TABLE `tbl_business_circuitboarddetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `circuitId` int(11) NOT NULL,
  `materielNo` varchar(50) NOT NULL COMMENT '器件名称',
  `locationNo` varchar(200) DEFAULT NULL COMMENT '位号',
  `quantity` int(50) DEFAULT NULL COMMENT '数量',
  `updateTime` datetime DEFAULT NULL,
  `comments` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `materielNo` (`materielNo`),
  KEY `circuitId` (`circuitId`),
  CONSTRAINT `tbl_business_circuitboarddetail_ibfk_4` FOREIGN KEY (`materielNo`) REFERENCES `tbl_business_materiel` (`materielNo`) ON DELETE CASCADE,
  CONSTRAINT `tbl_business_circuitboarddetail_ibfk_5` FOREIGN KEY (`circuitId`) REFERENCES `tbl_business_circuitboard` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=148 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_completemachine
-- ----------------------------
CREATE TABLE `tbl_business_completemachine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sapNo` varchar(100) NOT NULL,
  `completeMachineName` varchar(200) DEFAULT NULL,
  `completeMachineVersion` varchar(50) NOT NULL COMMENT '整机BOM单版本号',
  `createPerson` varchar(50) DEFAULT NULL COMMENT '创建者',
  `updateTime` datetime DEFAULT NULL COMMENT '创建,修改时间',
  `revisedRecord` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `fk_complSapNo` (`sapNo`),
  CONSTRAINT `fk_complSapNo` FOREIGN KEY (`sapNo`) REFERENCES `tbl_business_instrumentinfo` (`sapNo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_completemachinedetail
-- ----------------------------
CREATE TABLE `tbl_business_completemachinedetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `compleId` int(11) NOT NULL,
  `materielNo` varchar(50) NOT NULL COMMENT '器件名称',
  `quantity` int(11) DEFAULT NULL COMMENT '数量',
  `updateTime` datetime DEFAULT NULL,
  `comments` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `materielNo` (`materielNo`),
  KEY `fk_compleId` (`compleId`),
  CONSTRAINT `fk_compleId` FOREIGN KEY (`compleId`) REFERENCES `tbl_business_completemachine` (`id`) ON DELETE CASCADE,
  CONSTRAINT `tbl_business_completemachinedetail_ibfk_1` FOREIGN KEY (`materielNo`) REFERENCES `tbl_business_materiel` (`materielNo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=238 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_document
-- ----------------------------
CREATE TABLE `tbl_business_document` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sapNo` varchar(100) NOT NULL DEFAULT '' COMMENT '器仪sap代码',
  `fileName` varchar(200) DEFAULT NULL COMMENT '说明书名称',
  `reviser` varchar(50) DEFAULT NULL COMMENT '修订者',
  `version` varchar(50) NOT NULL DEFAULT '' COMMENT '本号版',
  `updateTime` datetime DEFAULT NULL COMMENT '上传时间',
  `revisedRecord` varchar(200) DEFAULT NULL COMMENT '修订记录',
  PRIMARY KEY (`id`),
  KEY `fk_documentSapNo` (`sapNo`),
  CONSTRAINT `fk_documentSapNo` FOREIGN KEY (`sapNo`) REFERENCES `tbl_business_instrumentinfo` (`sapNo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_instrumentinfo
-- ----------------------------
CREATE TABLE `tbl_business_instrumentinfo` (
  `sapNo` varchar(100) NOT NULL COMMENT 'SAP代码',
  `brand` varchar(50) NOT NULL COMMENT '品牌名称',
  `productName` varchar(50) NOT NULL COMMENT '产品名称',
  `productLinel` varchar(50) NOT NULL COMMENT '归属生产线',
  `listedTime` date DEFAULT NULL COMMENT '上市日期',
  `picture` varchar(200) DEFAULT NULL COMMENT '器仪图片',
  `executionStandard` varchar(100) DEFAULT NULL COMMENT '执行标准',
  `state` int(1) DEFAULT '0' COMMENT '状态(0=在售,1=退市)',
  `delistingDate` date DEFAULT NULL COMMENT '退市日期',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `netWeight` varchar(50) DEFAULT NULL COMMENT '裸机重量',
  `netSize` varchar(50) DEFAULT NULL COMMENT '裸机尺寸',
  `grossWeight` varchar(50) DEFAULT NULL COMMENT '装箱重量',
  `grossSize` varchar(50) DEFAULT NULL COMMENT '装箱尺寸',
  `comment` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`sapNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_manual
-- ----------------------------
CREATE TABLE `tbl_business_manual` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sapNo` varchar(100) NOT NULL COMMENT '仪器SAP代码',
  `fileName` varchar(200) NOT NULL COMMENT '说明书名称',
  `reviser` varchar(50) NOT NULL COMMENT '修订人',
  `version` varchar(50) NOT NULL DEFAULT '' COMMENT '版本号',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `revisedRecord` varchar(200) DEFAULT NULL COMMENT '修订记录',
  PRIMARY KEY (`id`),
  KEY `fk_sapNo` (`sapNo`),
  CONSTRAINT `fk_sapNo` FOREIGN KEY (`sapNo`) REFERENCES `tbl_business_instrumentinfo` (`sapNo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_materiel
-- ----------------------------
CREATE TABLE `tbl_business_materiel` (
  `materielNo` varchar(50) NOT NULL COMMENT '物料编号',
  `materielName` varchar(100) NOT NULL COMMENT '物料名称',
  `materielTypeId` int(11) NOT NULL COMMENT '料物种类',
  `modelSpecification` varchar(100) DEFAULT NULL COMMENT '型号规格',
  `footprint` varchar(500) DEFAULT NULL COMMENT '装封',
  `picture` varchar(200) DEFAULT NULL COMMENT '图片',
  `updateTime` datetime DEFAULT NULL,
  `comment` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`materielNo`),
  KEY `materielTypeId` (`materielTypeId`),
  CONSTRAINT `tbl_business_materiel_ibfk_1` FOREIGN KEY (`materielTypeId`) REFERENCES `tbl_business_materietype` (`materielTypeId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_materieldrawings
-- ----------------------------
CREATE TABLE `tbl_business_materieldrawings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `materielNo` varchar(50) NOT NULL COMMENT '物料编号',
  `drawingName` varchar(100) NOT NULL COMMENT '图纸名称',
  `version` varchar(50) NOT NULL DEFAULT '' COMMENT '版本号',
  `reviser` varchar(50) DEFAULT NULL COMMENT '修订人',
  `updateTime` date DEFAULT NULL COMMENT '上传时间',
  `revisedRecord` varchar(200) DEFAULT NULL COMMENT 'ä¿®è®¢è®°å½•',
  PRIMARY KEY (`id`),
  KEY `fk_materielNo` (`materielNo`),
  CONSTRAINT `fk_materielNo` FOREIGN KEY (`materielNo`) REFERENCES `tbl_business_materiel` (`materielNo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_materietype
-- ----------------------------
CREATE TABLE `tbl_business_materietype` (
  `materielTypeId` int(11) NOT NULL COMMENT '物料类型编号',
  `materielTypeName` varchar(50) NOT NULL COMMENT '物料类型名称',
  PRIMARY KEY (`materielTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_packinglist
-- ----------------------------
CREATE TABLE `tbl_business_packinglist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sapNo` varchar(100) NOT NULL DEFAULT '',
  `packingListName` varchar(200) NOT NULL DEFAULT '' COMMENT '仪器sap代码',
  `packingListVersion` varchar(50) NOT NULL DEFAULT '' COMMENT '装箱清单版本号',
  `createPerson` varchar(50) DEFAULT NULL COMMENT '修订者',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `revisedRecord` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_packSapNo` (`sapNo`),
  CONSTRAINT `fl_pack_sapNo` FOREIGN KEY (`sapNo`) REFERENCES `tbl_business_instrumentinfo` (`sapNo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_packinglistdetail
-- ----------------------------
CREATE TABLE `tbl_business_packinglistdetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `packId` int(11) NOT NULL,
  `materielNo` varchar(50) NOT NULL COMMENT '产品名称',
  `quantity` int(11) DEFAULT NULL COMMENT '数量',
  `updateTime` datetime DEFAULT NULL,
  `comments` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_packMaterNo` (`materielNo`),
  KEY `fk_packId` (`packId`),
  CONSTRAINT `fk_packId` FOREIGN KEY (`packId`) REFERENCES `tbl_business_packinglist` (`id`) ON DELETE CASCADE,
  CONSTRAINT `tbl_business_packinglistdetail_ibfk_1` FOREIGN KEY (`materielNo`) REFERENCES `tbl_business_materiel` (`materielNo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `sys_right` VALUES ('A', '仪器列表');
INSERT INTO `sys_right` VALUES ('A-1', '仪器资质管理');
INSERT INTO `sys_right` VALUES ('A-2', '仪器说明书管理');
INSERT INTO `sys_right` VALUES ('A-3', '整机BOM单管理');
INSERT INTO `sys_right` VALUES ('A-4', '电路板BOM单管理');
INSERT INTO `sys_right` VALUES ('A-5', '装箱清单管理');
INSERT INTO `sys_right` VALUES ('A-6', '设计文档管理');
INSERT INTO `sys_right` VALUES ('B', '试剂列表');
INSERT INTO `sys_right` VALUES ('B-1', '试剂资质管理');
INSERT INTO `sys_right` VALUES ('B-2', '试剂说明书管理');
INSERT INTO `sys_right` VALUES ('B-3', '试剂BOM单管理');
INSERT INTO `sys_right` VALUES ('B-4', '试剂装箱清单管理');
INSERT INTO `sys_right` VALUES ('B-5', '试剂设计文档管理');
INSERT INTO `sys_right` VALUES ('C', '物料管理');
INSERT INTO `sys_right` VALUES ('C-1', '物料管理');
INSERT INTO `sys_right` VALUES ('D', '用户管理');
INSERT INTO `sys_right` VALUES ('D-1', '基本信息');
INSERT INTO `sys_right` VALUES ('D-2', '密码设置');
INSERT INTO `sys_right` VALUES ('D-3', 'æ–°å¢žç”¨æˆ·');
INSERT INTO `sys_user` VALUES ('1123', '4QrcOUm6Wau+VuBX8g+IPg==', 'afsdf', '2344@qq.com', '0', '管理中心', '0', '', '', '2016-11-08 00:00:00', null);
INSERT INTO `sys_user` VALUES ('123', '4QrcOUm6Wau+VuBX8g+IPg==', 'afds', '1234@qq.com', '0', '管理中心', '0', 'A-1,A-5,B-1,B-5,C-1,D-1,D-2', '', '2016-11-08 00:00:00', null);
INSERT INTO `sys_user` VALUES ('1234', '4QrcOUm6Wau+VuBX8g+IPg==', 'eafsdf', '1232434', '0', '管理中心', '0', 'A-1,A-2,A-6,C-1,D-1,D-2,A,C,D', '', '2016-11-09 16:51:13', null);
INSERT INTO `sys_user` VALUES ('1234567', '7Za/ofAzMRRD18w/Ch0pbg==', '13244', '123434', '0', '管理中心', '0', 'A-1,A-2,A-5,A-6,A', '12344', '2016-11-01 00:00:00', null);
INSERT INTO `sys_user` VALUES ('admin', 'ISGMyneATSuhkiwz4BURBQ==', '系统管理员', '123324@qq.com', '1', '管理中心', '1', 'A,B,C,D,A-1,A-2,A-3,A-4,A-5,A-6,C-1,D-1,D-2', '12345678901', '2016-11-07 00:00:00', null);
INSERT INTO `sys_user` VALUES ('administrator', 'jdz/OoD0GJyhydTZAsPJCQ==', 'admin', '1693169234@qq.com', '0', '管理中心', '0', 'A-1,A-2,A-3,A-4,A-5,A-6,B-1,B-2,B-3,B-4,B-5,C-1,D-1,D-2,A,D', '1345678901', '2016-11-03 00:00:00', null);
INSERT INTO `sys_user` VALUES ('lzh', '05PQAPqtSpUi6lotVrUkdw==', 'lzh', 'lizh@chinafst.cn', '0', '仪器信息集成中心', '0', 'A-1,A-2,A-3,A-5,C-1,D-1,D-2,A,D', '', '2016-11-08 00:00:00', null);
INSERT INTO `sys_user` VALUES ('sgdfg', 'ICy5YqxZB1uWSwcVLSNLcA==', '123', '123123', '0', '管理中心', '0', 'A-1,A-2,A-5,A-6,D-1,D-2,A,D', '', '2016-11-09 00:00:00', null);
INSERT INTO `sys_user` VALUES ('shuanglong', '4QrcOUm6Wau+VuBX8g+IPg==', 'shuanglong', '1@qq.com', '1', '管理中心', '1', 'A,B,C,D,A-1,A-2,A-3,A-4,A-5,A-6,C-1,D-1,D-2', '123123', '2016-10-20 00:00:00', null);
INSERT INTO `sys_user` VALUES ('test', '4QrcOUm6Wau+VuBX8g+IPg==', 'sgdgdfg', '18123213@qq.com', '0', '研发中心', '0', 'A-1,A-2,A-5,A-6,A', '34546', '2016-11-01 00:00:00', null);
INSERT INTO `sys_user` VALUES ('Test01', 'ZZD3Ps3zUcON4Avv0uzxew==', '测试02', '1235456@qq.com', '0', '营销中心', '0', 'A-1,A-2,A-3,A-4,A-5,A', '12345678901', '2016-11-01 00:00:00', null);
INSERT INTO `sys_user` VALUES ('Test11', '4QrcOUm6Wau+VuBX8g+IPg==', '测试员11号', '1233435@qq.com', '0', '管理中心', '0', 'A-1,A-2,A-3,A-4,A-5,A-6,A', '', '2016-11-03 00:00:00', null);
INSERT INTO `sys_user` VALUES ('Test12', '4QrcOUm6Wau+VuBX8g+IPg==', '测试员12号', '123323455@qq.com', '0', '管理中心', '0', 'A-1,A-2,A-5,A-6,C-1,D-1,D-2,A,D', '', '2016-11-03 00:00:00', null);
INSERT INTO `sys_user` VALUES ('Test9', '4QrcOUm6Wau+VuBX8g+IPg==', 'Test9', '1235456@qq.com', '0', '管理中心', '0', 'A-1,A-5,C-1,D-1,D-2', '', '2016-11-08 00:00:00', null);
INSERT INTO `sys_user` VALUES ('Testxiaomi', '4QrcOUm6Wau+VuBX8g+IPg==', '小米', '123456@qq.com', '0', '研发中心', '0', 'A-1,A-2,A-5,A-6,A', '', '2016-11-03 00:00:00', null);
INSERT INTO `sys_user` VALUES ('user001', 'xMpCOKC5I4INzFCab3WEmw==', '测试员', '123@hao123.com', '0', '研发中心', '0', 'A-1,A-2,A-5,A-6', '21212121', '2016-11-08 00:00:00', null);
INSERT INTO `sys_user` VALUES ('xyl', '4QrcOUm6Wau+VuBX8g+IPg==', '小米', '1235456@qq.com', '0', '管理中心', '0', 'A-1,A-5', '', '2016-11-08 00:00:00', null);
INSERT INTO `tbl_business_certificate` VALUES ('35', '31.0002', 'GZDY-WEB概要设计说明书(基本功能)_1.0.doc', '1', '达元', '2016-11-09', null, 'sgdf', '2016-11-10 00:00:00');
INSERT INTO `tbl_business_certificatetype` VALUES ('1', '形式批准证书');
INSERT INTO `tbl_business_certificatetype` VALUES ('2', '校准证书');
INSERT INTO `tbl_business_certificatetype` VALUES ('3', '检定证书');
INSERT INTO `tbl_business_certificatetype` VALUES ('4', '计量证书');
INSERT INTO `tbl_business_certificatetype` VALUES ('5', '高新技术产品证书');
INSERT INTO `tbl_business_certificatetype` VALUES ('6', '验证报告');
INSERT INTO `tbl_business_certificatetype` VALUES ('7', '发明专利');
INSERT INTO `tbl_business_certificatetype` VALUES ('8', '实用新型专利');
INSERT INTO `tbl_business_certificatetype` VALUES ('9', '外观专利');
INSERT INTO `tbl_business_circuitboard` VALUES ('31', '31.0002', null, 'testV1.0', '1.0', 'sdgfgf', '2016-11-10 09:41:09', null);
INSERT INTO `tbl_business_circuitboard` VALUES ('32', '31.0002', null, 'testV2.0', '2.0', 'sdfg', '2016-11-10 09:42:31', null);
INSERT INTO `tbl_business_circuitboard` VALUES ('33', '31.0002', null, 'testV3.0', '3.0', 'fdgh', '2016-11-10 09:44:09', null);
INSERT INTO `tbl_business_circuitboard` VALUES ('34', '31.0002', null, 'testV5.0', '5.0', 'sfgd', '2016-11-10 09:45:18', null);
INSERT INTO `tbl_business_circuitboard` VALUES ('35', '31.0002', null, 'TestV11', '11', 'sgrdf', '2016-11-10 10:12:33', null);
INSERT INTO `tbl_business_circuitboarddetail` VALUES ('126', '31', '1613', null, '0', '2016-11-10 09:42:03', null);
INSERT INTO `tbl_business_circuitboarddetail` VALUES ('127', '31', '1701', null, '0', '2016-11-10 09:42:03', null);
INSERT INTO `tbl_business_circuitboarddetail` VALUES ('128', '31', '1702', null, '0', '2016-11-10 09:42:03', null);
INSERT INTO `tbl_business_circuitboarddetail` VALUES ('129', '31', '10.1610', null, '0', '2016-11-10 09:42:03', null);
INSERT INTO `tbl_business_circuitboarddetail` VALUES ('130', '31', '10.1710', null, '0', '2016-11-10 09:42:03', null);
INSERT INTO `tbl_business_circuitboarddetail` VALUES ('131', '31', '01.1127', null, '0', '2016-11-10 09:42:03', null);
INSERT INTO `tbl_business_circuitboarddetail` VALUES ('132', '31', '01.1131', null, '0', '2016-11-10 09:42:03', null);
INSERT INTO `tbl_business_circuitboarddetail` VALUES ('133', '31', '01.1601', null, '0', '2016-11-10 09:42:03', null);
INSERT INTO `tbl_business_circuitboarddetail` VALUES ('134', '31', '01.1602', null, '0', '2016-11-10 09:42:03', null);
INSERT INTO `tbl_business_circuitboarddetail` VALUES ('135', '31', '01.1603', null, '0', '2016-11-10 09:42:03', null);
INSERT INTO `tbl_business_circuitboarddetail` VALUES ('136', '32', '1613', '', '0', '2016-11-10 09:42:31', null);
INSERT INTO `tbl_business_circuitboarddetail` VALUES ('137', '32', '1701', '', '0', '2016-11-10 09:42:31', null);
INSERT INTO `tbl_business_circuitboarddetail` VALUES ('138', '32', '1702', '', '0', '2016-11-10 09:42:31', null);
INSERT INTO `tbl_business_circuitboarddetail` VALUES ('139', '32', '10.1610', '', '0', '2016-11-10 09:42:31', null);
INSERT INTO `tbl_business_circuitboarddetail` VALUES ('140', '32', '10.1710', '', '0', '2016-11-10 09:42:31', null);
INSERT INTO `tbl_business_circuitboarddetail` VALUES ('141', '32', '01.1127', '', '0', '2016-11-10 09:42:31', null);
INSERT INTO `tbl_business_circuitboarddetail` VALUES ('142', '32', '01.1131', '', '0', '2016-11-10 09:42:31', null);
INSERT INTO `tbl_business_circuitboarddetail` VALUES ('143', '32', '01.1601', '', '0', '2016-11-10 09:42:31', null);
INSERT INTO `tbl_business_circuitboarddetail` VALUES ('144', '32', '01.1602', '', '0', '2016-11-10 09:42:31', null);
INSERT INTO `tbl_business_circuitboarddetail` VALUES ('145', '32', '01.1603', '', '0', '2016-11-10 09:42:31', null);
INSERT INTO `tbl_business_completemachine` VALUES ('31', '31.0002', 'TestBOMV2.0', '2.0', 'dgdhfh', '2016-11-10 08:48:58', null);
INSERT INTO `tbl_business_completemachine` VALUES ('34', '31.0002', 'TestBOM1V1.0', '1.0', 'sdfg', '2016-11-10 10:17:37', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('185', '31', '1613', '0', '2016-11-10 08:50:09', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('186', '31', '1701', '0', '2016-11-10 08:50:09', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('187', '31', '1702', '0', '2016-11-10 08:50:09', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('188', '31', '10.1710', '0', '2016-11-10 08:50:49', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('189', '31', '01.1601', '200', '2016-11-10 08:51:50', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('190', '31', '01.1602', '0', '2016-11-10 08:51:25', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('191', '31', '01.1127', '0', '2016-11-10 08:55:05', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('192', '31', '01.1603', '0', '2016-11-10 08:55:05', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('193', '31', '01.1605', '0', '2016-11-10 08:57:30', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('194', '31', '01.1606', '0', '2016-11-10 08:57:30', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('195', '31', '01.1613', '0', '2016-11-10 08:57:30', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('196', '31', '01.1614', '0', '2016-11-10 09:01:45', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('197', '31', '01.1640', '0', '2016-11-10 09:01:45', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('198', '31', '01.1652', '0', '2016-11-10 09:02:13', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('199', '31', '01.1655', '0', '2016-11-10 09:02:13', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('223', '34', '01.1655', '0', '2016-11-10 10:17:37', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('224', '34', '01.1652', '0', '2016-11-10 10:17:37', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('225', '34', '01.1640', '0', '2016-11-10 10:17:37', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('226', '34', '01.1614', '0', '2016-11-10 10:17:37', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('227', '34', '01.1613', '0', '2016-11-10 10:17:37', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('228', '34', '01.1606', '0', '2016-11-10 10:17:37', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('229', '34', '01.1605', '0', '2016-11-10 10:17:37', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('230', '34', '01.1603', '0', '2016-11-10 10:17:37', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('231', '34', '01.1127', '0', '2016-11-10 10:17:37', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('232', '34', '01.1601', '200', '2016-11-10 10:17:37', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('233', '34', '01.1602', '0', '2016-11-10 10:17:37', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('234', '34', '10.1710', '0', '2016-11-10 10:17:37', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('235', '34', '1702', '0', '2016-11-10 10:17:37', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('236', '34', '1701', '0', '2016-11-10 10:17:37', null);
INSERT INTO `tbl_business_completemachinedetail` VALUES ('237', '34', '1613', '0', '2016-11-10 10:17:37', null);
INSERT INTO `tbl_business_document` VALUES ('34', '31.0002', '仪器管理信息系统数据库设计V1.0V1.0.docx', 'dfgd', '1.0', '2016-11-09 17:10:28', 'dfghf');
INSERT INTO `tbl_business_instrumentinfo` VALUES ('31.0002', '达元', 'DY-PR-10P便携式农药残留速测仪', '农药残留快检系列', '2010-11-09', '20161109170830_40.jpg,20161109170905_33.jpg,20161109170905_96.jpg,20161109170905_9.jpg', '', '0', null, '2016-11-09 17:09:11', '', '', '', '', '');
INSERT INTO `tbl_business_instrumentinfo` VALUES ('31.0025', '达元', 'DY-4300三聚氰胺快速分析仪', '农药残留快检系列', '2010-11-09', '20161109170740_96.jpg', '', '0', null, '2016-11-09 17:07:47', '', '', '', '', '');
INSERT INTO `tbl_business_instrumentinfo` VALUES ('31.1001', '达元', 'DY-1000多通道农药残留快速测试仪', '农药残留快检系列', '2010-11-09', '20161109170618_42.jpg', '', '0', null, '2016-11-09 17:06:20', '', '', '', '', '');
INSERT INTO `tbl_business_instrumentinfo` VALUES ('31.1011', '达元', 'DY-3100高通量食品安全分析仪', '农药残留快检系列', '2010-11-09', '20161109170702_73.jpg', '', '0', null, '2016-11-09 17:07:04', '', '', '', '', '');
INSERT INTO `tbl_business_manual` VALUES ('78', '31.0002', 'WEB概要设计说明书(基本功能)V1.0.doc', 'cbcfgh', '1.0', '2016-11-09 17:10:56', 'dfghg');
INSERT INTO `tbl_business_materiel` VALUES ('01.1127', 'DY3300圆柱磁铁', '5', 'DY3300圆柱磁铁 D6.0mm×H3.0mm', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1131', '喇叭', '5', '喇叭 8Ω/0.5W D40XH5mm', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1601', '金标卡-按键板', '5', 'JBK_SW_V1.0', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1602', '金标卡-检测主板', '5', 'JinBiaoKa-V1.4', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1603', '金标卡-光电开关板', '5', 'JBK_Ugroove_V1.0', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1605', '金标卡-状态板', '5', 'JBK_STA_V1.0', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1606', '打印机', '5', 'D8A', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1613', '胶体金-FFC软排线', '4', 'FFC线1.0mm 4P L60mm(反向)', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1614', '胶体金-FFC软排线', '4', 'FFC线1.0mm 4P L150mm(反向)', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1640', '主板电源线', '4', '双头，双排2P间距4.2mm L280mm', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1641', '电容触摸屏', '5', '12.1寸电容触摸屏（含控制板和USB连接线）', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1642', '电源开关连接线', '4', '两端都是PH2.0 2P L300mm', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1643', '箱仪一体机-充电板', '5', 'XYBAT-V1.2', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1644', '胶体金-FFC软排线', '4', 'FFC线1.0mm 6P L250mm(同向)', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1645', '箱仪一体机-锂电池', '5', '11.1V，6.4AH，L400MM带线红黑，一端VH3.96', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1646', '电源座连接线', '4', '一端剥线5mm镀锡，另一端XH2.54 4P L380mm', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1647', 'USB双排线', '4', '双头杜邦2.54 5P L400mm', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1648', '串口连接排线', '4', '2.0牛角排线10pin L400mm', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1649', '无线网卡（带蓝牙功能）', '5', '150M，mini PCIE接口，带蓝牙', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1650', '风扇', '1', '5CM 5.0V PH2.0 2P 线长100mm', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1651', 'DY-3000（BX1）便携式食品综合分析仪-型号标', '6', 'DY3000-026', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1652', '键盘', '5', '无线蓝牙键盘（wireless keyboard）', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1655', '箱仪一体机 显示屏上盖', '1', 'DY-3000(BX1)-01', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1656', '箱仪一体机 显示屏下盖', '1', 'DY-3000(BX1)-02', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1657', '箱仪一体机 显示屏饰盖', '1', 'DY-3000(BX1)-03', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1658', '箱仪一体机 底盖', '1', 'DY-3000(BX1)-04', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1659', '箱仪一体机 面盖', '1', 'DY-3000(BX1)-05', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1660', '箱仪一体机 检测室盖', '1', 'DY-3000(BX1)-06', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1661', '箱仪一体机 防尘盖', '1', 'DY-3000(BX1)-07', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1685', '黑块-FFC软排线', '4', 'FFC线1.0mm 9P L120mm(同向)', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1689', '箱仪一体机触摸屏线', '4', '线长930mm，带2个磁环', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1690', '箱仪一体机打印机排线', '4', '2.0牛角排线，20pin，100mm', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1691', '箱仪一体机打印机电源线', '4', 'XH2.54，3P，同向，60mm，红黑', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.3001', '电脑主板', '5', 'ITX-1037T-2U2C', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.3002', '内存', '5', '笔记本内存2GB DDR3 1333', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.3003', '硬盘', '5', 'mini SATA SSD硬盘 32GB', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.3009', '黑块-塑料件', '1', 'DY-3000,8通道', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.3010', '黑块-发射板', '5', 'SEND-32', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.3011', '黑块-接收板', '5', 'RECEIVE-8', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.3013', '黑块-FFC软排线', '4', 'FFC线1.0mm 16P L70mm(同向)', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.3014', '液晶屏', '5', '夏普12寸液晶屏(LQ121S1LG75)', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.3015', '液晶屏驱动板', '5', '鼎科2270V1.1', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.3016', 'LVDS屏线', '4', 'LQ121S1LG75专用LED屏线，L250mm', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.3017', '驱动板VGA线', '4', 'PH2.0 双向12P L400mm（反向）', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.3018', '液晶驱动板电源线', '4', 'PH2.0 6P转4P L400mm（同向）', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.3049', 'DY-3000LOGO铭牌', '6', 'DY3000-025', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.3069', '喇叭线', '4', '1007红黑，一端压PH2.04P端子，另一端剥线5mm', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.3076', '航空插座', '5', '直径12mm', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.3078', 'WIFI天线', '4', '笔记本无线网卡天线，L700mm', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.4090', 'DC电源座', '5', 'DC-022 5.5-2.5MM 圆孔螺纹螺母', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('10.1610', '贴片电阻', '3', '', '', '20161103163205_64.jpg,20161103163205_47.jpg', '2016-11-08 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('10.1710', '贴片电阻', '3', '', '', '20161103163205_64.jpg,20161103163205_47.jpg', '2016-11-08 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('1613', 'Test1', '2', '32545', 'dsfggf', '20161109152905_33.jpg,20161109152906_32.jpg,20161109152906_56.jpg,20161109152906_30.jpg,20161109152906_8.jpg', '2016-11-09 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('1701', 'Test', '2', '32545', '', '20161103160946_1.jpg,20161103160947_86.jpg,20161103160947_44.jpg', '2016-11-08 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('1702', 'Test', '2', '', '', '20161103161030_31.jpg,20161103161030_29.jpg,20161103161031_24.jpg', '2016-11-08 00:00:00', '');
INSERT INTO `tbl_business_materiel` VALUES ('41.0079', '7号电池', '5', '7# 1.5V', '', '', '2016-11-07 00:00:00', '');
INSERT INTO `tbl_business_materieldrawings` VALUES ('104', '10.1610', 'GZDY-MySQL 5.1 官方简体中文版参考手册.pdf1.0.pdf', '1.0', '', '2016-11-03', '');
INSERT INTO `tbl_business_materieldrawings` VALUES ('105', '10.1610', 'GZDY-001仪器信息首页1.0.jpg', '1.0', 'xiaomi', '2016-11-03', 'test');
INSERT INTO `tbl_business_materieldrawings` VALUES ('106', '10.1610', 'GZDY-001仪器信息首页3.0.jpg', '3.0', 'test', '2016-11-03', 'test');
INSERT INTO `tbl_business_materieldrawings` VALUES ('118', '10.1610', 'GZDY-MySQL 5.1 官方简体中文版参考手册.pdf1.0.pdf', '1.0', '', '2016-11-03', '');
INSERT INTO `tbl_business_materieldrawings` VALUES ('119', '10.1610', 'GZDY-001仪器信息首页1.0.jpg', '1.0', 'xiaomi', '2016-11-03', 'test');
INSERT INTO `tbl_business_materieldrawings` VALUES ('120', '10.1610', 'GZDY-001仪器信息首页3.0.jpg', '3.0', 'test', '2016-11-03', 'test');
INSERT INTO `tbl_business_materieldrawings` VALUES ('132', '10.1710', 'GZDY-MySQL 5.1 官方简体中文版参考手册.pdf1.0.pdf', '1.0', '', '2016-11-03', '');
INSERT INTO `tbl_business_materieldrawings` VALUES ('133', '10.1710', 'GZDY-001仪器信息首页1.0.jpg', '1.0', 'xiaomi', '2016-11-03', 'test');
INSERT INTO `tbl_business_materieldrawings` VALUES ('134', '10.1710', 'GZDY-001仪器信息首页3.0.jpg', '3.0', 'test', '2016-11-03', 'test');
INSERT INTO `tbl_business_materieldrawings` VALUES ('135', '1701', 'GZDY-admin-41.1030-TestBOM1.01.0.pdf', '1.0', '3254', '2016-11-02', '2345');
INSERT INTO `tbl_business_materieldrawings` VALUES ('136', '1702', 'GZDY-Test1.0.docx', '1.0', 'xiaomi', '2016-11-02', '23535');
INSERT INTO `tbl_business_materieldrawings` VALUES ('137', '1702', 'GZDY-Test1.5.docx', '1.5', 'sgdf', '2016-11-02', '');
INSERT INTO `tbl_business_materieldrawings` VALUES ('138', '1702', 'GZDY-Test1.2.doc', '1.2', 'xiaomi', '2016-11-02', 'ewrt');
INSERT INTO `tbl_business_materieldrawings` VALUES ('154', '1613', '仪器信息-装箱清单V1.0.jpg', '1.0', 'sdf', '2016-11-09', 'dfghh');
INSERT INTO `tbl_business_materieldrawings` VALUES ('155', '1613', '仪器信息-装箱清单-新增-查询V2.0.jpg', '2.0', 'wertr', '2016-11-09', 'fgh');
INSERT INTO `tbl_business_materietype` VALUES ('1', '塑料件');
INSERT INTO `tbl_business_materietype` VALUES ('2', '钣金件');
INSERT INTO `tbl_business_materietype` VALUES ('3', '包材');
INSERT INTO `tbl_business_materietype` VALUES ('4', '线材');
INSERT INTO `tbl_business_materietype` VALUES ('5', '电子料');
INSERT INTO `tbl_business_materietype` VALUES ('6', '五金件');
INSERT INTO `tbl_business_materietype` VALUES ('7', '其他');
INSERT INTO `tbl_business_packinglist` VALUES ('35', '31.0002', 'TestV3.0', '3.0', 'sdfghf', '2016-11-10 09:55:58', 'fghj');
INSERT INTO `tbl_business_packinglist` VALUES ('36', '31.0002', 'TestV1.0', '1.0', 'sdfg', '2016-11-10 10:00:39', 'dfgh');
INSERT INTO `tbl_business_packinglist` VALUES ('37', '31.0002', 'TestV2.0', '2.0', 'dfh', '2016-11-10 10:02:20', 'fgj');
INSERT INTO `tbl_business_packinglist` VALUES ('38', '31.0002', 'TestV5.0', '5.0', 'dhj', '2016-11-10 10:03:42', 'fghj');
INSERT INTO `tbl_business_packinglist` VALUES ('39', '31.0002', 'TestV8.0', '8.0', 'dg', '2016-11-10 10:05:36', 'fdsgd');
INSERT INTO `tbl_business_packinglistdetail` VALUES ('133', '35', '1613', '0', '2016-11-10 09:59:50', null);
INSERT INTO `tbl_business_packinglistdetail` VALUES ('134', '35', '1701', '0', '2016-11-10 09:59:50', null);
INSERT INTO `tbl_business_packinglistdetail` VALUES ('135', '35', '1702', '0', '2016-11-10 09:59:50', null);
INSERT INTO `tbl_business_packinglistdetail` VALUES ('136', '35', '10.1610', '0', '2016-11-10 09:59:50', null);
INSERT INTO `tbl_business_packinglistdetail` VALUES ('137', '35', '10.1710', '0', '2016-11-10 09:59:50', null);
INSERT INTO `tbl_business_packinglistdetail` VALUES ('138', '35', '01.1127', '0', '2016-11-10 09:59:50', null);
INSERT INTO `tbl_business_packinglistdetail` VALUES ('139', '35', '01.1131', '0', '2016-11-10 09:59:50', null);
INSERT INTO `tbl_business_packinglistdetail` VALUES ('140', '35', '01.1601', '0', '2016-11-10 09:59:50', null);
INSERT INTO `tbl_business_packinglistdetail` VALUES ('141', '35', '01.1602', '0', '2016-11-10 09:59:50', null);
INSERT INTO `tbl_business_packinglistdetail` VALUES ('142', '35', '01.1603', '0', '2016-11-10 09:59:50', null);
INSERT INTO `tbl_business_packinglistdetail` VALUES ('143', '36', '1613', '0', '2016-11-10 10:00:39', null);
INSERT INTO `tbl_business_packinglistdetail` VALUES ('144', '36', '01.1602', '0', '2016-11-10 10:00:39', null);
INSERT INTO `tbl_business_packinglistdetail` VALUES ('145', '36', '01.1601', '0', '2016-11-10 10:00:39', null);
INSERT INTO `tbl_business_packinglistdetail` VALUES ('146', '36', '01.1131', '0', '2016-11-10 10:00:39', null);
INSERT INTO `tbl_business_packinglistdetail` VALUES ('147', '36', '01.1127', '0', '2016-11-10 10:00:39', null);
INSERT INTO `tbl_business_packinglistdetail` VALUES ('148', '36', '10.1710', '0', '2016-11-10 10:00:39', null);
INSERT INTO `tbl_business_packinglistdetail` VALUES ('149', '36', '10.1610', '0', '2016-11-10 10:00:39', null);
INSERT INTO `tbl_business_packinglistdetail` VALUES ('150', '36', '1702', '0', '2016-11-10 10:00:39', null);
INSERT INTO `tbl_business_packinglistdetail` VALUES ('151', '36', '1701', '0', '2016-11-10 10:00:39', null);
INSERT INTO `tbl_business_packinglistdetail` VALUES ('152', '36', '01.1603', '0', '2016-11-10 10:00:39', null);
