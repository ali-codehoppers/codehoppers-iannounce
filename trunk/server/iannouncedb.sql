-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 04, 2011 at 06:33 AM
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `announcement`
--


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
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `comment`
--


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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `person`
--


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
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `rating`
--


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
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `usersession`
--

