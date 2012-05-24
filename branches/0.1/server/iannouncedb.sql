-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 05, 2011 at 07:57 AM
-- Server version: 5.5.8
-- PHP Version: 5.3.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `iannouncedb`
--

-- --------------------------------------------------------

--
-- Table structure for table `announcement`
--

CREATE TABLE IF NOT EXISTS `announcement` (
  `a_id` int(11) NOT NULL AUTO_INCREMENT,
  `announcement` varchar(255) DEFAULT NULL,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL,
  `radius` int(11) NOT NULL,
  `time` date DEFAULT NULL,
  `ttime` date DEFAULT NULL,
  `type` bit(1) NOT NULL,
  `username_FK` varchar(255) DEFAULT NULL,
  `totalRating` int(11) DEFAULT NULL,
  PRIMARY KEY (`a_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `announcement`
--

INSERT INTO `announcement` (`a_id`, `announcement`, `latitude`, `longitude`, `radius`, `time`, `ttime`, `type`, `username_FK`, `totalRating`) VALUES
(2, 'sample', 0, 0, 3, NULL, '2011-07-04', b'0', 'awais', 1),
(3, 'sample 2', 0, 0, 5, NULL, '2011-07-04', b'0', 'awais', 1),
(4, 'txt', 31.4743, 74.4023, 5, NULL, '2011-07-04', b'0', 'awais', 1),
(5, 'sample announcememt', 31.4746, 74.4029, 3, NULL, '2011-07-04', b'0', 'awais', 1),
(6, 'muax announcemvrnt', 0, 0, 5, NULL, '2011-07-04', b'0', 'muaz123', 0),
(7, 'sample test', 31.4752, 74.4018, 3, NULL, '2011-07-04', b'0', 'awais', 1),
(8, 'testing', 0, 0, 3, NULL, '2011-07-04', b'0', 'muaz123', 0),
(9, 'testing', 31.4747, 74.4027, 3, NULL, '2011-07-04', b'0', 'awais', 1),
(10, 'post', 31.4744, 74.4028, 3, NULL, '2011-07-05', b'0', 'awais', -1);

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE IF NOT EXISTS `comment` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `a_id_KF` int(11) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `time` date DEFAULT NULL,
  `username_FK` varchar(255) DEFAULT NULL,
  `ttime` datetime DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `comment`
--

INSERT INTO `comment` (`cid`, `a_id_KF`, `comment`, `time`, `username_FK`, `ttime`) VALUES
(1, 3, 'comm1', NULL, 'awais', '2011-07-04 12:59:08');

-- --------------------------------------------------------

--
-- Table structure for table `paidannouncement`
--

CREATE TABLE IF NOT EXISTS `paidannouncement` (
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `paidannouncement`
--


-- --------------------------------------------------------

--
-- Table structure for table `paidannouncementarea`
--

CREATE TABLE IF NOT EXISTS `paidannouncementarea` (
  `paa_id` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL,
  `pa_id` int(11) NOT NULL,
  `rangee` int(11) NOT NULL,
  PRIMARY KEY (`paa_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `paidannouncementarea`
--


-- --------------------------------------------------------

--
-- Table structure for table `person`
--

CREATE TABLE IF NOT EXISTS `person` (
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
  `verificationcode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`id`, `active`, `avgrating`, `dateOfBirth`, `email`, `firstName`, `gender`, `lastName`, `latitude`, `longitude`, `password`, `type`, `username`, `verified`, `verificationcode`) VALUES
(2, b'1', 0.714286, '1990-11-16', 'awaisakhtar16@yaho.com', 'awais', b'1', 'akhtar', 31.4744, 74.4028, '440ac85892ca43ad26d44c7ad9d47d3e', b'0', 'awais', b'1', '9ba3c912-5a58-4eb1-877a-c467ce2c1aa8'),
(4, b'1', 0, '1979-12-01', 'owasii@yahoo.com', 'muaz', b'1', 'A', 0, 0, '440ac85892ca43ad26d44c7ad9d47d3e', b'0', 'muaz123', b'1', '737f8eec-a07d-44c8-8c68-d82fdf4d4220');

-- --------------------------------------------------------

--
-- Table structure for table `rangee`
--

CREATE TABLE IF NOT EXISTS `rangee` (
  `ra_id` int(11) NOT NULL AUTO_INCREMENT,
  `s` text NOT NULL,
  `valuee` int(11) NOT NULL,
  PRIMARY KEY (`ra_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `rangee`
--

INSERT INTO `rangee` (`ra_id`, `s`, `valuee`) VALUES
(1, 'Loud', 3),
(2, 'Louder', 5),
(3, 'Loudest', 8);

-- --------------------------------------------------------

--
-- Table structure for table `rating`
--

CREATE TABLE IF NOT EXISTS `rating` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT,
  `a_id` int(11) NOT NULL,
  `status` bit(1) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `rating`
--

INSERT INTO `rating` (`r_id`, `a_id`, `status`, `username`) VALUES
(1, 2, b'1', 'awais'),
(2, 3, b'1', 'awais'),
(3, 4, b'1', 'awais'),
(4, 9, b'1', 'awais'),
(5, 7, b'1', 'awais'),
(6, 5, b'1', 'awais'),
(7, 10, b'0', 'awais');

-- --------------------------------------------------------

--
-- Table structure for table `usersession`
--

CREATE TABLE IF NOT EXISTS `usersession` (
  `s_id` int(11) NOT NULL AUTO_INCREMENT,
  `statuss` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `sessionID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`s_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=33 ;

--
-- Dumping data for table `usersession`
--

INSERT INTO `usersession` (`s_id`, `statuss`, `username`, `sessionID`) VALUES
(1, 1, 'awais', '25ee8558-4477-4e94-b2e6-b2db18030e0b'),
(2, 1, 'awais', '46f32a7a-bc50-4e3a-b159-6a113731d1bd'),
(3, 1, 'awais', '332ad707-47b9-4230-8d35-f57d80cdc7e2'),
(4, 1, 'awais', '3bdfeca5-1b59-43cd-a5d0-3e9f858b5fac'),
(5, 1, 'awais', 'fcaab121-f6ab-4f75-a050-9faa5f27e352'),
(6, 1, 'awais', 'b24c20b6-bdf3-43c3-b0b7-7207294f7b67'),
(7, 1, 'awais', 'a2ee7129-84ba-43ac-861d-c854d0944078'),
(8, 1, 'awais', 'd2badcef-6c10-4179-b710-4e466740b607'),
(9, 1, 'awais', '9d2c3659-c942-478d-8d01-6251e06adb23'),
(10, 1, 'awais', 'f471e1a7-fbcd-4306-ae05-4e96cf2eeb0d'),
(11, 1, 'awais', '399af27d-b7ae-42a2-8247-6b01621c7579'),
(12, 1, 'awais', '102a2210-88ee-4036-8edf-bc978976c470'),
(13, 1, 'awais', '05b601f6-e026-49e8-b946-28ddf99e1a63'),
(14, 1, 'muaz123', '5bc6455e-84ab-48a6-9b88-e90732623c32'),
(15, 1, 'awais', 'f043d18e-d28e-4c96-8f84-c950e5807193'),
(16, 1, 'awais', '22d80165-6cba-40ef-969d-8793618da146'),
(17, 1, 'muaz123', 'c88cdc17-5290-4f44-851d-2486484310e0'),
(18, 1, 'muaz123', '553f5403-5f6c-4c20-b77f-882c1dd59a4c'),
(19, 1, 'awais', '763589c2-45b2-4dff-a020-a96d1c95af5e'),
(20, 1, 'awais', '6bcd2d1a-3ceb-4155-bb90-a37938ce84b5'),
(21, 1, 'awais', 'fb59ff6f-c97b-40e2-8282-c7b013acd305'),
(22, 1, 'awais', 'ebc50ba6-1a03-47bb-a2d8-b02736bd0171'),
(23, 1, 'awais', '5196d21b-c336-478a-9bbc-66cbb4086fb5'),
(24, 1, 'awais', '7cfe3210-0ca1-41ec-8805-354baf65a051'),
(25, 1, 'awais', '58180542-e84b-4ba3-a48f-0b257aa22dcc'),
(26, 1, 'awais', 'd5bcc3a9-de59-47cc-bbdc-9f465ffe3e80'),
(27, 1, 'awais', '32061bd3-039a-4076-ac7e-b5ef1c64ad5a'),
(28, 1, 'awais', '02c3229c-1966-4d57-bce9-e1899451ddf2'),
(29, 1, 'awais', 'ac6ea0e9-06c0-4a9d-a358-f85136cba12b'),
(30, 1, 'awais', '66914ec0-ec45-4bba-a0d3-4eb07ead8bce'),
(31, 1, 'awais', '3745cbed-7349-4307-9217-f7e021a2d97b'),
(32, 1, 'awais', '6043fff9-8d68-488b-8d59-a7beef495cdc');
