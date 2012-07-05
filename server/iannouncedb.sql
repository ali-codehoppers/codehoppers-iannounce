/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50143
Source Host           : localhost:3306
Source Database       : iannouncedb

Target Server Type    : MYSQL
Target Server Version : 50143
File Encoding         : 65001

Date: 2012-06-15 12:55:38
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `announcement`
-- ----------------------------
DROP TABLE IF EXISTS `announcement`;
CREATE TABLE `announcement` (
  `a_id` int(11) NOT NULL AUTO_INCREMENT,
  `announcement` varchar(255) DEFAULT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `radius` int(11) NOT NULL,
  `ttime` datetime DEFAULT NULL,
  `type` bit(1) NOT NULL,
  `username_FK` varchar(255) DEFAULT NULL,
  `totalRating` int(11) DEFAULT NULL,
  `neighbourhood_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`a_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of announcement
-- ----------------------------
INSERT INTO `announcement` VALUES ('1', 'Hello, iAnnounce. ?bient?', '31.4748', '74.4027', '8', '2012-06-08 19:04:28', '\0', 'muazahmed', '1', '0');
INSERT INTO `announcement` VALUES ('2', 'Where can I find good burger around here?', '31.4747', '74.4026', '8', '2012-06-11 14:02:38', '\0', 'muazahmed', '0', '1');
INSERT INTO `announcement` VALUES ('3', 'At OPTP', '31.4747', '74.4026', '3', '2012-06-11 17:52:49', '\0', 'muazahmed', '0', '1');
INSERT INTO `announcement` VALUES ('4', 'just been to Copper Kettle. they serve the best steaks', '31.4747', '74.4026', '5', '2012-06-11 18:24:19', '\0', 'muazahmed', '1', '1');
INSERT INTO `announcement` VALUES ('5', 'Est-ce que parler fran?is?', '31.4747', '74.4026', '3', '2012-06-12 13:09:44', '\0', 'muazahmed', '0', '0');
INSERT INTO `announcement` VALUES ('6', 'Café', '31.4747', '74.4026', '3', '2012-06-12 13:16:27', '\0', 'muazahmed', '0', '0');
INSERT INTO `announcement` VALUES ('7', 'Á Bientôt', '31.4747', '74.4026', '3', '2012-06-13 13:52:17', '\0', 'muazahmed', '0', '0');
INSERT INTO `announcement` VALUES ('8', 'dfghjhddhffhdhkfbexjtxjrxntcheavdxgegrff ', '31.4747', '74.4026', '3', '2012-06-13 19:46:32', '\0', 'awais', '0', '0');

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
  `ttime` datetime DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('1', '1', 'The French characters are not working', null, 'muazahmed', '2012-06-08 19:05:53');
INSERT INTO `comment` VALUES ('2', '1', 'hello', null, 'muazahmed', '2012-06-08 19:22:24');
INSERT INTO `comment` VALUES ('3', '1', 'testing', null, 'muazahmed', '2012-06-08 20:05:05');
INSERT INTO `comment` VALUES ('4', '4', 'yeah its great', null, 'muazahmed', '2012-06-11 20:17:23');
INSERT INTO `comment` VALUES ('5', '4', 'lol', null, 'muazahmed', '2012-06-11 20:48:55');
INSERT INTO `comment` VALUES ('6', '3', '', null, 'muazahmed', '2012-06-11 20:55:10');
INSERT INTO `comment` VALUES ('7', '3', 'sorry about last comment. I said I love cheese fries', null, 'muazahmed', '2012-06-11 20:58:20');
INSERT INTO `comment` VALUES ('8', '3', 'LOL', null, 'muazahmed', '2012-06-11 20:59:07');
INSERT INTO `comment` VALUES ('9', '2', '', null, 'muazahmed', '2012-06-12 12:14:34');
INSERT INTO `comment` VALUES ('10', '2', 'LOL', null, 'muazahmed', '2012-06-12 12:15:25');
INSERT INTO `comment` VALUES ('11', '2', 'let see', null, 'muazahmed', '2012-06-12 12:18:49');
INSERT INTO `comment` VALUES ('12', '2', 'I tried Hardees and it was Awesome', null, 'muazahmed', '2012-06-12 12:19:16');
INSERT INTO `comment` VALUES ('13', '6', '33\Z ', null, 'muazahmed', '2012-06-12 14:17:18');
INSERT INTO `comment` VALUES ('14', '7', 'Á Demain', null, 'awais', '2012-06-13 17:33:24');
INSERT INTO `comment` VALUES ('15', '8', 'what is this?', null, 'muazahmed', '2012-06-13 19:47:20');

-- ----------------------------
-- Table structure for `community`
-- ----------------------------
DROP TABLE IF EXISTS `community`;
CREATE TABLE `community` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isPrivate` bit(1) DEFAULT NULL,
  `owner` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of community
-- ----------------------------
INSERT INTO `community` VALUES ('1', 'Food', 'For all Food lovers', '\0', '1');
INSERT INTO `community` VALUES ('2', 'Café', 'Talking about coffee shops near ', '\0', '4');
INSERT INTO `community` VALUES ('3', 'Astronomy', 'Discussion about stars', '\0', '1');

-- ----------------------------
-- Table structure for `location`
-- ----------------------------
DROP TABLE IF EXISTS `location`;
CREATE TABLE `location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `neighbourhoodId` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `addedBy` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of location
-- ----------------------------
INSERT INTO `location` VALUES ('1', '2', 'Jammin Java', 'good Coffee', '31.4746985', '74.40256705', '4');
INSERT INTO `location` VALUES ('2', '2', 'OPTP', 'Potatoes', '31.4746985', '74.40256705', '4');
INSERT INTO `location` VALUES ('3', '1', 'Subway', 'Sandwiches', '31.4746985', '74.40256705', '4');
INSERT INTO `location` VALUES ('4', '1', 'Hot N Spicy', 'Serves all cuisines', '31.4755245', '74.41462505', '4');

-- ----------------------------
-- Table structure for `neighbour`
-- ----------------------------
DROP TABLE IF EXISTS `neighbour`;
CREATE TABLE `neighbour` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `neighbourhoodId` int(11) DEFAULT NULL,
  `personId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK90C9C3FD66E4168C` (`neighbourhoodId`),
  CONSTRAINT `FK90C9C3FD66E4168C` FOREIGN KEY (`neighbourhoodId`) REFERENCES `community` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of neighbour
-- ----------------------------
INSERT INTO `neighbour` VALUES ('1', '1', '4');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of paidannouncement
-- ----------------------------

-- ----------------------------
-- Table structure for `paidannouncementarea`
-- ----------------------------
DROP TABLE IF EXISTS `paidannouncementarea`;
CREATE TABLE `paidannouncementarea` (
  `paa_id` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `pa_id` int(11) NOT NULL,
  `rangee` int(11) NOT NULL,
  PRIMARY KEY (`paa_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `avgrating` double NOT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `gender` bit(1) NOT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `type` bit(1) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `verified` bit(1) NOT NULL,
  `verificationcode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of person
-- ----------------------------
INSERT INTO `person` VALUES ('1', '', '0.5', '1987-11-21', 'muaz.ahmed@codehoppers.com', 'Muaz', '', 'Ahmed', '31.4747', '74.4026', 'e4800f0e5daa3656db980fabf10a5292', '\0', 'muazahmed', '', '20fdbd5f-2176-4e7c-8335-e94d39a9e01d');
INSERT INTO `person` VALUES ('4', '', '0', '1990-11-01', 'awais.akhtar@codehoppers.com', 'Awais', '', 'Akhtar', '31.4747', '74.4026', '440ac85892ca43ad26d44c7ad9d47d3e', '\0', 'awais', '', '7d72a0ad-e1e6-4ac5-a4d2-ca6ed1e5ddd4');
INSERT INTO `person` VALUES ('5', '', '0', '1979-12-01', 'mianzx@gmail.com', 'Ahmed', '', 'Mian', '0', '0', '440ac85892ca43ad26d44c7ad9d47d3e', '\0', 'ahmed', '', '4ebf9b99-b5d5-43fc-9f64-4cf0bfd22c16');

-- ----------------------------
-- Table structure for `rangee`
-- ----------------------------
DROP TABLE IF EXISTS `rangee`;
CREATE TABLE `rangee` (
  `ra_id` int(11) NOT NULL AUTO_INCREMENT,
  `s` text NOT NULL,
  `valuee` int(11) NOT NULL,
  PRIMARY KEY (`ra_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rangee
-- ----------------------------
INSERT INTO `rangee` VALUES ('1', 'Loud', '3');
INSERT INTO `rangee` VALUES ('2', 'Louder', '5');
INSERT INTO `rangee` VALUES ('3', 'Loudest', '8');

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rating
-- ----------------------------
INSERT INTO `rating` VALUES ('1', '1', '', 'muazahmed');
INSERT INTO `rating` VALUES ('2', '4', '', 'muazahmed');

-- ----------------------------
-- Table structure for `usersession`
-- ----------------------------
DROP TABLE IF EXISTS `usersession`;
CREATE TABLE `usersession` (
  `s_id` int(11) NOT NULL AUTO_INCREMENT,
  `statuss` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `sessionID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`s_id`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of usersession
-- ----------------------------
INSERT INTO `usersession` VALUES ('1', '1', 'muazahmed', '42e54971-9b78-49b5-83a7-6a96b2f40304');
INSERT INTO `usersession` VALUES ('2', '1', 'muazahmed', '862c60be-ea0d-47f1-9e4c-fa98f71625d5');
INSERT INTO `usersession` VALUES ('3', '1', 'muazahmed', '4e8e00ca-28bd-4d38-8bb7-811947a33106');
INSERT INTO `usersession` VALUES ('4', '1', 'muazahmed', '8c49c044-e694-4936-bb1a-ec624f933389');
INSERT INTO `usersession` VALUES ('5', '1', 'muazahmed', 'ea8b9ab7-4caf-4230-90ea-213aa6b29e6b');
INSERT INTO `usersession` VALUES ('6', '1', 'muazahmed', 'b934188c-066d-4d9b-90b8-815b7593d42c');
INSERT INTO `usersession` VALUES ('7', '1', 'muazahmed', 'a7bfd713-7a50-4726-b2ad-cfe12d1fdee5');
INSERT INTO `usersession` VALUES ('8', '1', 'muazahmed', 'ec3ed91b-514a-44d2-bc6e-7b1b3bc91275');
INSERT INTO `usersession` VALUES ('9', '1', 'muazahmed', '0a73b74a-7897-4ed5-992f-af74ad78b5a8');
INSERT INTO `usersession` VALUES ('10', '1', 'muazahmed', '7e9228e9-bbf4-4521-a862-0eebfb88dbde');
INSERT INTO `usersession` VALUES ('11', '1', 'muazahmed', 'ce9cbfb5-3b4f-4a8a-9f9e-585bb8d9d960');
INSERT INTO `usersession` VALUES ('12', '1', 'muazahmed', '780fc2c5-f112-4eec-9933-01f097bf5d8d');
INSERT INTO `usersession` VALUES ('13', '1', 'muazahmed', 'b37e1c85-45b5-4425-8652-99f2a6017792');
INSERT INTO `usersession` VALUES ('14', '1', 'muazahmed', '733a8548-b57b-4ce7-ac3e-dc1dd499775c');
INSERT INTO `usersession` VALUES ('15', '1', 'muazahmed', '6b340114-d1b1-432f-a2cc-11fd28be0125');
INSERT INTO `usersession` VALUES ('16', '1', 'muazahmed', '49832dd1-9deb-49c2-9684-89979b8e0d5e');
INSERT INTO `usersession` VALUES ('17', '1', 'muazahmed', '8b6a4f9d-fcb1-4cbc-a7ee-da7328be084a');
INSERT INTO `usersession` VALUES ('18', '1', 'muazahmed', 'c35e9992-d779-4213-8666-c0e5e1b2e5bd');
INSERT INTO `usersession` VALUES ('19', '1', 'muazahmed', '2f19c8e0-a634-4c68-9455-8c782cda64bd');
INSERT INTO `usersession` VALUES ('20', '1', 'muazahmed', 'f4a214eb-87e0-40cc-beaf-88daec40812e');
INSERT INTO `usersession` VALUES ('21', '1', 'muazahmed', 'b8b3d4b9-a356-4f35-bd38-e23f77cdd747');
INSERT INTO `usersession` VALUES ('22', '1', 'muazahmed', 'fc361e89-b9c3-413f-bb1e-4325cc2e3fed');
INSERT INTO `usersession` VALUES ('23', '1', 'muazahmed', 'db42e2e4-677e-4230-97f7-8558bb247b36');
INSERT INTO `usersession` VALUES ('24', '1', 'muazahmed', '42b4b8cf-4689-4cdc-a024-7188cc452c6e');
INSERT INTO `usersession` VALUES ('25', '1', 'muazahmed', 'de8825fb-8bf1-4092-b226-30ba57ec922b');
INSERT INTO `usersession` VALUES ('26', '1', 'muazahmed', '8825f9a7-05ab-48a8-ae4d-447158ba73fc');
INSERT INTO `usersession` VALUES ('27', '1', 'muazahmed', 'a827ed44-ef5a-4508-b3e0-2e282005a060');
INSERT INTO `usersession` VALUES ('28', '1', 'muazahmed', 'f50ecae0-569b-4e1c-9310-bec6f6a34362');
INSERT INTO `usersession` VALUES ('29', '1', 'muazahmed', 'b811e599-a16c-4395-ab6f-d28b45086cfe');
INSERT INTO `usersession` VALUES ('30', '1', 'muazahmed', '9cb4c631-634b-4964-b4d9-c01568e210b2');
INSERT INTO `usersession` VALUES ('31', '1', 'muazahmed', 'e3e47c7b-b9fb-45e0-912c-f578c35a0a63');
INSERT INTO `usersession` VALUES ('32', '1', 'muazahmed', '46fe3518-debd-4732-9547-b91c634f477c');
INSERT INTO `usersession` VALUES ('33', '1', 'muazahmed', '781e56da-0c6c-431b-832c-c79863d7f2f0');
INSERT INTO `usersession` VALUES ('34', '1', 'muazahmed', '6d8d280d-ec56-49d0-bfb3-2923c28e5efa');
INSERT INTO `usersession` VALUES ('35', '1', 'muazahmed', '2aebf0e8-54b1-42e2-825d-f50f54c78151');
INSERT INTO `usersession` VALUES ('36', '1', 'muazahmed', '660c4476-0542-4b48-ab1e-72b7d47e9a9e');
INSERT INTO `usersession` VALUES ('37', '1', 'muazahmed', 'f6b79c94-b191-4075-99d3-9ac1db56e796');
INSERT INTO `usersession` VALUES ('38', '1', 'muazahmed', '381e958f-2589-4ba1-8ce7-81819d45dd1e');
INSERT INTO `usersession` VALUES ('39', '1', 'muazahmed', '74667eb8-d145-4fac-a911-fbeaf3cd60e5');
INSERT INTO `usersession` VALUES ('40', '1', 'muazahmed', '94fccaf3-f0db-4fc5-b3ad-da1c66d1f10b');
INSERT INTO `usersession` VALUES ('41', '1', 'muazahmed', 'b2eb79fe-e0a2-4c76-b538-239f933c6a7b');
INSERT INTO `usersession` VALUES ('42', '1', 'muazahmed', 'd8540c6f-aabf-4f68-9788-74c4a854df96');
INSERT INTO `usersession` VALUES ('43', '1', 'muazahmed', '27043154-4768-4676-843e-558010478ec6');
INSERT INTO `usersession` VALUES ('44', '1', 'muazahmed', '85f05052-cbae-42d7-985c-8c1bd7dddefe');
INSERT INTO `usersession` VALUES ('45', '1', 'muazahmed', 'b5f729ae-b761-4c91-af49-9b1c9f5e0dd9');
INSERT INTO `usersession` VALUES ('46', '1', 'muazahmed', '4270bde3-e71d-4977-9a28-da0c053ecf2a');
INSERT INTO `usersession` VALUES ('47', '1', 'muazahmed', '47731561-b605-4248-8055-7f2c1bc84328');
INSERT INTO `usersession` VALUES ('48', '1', 'muazahmed', 'dec16685-7850-462a-b986-8271b7cf3a42');
INSERT INTO `usersession` VALUES ('49', '1', 'muazahmed', 'f2a6b5d4-35fd-41d8-8d66-b7edfe467a9b');
INSERT INTO `usersession` VALUES ('50', '1', 'muazahmed', '136fa637-585b-4708-97ea-a2eabc0377fc');
INSERT INTO `usersession` VALUES ('51', '1', 'muazahmed', '90e80369-a685-4bd4-8ff9-29744c3d6884');
INSERT INTO `usersession` VALUES ('52', '1', 'muazahmed', '05daf818-e9f0-4763-9569-a0c698bbb6be');
INSERT INTO `usersession` VALUES ('53', '1', 'muazahmed', '9df9c521-11d3-49d8-b7e7-8b9526129e25');
INSERT INTO `usersession` VALUES ('54', '1', 'muazahmed', '02bb20df-911b-4bcd-9701-3faa7dbbb0f0');
INSERT INTO `usersession` VALUES ('55', '1', 'muazahmed', 'bdb6ffdf-3e2b-4491-b188-488f08be4c3c');
INSERT INTO `usersession` VALUES ('56', '1', 'muazahmed', '8f8cf5a7-9d73-4e1f-8dec-1df490d09876');
INSERT INTO `usersession` VALUES ('57', '1', 'muazahmed', '00fb765d-908c-42d4-9443-f9cda07f11a9');
INSERT INTO `usersession` VALUES ('58', '1', 'muazahmed', '12c6182f-6065-4d5d-a2b5-060ce6f54d1d');
INSERT INTO `usersession` VALUES ('59', '1', 'muazahmed', '54867e4f-b548-4585-bc4e-23e66d6e9b0f');
INSERT INTO `usersession` VALUES ('60', '1', 'muazahmed', '88cb1ee5-59e9-48f6-a1ea-08a27baf437d');
INSERT INTO `usersession` VALUES ('61', '1', 'muazahmed', 'fce05593-c758-4f23-bf70-87487b9f6969');
INSERT INTO `usersession` VALUES ('62', '1', 'muazahmed', 'e1e54235-5a14-468d-9b9d-be8bbf939149');
INSERT INTO `usersession` VALUES ('63', '1', 'muazahmed', 'f76577ac-2237-450a-8d25-5ea0ad5a7c98');
INSERT INTO `usersession` VALUES ('64', '1', 'muazahmed', 'a2e578f5-5443-49b1-be4e-4d4f8ceb74bc');
INSERT INTO `usersession` VALUES ('65', '1', 'muazahmed', '8cca5c0f-986c-4f39-8f04-a58edf776254');
INSERT INTO `usersession` VALUES ('66', '1', 'muazahmed', '906e32c1-61bc-4f19-bdff-d84ed1acfb06');
INSERT INTO `usersession` VALUES ('67', '1', 'muazahmed', 'b0d09187-ef3d-4603-85e5-d9fcf0a181af');
INSERT INTO `usersession` VALUES ('68', '1', 'muazahmed', '53b99878-5019-4458-bcf5-e91e53739cbd');
INSERT INTO `usersession` VALUES ('69', '1', 'muazahmed', 'b8e8a216-18e5-4162-88cc-af218523c9e5');
INSERT INTO `usersession` VALUES ('70', '1', 'muazahmed', '151a6588-0348-4dc9-ba6d-df337c4dd392');
INSERT INTO `usersession` VALUES ('71', '1', 'muazahmed', '9c02c4a9-da37-4edb-87e1-b0097fe13fcc');
INSERT INTO `usersession` VALUES ('72', '1', 'muazahmed', 'e26cd48b-0287-4b33-865f-40c9ccbad7d9');
INSERT INTO `usersession` VALUES ('73', '1', 'muazahmed', 'e30fe902-5d72-447c-a18d-5b1270901c0d');
INSERT INTO `usersession` VALUES ('74', '1', 'muazahmed', '1bbc87cd-29a7-4109-8674-dc021d8e1878');
INSERT INTO `usersession` VALUES ('75', '1', 'muazahmed', '2986951e-f807-43bf-a5d0-b0ffba81b9e9');
INSERT INTO `usersession` VALUES ('76', '1', 'muazahmed', '443db536-6158-4ce5-ab13-c7f50a2aa79a');
INSERT INTO `usersession` VALUES ('77', '1', 'muazahmed', '9c8ae090-8ae0-44b5-82ce-c46496bf7b7a');
INSERT INTO `usersession` VALUES ('78', '1', 'muazahmed', '07fde46f-f47b-4609-95a1-e76522749f62');
INSERT INTO `usersession` VALUES ('79', '1', 'muazahmed', 'b1550f06-2657-435e-b144-6f028bca34a3');
INSERT INTO `usersession` VALUES ('80', '1', 'muazahmed', '7dba0c32-3465-4dfc-8a7d-1ac12a72ae1c');
INSERT INTO `usersession` VALUES ('81', '1', 'muazahmed', '6d53a3ce-7261-4d1c-8e34-30ba77e20d04');
INSERT INTO `usersession` VALUES ('82', '1', 'muazahmed', '6c669640-0dcd-4592-95c8-40d15d365aba');
INSERT INTO `usersession` VALUES ('83', '1', 'muazahmed', 'ab2d684c-79ad-446d-ab21-a47581a20db4');
INSERT INTO `usersession` VALUES ('84', '1', 'muazahmed', '2f2f04d3-b8d2-44e5-965b-71869c0c242a');
INSERT INTO `usersession` VALUES ('85', '1', 'muazahmed', '10fdc3c2-2977-4283-afe6-1671b7bc6e44');
INSERT INTO `usersession` VALUES ('86', '1', 'muazahmed', '17fc72df-4f5b-455a-a2aa-bf45bc0545a4');
INSERT INTO `usersession` VALUES ('87', '1', 'muazahmed', 'f49b07de-daf1-44d8-8f4e-0ea26067b6ec');
INSERT INTO `usersession` VALUES ('88', '1', 'muazahmed', '1edd426f-a5bc-4983-9801-0b0f4bb55f7d');
INSERT INTO `usersession` VALUES ('89', '1', 'muazahmed', 'b1b3f15a-148e-4c00-a0e6-e2f2b61d0f47');
INSERT INTO `usersession` VALUES ('90', '1', 'muazahmed', '37e0198f-cc3b-49f1-9dc3-0af76a52dc47');
INSERT INTO `usersession` VALUES ('91', '1', 'muazahmed', '8e28409b-1be9-4bbc-873f-e99b727f3c22');
INSERT INTO `usersession` VALUES ('92', '1', 'muazahmed', '3dc33dbd-a9b3-4d84-a6ca-79d254e5e4fd');
INSERT INTO `usersession` VALUES ('93', '1', 'awais', '4cad876a-2da0-4713-88f6-6a196a2e0597');
INSERT INTO `usersession` VALUES ('94', '1', 'awais', '05822d48-48dc-4f08-a416-1fd39731aeb1');
INSERT INTO `usersession` VALUES ('95', '1', 'awais', '2bfcda6a-3348-4ebc-b0f4-584548de6874');
INSERT INTO `usersession` VALUES ('96', '1', 'awais', '77ea7165-b085-4dad-9bfb-a63ee3dd03b8');
INSERT INTO `usersession` VALUES ('97', '1', 'awais', '9e010940-7d1d-4d22-b8f5-cd8fa6093b2b');
INSERT INTO `usersession` VALUES ('98', '1', 'awais', '57f39936-9477-4c1a-a4d4-b2fadad7b899');
INSERT INTO `usersession` VALUES ('99', '1', 'awais', '083526ef-2d48-4d30-b8e3-7e72b003f278');
INSERT INTO `usersession` VALUES ('100', '1', 'awais', '35018d74-abba-4b63-b6c6-35e8c0d161f4');
INSERT INTO `usersession` VALUES ('101', '1', 'awais', '0daa040b-e8a9-46c4-be88-ebd8f96e9654');
INSERT INTO `usersession` VALUES ('102', '1', 'awais', 'ee2df131-1f57-44a1-a5fd-5feca07d5c23');
INSERT INTO `usersession` VALUES ('103', '1', 'awais', '2f6caaee-2ede-4633-a170-779f45c5b1d4');
INSERT INTO `usersession` VALUES ('104', '1', 'awais', '24ff9c2e-21f1-4f52-8154-47a35fcbefa5');
INSERT INTO `usersession` VALUES ('105', '1', 'awais', 'b7b76818-cdb7-4154-afbf-772b14a81ccf');
INSERT INTO `usersession` VALUES ('106', '1', 'awais', '7c7d7f4b-26ff-44f2-94fc-155b8c77bf29');
INSERT INTO `usersession` VALUES ('107', '1', 'muazahmed', 'abee4dc7-7b10-461c-af1a-3d3b4cded1e3');
INSERT INTO `usersession` VALUES ('108', '1', 'muazahmed', '88ddf2a0-727c-4379-8d90-bc53cce85ab8');
INSERT INTO `usersession` VALUES ('109', '1', 'muazahmed', '905b1d3e-e8fb-4631-95ec-2448aed4abbd');
INSERT INTO `usersession` VALUES ('110', '1', 'muazahmed', 'c619866f-88f4-4a8e-b2d0-25a8f82414f8');
INSERT INTO `usersession` VALUES ('111', '1', 'muazahmed', '853f4e94-3ab8-43ac-bf8f-333ccb14d7e8');
INSERT INTO `usersession` VALUES ('112', '1', 'muazahmed', '6d99d061-8a50-4bac-8f5a-c8b2fb9b23a0');
INSERT INTO `usersession` VALUES ('113', '1', 'muazahmed', '24f103bf-bd97-4f49-90f5-9c3ed7f7307a');
INSERT INTO `usersession` VALUES ('114', '1', 'muazahmed', '05808b9f-2d79-4dc7-bf0f-fc2bd4ca9d4b');
INSERT INTO `usersession` VALUES ('115', '1', 'muazahmed', '3da894bc-1ea5-4d4a-b15a-e078cb5b52b3');
