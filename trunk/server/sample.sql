/*
Navicat MySQL Data Transfer

Source Server         : dataSource
Source Server Version : 50153
Source Host           : localhost:3306
Source Database       : sample

Target Server Type    : MYSQL
Target Server Version : 50153
File Encoding         : 65001

Date: 2011-05-07 17:05:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `announcement`
-- ----------------------------
DROP TABLE IF EXISTS `announcement`;
CREATE TABLE `announcement` (
  `a_id` int(11) NOT NULL AUTO_INCREMENT,
  `announcement` varchar(255) DEFAULT NULL,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL,
  `radius` int(11) NOT NULL,
  `time` date DEFAULT NULL,
  `ttime` date DEFAULT NULL,
  `type` bit(1) NOT NULL,
  `username_FK` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`a_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of announcement
-- ----------------------------
INSERT INTO `announcement` VALUES ('1', 'thisisit', '13.6', '14.6', '5', '2011-05-07', '2011-05-07', '', 'testing');

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `a_id_KF` int(11) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `time` date DEFAULT NULL,
  `username_FK` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for `paidannouncement`
-- ----------------------------
DROP TABLE IF EXISTS `paidannouncement`;
CREATE TABLE `paidannouncement` (
  `pa_id` int(11) NOT NULL AUTO_INCREMENT,
  `end` date DEFAULT NULL,
  `nooofposts` int(11) NOT NULL,
  `pannouncement` varchar(255) DEFAULT NULL,
  `position` int(11) NOT NULL,
  `postcount` int(11) NOT NULL,
  `start` date DEFAULT NULL,
  `time` date DEFAULT NULL,
  `username_FK` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pa_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of paidannouncement
-- ----------------------------

-- ----------------------------
-- Table structure for `paidannouncementarea`
-- ----------------------------
DROP TABLE IF EXISTS `paidannouncementarea`;
CREATE TABLE `paidannouncementarea` (
  `paa_id` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL,
  `pa_id` int(11) NOT NULL,
  `rangee` int(11) NOT NULL,
  PRIMARY KEY (`paa_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of paidannouncementarea
-- ----------------------------

-- ----------------------------
-- Table structure for `person`
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `avgrating` float NOT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `gender` bit(1) NOT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `type` bit(1) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `verified` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of person
-- ----------------------------
INSERT INTO `person` VALUES ('1', '', '0', '2000-05-04', 'testapp@testing.com', 'testfirst', '', 'testlast', '3.66', '98', 'testpass', '', 'testuser', '');

-- ----------------------------
-- Table structure for `rating`
-- ----------------------------
DROP TABLE IF EXISTS `rating`;
CREATE TABLE `rating` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT,
  `a_id` int(11) NOT NULL,
  `status` bit(1) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of rating
-- ----------------------------

-- ----------------------------
-- Table structure for `usersession`
-- ----------------------------
DROP TABLE IF EXISTS `usersession`;
CREATE TABLE `usersession` (
  `s_id` int(11) NOT NULL AUTO_INCREMENT,
  `statuss` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`s_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of usersession
-- ----------------------------
