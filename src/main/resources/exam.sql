/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.7.24-log : Database - exam
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`exam` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `exam`;

/*Table structure for table `sys_dict` */

CREATE TABLE `sys_dict` (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dict_type` varchar(64) DEFAULT NULL,
  `dict_value` varchar(64) DEFAULT NULL,
  `dict_label` varchar(256) DEFAULT NULL,
  `rank` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`dict_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `sys_dict` */

insert  into `sys_dict`(`dict_id`,`dict_type`,`dict_value`,`dict_label`,`rank`) values (1,'sex','1','男',10),(2,'sex','2','女',20),(9,'active','1','可用',10),(10,'active','0','不可用',20),(11,'background','背景图片','http://pic1.win4000.com/wallpaper/2018-11-23/5bf7be0d912f5.jpg',10);

/*Table structure for table `sys_menu` */

CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL,
  `text` varchar(128) DEFAULT NULL,
  `icon_cls` varchar(64) DEFAULT NULL,
  `view_type` varchar(64) DEFAULT NULL,
  `menu_href` varchar(64) DEFAULT NULL,
  `href_target` varchar(64) DEFAULT NULL,
  `row_cls` varchar(64) DEFAULT NULL,
  `ui` varchar(32) DEFAULT NULL,
  `tooltip` varchar(128) DEFAULT NULL,
  `rank` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`menu_id`,`pid`,`text`,`icon_cls`,`view_type`,`menu_href`,`href_target`,`row_cls`,`ui`,`tooltip`,`rank`) values (1,0,'在线考试系统','x-fa fa-university','#home','#home',NULL,'',NULL,'',1),(2,1,'首页','x-fa fa-home','(NULL)','#dashboard',NULL,'',NULL,'',10),(3,1,'设置中心','x-fa fa-cog','(NULL)','#plat',NULL,'',NULL,'',200),(4,1,'试题管理','x-fa fa-edit','(NULL)','#exam',NULL,'',NULL,'',40),(5,3,'菜单管理','x-fa fa-sitemap','menuManager','#plat',NULL,'nav-tree-badge nav-tree-badge-new',NULL,'',30),(7,3,'角色管理','x-fa fa-address-book','roleManager','#plat',NULL,'nav-tree-badge nav-tree-badge-hot',NULL,'',20),(14,3,'用户管理','x-fa fa-user','userManager','#plat',NULL,'nav-tree-badge nav-tree-badge-new',NULL,'',10),(15,3,'词典管理','x-fa fa-book','dictManager','#plat',NULL,'nav-tree-badge nav-tree-badge-new',NULL,'',40),(16,2,'上传头像','x-fa fa-picture-o','headphoto','#dashboard',NULL,'nav-tree-badge nav-tree-badge-new',NULL,'',90),(17,2,'待考试卷','x-fa fa-envelope-o','remain','#dashboard',NULL,'nav-tree-badge nav-tree-badge-hot',NULL,'',10),(18,2,'试题练习','x-fa fa-list-alt','exercise','#dashboard',NULL,'nav-tree-badge nav-tree-badge-new',NULL,'',20),(19,4,'单选题管理','x-fa fa-th-list','single','#exam',NULL,'',NULL,'',10),(20,4,'多选题管理','x-fa fa-th','multiple','#exam',NULL,'',NULL,'',20),(21,4,'判断题管理','x-fa fa-check','judge','#exam',NULL,'',NULL,'',30),(22,4,'填空题管理','x-fa fa-pencil-square-o','blank','#exam',NULL,'',NULL,'',40),(23,4,'简答题管理','x-fa fa-file-o','answer','#exam',NULL,'',NULL,'',50),(24,4,'试卷管理','x-fa fa-file-text-o','testpaper','#exam',NULL,'nav-tree-badge nav-tree-badge-hot',NULL,'',1);

/*Table structure for table `sys_photo` */

CREATE TABLE `sys_photo` (
  `empid` varchar(32) NOT NULL,
  `photo` longblob,
  PRIMARY KEY (`empid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_photo` */

/*Table structure for table `sys_role` */

CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ch_role_name` varchar(64) DEFAULT NULL,
  `en_role_name` varchar(32) DEFAULT NULL,
  `remark` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`role_id`,`ch_role_name`,`en_role_name`,`remark`) values (1,'超级管理员','admin','超级管理员'),(2,'学生','student','学生');

/*Table structure for table `sys_role_menu` */

CREATE TABLE `sys_role_menu` (
  `role_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,1),(1,2),(1,3),(1,4),(1,5),(1,7),(1,14),(1,15),(1,16),(1,17),(1,18),(1,19),(1,20),(1,21),(1,22),(1,23),(1,24),(2,1),(2,2),(2,16);

/*Table structure for table `sys_user` */

CREATE TABLE `sys_user` (
  `empid` varchar(16) NOT NULL COMMENT '工号',
  `username` varchar(32) DEFAULT NULL COMMENT '姓名',
  `department` varchar(128) DEFAULT NULL COMMENT '部门',
  `phone` varchar(32) DEFAULT NULL COMMENT '电话',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `active` tinyint(4) DEFAULT NULL COMMENT '激活状态：0为不可用，1为可用',
  PRIMARY KEY (`empid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`empid`,`username`,`department`,`phone`,`password`,`active`) values ('1232','闫要冬(闫冬)','大数据中心','18321751160','202cb962ac59075b964b07152d234b70',1);

/*Table structure for table `sys_user_role` */

CREATE TABLE `sys_user_role` (
  `role_id` bigint(20) NOT NULL,
  `empid` varchar(32) NOT NULL,
  PRIMARY KEY (`role_id`,`empid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`role_id`,`empid`) values (1,'1232'),(2,'1232');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
