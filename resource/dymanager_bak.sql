/*
MySQL Data Transfer
Source Host: localhost
Source Database: dymanager
Target Host: localhost
Target Database: dymanager
Date: 2016/10/11 11:39:55
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
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '状态 1登录 ,0注销',
  `department` varchar(50) NOT NULL COMMENT '所属部门',
  `superAdmin` int(1) DEFAULT '0' COMMENT '是否为超级管理员0=不是,1=是',
  `rightList` varchar(50) DEFAULT NULL,
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
  `calibrationCertificate` varchar(200) DEFAULT NULL COMMENT '证书名称',
  `certificateType` int(11) DEFAULT NULL COMMENT '书证类型',
  `qualificationAttribution` varchar(50) DEFAULT NULL COMMENT '资质归属',
  `calibStartTime` datetime DEFAULT NULL COMMENT '证书有效开始日期',
  `calibEndTime` datetime DEFAULT NULL COMMENT '书证有效束结时间',
  `reviser` varchar(50) DEFAULT NULL COMMENT '修订者',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `fk_cerTisapNo` (`sapNo`),
  CONSTRAINT `fk_cerTisapNo` FOREIGN KEY (`sapNo`) REFERENCES `tbl_business_instrumentinfo` (`sapNo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_certificatetype
-- ----------------------------
CREATE TABLE `tbl_business_certificatetype` (
  `certificateNo` int(11) NOT NULL AUTO_INCREMENT COMMENT '证书编号',
  `certificateName` varchar(50) DEFAULT NULL COMMENT '证书类型',
  PRIMARY KEY (`certificateNo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_circuitboard
-- ----------------------------
CREATE TABLE `tbl_business_circuitboard` (
  `sapNo` varchar(100) NOT NULL COMMENT '器仪sap代码',
  `circuitBoardSapNo` varchar(100) DEFAULT NULL COMMENT '电路板sap代码',
  `circuitBoardName` varchar(200) DEFAULT NULL COMMENT '电路板名称',
  `circuitBoardVersion` varchar(50) NOT NULL COMMENT '电路板BOM单版次',
  `createPerson` varchar(50) DEFAULT NULL COMMENT '创建者',
  `updateTime` datetime DEFAULT NULL COMMENT '创建,修改时间',
  `comment` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`sapNo`,`circuitBoardVersion`),
  CONSTRAINT `fk_circuitSapNo` FOREIGN KEY (`sapNo`) REFERENCES `tbl_business_instrumentinfo` (`sapNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_circuitboarddetail
-- ----------------------------
CREATE TABLE `tbl_business_circuitboarddetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sapNo` varchar(100) DEFAULT NULL COMMENT '仪器SAP',
  `circuitBoardVersion` varchar(50) NOT NULL COMMENT '电路板BOM单版本号',
  `materielNo` varchar(50) DEFAULT NULL COMMENT '器件名称',
  `locationNo` varchar(200) DEFAULT NULL COMMENT '位号',
  `quantity` varchar(50) DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`id`),
  KEY `fk_cuitBoardVersion` (`circuitBoardVersion`),
  KEY `fk_sapNo_circuitBoardVersion` (`sapNo`,`circuitBoardVersion`),
  KEY `materielNo` (`materielNo`),
  CONSTRAINT `fk_sapNo_circuitBoardVersion` FOREIGN KEY (`sapNo`, `circuitBoardVersion`) REFERENCES `tbl_business_circuitboard` (`sapNo`, `circuitBoardVersion`),
  CONSTRAINT `tbl_business_circuitboarddetail_ibfk_1` FOREIGN KEY (`materielNo`) REFERENCES `tbl_business_materiel` (`materielNo`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_completemachine
-- ----------------------------
CREATE TABLE `tbl_business_completemachine` (
  `sapNo` varchar(100) NOT NULL,
  `completeMachineName` varchar(100) DEFAULT NULL,
  `completeMachineVersion` varchar(50) NOT NULL COMMENT '整机BOM单版本号',
  `createPerson` varchar(50) DEFAULT NULL COMMENT '创建者',
  `updateTime` datetime DEFAULT NULL COMMENT '创建,修改时间',
  `comment` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`sapNo`,`completeMachineVersion`),
  CONSTRAINT `fk_complSapNo` FOREIGN KEY (`sapNo`) REFERENCES `tbl_business_instrumentinfo` (`sapNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_completemachinedetail
-- ----------------------------
CREATE TABLE `tbl_business_completemachinedetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sapNo` varchar(100) DEFAULT NULL COMMENT 'SAP代码',
  `completeMachineVersion` varchar(50) NOT NULL COMMENT '整机BOM单版本号',
  `materielNo` varchar(50) DEFAULT NULL COMMENT '器件名称',
  `quantity` int(11) DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`id`),
  KEY `fk_completeMachineVersion` (`completeMachineVersion`),
  KEY `fk_sapNo_Version` (`sapNo`,`completeMachineVersion`),
  KEY `materielNo` (`materielNo`),
  CONSTRAINT `fk_sapNo_Version` FOREIGN KEY (`sapNo`, `completeMachineVersion`) REFERENCES `tbl_business_completemachine` (`sapNo`, `completeMachineVersion`),
  CONSTRAINT `tbl_business_completemachinedetail_ibfk_1` FOREIGN KEY (`materielNo`) REFERENCES `tbl_business_materiel` (`materielNo`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_document
-- ----------------------------
CREATE TABLE `tbl_business_document` (
  `sapNo` varchar(100) NOT NULL DEFAULT '' COMMENT '器仪sap代码',
  `fileName` varchar(200) DEFAULT NULL COMMENT '说明书名称',
  `documentType` varchar(50) DEFAULT NULL COMMENT '档文类型',
  `reviser` varchar(50) DEFAULT NULL COMMENT '修订者',
  `version` varchar(50) NOT NULL DEFAULT '' COMMENT '本号版',
  `updateTime` datetime DEFAULT NULL COMMENT '上传时间',
  `revisedRecord` varchar(200) DEFAULT NULL COMMENT '修订记录',
  PRIMARY KEY (`sapNo`,`version`),
  CONSTRAINT `fk_DocumsapNo` FOREIGN KEY (`sapNo`) REFERENCES `tbl_business_instrumentinfo` (`sapNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_instrumentinfo
-- ----------------------------
CREATE TABLE `tbl_business_instrumentinfo` (
  `sapNo` varchar(100) NOT NULL COMMENT 'SAP代码',
  `brand` varchar(50) NOT NULL COMMENT '品牌名称',
  `productName` varchar(50) NOT NULL COMMENT '产品名称',
  `productLinel` varchar(50) NOT NULL COMMENT '归属生产线',
  `listedTime` datetime DEFAULT NULL COMMENT '上市日期',
  `image` varchar(200) DEFAULT NULL COMMENT '器仪图片',
  `executionStandard` varchar(100) DEFAULT NULL COMMENT '执行标准',
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '状态(0=在售,1=退市)',
  `delistingDate` datetime DEFAULT NULL COMMENT '退市日期',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `comment` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`sapNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_manual
-- ----------------------------
CREATE TABLE `tbl_business_manual` (
  `sapNo` varchar(100) NOT NULL COMMENT '仪器SAP代码',
  `fileName` varchar(200) DEFAULT NULL COMMENT '说明书名称',
  `reviser` varchar(50) DEFAULT NULL COMMENT '修订人',
  `version` varchar(50) NOT NULL DEFAULT '' COMMENT '版本号',
  `updateTime` datetime NOT NULL COMMENT '更新时间',
  `revisedRecord` varchar(200) DEFAULT NULL COMMENT '修订记录',
  PRIMARY KEY (`sapNo`,`version`),
  CONSTRAINT `fk_sapNo` FOREIGN KEY (`sapNo`) REFERENCES `tbl_business_instrumentinfo` (`sapNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_materiel
-- ----------------------------
CREATE TABLE `tbl_business_materiel` (
  `materielNo` varchar(50) NOT NULL COMMENT '物料编号',
  `materielName` varchar(100) DEFAULT NULL COMMENT '物料名称',
  `materielTypeId` int(11) DEFAULT NULL COMMENT '料物种类',
  `modelSpecification` varchar(100) DEFAULT NULL COMMENT '型号规格',
  `footprint` varchar(500) DEFAULT NULL COMMENT '装封',
  `picture` varchar(200) DEFAULT NULL COMMENT '图片',
  `comment` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`materielNo`),
  KEY `materielTypeId` (`materielTypeId`),
  CONSTRAINT `tbl_business_materiel_ibfk_1` FOREIGN KEY (`materielTypeId`) REFERENCES `tbl_business_materietype` (`materielTypeId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_materieldrawings
-- ----------------------------
CREATE TABLE `tbl_business_materieldrawings` (
  `materielNo` varchar(50) NOT NULL COMMENT '物料编号',
  `drawingName` varchar(100) DEFAULT NULL COMMENT '图纸名称',
  `version` varchar(50) NOT NULL DEFAULT '' COMMENT '版本号',
  `reviser` varchar(50) DEFAULT NULL COMMENT '修订人',
  `updateTime` date DEFAULT NULL COMMENT '上传时间',
  `revisedRecord` varchar(200) DEFAULT NULL COMMENT '修订记录',
  PRIMARY KEY (`materielNo`,`version`),
  CONSTRAINT `tbl_business_materieldrawings_ibfk_1` FOREIGN KEY (`materielNo`) REFERENCES `tbl_business_materiel` (`materielNo`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_materietype
-- ----------------------------
CREATE TABLE `tbl_business_materietype` (
  `materielTypeId` int(11) NOT NULL COMMENT '物料类型编号',
  `materielTypeName` varchar(50) DEFAULT NULL COMMENT '物料类型名称',
  PRIMARY KEY (`materielTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_packinglist
-- ----------------------------
CREATE TABLE `tbl_business_packinglist` (
  `sapNo` varchar(100) NOT NULL DEFAULT '',
  `packingListName` varchar(200) NOT NULL DEFAULT '' COMMENT '仪器sap代码',
  `packingListVersion` varchar(50) NOT NULL DEFAULT '' COMMENT '装箱清单版本号',
  `createPerson` varchar(50) DEFAULT NULL COMMENT '修订者',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`packingListVersion`,`sapNo`),
  KEY `fk_packSapNo` (`sapNo`),
  CONSTRAINT `fk_packSapNo` FOREIGN KEY (`sapNo`) REFERENCES `tbl_business_instrumentinfo` (`sapNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_business_packinglistdetail
-- ----------------------------
CREATE TABLE `tbl_business_packinglistdetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sapNo` varchar(100) DEFAULT NULL COMMENT 'sap代码',
  `packingListVersion` varchar(50) DEFAULT NULL COMMENT '箱装清单版本号',
  `materielNo` varchar(50) DEFAULT NULL COMMENT '产品名称',
  `quantity` int(11) DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`id`),
  KEY `packingListVersion` (`packingListVersion`,`sapNo`),
  KEY `fk_packMaterNo` (`materielNo`),
  CONSTRAINT `fk_packMaterNo` FOREIGN KEY (`materielNo`) REFERENCES `tbl_business_materiel` (`materielNo`),
  CONSTRAINT `tbl_business_packinglistdetail_ibfk_1` FOREIGN KEY (`packingListVersion`, `sapNo`) REFERENCES `tbl_business_packinglist` (`packingListVersion`, `sapNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `sys_right` VALUES ('A-1', '仪器管理');
INSERT INTO `sys_right` VALUES ('A-2', '资质管理');
INSERT INTO `sys_user` VALUES ('admin', '4QrcOUm6Wau+VuBX8g+IPg==', 'admin', null, '1', '测试部', '0', 'A-1,A-2', null, null, null);
INSERT INTO `sys_user` VALUES ('xiaobai', '4QrcOUm6Wau+VuBX8g+IPg==', '小白', '169@qq.com', '1', '测试部', '0', 'A-1,A-2', null, null, null);
INSERT INTO `tbl_business_certificate` VALUES ('1', '31.1001', 'GZDY-null.xls', '1', null, null, null, null, null);
INSERT INTO `tbl_business_certificate` VALUES ('2', '31.1011', '123', '2', null, null, null, null, null);
INSERT INTO `tbl_business_certificate` VALUES ('3', '31.1001', '卫生部医药生物工程技术研究中心2', '1', null, null, null, null, null);
INSERT INTO `tbl_business_certificatetype` VALUES ('1', '高新证书');
INSERT INTO `tbl_business_certificatetype` VALUES ('2', '计量证书');
INSERT INTO `tbl_business_certificatetype` VALUES ('3', '校准证书');
INSERT INTO `tbl_business_circuitboard` VALUES ('31.1001', '31.10000001', 'DY-3000电路板 BOM', '1.0', null, null, null);
INSERT INTO `tbl_business_circuitboard` VALUES ('31.1011', '31.1000011', 'DY-3000电路板 BOM', '1.0', null, null, null);
INSERT INTO `tbl_business_instrumentinfo` VALUES ('31.1001', '达元', 'DY-1000多通道农药残留快速测试仪', '1', '2011-08-01 00:00:00', null, null, '1', null, null, null);
INSERT INTO `tbl_business_instrumentinfo` VALUES ('31.1011', '达元', 'DY-3100高通量食品安全分析仪', '1', '2012-08-01 00:00:00', null, null, '1', null, null, null);
INSERT INTO `tbl_business_instrumentinfo` VALUES ('31.1012', '达元', 'DY-1100多通道农药残留快速测试仪', '1', '2016-10-09 15:27:35', '', '', '1', '2016-10-09 15:27:35', '2016-10-09 15:27:35', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1602', '金标卡-检测主板', '1', 'JinBiaoKa-V1.4', null, 'mater.jpg,mater2.jpeg', null);
INSERT INTO `tbl_business_materiel` VALUES ('01.1603', '金标卡-按键板', '1', 'JBK_SW_V1.0', null, null, null);
INSERT INTO `tbl_business_materiel` VALUES ('01.1604', '金标卡-检测主板', '1', 'JinBiaoKa-V1.4', '', 'mater.jpg,mater2.jpeg', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1605', '金标卡-按键板', '1', 'JBK_SW_V1.0', '', '', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1606', '金标卡-检测主板', '1', 'JinBiaoKa-V1.4', '', 'mater.jpg,mater2.jpeg', '');
INSERT INTO `tbl_business_materiel` VALUES ('01.1607', '金标卡-按键板', '1', 'JBK_SW_V1.0', '', '', '');
INSERT INTO `tbl_business_materieldrawings` VALUES ('01.1602', 'Test01.pdf', '1.1', 'xiaomi', '2016-10-08', 'test');
INSERT INTO `tbl_business_materieldrawings` VALUES ('01.1602', 'Test02.pdf', '1.2', 'xiaomi', '2016-10-08', 'text');
INSERT INTO `tbl_business_materieldrawings` VALUES ('01.1602', 'Test05.pdf', '1.5', 'xiaobai', '2016-10-08', '123');
INSERT INTO `tbl_business_materieldrawings` VALUES ('01.1603', 'Test02.pdf', '1.2', 'xiaohei', '2016-10-08', 'dgdfg');
INSERT INTO `tbl_business_materieldrawings` VALUES ('01.1603', 'Test03.pdf', '1.3', 'xiaohui', '2016-10-08', 'sfs');
INSERT INTO `tbl_business_materieldrawings` VALUES ('01.1603', 'Test04.pdf', '1.4', 'xiaohua', '2016-10-08', '32edf');
INSERT INTO `tbl_business_materieldrawings` VALUES ('01.1603', 'Test05.pdf', '1.5', 'xiaohuang', '2016-10-08', 'fsgdg');
INSERT INTO `tbl_business_materieldrawings` VALUES ('01.1604', 'Test01.pdf', '1.1', 'xiaomi', '2016-10-08', 'test');
INSERT INTO `tbl_business_materieldrawings` VALUES ('01.1604', '\r\nTest02.pdf', '1.2', 'xiaomi', '2016-10-08', 'text');
INSERT INTO `tbl_business_materieldrawings` VALUES ('01.1604', '\r\nTest05.pdf', '1.5', 'xiaobai', '2016-10-08', '123');
INSERT INTO `tbl_business_materieldrawings` VALUES ('01.1605', 'Test02.pdf', '1.2', 'xiaohei', '2016-10-08', 'dgdfg');
INSERT INTO `tbl_business_materieldrawings` VALUES ('01.1605', '\r\nTest03.pdf', '1.3', 'xiaohui', '2016-10-08', 'sfs');
INSERT INTO `tbl_business_materieldrawings` VALUES ('01.1605', '\r\nTest04.pdf', '1.4', 'xiaohua', '2016-10-08', '32edf');
INSERT INTO `tbl_business_materieldrawings` VALUES ('01.1605', '\r\nTest05.pdf', '1.5', 'xiaohuang', '2016-10-08', 'fsgdg');
INSERT INTO `tbl_business_materieldrawings` VALUES ('01.1606', 'Test01.pdf', '1.1', 'xiaomi', '2016-10-08', 'test');
INSERT INTO `tbl_business_materieldrawings` VALUES ('01.1606', '\r\nTest02.pdf', '1.2', 'xiaomi', '2016-10-08', 'text');
INSERT INTO `tbl_business_materieldrawings` VALUES ('01.1606', '\r\nTest05.pdf', '1.5', 'xiaobai', '2016-10-08', '123');
INSERT INTO `tbl_business_materieldrawings` VALUES ('01.1607', 'Test02.pdf', '1.2', 'xiaohei', '2016-10-08', 'dgdfg');
INSERT INTO `tbl_business_materieldrawings` VALUES ('01.1607', '\r\nTest03.pdf', '1.3', 'xiaohui', '2016-10-08', 'sfs');
INSERT INTO `tbl_business_materieldrawings` VALUES ('01.1607', '\r\nTest04.pdf', '1.4', 'xiaohua', '2016-10-08', '32edf');
INSERT INTO `tbl_business_materieldrawings` VALUES ('01.1607', '\r\nTest05.pdf', '1.5', 'xiaohuang', '2016-10-08', 'fsgdg');
INSERT INTO `tbl_business_materietype` VALUES ('1', '塑料jian');
INSERT INTO `tbl_business_materietype` VALUES ('2', '钣金件');
INSERT INTO `tbl_business_materietype` VALUES ('3', '包材');
INSERT INTO `tbl_business_materietype` VALUES ('4', '线材');
INSERT INTO `tbl_business_materietype` VALUES ('5', '电子料');
