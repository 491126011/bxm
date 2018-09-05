/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.20 : Database - bxm
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bxm` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `bxm`;

/*Table structure for table `basedata` */

DROP TABLE IF EXISTS `basedata`;

CREATE TABLE `basedata` (
  `id` varchar(32) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '主键ID',
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '名称',
  `type` varchar(100) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '类型',
  `value` varchar(255) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '值',
  `parentId` varchar(32) COLLATE utf8_unicode_ci DEFAULT '0' COMMENT '父级ID',
  `delFlag` tinyint(1) DEFAULT '0' COMMENT '删除标记(0:否,1:是)',
  `createDate` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='基础数据表';

/*Data for the table `basedata` */

/*Table structure for table `customer` */

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `id` varchar(32) NOT NULL COMMENT '主健ID',
  `name` varchar(255) NOT NULL COMMENT '用户名字',
  `phone` varchar(100) NOT NULL COMMENT '手机号码',
  `idCard` varchar(255) NOT NULL COMMENT '身份证号码',
  `level` int(1) DEFAULT '1' COMMENT '等级(1:优秀)',
  `money` varchar(100) NOT NULL COMMENT '下款金额',
  `createDate` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户信息表';

/*Data for the table `customer` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `userName` varchar(100) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `type` int(1) NOT NULL COMMENT '用户身份(1:管理员,2:客服)',
  `createDate` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

/*Data for the table `user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
