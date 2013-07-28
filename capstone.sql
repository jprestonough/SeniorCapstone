-- MySQL dump 10.13  Distrib 5.5.32, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: capstone
-- ------------------------------------------------------
-- Server version	5.5.32-0ubuntu0.13.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Account`
--

DROP TABLE IF EXISTS `Account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Account` (
  `AccountID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Customer_CustomerID` int(10) unsigned NOT NULL,
  `CardType` varchar(20) DEFAULT NULL,
  `CardNumber` int(10) unsigned zerofill DEFAULT NULL,
  `ZipCode` int(10) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`AccountID`),
  KEY `Account_FKIndex1` (`Customer_CustomerID`),
  CONSTRAINT `fk_{891E91A7-CD08-4C07-9113-446AEED1B9DD}` FOREIGN KEY (`Customer_CustomerID`) REFERENCES `Customer` (`CustomerID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Action`
--

DROP TABLE IF EXISTS `Action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Action` (
  `ActionID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ActionID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `AuditLog`
--

DROP TABLE IF EXISTS `AuditLog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AuditLog` (
  `AuditLogID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Action_ActionID` int(10) unsigned NOT NULL,
  `Detail` varchar(255) DEFAULT NULL,
  `Timestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`AuditLogID`),
  KEY `AuditLog_FKIndex1` (`Action_ActionID`),
  CONSTRAINT `fk_{33986231-F09B-4B6E-8490-991FFEAD22B5}` FOREIGN KEY (`Action_ActionID`) REFERENCES `Action` (`ActionID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Basket`
--

DROP TABLE IF EXISTS `Basket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Basket` (
  `BasketID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Available` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`BasketID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Customer`
--

DROP TABLE IF EXISTS `Customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Customer` (
  `CustomerID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `CustomerFirst` varchar(20) DEFAULT NULL,
  `CustomerLast` varchar(20) DEFAULT NULL,
  `CustomerEmail` varchar(50) DEFAULT NULL,
  `HashedPassword` varchar(255) DEFAULT NULL,
  `ConfirmationCode` varchar(255) DEFAULT NULL,
  `Confirmed` tinyint(1) DEFAULT '0',
  `RegistrationID` text,
  PRIMARY KEY (`CustomerID`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CustomerOrder`
--

DROP TABLE IF EXISTS `CustomerOrder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CustomerOrder` (
  `OrderID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Customer_CustomerID` int(10) unsigned NOT NULL,
  `DateCreated` timestamp NULL DEFAULT NULL,
  `OrderStatus_OrderStatusID` int(10) unsigned NOT NULL,
  `PickupCode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`OrderID`),
  KEY `CustomerOrder_ibfk_1` (`Customer_CustomerID`),
  KEY `CustomerOrder_ibfk_2` (`OrderStatus_OrderStatusID`),
  CONSTRAINT `CustomerOrder_ibfk_1` FOREIGN KEY (`Customer_CustomerID`) REFERENCES `Customer` (`CustomerID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `CustomerOrder_ibfk_2` FOREIGN KEY (`OrderStatus_OrderStatusID`) REFERENCES `OrderStatus` (`OrderStatusID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Department`
--

DROP TABLE IF EXISTS `Department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Department` (
  `DepartmentID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`DepartmentID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Department`
--

LOCK TABLES `Department` WRITE;
/*!40000 ALTER TABLE `Department` DISABLE KEYS */;
INSERT INTO `Department` VALUES (1,'Cameras'),(2,'Movies'),(3,'Video Games'),(4,'Electronics'),(5,'Laptops');
/*!40000 ALTER TABLE `Department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Employee`
--

DROP TABLE IF EXISTS `Employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Employee` (
  `EmployeeID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `EmployeeType_EmployeeTypeID` int(10) unsigned NOT NULL,
  `HashedPassword` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`EmployeeID`),
  KEY `Employee_FKIndex1` (`EmployeeType_EmployeeTypeID`),
  CONSTRAINT `fk_{C87858B7-484B-425F-9F20-5684BF873C7F}` FOREIGN KEY (`EmployeeType_EmployeeTypeID`) REFERENCES `EmployeeType` (`EmployeeTypeID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `EmployeeType`
--

DROP TABLE IF EXISTS `EmployeeType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EmployeeType` (
  `EmployeeTypeID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Title` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`EmployeeTypeID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EmployeeType`
--

LOCK TABLES `EmployeeType` WRITE;
/*!40000 ALTER TABLE `EmployeeType` DISABLE KEYS */;
INSERT INTO `EmployeeType` VALUES (1,'inventory'),(2,'manager'),(3,'pickup');
/*!40000 ALTER TABLE `EmployeeType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Item`
--

DROP TABLE IF EXISTS `Item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Item` (
  `ItemID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Department_DepartmentID` int(10) unsigned NOT NULL,
  `Description` text,
  `Name` varchar(255) DEFAULT NULL,
  `Price` double DEFAULT NULL,
  `Quantity` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`ItemID`),
  KEY `Item_FKIndex1` (`Department_DepartmentID`),
  CONSTRAINT `fk_{C50EEA61-024F-49D4-88B7-A0CA22DC7B71}` FOREIGN KEY (`Department_DepartmentID`) REFERENCES `Department` (`DepartmentID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Item`
--

LOCK TABLES `Item` WRITE;
/*!40000 ALTER TABLE `Item` DISABLE KEYS */;
INSERT INTO `Item` VALUES (1,1,'Compact and lightweight DSLR featuring a newly-developed 18.0 Megapixel CMOS (APS-C) sensor, ISO 100-12800 (expandable to H: 25600) for stills and ISO-6400 (expandable to H: 12800) for videos for shooting from bright to dim light, and high performance DIGIC 5 Image Processor for exceptional image quality and speed. 9-point AF system (including a high-precision dual-cross f/2.8 center point) for exceptional autofocus performance when shooting with the viewfinder; new Hybrid CMOS AF II, which combines the advantages of high-speed phase-detection AF and high-precision contrast AF, provides a widened Hybrid CMOS AF focus area for increased autofocus speed and accuracy when shooting photos and movies in Live View. High speed continuous shooting up to 4.0 fps allows you to capture all the action EOS Full HD Movie mode with Movie Servo AF for continuous focus tracking of moving subjects, manual exposure control and multiple frame rates (1080: 30p (29.97) / 24p (23.976) / 25p, 720: 60p (59.94) / 50p, 480: 30p (29.97) / 25p), built-in monaural microphone, manual audio level adjustment, and Video Snapshot with editing for expanded movie shooting options. Touch Screen Wide 3.0-inch Clear View LCD monitor II (approximately 1,040,000 dots) with smudge-resistant coating features multi-touch operation with direct access to functions for setting changes and Touch AF for an easy, intuitive experience and clear viewing when outdoors; an Optical Viewfinder with approx. 0.87x magnification makes subjects easier to see.','Canon EOS Rebel SL1',749,25),(2,2,'Game of Thrones, the first book in author George R.R. Martin\'s sprawling fantasy saga A Song of Fire and Ice, serves as the basis for this brawny, lusty series about courtly intrigue and civil war in a sprawling fantasy kingdom. TV and fantasy veteran Sean Bean (The Lord of the Rings, Sharpe\'s Rifles) leads the massive cast as the warrior-noble Eddard Stark, who reluctantly assumes the role as the Hand of the King after the mysterious death of his predecessor. The King, Robert Baratheon, has leadership of the lands of Westeros, a mythical country plagued by severe, decade-long shifts in weather. His rule is challenged by the exiled Prince Viserys Targaryen (Harry Lloyd), who trades his own sister (Emilia Clarke) for the allegiance of the Dothraki, a savage nomadic tribe led by Khal Drogo (Jason Momoa of the 2011 Conan the Barbarian). A shocking secret kept hidden by Queen Cersei Lannister (Lena Headey, 300) leads to an upset in the balance of power and, ultimately, a challenge to the House of Stark to bring control to the bloodshed that threatens to overtake Westeros.','Game of Thrones: The Complete First Season',45.99,32),(3,5,'Chromebooks are easy to use, and don’t slow down over time. They have built-in security, so you’re protected against viruses and malware. They come with apps for all your everyday needs, and keep your files safely backed up on the cloud. And with free, automatic updates, your Chromebook keeps getting better and better.','Samsung Chromebook (Wi-Fi, 11.6-Inch)',249,8),(4,3,'PlayStation 4 is the best place to play with dynamic, connected gaming, powerful graphics and speed, intelligent personalization, deeply integrated social capabilities, and innovative second-screen features. Combining unparalleled content, immersive gaming experiences, all of your favorite digital entertainment apps, and PlayStation exclusives, PS4 centers on gamers, enabling them to play when, where and how they want. PS4 enables the greatest game developers in the world to unlock their creativity and push the boundaries of play through a system that is tuned specifically to their needs.','PlayStation 4',399.99,21),(5,4,'Begin navigating with the TomTom START 55 TM—the biggest, best in basic navigation, with a 5″ screen for a better view of the road ahead. Plus, it comes with Free Lifetime Traffic and Map Updates covering the US and Canada. That means you’ll automatically get real-time traffic updates to your TomTom device and your map will always stay current. The START 55 TM includes our TomTom Maps with IQ Routes and Map Share technology, which keeps you informed of dynamic road changes on a daily basis.','TomTom START 55TM',159.95,13),(6,4,'VS monitors deliver Full HD quality visuals and are LED backlit delivering brighter displays than non-LED backlit monitors. With an ultra-fast 2ms response time, enjoy smooth motion playback for videos and movies. The first monitors to be featured in the renowned Corporate Stable Model (CSM) program, VS monitors are guaranteed in stable supply for a minimum of one year so you can order the same model without worry of it being discontinued and, with the included advanced swap replacement service, saving you time should the unit require servicing. These monitors are EPEAT Gold and ENERGY STAR certified to reduce environmental impact and save on energy costs.','Asus 24-Inch Monitor',194.99,18);
/*!40000 ALTER TABLE `Item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ItemStatus`
--

DROP TABLE IF EXISTS `ItemStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ItemStatus` (
  `ItemStatusID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ItemStatusID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ItemStatus`
--

LOCK TABLES `ItemStatus` WRITE;
/*!40000 ALTER TABLE `ItemStatus` DISABLE KEYS */;
INSERT INTO `ItemStatus` VALUES (1,'in cart'),(2,'paid'),(3,'in basket');
/*!40000 ALTER TABLE `ItemStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrderItem`
--

DROP TABLE IF EXISTS `OrderItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OrderItem` (
  `Item_ItemID` int(10) unsigned NOT NULL,
  `CustomerOrder_OrderID` int(10) unsigned NOT NULL,
  `ItemStatus_ItemStatusID` int(10) unsigned NOT NULL,
  `Quantity` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`Item_ItemID`,`CustomerOrder_OrderID`,`ItemStatus_ItemStatusID`),
  KEY `OrderItem_FKIndex1` (`CustomerOrder_OrderID`),
  KEY `OrderItem_FKIndex2` (`Item_ItemID`),
  KEY `OrderItem_FKIndex3` (`ItemStatus_ItemStatusID`),
  CONSTRAINT `fk_{58789A35-ED13-442C-9D94-207EB3D6551B}` FOREIGN KEY (`ItemStatus_ItemStatusID`) REFERENCES `ItemStatus` (`ItemStatusID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_{5A5F4B7E-A3E1-4664-8C60-35CF624D992F}` FOREIGN KEY (`CustomerOrder_OrderID`) REFERENCES `CustomerOrder` (`OrderID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_{DD48E5BD-5342-49DC-91CC-1F37A5175DB8}` FOREIGN KEY (`Item_ItemID`) REFERENCES `Item` (`ItemID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `OrderStatus`
--

DROP TABLE IF EXISTS `OrderStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OrderStatus` (
  `OrderStatusID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`OrderStatusID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderStatus`
--

LOCK TABLES `OrderStatus` WRITE;
/*!40000 ALTER TABLE `OrderStatus` DISABLE KEYS */;
INSERT INTO `OrderStatus` VALUES (1,'pending'),(2,'paid'),(3,'working'),(4,'completed'),(5,'picked up');
/*!40000 ALTER TABLE `OrderStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Specification`
--

DROP TABLE IF EXISTS `Specification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Specification` (
  `SpecificationID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Item_ItemID` int(10) unsigned NOT NULL,
  `Name` varchar(20) DEFAULT NULL,
  `Detail` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`SpecificationID`),
  KEY `Specification_FKIndex1` (`Item_ItemID`),
  CONSTRAINT `fk_{DA8C8F2C-A949-4C87-BD2F-3548A29CB7ED}` FOREIGN KEY (`Item_ItemID`) REFERENCES `Item` (`ItemID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-07-28 18:21:48
