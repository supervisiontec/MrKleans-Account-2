-- MySQL dump 10.13  Distrib 5.7.21, for Win64 (x86_64)
--
-- Host: localhost    Database: sv_account
-- ------------------------------------------------------
-- Server version	5.7.21-log

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
-- Table structure for table `m_acc_account`
--

DROP TABLE IF EXISTS `m_acc_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_acc_account` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `level` varchar(50) NOT NULL,
  `acc_code` varchar(50) NOT NULL,
  `cop` tinyint(4) NOT NULL,
  `acc_main` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  `acc_type` varchar(50) NOT NULL DEFAULT 'COMMON',
  `sub_account_of` int(11) DEFAULT NULL,
  `is_acc_account` tinyint(4) NOT NULL,
  `description` varchar(500) NOT NULL DEFAULT ' - - -',
  `sub_account_count` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`index_no`),
  KEY `FK_m_acc_account_m_acc_main` (`acc_main`),
  CONSTRAINT `FK_m_acc_account_m_acc_main` FOREIGN KEY (`acc_main`) REFERENCES `m_acc_main` (`index_no`)
) ENGINE=InnoDB AUTO_INCREMENT=2436 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_acc_account`
--

LOCK TABLES `m_acc_account` WRITE;
/*!40000 ALTER TABLE `m_acc_account` DISABLE KEYS */;
INSERT INTO `m_acc_account` VALUES (1580,'CAPITAL','1','01',0,1,1,'COMMON',0,0,'capital',3),(1581,'ASSETS','1','02',0,2,1,'COMMON',0,0,'accets',2),(1582,'LIABILITY','1','03',0,3,1,'COMMON',0,0,'liability',2),(1583,'INCOME','1','04',0,4,1,'COMMON',0,0,'income',1),(1584,'EXPENSE','1','05',0,5,1,'COMMON',0,0,'expense',4),(1585,'Current Assets','2','02.01',0,2,1,'COMMON',1581,0,'Current Assets',5),(1586,'Non Current Assets','2','02.02',0,2,1,'COMMON',1581,1,'Non Current Assets',0),(1587,'Debtors','3','02.01.01',0,2,1,'COMMON',1585,1,'Debtors',0),(1588,'Sales','2','04.01',0,4,1,'COMMON',1583,0,'Sales',2),(1589,'Service Sales Income','3','04.01.01',0,4,1,'COMMON',1588,1,'Service Income',0),(1590,'Cash Book','3','02.01.02',0,2,1,'COMMON',1585,0,'Cash Book',3),(1591,'Cash in Hand','4','02.01.02.01',0,2,2,'CASH',1590,1,'Sales Cash',0),(1607,'Petty Cash','4','02.01.02.02',0,2,1,'CASH',1590,1,'Petty Cash',0),(1639,'Item Sales Income','3','04.01.02',0,4,1,'COMMON',1588,1,'a',0),(1712,'Cheque In Hand','4','02.01.02.03',0,2,1,'CHEQUE',1590,1,'Received(not deposited) issued (not realized) cheques',0),(1713,'Non Current Liability','2','03.01',0,3,1,'COMMON',1582,0,'Non Current Liabilities',1),(1715,'Current Liabilities','2','03.02',0,3,1,'COMMON',1582,0,'current Liabilities',2),(1716,'Creditors','3','03.02.01',0,3,1,'COMMON',1715,1,'Accounts Payables',0),(1717,'Inventory','3','02.01.03',0,2,1,'COMMON',1585,1,'Stock in Hand',0),(1718,'Cost of Sales','2','05.01',0,5,1,'COMMON',1584,0,'Cost of Sales',5),(1719,'Administration Expenses','2','05.02',0,5,1,'COMMON',1584,0,'Admin Expenses',22),(1720,'Sales Discount','3','05.01.01',0,5,1,'COMMON',1718,1,'Sales Discount',0),(1721,'Bank','3','02.01.04',0,2,2,'COMMON',1585,0,'bank',5),(1722,'BOC Credit Card','4','02.01.04.01',0,2,2,'BANK',1721,1,'boc credit card',0),(2144,'Opening Balance Equity','2','01.01',0,1,1,'COMMON',1580,1,'Opening Balance Equity',0),(2240,'DFCC Current Account','4','02.01.04.02',0,2,2,'BANK',1721,1,'Current Account',0),(2241,'DFCC Saving Account','4','02.01.04.03',0,2,2,'COMMON',1721,1,'Saving - DFCC',0),(2242,'Cargills Bank','4','02.01.04.04',0,2,2,'COMMON',1721,1,'Saving - Cargils',0),(2243,'Other Current Assets','3','02.01.05',0,2,2,'COMMON',1585,0,'Other Current Assets',7),(2244,'Advance & Prepayments','4','02.01.05.01',0,2,2,'COMMON',2243,1,'Other Current Assets',0),(2245,'Salary Advance','4','02.01.05.02',0,2,2,'COMMON',2243,1,'Other Current Assets',0),(2246,'Uniform Deduction','4','02.01.05.03',0,2,2,'COMMON',2243,1,'Other Current Assets',0),(2247,'Staff Loan','4','02.01.05.04',0,2,2,'COMMON',2243,1,'Other Current Assets',0),(2248,'Detail ArtXperts (Pvt) Ltd - Non Trade','4','02.01.05.05',0,2,2,'COMMON',2243,1,'Other Current Assets',0),(2249,'DetailXperts (Pvt) Ltd - Non Trade','4','02.01.05.06',0,2,2,'COMMON',2243,1,'Other Current Assets',0),(2250,'Other Current Liabilities','3','03.02.02',0,3,2,'COMMON',1715,1,'Other Current Liabilities',0),(2261,'Retirement Benefit Liability','3','03.01.01',0,3,2,'COMMON',1713,1,'Other Current Liabilities',0),(2262,'Stated Capital','2','01.02',0,1,2,'COMMON',1580,1,'Stated Capital',0),(2263,'Retained Earning','2','01.03',0,1,2,'COMMON',1580,1,'Stated Capital',0),(2264,'Purchases','3','05.01.02',0,5,2,'COMMON',1718,1,'Purchases',0),(2265,'Direct Labour','3','05.01.03',0,5,2,'COMMON',1718,0,'Labour Cost',4),(2266,'Salaries','4','05.01.03.01',0,5,2,'COMMON',2265,1,'Salaries for the period',0),(2267,'EPF','4','05.01.03.02',0,5,2,'COMMON',2265,1,'EPF',0),(2268,'ETF','4','05.01.03.03',0,5,2,'COMMON',2265,1,'EPF',0),(2269,'Casual Wages','4','05.01.03.04',0,5,2,'COMMON',2265,1,'Casual Wages',0),(2271,'Direct Other Cost','3','05.01.04',0,5,2,'COMMON',1718,0,'Direct Other Cost',14),(2272,'Repair - outside','4','05.01.04.01',0,5,2,'COMMON',2271,1,'Direct Other Cost',0),(2273,'Hardware Item','4','05.01.04.02',0,5,2,'COMMON',2271,1,'Direct Other Cost',0),(2274,'Travelling & Transport Charges','4','05.01.04.03',0,5,2,'COMMON',2271,1,'Direct Other Cost',0),(2275,'Credit Card Commission','4','05.01.04.04',0,5,2,'COMMON',2271,1,'Direct Other Cost',0),(2276,'Electricity Charges','4','05.01.04.05',0,5,2,'COMMON',2271,1,'Direct Other Cost',0),(2277,'Water Charges','4','05.01.04.06',0,5,2,'COMMON',2271,1,'Direct Other Cost',0),(2278,'Repair & Maintenance','4','05.01.04.07',0,5,2,'COMMON',2271,1,'Direct Other Cost',0),(2279,'Uniform & Boot','4','05.01.04.08',0,5,2,'COMMON',2271,1,'Direct Other Cost',0),(2280,'Land Rent','4','05.01.04.09',0,5,2,'COMMON',2271,1,'Direct Other Cost',0),(2281,'Depreciation - Building','4','05.01.04.10',0,5,2,'COMMON',2271,1,'Direct Other Cost',0),(2282,'Depreciation - Plant & Machinery','4','05.01.04.11',0,5,2,'COMMON',2271,1,'Direct Other Cost',0),(2283,'Depreciation - Office Equipment','4','05.01.04.12',0,5,2,'COMMON',2271,1,'Direct Other Cost',0),(2284,'Fuel Expenses','4','05.01.04.13',0,5,2,'COMMON',2271,1,'Direct Other Cost',0),(2285,'Meal Expenses','4','05.01.04.14',0,5,2,'COMMON',2271,1,'Direct Other Cost',0),(2286,'Salaries - Admin','3','05.02.01',0,5,2,'COMMON',1719,1,'Administration Expenses',0),(2287,'EPF - Admin','3','05.02.02',0,5,2,'COMMON',1719,1,'Administration Expenses',0),(2288,'ETF - Admin','3','05.02.03',0,5,2,'COMMON',1719,1,'Administration Expenses',0),(2289,'Gratuity Charge','3','05.02.04',0,5,2,'COMMON',1719,1,'Administration Expenses',0),(2290,'Office Equipment Maintenance','3','05.02.05',0,5,2,'COMMON',1719,1,'Administration Expenses',0),(2291,'Office Building MAintenance','3','05.02.06',0,5,2,'COMMON',1719,1,'Administration Expenses',0),(2292,'Printing & Stationary','3','05.02.07',0,5,2,'COMMON',1719,1,'Administration Expenses',0),(2293,'Telephone Charges','3','05.02.08',0,5,2,'COMMON',1719,1,'Administration Expenses',0),(2294,'Travelling','3','05.02.09',0,5,2,'COMMON',1719,1,'Administration Expenses',0),(2295,'Welfare','3','05.02.10',0,5,2,'COMMON',1719,1,'Administration Expenses',0),(2296,'Donation','3','05.02.11',0,5,2,'COMMON',1719,1,'Administration Expenses',0),(2297,'Depreciation - Office Equipment','3','05.02.12',0,5,2,'COMMON',1719,1,'Administration Expenses',0),(2298,'Depreciation -Motor Vehicles','3','05.02.13',0,5,2,'COMMON',1719,1,'Administration Expenses',0),(2299,'Professional Charges','3','05.02.14',0,5,2,'COMMON',1719,1,'Administration Expenses',0),(2300,'Dialog Television Charges','3','05.02.15',0,5,2,'COMMON',1719,1,'Administration Expenses',0),(2301,'Rates & Taxes','3','05.02.16',0,5,2,'COMMON',1719,1,'Administration Expenses',0),(2302,'Medical Expenses','3','05.02.17',0,5,2,'COMMON',1719,1,'Administration Expenses',0),(2303,'Fuel Expenses','3','05.02.18',0,5,2,'COMMON',1719,1,'Administration Expenses',0),(2304,'Vehicle Maintenance Expenses','3','05.02.19',0,5,2,'COMMON',1719,1,'Administration Expenses',0),(2305,'Bank Charges','3','05.02.20',0,5,2,'COMMON',1719,1,'Administration Expenses',0),(2306,'Other Expenses','3','05.02.21',0,5,2,'COMMON',1719,1,'Administration Expenses',0),(2307,'Sales & Marketing','2','05.03',0,5,2,'COMMON',1584,0,'Administration Expenses',2),(2308,'Advertising','3','05.03.01',0,5,2,'COMMON',2307,1,'Administration Expenses',0),(2309,'Sales Commission','3','05.03.02',0,5,2,'COMMON',2307,1,'Administration Expenses',0),(2310,'Advertising Admin','3','05.02.22',0,5,2,'COMMON',1719,1,'Administration Expenses',0),(2311,'Finance Expenses','2','05.04',0,5,2,'COMMON',1584,0,'Administration Expenses',3),(2312,'Lease Interest','3','05.04.01',0,5,2,'COMMON',2311,1,'Finance Expenses',0),(2313,'Loan Interest','3','05.04.02',0,5,2,'COMMON',2311,1,'Finance Expenses',0),(2314,'Overdraft Interest','3','05.04.03',0,5,2,'COMMON',2311,1,'Finance Expenses',0),(2330,'Purchase control Account','3','05.01.05',0,5,2,'COMMON',1718,1,'Purchase control Account',0),(2414,'Cheque Issed','4','02.01.04.05',0,2,2,'COMMON',1721,1,'Issued cheques',0),(2434,'Over Payment Issue','4','02.01.05.07',0,2,2,'COMMON',2243,1,'Over Payment Issue',0);
/*!40000 ALTER TABLE `m_acc_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_acc_account_type`
--

DROP TABLE IF EXISTS `m_acc_account_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_acc_account_type` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `value` varchar(50) DEFAULT NULL,
  `view_ref_no` tinyint(4) DEFAULT NULL,
  `view_cheque_no` tinyint(4) DEFAULT NULL,
  `view_cheque_date` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_acc_account_type`
--

LOCK TABLES `m_acc_account_type` WRITE;
/*!40000 ALTER TABLE `m_acc_account_type` DISABLE KEYS */;
INSERT INTO `m_acc_account_type` VALUES (1,'CASH','CASH',1,0,0),(2,'BANK','BANK',0,1,1),(3,'ONLINE','BANK',1,0,0);
/*!40000 ALTER TABLE `m_acc_account_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_acc_code_setting`
--

DROP TABLE IF EXISTS `m_acc_code_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_acc_code_setting` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `account` int(11) NOT NULL,
  `max_no` int(11) NOT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_acc_code_setting`
--

LOCK TABLES `m_acc_code_setting` WRITE;
/*!40000 ALTER TABLE `m_acc_code_setting` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_acc_code_setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_acc_main`
--

DROP TABLE IF EXISTS `m_acc_main`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_acc_main` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `increment` varchar(50) NOT NULL,
  `is_expence` tinyint(4) NOT NULL,
  `is_income` tinyint(4) NOT NULL,
  `is_balance_sheet` tinyint(4) NOT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_acc_main`
--

LOCK TABLES `m_acc_main` WRITE;
/*!40000 ALTER TABLE `m_acc_main` DISABLE KEYS */;
INSERT INTO `m_acc_main` VALUES (1,'CAPITAL','CREDIT',0,0,1),(2,'ASSETS','DEBIT',0,0,1),(3,'LIABILITY','CREDIT',0,0,1),(4,'INCOME','CREDIT',0,1,0),(5,'EXPENSE','DEBIT',1,0,0);
/*!40000 ALTER TABLE `m_acc_main` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_acc_setting`
--

DROP TABLE IF EXISTS `m_acc_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_acc_setting` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '0',
  `description` varchar(500) DEFAULT '',
  `acc_account` int(11) DEFAULT '0',
  `filter` int(11) DEFAULT '0',
  PRIMARY KEY (`index_no`),
  KEY `FK_m_acc_setting_m_acc_account` (`acc_account`),
  CONSTRAINT `FK_m_acc_setting_m_acc_account` FOREIGN KEY (`acc_account`) REFERENCES `m_acc_account` (`index_no`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_acc_setting`
--

LOCK TABLES `m_acc_setting` WRITE;
/*!40000 ALTER TABLE `m_acc_setting` DISABLE KEYS */;
INSERT INTO `m_acc_setting` VALUES (1,'unrealized_issued','desc',NULL,0),(3,'unrealized_received','desc',NULL,0),(22,'customer_sub_account_of','desc',NULL,0),(23,'supplier_sub_account_of','desc',NULL,0),(24,'stock_item_sub_account_of','desc',NULL,0),(25,'service_item_sub_account_of','desc',NULL,0),(26,'non_stock_item_sub_account_of','desc',NULL,0),(27,'item_sales_cash_in','desc',NULL,0),(28,'cheque_in_hand','desc',NULL,0),(29,'item_discount_out','desc',NULL,0),(30,'item_sales','desc',NULL,0),(31,'nbt_account_out','desc',NULL,0),(32,'vat_account_out','desc',NULL,0),(33,'nbt_account_in','desc',NULL,0),(34,'vat_account_in','desc',NULL,0),(35,'over_payment_issue','desc',NULL,2),(36,'over_payment_received','desc',NULL,3),(37,'service_sales','desc',NULL,0),(38,'stock_adjustment_contol_account','desc',NULL,0);
/*!40000 ALTER TABLE `m_acc_setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_account`
--

DROP TABLE IF EXISTS `m_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_account` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_account`
--

LOCK TABLES `m_account` WRITE;
/*!40000 ALTER TABLE `m_account` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_appointment_bay`
--

DROP TABLE IF EXISTS `m_appointment_bay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_appointment_bay` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `branch` int(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `time` time DEFAULT NULL,
  `size` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_appointment_bay`
--

LOCK TABLES `m_appointment_bay` WRITE;
/*!40000 ALTER TABLE `m_appointment_bay` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_appointment_bay` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_appointment_item`
--

DROP TABLE IF EXISTS `m_appointment_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_appointment_item` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `m_item` int(10) NOT NULL,
  `other_name` varchar(50) DEFAULT NULL,
  `colour_code` varchar(50) NOT NULL,
  `time` time DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_m_appointment_item_m_item1_idx` (`m_item`),
  CONSTRAINT `fk_m_appointment_item_m_item1` FOREIGN KEY (`m_item`) REFERENCES `m_item` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_appointment_item`
--

LOCK TABLES `m_appointment_item` WRITE;
/*!40000 ALTER TABLE `m_appointment_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_appointment_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_bank`
--

DROP TABLE IF EXISTS `m_bank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_bank` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `code` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_bank`
--

LOCK TABLES `m_bank` WRITE;
/*!40000 ALTER TABLE `m_bank` DISABLE KEYS */;
INSERT INTO `m_bank` VALUES (1,'Sampath',NULL),(2,'Commercial ',NULL),(3,'Hatton National Bank',NULL),(4,'Bank of Ceylon',NULL),(5,'People\'s Bank',NULL),(6,'Nations Trust Bank',NULL),(7,'Seylan',NULL),(9,'DFCC',NULL),(10,'BOC',NULL),(11,'-',NULL);
/*!40000 ALTER TABLE `m_bank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_bank_branch`
--

DROP TABLE IF EXISTS `m_bank_branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_bank_branch` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `code` varchar(25) DEFAULT NULL,
  `bank` int(10) NOT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_m_bank_branch_m_bank1_idx` (`bank`),
  CONSTRAINT `fk_m_bank_branch_m_bank1` FOREIGN KEY (`bank`) REFERENCES `m_bank` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_bank_branch`
--

LOCK TABLES `m_bank_branch` WRITE;
/*!40000 ALTER TABLE `m_bank_branch` DISABLE KEYS */;
INSERT INTO `m_bank_branch` VALUES (1,'Panadura',NULL,1),(2,'Galle',NULL,1),(3,'Nugegoda',NULL,2),(4,'Maharagama',NULL,2),(5,'Rathnapura',NULL,3),(6,'Kandy',NULL,3),(7,'Thalawtugoda',NULL,4),(8,'Atulkotte',NULL,4),(9,'Galle',NULL,5),(10,'Nugegoda',NULL,5),(11,'Maharagama',NULL,6),(12,'Rathnapura',NULL,6),(13,'Kandy',NULL,7),(14,'Thalawtugoda',NULL,7),(15,'Atulkotte',NULL,1),(17,'walana',NULL,9),(18,'Union Place',NULL,10),(19,'-',NULL,11);
/*!40000 ALTER TABLE `m_bank_branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_bay`
--

DROP TABLE IF EXISTS `m_bay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_bay` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  `main_bay` int(11) DEFAULT NULL,
  `max_vehicle` int(10) NOT NULL,
  `max_employee` int(10) NOT NULL,
  `x` int(10) NOT NULL,
  `y` int(10) NOT NULL,
  `w` int(10) NOT NULL,
  `h` int(10) NOT NULL,
  `type` varchar(25) DEFAULT NULL,
  `assign_employee` tinyint(1) NOT NULL,
  `assign_vehicle` tinyint(1) NOT NULL,
  `color` varchar(25) DEFAULT NULL,
  `vehicle_is_view` tinyint(1) DEFAULT NULL,
  `employee_is_view` tinyint(1) NOT NULL,
  `branch` int(11) DEFAULT NULL,
  `time_period` time DEFAULT NULL,
  PRIMARY KEY (`index_no`),
  KEY `FK_m_bay_m_bay_main` (`main_bay`),
  CONSTRAINT `FK_m_bay_m_bay_main` FOREIGN KEY (`main_bay`) REFERENCES `m_bay_main` (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_bay`
--

LOCK TABLES `m_bay` WRITE;
/*!40000 ALTER TABLE `m_bay` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_bay` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_bay_main`
--

DROP TABLE IF EXISTS `m_bay_main`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_bay_main` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_bay_main`
--

LOCK TABLES `m_bay_main` WRITE;
/*!40000 ALTER TABLE `m_bay_main` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_bay_main` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_branch`
--

DROP TABLE IF EXISTS `m_branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_branch` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `branch_code` varchar(45) NOT NULL,
  `reg_number` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `address_line1` varchar(45) DEFAULT NULL,
  `address_line2` varchar(45) DEFAULT NULL,
  `address_line3` varchar(45) DEFAULT NULL,
  `telephone_number` varchar(45) DEFAULT NULL,
  `color` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`index_no`,`branch_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_branch`
--

LOCK TABLES `m_branch` WRITE;
/*!40000 ALTER TABLE `m_branch` DISABLE KEYS */;
INSERT INTO `m_branch` VALUES (1,'DE','swd-dd33','DE',NULL,NULL,NULL,NULL,'#50c14e','MAIN_BRANCH');
/*!40000 ALTER TABLE `m_branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_brand`
--

DROP TABLE IF EXISTS `m_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_brand` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_brand`
--

LOCK TABLES `m_brand` WRITE;
/*!40000 ALTER TABLE `m_brand` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_budget`
--

DROP TABLE IF EXISTS `m_budget`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_budget` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `financial_year` int(11) NOT NULL,
  `budget_month` int(11) NOT NULL,
  `cost_department` int(11) NOT NULL,
  `cost_center` int(11) NOT NULL,
  `budget` decimal(20,4) NOT NULL,
  `branch` int(11) NOT NULL,
  PRIMARY KEY (`index_no`),
  KEY `FK_t_budget_m_financial_year` (`financial_year`),
  KEY `FK_t_budget_m_cost_center` (`cost_center`),
  KEY `FK_t_budget_m_cost_department` (`cost_department`),
  CONSTRAINT `FK_t_budget_m_cost_center` FOREIGN KEY (`cost_center`) REFERENCES `m_cost_center` (`index_no`),
  CONSTRAINT `FK_t_budget_m_cost_department` FOREIGN KEY (`cost_department`) REFERENCES `m_cost_department` (`index_no`),
  CONSTRAINT `FK_t_budget_m_financial_year` FOREIGN KEY (`financial_year`) REFERENCES `m_financial_year` (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_budget`
--

LOCK TABLES `m_budget` WRITE;
/*!40000 ALTER TABLE `m_budget` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_budget` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_calander`
--

DROP TABLE IF EXISTS `m_calander`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_calander` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `status` varchar(50) NOT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_calander`
--

LOCK TABLES `m_calander` WRITE;
/*!40000 ALTER TABLE `m_calander` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_calander` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_calander_default`
--

DROP TABLE IF EXISTS `m_calander_default`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_calander_default` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `year` int(11) NOT NULL,
  `set_default` tinyint(4) NOT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_calander_default`
--

LOCK TABLES `m_calander_default` WRITE;
/*!40000 ALTER TABLE `m_calander_default` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_calander_default` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_card_reader`
--

DROP TABLE IF EXISTS `m_card_reader`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_card_reader` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `number` int(11) NOT NULL,
  `branch` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `acc_account` int(11) DEFAULT NULL,
  PRIMARY KEY (`index_no`),
  KEY `FK_m_card_reader_m_acc_account` (`acc_account`),
  KEY `FK_m_card_reader_m_branch` (`branch`),
  CONSTRAINT `FK_m_card_reader_m_acc_account` FOREIGN KEY (`acc_account`) REFERENCES `m_acc_account` (`index_no`),
  CONSTRAINT `FK_m_card_reader_m_branch` FOREIGN KEY (`branch`) REFERENCES `m_branch` (`index_no`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_card_reader`
--

LOCK TABLES `m_card_reader` WRITE;
/*!40000 ALTER TABLE `m_card_reader` DISABLE KEYS */;
INSERT INTO `m_card_reader` VALUES (1,1,2,'BOC',NULL);
/*!40000 ALTER TABLE `m_card_reader` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_card_type`
--

DROP TABLE IF EXISTS `m_card_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_card_type` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_card_type`
--

LOCK TABLES `m_card_type` WRITE;
/*!40000 ALTER TABLE `m_card_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_card_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_category`
--

DROP TABLE IF EXISTS `m_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_category` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_category`
--

LOCK TABLES `m_category` WRITE;
/*!40000 ALTER TABLE `m_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_client`
--

DROP TABLE IF EXISTS `m_client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_client` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `address_line1` varchar(100) DEFAULT NULL,
  `address_line2` varchar(50) DEFAULT NULL,
  `address_line3` varchar(50) DEFAULT NULL,
  `mobile` varchar(25) DEFAULT NULL,
  `nic` varchar(25) DEFAULT NULL,
  `branch` int(10) NOT NULL,
  `resident` varchar(50) DEFAULT NULL,
  `is_new` tinyint(4) DEFAULT '1',
  `date` date DEFAULT NULL,
  `acc_account` int(11) DEFAULT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_client`
--

LOCK TABLES `m_client` WRITE;
/*!40000 ALTER TABLE `m_client` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_cost_center`
--

DROP TABLE IF EXISTS `m_cost_center`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_cost_center` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `is_active` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_cost_center`
--

LOCK TABLES `m_cost_center` WRITE;
/*!40000 ALTER TABLE `m_cost_center` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_cost_center` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_cost_department`
--

DROP TABLE IF EXISTS `m_cost_department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_cost_department` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `is_active` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_cost_department`
--

LOCK TABLES `m_cost_department` WRITE;
/*!40000 ALTER TABLE `m_cost_department` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_cost_department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_employee`
--

DROP TABLE IF EXISTS `m_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_employee` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `address_line1` varchar(100) DEFAULT NULL,
  `address_line2` varchar(50) DEFAULT NULL,
  `address_line3` varchar(50) DEFAULT NULL,
  `mobile` varchar(25) DEFAULT NULL,
  `branch` int(10) NOT NULL,
  `type` varchar(25) NOT NULL,
  `rol` varchar(25) NOT NULL,
  `image` varchar(45) DEFAULT NULL,
  `bay` int(11) DEFAULT NULL,
  `epf_no` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_employee`
--

LOCK TABLES `m_employee` WRITE;
/*!40000 ALTER TABLE `m_employee` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_final_check_list_item`
--

DROP TABLE IF EXISTS `m_final_check_list_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_final_check_list_item` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_final_check_list_item`
--

LOCK TABLES `m_final_check_list_item` WRITE;
/*!40000 ALTER TABLE `m_final_check_list_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_final_check_list_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_financial_year`
--

DROP TABLE IF EXISTS `m_financial_year`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_financial_year` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `name` varchar(50) NOT NULL,
  `is_balance_sheet` tinyint(4) NOT NULL,
  `is_pl` tinyint(4) NOT NULL,
  `profit` decimal(20,4) DEFAULT '0.0000',
  `is_current` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_financial_year`
--

LOCK TABLES `m_financial_year` WRITE;
/*!40000 ALTER TABLE `m_financial_year` DISABLE KEYS */;
INSERT INTO `m_financial_year` VALUES (1,'2018-03-31','2019-04-01','2018/19',0,0,0.0000,1);
/*!40000 ALTER TABLE `m_financial_year` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_form_name`
--

DROP TABLE IF EXISTS `m_form_name`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_form_name` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_form_name`
--

LOCK TABLES `m_form_name` WRITE;
/*!40000 ALTER TABLE `m_form_name` DISABLE KEYS */;
INSERT INTO `m_form_name` VALUES (1,'Create Account'),(2,'Account Setting'),(3,'User Permission'),(4,'Item Sales'),(5,'Customer Payment'),(6,'Item Registration'),(7,'Client Registration'),(8,'Supplier Registration'),(9,'Return Cheque'),(10,'Reports'),(11,'Supplier Payment'),(12,'Accrued Bill'),(13,'Fund Transfer'),(14,'Bank Reconciliation'),(15,'General Voucher'),(16,'Journal'),(17,'Grn Direct'),(18,'Trial Balance'),(19,'Code Setting'),(20,'Cost Center'),(21,'Cost Department'),(22,'Budget');
/*!40000 ALTER TABLE `m_form_name` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_item`
--

DROP TABLE IF EXISTS `m_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_item` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `barcode` varchar(125) DEFAULT NULL,
  `print_description` varchar(150) DEFAULT NULL,
  `unit` varchar(125) DEFAULT NULL,
  `supplier` int(10) DEFAULT NULL,
  `department` int(10) DEFAULT NULL,
  `brand` int(10) DEFAULT NULL,
  `category` int(10) DEFAULT NULL,
  `item_category` int(10) DEFAULT NULL,
  `sub_category` int(10) DEFAULT NULL,
  `price_category` int(10) DEFAULT NULL,
  `sale_price_normal` decimal(10,4) DEFAULT NULL,
  `sale_price_register` decimal(10,4) DEFAULT NULL,
  `cost_price` decimal(10,0) DEFAULT NULL,
  `type` varchar(25) DEFAULT NULL,
  `re_order_max` decimal(10,4) DEFAULT NULL,
  `re_order_min` decimal(10,4) DEFAULT NULL,
  `discount` decimal(10,4) DEFAULT NULL,
  `supplier_price` decimal(10,4) DEFAULT NULL,
  `account` int(11) DEFAULT NULL,
  `master_item` int(11) DEFAULT NULL,
  `qty_wise` tinyint(4) DEFAULT NULL,
  `bay` int(11) DEFAULT NULL,
  `bay2` int(11) DEFAULT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_m_item_m_item_department1_idx` (`department`),
  KEY `fk_m_item_m_category1_idx` (`category`),
  KEY `fk_m_item_m_brand1_idx` (`brand`),
  KEY `fk_m_item_m_sub_category1_idx` (`sub_category`),
  KEY `fk_m_item_m_price_category1_idx` (`price_category`),
  KEY `fk_m_item_m_supplier1_idx` (`supplier`),
  KEY `FK_m_item_m_item_caregory` (`item_category`),
  KEY `FK_m_item_m_bay_main` (`bay`),
  CONSTRAINT `FK_m_item_m_bay_main` FOREIGN KEY (`bay`) REFERENCES `m_bay_main` (`index_no`),
  CONSTRAINT `FK_m_item_m_item_caregory` FOREIGN KEY (`item_category`) REFERENCES `m_item_caregory` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_m_item_m_brand1` FOREIGN KEY (`brand`) REFERENCES `m_brand` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_m_item_m_category1` FOREIGN KEY (`category`) REFERENCES `m_category` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_m_item_m_item_department1` FOREIGN KEY (`department`) REFERENCES `m_item_department` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_m_item_m_price_category1` FOREIGN KEY (`price_category`) REFERENCES `m_price_category` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_m_item_m_sub_category1` FOREIGN KEY (`sub_category`) REFERENCES `m_sub_category` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_m_item_m_supplier1` FOREIGN KEY (`supplier`) REFERENCES `m_supplier` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_item`
--

LOCK TABLES `m_item` WRITE;
/*!40000 ALTER TABLE `m_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_item_caregory`
--

DROP TABLE IF EXISTS `m_item_caregory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_item_caregory` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_item_caregory`
--

LOCK TABLES `m_item_caregory` WRITE;
/*!40000 ALTER TABLE `m_item_caregory` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_item_caregory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_item_check_detail`
--

DROP TABLE IF EXISTS `m_item_check_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_item_check_detail` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `item` int(10) NOT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_m_item_check_detail_m_item1_idx` (`item`),
  CONSTRAINT `fk_m_item_check_detail_m_item1` FOREIGN KEY (`item`) REFERENCES `m_item` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_item_check_detail`
--

LOCK TABLES `m_item_check_detail` WRITE;
/*!40000 ALTER TABLE `m_item_check_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_item_check_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_item_department`
--

DROP TABLE IF EXISTS `m_item_department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_item_department` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_item_department`
--

LOCK TABLES `m_item_department` WRITE;
/*!40000 ALTER TABLE `m_item_department` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_item_department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_item_units`
--

DROP TABLE IF EXISTS `m_item_units`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_item_units` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `item` int(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `unit` varchar(100) DEFAULT 'number',
  `qty` decimal(10,4) NOT NULL,
  `sale_price_normal` decimal(20,4) DEFAULT '0.0000',
  `sale_price_register` decimal(20,4) DEFAULT '0.0000',
  `cost_price` decimal(20,4) NOT NULL,
  `item_unit_type` varchar(25) NOT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_m_item_units_m_item1_idx` (`item`),
  CONSTRAINT `fk_m_item_units_m_item1` FOREIGN KEY (`item`) REFERENCES `m_item` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=big5;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_item_units`
--

LOCK TABLES `m_item_units` WRITE;
/*!40000 ALTER TABLE `m_item_units` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_item_units` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_price_category`
--

DROP TABLE IF EXISTS `m_price_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_price_category` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `colour` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_price_category`
--

LOCK TABLES `m_price_category` WRITE;
/*!40000 ALTER TABLE `m_price_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_price_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_price_category_details`
--

DROP TABLE IF EXISTS `m_price_category_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_price_category_details` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `price_category` int(10) NOT NULL DEFAULT '0',
  `item` int(10) NOT NULL DEFAULT '0',
  `normal_price` decimal(10,4) NOT NULL DEFAULT '0.0000',
  `register_price` decimal(10,4) NOT NULL DEFAULT '0.0000',
  `time` time DEFAULT '00:00:00',
  PRIMARY KEY (`index_no`),
  KEY `FK__m_price_category` (`price_category`),
  KEY `FK__m_item` (`item`),
  CONSTRAINT `FK__m_item` FOREIGN KEY (`item`) REFERENCES `m_item` (`index_no`),
  CONSTRAINT `FK__m_price_category` FOREIGN KEY (`price_category`) REFERENCES `m_price_category` (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_price_category_details`
--

LOCK TABLES `m_price_category_details` WRITE;
/*!40000 ALTER TABLE `m_price_category_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_price_category_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_printers_details`
--

DROP TABLE IF EXISTS `m_printers_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_printers_details` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `branch_index_no` int(11) DEFAULT '0',
  `branch_name` varchar(50) DEFAULT '0',
  `branch_contact_no` varchar(50) DEFAULT NULL,
  `printers` varchar(50) DEFAULT '0',
  `print_status` enum('ACTIVE','INACTIVE') DEFAULT NULL,
  `estimate_sms` enum('ACTIVE','INACTIVE') DEFAULT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_printers_details`
--

LOCK TABLES `m_printers_details` WRITE;
/*!40000 ALTER TABLE `m_printers_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_printers_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_re_order_level`
--

DROP TABLE IF EXISTS `m_re_order_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_re_order_level` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `item` int(10) NOT NULL,
  `branch` int(10) NOT NULL,
  `re_order_max` decimal(10,4) NOT NULL DEFAULT '0.0000',
  `re_order_min` decimal(10,4) NOT NULL DEFAULT '0.0000',
  PRIMARY KEY (`index_no`),
  KEY `fk_table1_m_item1_idx` (`item`),
  CONSTRAINT `fk_table1_m_item1` FOREIGN KEY (`item`) REFERENCES `m_item` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_re_order_level`
--

LOCK TABLES `m_re_order_level` WRITE;
/*!40000 ALTER TABLE `m_re_order_level` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_re_order_level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_store`
--

DROP TABLE IF EXISTS `m_store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_store` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  `number` int(10) DEFAULT '1',
  `type` varchar(25) NOT NULL,
  `branch` int(10) NOT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_store`
--

LOCK TABLES `m_store` WRITE;
/*!40000 ALTER TABLE `m_store` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_sub_category`
--

DROP TABLE IF EXISTS `m_sub_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_sub_category` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_sub_category`
--

LOCK TABLES `m_sub_category` WRITE;
/*!40000 ALTER TABLE `m_sub_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_sub_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_sub_item`
--

DROP TABLE IF EXISTS `m_sub_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_sub_item` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `branch` int(10) NOT NULL,
  `sub_name` varchar(25) NOT NULL,
  `short_order` int(10) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `item` int(10) NOT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_m_sub_item_m_item1_idx` (`item`),
  CONSTRAINT `fk_m_sub_item_m_item1` FOREIGN KEY (`item`) REFERENCES `m_item` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_sub_item`
--

LOCK TABLES `m_sub_item` WRITE;
/*!40000 ALTER TABLE `m_sub_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_sub_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_supplier`
--

DROP TABLE IF EXISTS `m_supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_supplier` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `contact_name` varchar(50) DEFAULT NULL,
  `contact_no` varchar(25) DEFAULT NULL,
  `address_line_1` varchar(25) DEFAULT NULL,
  `address_line_2` varchar(25) DEFAULT NULL,
  `address_line_3` varchar(25) DEFAULT NULL,
  `credit_period` int(10) DEFAULT NULL,
  `acc_account` int(10) DEFAULT NULL,
  `type` varchar(50) NOT NULL DEFAULT 'MORMAL',
  PRIMARY KEY (`index_no`),
  KEY `FK_m_supplier_m_acc_account` (`acc_account`),
  CONSTRAINT `FK_m_supplier_m_acc_account` FOREIGN KEY (`acc_account`) REFERENCES `m_acc_account` (`index_no`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_supplier`
--

LOCK TABLES `m_supplier` WRITE;
/*!40000 ALTER TABLE `m_supplier` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_time_percentage`
--

DROP TABLE IF EXISTS `m_time_percentage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_time_percentage` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `employee_count` int(11) NOT NULL,
  `percentage` int(11) NOT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_time_percentage`
--

LOCK TABLES `m_time_percentage` WRITE;
/*!40000 ALTER TABLE `m_time_percentage` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_time_percentage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_user`
--

DROP TABLE IF EXISTS `m_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_user` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `branch` int(10) NOT NULL,
  `name` varchar(25) NOT NULL,
  `username` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL,
  PRIMARY KEY (`index_no`),
  KEY `FK_m_user_m_branch` (`branch`),
  CONSTRAINT `FK_m_user_m_branch` FOREIGN KEY (`branch`) REFERENCES `m_branch` (`index_no`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_user`
--

LOCK TABLES `m_user` WRITE;
/*!40000 ALTER TABLE `m_user` DISABLE KEYS */;
INSERT INTO `m_user` VALUES (1,1,'COMPASS','de','de'),(2,1,'admin','mac','123');
/*!40000 ALTER TABLE `m_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_vehicle`
--

DROP TABLE IF EXISTS `m_vehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_vehicle` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `client` int(10) NOT NULL,
  `vehicle_no` varchar(25) DEFAULT NULL,
  `vehicle_type` int(10) DEFAULT NULL,
  `year` int(10) DEFAULT NULL,
  `engine_no` varchar(50) DEFAULT NULL,
  `chasis_no` varchar(50) DEFAULT NULL,
  `insurance_expiry_date` date DEFAULT NULL,
  `revenue_expiry_date` date DEFAULT NULL,
  `last_milage` int(10) DEFAULT NULL,
  `next_milage` int(10) DEFAULT NULL,
  `colour` varchar(25) DEFAULT NULL,
  `price_category` int(10) DEFAULT NULL,
  `type` varchar(25) DEFAULT NULL,
  `brand` varchar(25) DEFAULT NULL,
  `model` varchar(25) DEFAULT NULL,
  `fuel_type` varchar(25) DEFAULT NULL,
  `is_new` tinyint(4) DEFAULT '1',
  `date` date DEFAULT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_m_vehicle_m_client_idx` (`client`),
  KEY `fk_m_vehicle_m_vehicle_type1_idx` (`vehicle_type`),
  KEY `fk_m_vehicle_m_price_category1_idx` (`price_category`),
  CONSTRAINT `fk_m_vehicle_m_client` FOREIGN KEY (`client`) REFERENCES `m_client` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_m_vehicle_m_price_category1` FOREIGN KEY (`price_category`) REFERENCES `m_price_category` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_m_vehicle_m_vehicle_type1` FOREIGN KEY (`vehicle_type`) REFERENCES `m_vehicle_type` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_vehicle`
--

LOCK TABLES `m_vehicle` WRITE;
/*!40000 ALTER TABLE `m_vehicle` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_vehicle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_vehicle_attenctions`
--

DROP TABLE IF EXISTS `m_vehicle_attenctions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_vehicle_attenctions` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `category` int(10) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`index_no`),
  KEY `fk_vehicle_attenctions_vehicle_attenctions_category1_idx` (`category`),
  CONSTRAINT `fk_vehicle_attenctions_vehicle_attenctions_category1` FOREIGN KEY (`category`) REFERENCES `m_vehicle_attenctions_category` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_vehicle_attenctions`
--

LOCK TABLES `m_vehicle_attenctions` WRITE;
/*!40000 ALTER TABLE `m_vehicle_attenctions` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_vehicle_attenctions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_vehicle_attenctions_category`
--

DROP TABLE IF EXISTS `m_vehicle_attenctions_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_vehicle_attenctions_category` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `color` varchar(50) NOT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_vehicle_attenctions_category`
--

LOCK TABLES `m_vehicle_attenctions_category` WRITE;
/*!40000 ALTER TABLE `m_vehicle_attenctions_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_vehicle_attenctions_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_vehicle_type`
--

DROP TABLE IF EXISTS `m_vehicle_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_vehicle_type` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `model` varchar(50) NOT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_vehicle_type`
--

LOCK TABLES `m_vehicle_type` WRITE;
/*!40000 ALTER TABLE `m_vehicle_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_vehicle_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_consumable`
--

DROP TABLE IF EXISTS `r_consumable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_consumable` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `service` int(10) NOT NULL COMMENT 'service item\n',
  `consumable` int(10) NOT NULL COMMENT 'non stock item\n',
  `qty` decimal(10,4) NOT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_consumable`
--

LOCK TABLES `r_consumable` WRITE;
/*!40000 ALTER TABLE `r_consumable` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_consumable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `r_package_item`
--

DROP TABLE IF EXISTS `r_package_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `r_package_item` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `package` int(10) NOT NULL,
  `item` int(10) NOT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_r_package_item_m_item2_idx` (`item`),
  KEY `fk_r_package_item_m_item1` (`package`),
  CONSTRAINT `fk_r_package_item_m_item1` FOREIGN KEY (`package`) REFERENCES `m_item` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_r_package_item_m_item2` FOREIGN KEY (`item`) REFERENCES `m_item` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `r_package_item`
--

LOCK TABLES `r_package_item` WRITE;
/*!40000 ALTER TABLE `r_package_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `r_package_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ras_attrecord`
--

DROP TABLE IF EXISTS `ras_attrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ras_attrecord` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DN` int(11) DEFAULT NULL,
  `DIN` int(11) DEFAULT NULL,
  `Clock` datetime DEFAULT NULL,
  `type` varchar(50) NOT NULL DEFAULT 'AUTO',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ras_attrecord`
--

LOCK TABLES `ras_attrecord` WRITE;
/*!40000 ALTER TABLE `ras_attrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `ras_attrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_acc_ledger`
--

DROP TABLE IF EXISTS `t_acc_ledger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_acc_ledger` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `number` int(11) DEFAULT '0',
  `search_code` varchar(50) DEFAULT '0',
  `transaction_date` date NOT NULL,
  `current_date` date NOT NULL,
  `time` time NOT NULL,
  `branch` int(11) NOT NULL,
  `current_branch` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  `debit` decimal(20,4) NOT NULL,
  `credit` decimal(20,4) NOT NULL,
  `acc_account` int(11) NOT NULL,
  `form_name` varchar(50) NOT NULL,
  `ref_number` varchar(50) DEFAULT NULL,
  `type` varchar(50) NOT NULL COMMENT 'm_customer.m_supplier,m_bank',
  `type_index_no` int(11) DEFAULT NULL,
  `delete_ref_no` int(11) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `cheque_date` date DEFAULT NULL,
  `bank_reconciliation` tinyint(4) NOT NULL DEFAULT '0',
  `reconcile_account` int(11) DEFAULT NULL,
  `reconcile_group` int(11) DEFAULT '0',
  `is_main` tinyint(4) NOT NULL DEFAULT '0',
  `is_cheque` tinyint(4) DEFAULT '0',
  `financial_year` int(11) DEFAULT '1',
  `cost_center` int(11) DEFAULT NULL,
  `cost_department` int(11) DEFAULT NULL,
  PRIMARY KEY (`index_no`),
  KEY `FK__m_acc_account` (`acc_account`),
  CONSTRAINT `FK__m_acc_account` FOREIGN KEY (`acc_account`) REFERENCES `m_acc_account` (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_acc_ledger`
--

LOCK TABLES `t_acc_ledger` WRITE;
/*!40000 ALTER TABLE `t_acc_ledger` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_acc_ledger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_appointment`
--

DROP TABLE IF EXISTS `t_appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_appointment` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `vehicle` int(10) DEFAULT NULL,
  `m_appointment_item` int(10) NOT NULL,
  `price_category` int(10) DEFAULT NULL,
  `received_date` date DEFAULT NULL,
  `appointment_date` date NOT NULL,
  `in_time` time DEFAULT NULL,
  `branch` int(10) NOT NULL,
  `client_name` varchar(50) DEFAULT NULL,
  `contact_no` varchar(25) DEFAULT NULL,
  `vehicle_no` varchar(25) NOT NULL,
  `vehicle_model` varchar(50) DEFAULT NULL,
  `status` int(10) NOT NULL,
  `price_free` tinyint(4) DEFAULT NULL,
  `day_complete` int(11) DEFAULT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_t_appointment_m_vehicle1_idx` (`vehicle`),
  KEY `fk_t_appointment_m_appointment_item1_idx` (`m_appointment_item`),
  KEY `FK_t_appointment_m_price_category` (`price_category`),
  CONSTRAINT `FK_t_appointment_m_price_category` FOREIGN KEY (`price_category`) REFERENCES `m_price_category` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_appointment_m_appointment_item1` FOREIGN KEY (`m_appointment_item`) REFERENCES `m_appointment_item` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_appointment_m_vehicle1` FOREIGN KEY (`vehicle`) REFERENCES `m_vehicle` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_appointment`
--

LOCK TABLES `t_appointment` WRITE;
/*!40000 ALTER TABLE `t_appointment` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_bay_details`
--

DROP TABLE IF EXISTS `t_bay_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_bay_details` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `m_appointment_item` int(10) NOT NULL,
  `m_appointment_bay` int(10) NOT NULL,
  `t_appointment` int(11) DEFAULT NULL,
  `in_time` time DEFAULT NULL,
  `branch` int(10) DEFAULT NULL,
  `date` date NOT NULL,
  `vehicle_no` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_t_bay_details_m_appointment_item1_idx` (`m_appointment_item`),
  KEY `fk_t_bay_details_m_appointment_bay1_idx` (`m_appointment_bay`),
  KEY `FK_t_bay_details_t_appointment` (`t_appointment`),
  CONSTRAINT `FK_t_bay_details_t_appointment` FOREIGN KEY (`t_appointment`) REFERENCES `t_appointment` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_bay_details_m_appointment_bay1` FOREIGN KEY (`m_appointment_bay`) REFERENCES `m_appointment_bay` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_bay_details_m_appointment_item1` FOREIGN KEY (`m_appointment_item`) REFERENCES `m_appointment_item` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_bay_details`
--

LOCK TABLES `t_bay_details` WRITE;
/*!40000 ALTER TABLE `t_bay_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_bay_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_bay_issue`
--

DROP TABLE IF EXISTS `t_bay_issue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_bay_issue` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `item` int(10) NOT NULL,
  `item_units` int(10) NOT NULL,
  `quantity` decimal(10,4) NOT NULL,
  `stock_remove_qty` decimal(10,4) NOT NULL,
  `date` date NOT NULL,
  `order_status` varchar(25) NOT NULL,
  `bay` int(10) NOT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_t_bay_issue_m_item1_idx` (`item`),
  KEY `fk_t_bay_issue_m_item_units1_idx` (`item_units`),
  KEY `fk_t_bay_issue_m_bay1_idx` (`bay`),
  CONSTRAINT `fk_t_bay_issue_m_bay1` FOREIGN KEY (`bay`) REFERENCES `m_bay` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_bay_issue_m_item1` FOREIGN KEY (`item`) REFERENCES `m_item` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_bay_issue_m_item_units1` FOREIGN KEY (`item_units`) REFERENCES `m_item_units` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_bay_issue`
--

LOCK TABLES `t_bay_issue` WRITE;
/*!40000 ALTER TABLE `t_bay_issue` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_bay_issue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_bay_order`
--

DROP TABLE IF EXISTS `t_bay_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_bay_order` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `m_appointment_item` int(10) NOT NULL,
  `m_appointment_bay` int(10) NOT NULL,
  `order` int(10) NOT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_m_appointment_item_has_m_bay_m_bay1_idx` (`m_appointment_bay`),
  KEY `fk_m_appointment_item_has_m_bay_m_appointment_item1_idx` (`m_appointment_item`),
  CONSTRAINT `fk_m_appointment_item_has_m_bay_m_appointment_item1` FOREIGN KEY (`m_appointment_item`) REFERENCES `m_appointment_item` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_m_appointment_item_has_m_bay_m_bay1` FOREIGN KEY (`m_appointment_bay`) REFERENCES `m_appointment_bay` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_bay_order`
--

LOCK TABLES `t_bay_order` WRITE;
/*!40000 ALTER TABLE `t_bay_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_bay_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_bay_stock_leger`
--

DROP TABLE IF EXISTS `t_bay_stock_leger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_bay_stock_leger` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `item` int(10) NOT NULL,
  `in_qty` decimal(10,4) DEFAULT NULL,
  `out_qty` decimal(10,4) DEFAULT NULL,
  `form_index_no` int(10) NOT NULL,
  `form` varchar(25) NOT NULL,
  `bay` int(10) NOT NULL,
  `branch` int(10) NOT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_t_bay_stock_leger_m_bay1_idx` (`bay`),
  KEY `fk_t_bay_stock_leger_m_item1_idx` (`item`),
  CONSTRAINT `fk_t_bay_stock_leger_m_bay1` FOREIGN KEY (`bay`) REFERENCES `m_bay` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_bay_stock_leger_m_item1` FOREIGN KEY (`item`) REFERENCES `m_item` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_bay_stock_leger`
--

LOCK TABLES `t_bay_stock_leger` WRITE;
/*!40000 ALTER TABLE `t_bay_stock_leger` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_bay_stock_leger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_customer_ledger`
--

DROP TABLE IF EXISTS `t_customer_ledger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_customer_ledger` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `invoice` int(10) DEFAULT NULL,
  `payment` int(10) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `debit_amount` decimal(20,4) DEFAULT '0.0000',
  `credit_amount` decimal(20,4) DEFAULT '0.0000',
  `type` varchar(125) DEFAULT NULL,
  `client` int(10) NOT NULL,
  `ref_number` int(10) DEFAULT NULL,
  `form_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_t_customer_payment_t_invoice1_idx` (`invoice`),
  KEY `fk_t_customer_payment_t_payment1_idx` (`payment`),
  KEY `fk_t_customer_ledger_m_client1_idx` (`client`),
  CONSTRAINT `fk_t_customer_ledger_m_client1` FOREIGN KEY (`client`) REFERENCES `m_client` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_customer_payment_t_invoice1` FOREIGN KEY (`invoice`) REFERENCES `t_invoice` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_customer_payment_t_payment1` FOREIGN KEY (`payment`) REFERENCES `t_payment` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_customer_ledger`
--

LOCK TABLES `t_customer_ledger` WRITE;
/*!40000 ALTER TABLE `t_customer_ledger` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_customer_ledger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_daily_check_list`
--

DROP TABLE IF EXISTS `t_daily_check_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_daily_check_list` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `branch` int(10) DEFAULT NULL,
  `transaction` int(10) DEFAULT NULL,
  `complete` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_daily_check_list`
--

LOCK TABLES `t_daily_check_list` WRITE;
/*!40000 ALTER TABLE `t_daily_check_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_daily_check_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_employee_assingment`
--

DROP TABLE IF EXISTS `t_employee_assingment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_employee_assingment` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `employee` int(10) NOT NULL,
  `bay` int(10) NOT NULL,
  `in_time` datetime NOT NULL,
  `out_time` datetime DEFAULT NULL,
  `status` varchar(50) NOT NULL,
  `date` date DEFAULT NULL,
  `is_out` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`index_no`),
  KEY `fk_employee_assingment_m_employee1_idx` (`employee`),
  KEY `fk_employee_assingment_m_bay1_idx` (`bay`),
  CONSTRAINT `fk_employee_assingment_m_bay1` FOREIGN KEY (`bay`) REFERENCES `m_bay` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_employee_assingment_m_employee1` FOREIGN KEY (`employee`) REFERENCES `m_employee` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_employee_assingment`
--

LOCK TABLES `t_employee_assingment` WRITE;
/*!40000 ALTER TABLE `t_employee_assingment` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_employee_assingment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_employee_attendance`
--

DROP TABLE IF EXISTS `t_employee_attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_employee_attendance` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `employee` int(11) NOT NULL DEFAULT '0',
  `date` date NOT NULL,
  `in_time` time NOT NULL DEFAULT '00:00:00',
  `out_time` time NOT NULL DEFAULT '00:00:00',
  `current_bay` int(11) DEFAULT NULL,
  PRIMARY KEY (`index_no`),
  KEY `FK__m_employee` (`employee`),
  CONSTRAINT `FK__m_employee` FOREIGN KEY (`employee`) REFERENCES `m_employee` (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_employee_attendance`
--

LOCK TABLES `t_employee_attendance` WRITE;
/*!40000 ALTER TABLE `t_employee_attendance` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_employee_attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_employee_bay_detail`
--

DROP TABLE IF EXISTS `t_employee_bay_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_employee_bay_detail` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `vehicle_assignment` int(11) NOT NULL,
  `employee` int(11) NOT NULL,
  `status` varchar(50) NOT NULL,
  `in_time` datetime NOT NULL,
  `out_time` datetime DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `type_desc` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`index_no`),
  KEY `employee` (`employee`),
  KEY `FK_t_employee_bay_detail_t_vehicle_assignment` (`vehicle_assignment`),
  CONSTRAINT `FK_t_employee_bay_detail_m_employee` FOREIGN KEY (`employee`) REFERENCES `m_employee` (`index_no`),
  CONSTRAINT `FK_t_employee_bay_detail_t_vehicle_assignment` FOREIGN KEY (`vehicle_assignment`) REFERENCES `t_vehicle_assignment` (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_employee_bay_detail`
--

LOCK TABLES `t_employee_bay_detail` WRITE;
/*!40000 ALTER TABLE `t_employee_bay_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_employee_bay_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_finger_print_mashine`
--

DROP TABLE IF EXISTS `t_finger_print_mashine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_finger_print_mashine` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT 'finger_print',
  `finger_print` int(11) DEFAULT NULL,
  `branch` int(11) DEFAULT NULL,
  PRIMARY KEY (`index_no`),
  KEY `FK__m_branch` (`branch`),
  CONSTRAINT `FK__m_branch` FOREIGN KEY (`branch`) REFERENCES `m_branch` (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_finger_print_mashine`
--

LOCK TABLES `t_finger_print_mashine` WRITE;
/*!40000 ALTER TABLE `t_finger_print_mashine` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_finger_print_mashine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_grn`
--

DROP TABLE IF EXISTS `t_grn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_grn` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `supplier` int(10) NOT NULL,
  `number` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `amount` decimal(20,4) NOT NULL,
  `ref_number` varchar(25) DEFAULT NULL,
  `branch` int(10) NOT NULL,
  `nbt` decimal(20,4) DEFAULT '0.0000',
  `nbt_value` decimal(20,4) DEFAULT '0.0000',
  `vat` decimal(20,4) DEFAULT '0.0000',
  `vat_value` decimal(20,4) DEFAULT '0.0000',
  `grand_amount` decimal(20,4) DEFAULT '0.0000',
  `pay_amount` decimal(20,4) DEFAULT '0.0000',
  `balance_amount` decimal(20,4) DEFAULT '0.0000',
  `return_value` decimal(20,4) DEFAULT '0.0000',
  `status` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `is_nbt` tinyint(1) DEFAULT NULL,
  `is_vat` tinyint(1) DEFAULT NULL,
  `credit_period` int(10) DEFAULT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_t_grn_m_supplier1_idx` (`supplier`),
  CONSTRAINT `fk_t_grn_m_supplier1` FOREIGN KEY (`supplier`) REFERENCES `m_supplier` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_grn`
--

LOCK TABLES `t_grn` WRITE;
/*!40000 ALTER TABLE `t_grn` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_grn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_grn_item`
--

DROP TABLE IF EXISTS `t_grn_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_grn_item` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `purchase_order_item` int(10) DEFAULT NULL,
  `grn` int(10) NOT NULL,
  `item` int(10) DEFAULT NULL,
  `price` decimal(20,4) NOT NULL DEFAULT '0.0000',
  `qty` decimal(20,4) NOT NULL DEFAULT '0.0000',
  `value` decimal(20,4) NOT NULL DEFAULT '0.0000',
  `discount` decimal(20,4) DEFAULT '0.0000',
  `discount_value` decimal(20,4) DEFAULT '0.0000',
  `net_value` decimal(20,4) NOT NULL DEFAULT '0.0000',
  PRIMARY KEY (`index_no`),
  KEY `fk_m_item_has_t_grn_t_grn1_idx` (`grn`),
  KEY `fk_t_grn_item_t_purchase_order_detail1_idx` (`purchase_order_item`),
  CONSTRAINT `fk_m_item_has_t_grn_t_grn1` FOREIGN KEY (`grn`) REFERENCES `t_grn` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_grn_item_t_purchase_order_detail1` FOREIGN KEY (`purchase_order_item`) REFERENCES `t_purchase_order_detail` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_grn_item`
--

LOCK TABLES `t_grn_item` WRITE;
/*!40000 ALTER TABLE `t_grn_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_grn_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_invoice`
--

DROP TABLE IF EXISTS `t_invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_invoice` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `job_card` int(10) NOT NULL,
  `date` date NOT NULL,
  `number` int(10) NOT NULL,
  `amount` decimal(20,4) NOT NULL,
  `discount_rate` decimal(20,4) DEFAULT '0.0000',
  `discount_amount` decimal(20,4) DEFAULT '0.0000',
  `net_amount` decimal(20,4) NOT NULL,
  `nbt_rate` decimal(20,4) DEFAULT '0.0000',
  `nbt_value` decimal(20,4) DEFAULT '0.0000',
  `vat_rate` decimal(20,4) DEFAULT '0.0000',
  `vat_value` decimal(20,4) DEFAULT '0.0000',
  `final_value` decimal(20,4) NOT NULL,
  `branch` int(10) NOT NULL,
  `status` varchar(225) NOT NULL COMMENT 'invoice fill completed or pending\\n',
  PRIMARY KEY (`index_no`),
  UNIQUE KEY `job_card_UNIQUE` (`job_card`),
  KEY `fk_t_invoice_t_job_card1_idx` (`job_card`),
  CONSTRAINT `fk_t_invoice_t_job_card1` FOREIGN KEY (`job_card`) REFERENCES `t_job_card` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_invoice`
--

LOCK TABLES `t_invoice` WRITE;
/*!40000 ALTER TABLE `t_invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_job_activities`
--

DROP TABLE IF EXISTS `t_job_activities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_job_activities` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `item` int(11) NOT NULL,
  `job_card` int(11) NOT NULL,
  `vehicle_assignment` int(11) DEFAULT NULL,
  `job_item` int(11) DEFAULT NULL,
  `activity_time` time NOT NULL,
  `used` tinyint(4) NOT NULL,
  PRIMARY KEY (`index_no`),
  KEY `FK_t_job_activities_m_item` (`item`),
  KEY `FK_t_job_activities_t_vehicle_assignment` (`vehicle_assignment`),
  KEY `FK_t_job_activities_t_job_card` (`job_card`),
  CONSTRAINT `FK_t_job_activities_m_item` FOREIGN KEY (`item`) REFERENCES `m_item` (`index_no`),
  CONSTRAINT `FK_t_job_activities_t_job_card` FOREIGN KEY (`job_card`) REFERENCES `t_job_card` (`index_no`),
  CONSTRAINT `FK_t_job_activities_t_vehicle_assignment` FOREIGN KEY (`vehicle_assignment`) REFERENCES `t_vehicle_assignment` (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_job_activities`
--

LOCK TABLES `t_job_activities` WRITE;
/*!40000 ALTER TABLE `t_job_activities` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_job_activities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_job_card`
--

DROP TABLE IF EXISTS `t_job_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_job_card` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `vehicle` int(10) DEFAULT NULL,
  `number` int(10) DEFAULT '1',
  `branch` int(10) NOT NULL,
  `client` int(10) NOT NULL,
  `date` date NOT NULL,
  `transaction` int(10) DEFAULT NULL,
  `price_category` int(10) DEFAULT NULL,
  `in_time` datetime DEFAULT NULL,
  `out_time` datetime DEFAULT NULL,
  `in_mileage` int(10) DEFAULT NULL,
  `next_mileage` int(10) DEFAULT NULL,
  `status` varchar(25) DEFAULT NULL,
  `form_name` varchar(25) DEFAULT NULL,
  `bay` int(10) DEFAULT NULL,
  `vehicle_images` tinyint(4) NOT NULL DEFAULT '0',
  `service_chagers` tinyint(4) NOT NULL DEFAULT '0',
  `final_check` tinyint(4) NOT NULL DEFAULT '0',
  `attenctions` tinyint(4) NOT NULL DEFAULT '0',
  `stock_issue` tinyint(4) NOT NULL DEFAULT '0',
  `default_final_check` tinyint(4) NOT NULL DEFAULT '0',
  `invoice` tinyint(4) NOT NULL DEFAULT '0',
  `rate` int(11) DEFAULT NULL,
  `rate_reason` varchar(50) DEFAULT NULL,
  `carepet_original` int(11) DEFAULT NULL,
  `carepet_other` int(11) DEFAULT NULL,
  `carepet_3m` int(11) DEFAULT NULL,
  `driver_name` varchar(200) DEFAULT NULL,
  `driver_mobile` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_t_job_card_m_client1_idx` (`client`),
  KEY `fk_t_job_card_m_vehicle1_idx` (`vehicle`),
  CONSTRAINT `fk_t_job_card_m_client1` FOREIGN KEY (`client`) REFERENCES `m_client` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_job_card_m_vehicle1` FOREIGN KEY (`vehicle`) REFERENCES `m_vehicle` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_job_card`
--

LOCK TABLES `t_job_card` WRITE;
/*!40000 ALTER TABLE `t_job_card` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_job_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_job_final_check_list`
--

DROP TABLE IF EXISTS `t_job_final_check_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_job_final_check_list` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `check` varchar(50) NOT NULL,
  `job_card` int(10) NOT NULL,
  `final_check_list_item` int(10) DEFAULT NULL,
  `vehicle` int(10) NOT NULL,
  `date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_t_job_final_check_list_t_job_card1_idx` (`job_card`),
  KEY `fk_t_job_final_check_list_m_final_check_list_item1_idx` (`final_check_list_item`),
  KEY `fk_t_job_final_check_list_m_vehicle1_idx` (`vehicle`),
  CONSTRAINT `fk_t_job_final_check_list_m_final_check_list_item1` FOREIGN KEY (`final_check_list_item`) REFERENCES `m_final_check_list_item` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_job_final_check_list_m_vehicle1` FOREIGN KEY (`vehicle`) REFERENCES `m_vehicle` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_job_final_check_list_t_job_card1` FOREIGN KEY (`job_card`) REFERENCES `t_job_card` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_job_final_check_list`
--

LOCK TABLES `t_job_final_check_list` WRITE;
/*!40000 ALTER TABLE `t_job_final_check_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_job_final_check_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_job_item`
--

DROP TABLE IF EXISTS `t_job_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_job_item` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `job_card` int(10) NOT NULL,
  `item` int(10) DEFAULT NULL,
  `item_unit` int(10) DEFAULT NULL,
  `item_name` varchar(100) DEFAULT NULL,
  `item_type` varchar(25) NOT NULL,
  `quantity` decimal(20,4) NOT NULL DEFAULT '0.0000',
  `stock_remove_qty` decimal(20,4) DEFAULT '0.0000',
  `price` decimal(20,4) DEFAULT '0.0000',
  `value` decimal(20,4) DEFAULT '0.0000',
  `cost_price` decimal(20,4) DEFAULT '0.0000',
  `order_status` varchar(25) DEFAULT NULL,
  `job_status` varchar(25) DEFAULT NULL,
  `is_change` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_t_job_item_t_job_card1_idx` (`job_card`),
  KEY `fk_t_job_item_m_item1_idx` (`item`),
  KEY `fk_t_job_item_m_item_units1_idx` (`item_unit`),
  CONSTRAINT `fk_t_job_item_m_item1` FOREIGN KEY (`item`) REFERENCES `m_item` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_job_item_m_item_units1` FOREIGN KEY (`item_unit`) REFERENCES `m_item_units` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_job_item_t_job_card1` FOREIGN KEY (`job_card`) REFERENCES `t_job_card` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_job_item`
--

LOCK TABLES `t_job_item` WRITE;
/*!40000 ALTER TABLE `t_job_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_job_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_job_item_check`
--

DROP TABLE IF EXISTS `t_job_item_check`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_job_item_check` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `job_item` int(10) NOT NULL,
  `item_check_detail` int(10) NOT NULL,
  `date` date NOT NULL,
  `status` varchar(25) NOT NULL,
  `job_card` int(10) NOT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_t_job_item_check_t_job_item1_idx` (`job_item`),
  KEY `fk_t_job_item_check_m_item_check_detail1_idx` (`item_check_detail`),
  KEY `FK_t_job_item_check_t_job_card` (`job_card`),
  CONSTRAINT `FK_t_job_item_check_t_job_card` FOREIGN KEY (`job_card`) REFERENCES `t_job_card` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_job_item_check_m_item_check_detail1` FOREIGN KEY (`item_check_detail`) REFERENCES `m_item_check_detail` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_job_item_check_t_job_item1` FOREIGN KEY (`job_item`) REFERENCES `t_job_item` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_job_item_check`
--

LOCK TABLES `t_job_item_check` WRITE;
/*!40000 ALTER TABLE `t_job_item_check` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_job_item_check` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_job_item_employee`
--

DROP TABLE IF EXISTS `t_job_item_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_job_item_employee` (
  `job_item` int(10) NOT NULL,
  `employee` int(10) NOT NULL,
  KEY `fk_t_job_item_employee_t_job_item1_idx` (`job_item`),
  KEY `fk_t_job_item_employee_m_employee1_idx` (`employee`),
  CONSTRAINT `fk_t_job_item_employee_m_employee1` FOREIGN KEY (`employee`) REFERENCES `m_employee` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_job_item_employee_t_job_item1` FOREIGN KEY (`job_item`) REFERENCES `t_job_item` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_job_item_employee`
--

LOCK TABLES `t_job_item_employee` WRITE;
/*!40000 ALTER TABLE `t_job_item_employee` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_job_item_employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_job_vehicle_attenctions`
--

DROP TABLE IF EXISTS `t_job_vehicle_attenctions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_job_vehicle_attenctions` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `vehicle_attenctions` int(10) NOT NULL,
  `vehicle_attenctions_category` int(10) NOT NULL,
  `job_card` int(10) NOT NULL,
  `status` varchar(25) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_t_job_m_vehicle_attenctions1_idx` (`vehicle_attenctions`),
  KEY `fk_t_job_t_job_card1_idx` (`job_card`),
  CONSTRAINT `fk_t_job_m_vehicle_attenctions1` FOREIGN KEY (`vehicle_attenctions`) REFERENCES `m_vehicle_attenctions` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_job_t_job_card1` FOREIGN KEY (`job_card`) REFERENCES `t_job_card` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_job_vehicle_attenctions`
--

LOCK TABLES `t_job_vehicle_attenctions` WRITE;
/*!40000 ALTER TABLE `t_job_vehicle_attenctions` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_job_vehicle_attenctions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_normal_invoice_item`
--

DROP TABLE IF EXISTS `t_normal_invoice_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_normal_invoice_item` (
  `index_no` int(10) NOT NULL,
  `invoice` int(10) NOT NULL,
  `item` int(10) DEFAULT NULL,
  `item_unit` int(10) DEFAULT NULL,
  `quantity` decimal(10,4) NOT NULL,
  `price` decimal(10,4) NOT NULL,
  `value` decimal(10,4) NOT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_t_normal_invoice_item_t_invoice1_idx` (`invoice`),
  KEY `fk_t_normal_invoice_item_m_item1_idx` (`item`),
  KEY `fk_t_normal_invoice_item_m_item_units1_idx` (`item_unit`),
  CONSTRAINT `fk_t_normal_invoice_item_m_item1` FOREIGN KEY (`item`) REFERENCES `m_item` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_normal_invoice_item_m_item_units1` FOREIGN KEY (`item_unit`) REFERENCES `m_item_units` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_normal_invoice_item_t_invoice1` FOREIGN KEY (`invoice`) REFERENCES `t_invoice` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_normal_invoice_item`
--

LOCK TABLES `t_normal_invoice_item` WRITE;
/*!40000 ALTER TABLE `t_normal_invoice_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_normal_invoice_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_package_item_detail`
--

DROP TABLE IF EXISTS `t_package_item_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_package_item_detail` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `job_card` int(10) NOT NULL,
  `job_item` int(10) NOT NULL,
  `item` int(10) NOT NULL,
  `status` varchar(25) NOT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_t_package_item_detail_t_job_item1_idx` (`job_item`),
  KEY `fk_t_package_item_detail_t_job_card1_idx` (`job_card`),
  CONSTRAINT `fk_t_package_item_detail_t_job_card1` FOREIGN KEY (`job_card`) REFERENCES `t_job_card` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_package_item_detail_t_job_item1` FOREIGN KEY (`job_item`) REFERENCES `t_job_item` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_package_item_detail`
--

LOCK TABLES `t_package_item_detail` WRITE;
/*!40000 ALTER TABLE `t_package_item_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_package_item_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_payment`
--

DROP TABLE IF EXISTS `t_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_payment` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `number` varchar(50) NOT NULL,
  `total_amount` decimal(10,4) NOT NULL,
  `cash_amount` decimal(20,4) DEFAULT '0.0000',
  `cheque_amount` decimal(20,4) DEFAULT '0.0000',
  `card_amount` decimal(20,4) DEFAULT '0.0000',
  `over_payment_amount` decimal(20,4) DEFAULT '0.0000',
  `resp_employee` int(10) DEFAULT NULL COMMENT 'responsibility employee\n(check and balance amount)',
  `is_down_payment` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`index_no`),
  KEY `fk_t_payment_m_employee1_idx` (`resp_employee`),
  CONSTRAINT `fk_t_payment_m_employee1` FOREIGN KEY (`resp_employee`) REFERENCES `m_employee` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_payment`
--

LOCK TABLES `t_payment` WRITE;
/*!40000 ALTER TABLE `t_payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_payment_information`
--

DROP TABLE IF EXISTS `t_payment_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_payment_information` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `payment` int(10) NOT NULL,
  `number` varchar(50) DEFAULT NULL,
  `cheque_date` date DEFAULT NULL,
  `amount` decimal(20,4) NOT NULL,
  `type` varchar(25) NOT NULL COMMENT 'cash,card,cheque',
  `form_name` varchar(125) NOT NULL,
  `bank` int(10) DEFAULT NULL,
  `bank_branch` int(10) DEFAULT NULL,
  `card_type` int(10) DEFAULT NULL,
  `card_reader` int(10) DEFAULT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_t_card_detail_t_payment1_idx` (`payment`),
  KEY `fk_t_payment_information_m_bank1_idx` (`bank`),
  KEY `fk_t_payment_information_m_bank_branch1_idx` (`bank_branch`),
  KEY `fk_t_payment_information_m_card_type1_idx` (`card_type`),
  KEY `FK_t_payment_information_m_card_reader` (`card_reader`),
  CONSTRAINT `FK_t_payment_information_m_card_reader` FOREIGN KEY (`card_reader`) REFERENCES `m_card_reader` (`index_no`),
  CONSTRAINT `fk_t_card_detail_t_payment1` FOREIGN KEY (`payment`) REFERENCES `t_payment` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_payment_information_m_bank1` FOREIGN KEY (`bank`) REFERENCES `m_bank` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_payment_information_m_bank_branch1` FOREIGN KEY (`bank_branch`) REFERENCES `m_bank_branch` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_payment_information_m_card_type1` FOREIGN KEY (`card_type`) REFERENCES `m_card_type` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_payment_information`
--

LOCK TABLES `t_payment_information` WRITE;
/*!40000 ALTER TABLE `t_payment_information` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_payment_information` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_price_category_change_details`
--

DROP TABLE IF EXISTS `t_price_category_change_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_price_category_change_details` (
  `inde_no` int(11) NOT NULL AUTO_INCREMENT,
  `vehicle` int(11) NOT NULL DEFAULT '0',
  `old_price_category` int(11) NOT NULL DEFAULT '0',
  `new_price_category` int(11) NOT NULL DEFAULT '0',
  `reponceble_employee` int(11) NOT NULL DEFAULT '0',
  `job_card` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`inde_no`),
  KEY `FK__m_vehicle` (`vehicle`),
  KEY `FK_t_price_category_change_details_m_price_category` (`old_price_category`),
  KEY `FK_t_price_category_change_details_m_price_category_2` (`new_price_category`),
  KEY `FK_t_price_category_change_details_m_employee` (`reponceble_employee`),
  KEY `FK_t_price_category_change_details_t_job_card` (`job_card`),
  CONSTRAINT `FK__m_vehicle` FOREIGN KEY (`vehicle`) REFERENCES `m_vehicle` (`index_no`),
  CONSTRAINT `FK_t_price_category_change_details_m_employee` FOREIGN KEY (`reponceble_employee`) REFERENCES `m_employee` (`index_no`),
  CONSTRAINT `FK_t_price_category_change_details_m_price_category` FOREIGN KEY (`old_price_category`) REFERENCES `m_price_category` (`index_no`),
  CONSTRAINT `FK_t_price_category_change_details_m_price_category_2` FOREIGN KEY (`new_price_category`) REFERENCES `m_price_category` (`index_no`),
  CONSTRAINT `FK_t_price_category_change_details_t_job_card` FOREIGN KEY (`job_card`) REFERENCES `t_job_card` (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_price_category_change_details`
--

LOCK TABLES `t_price_category_change_details` WRITE;
/*!40000 ALTER TABLE `t_price_category_change_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_price_category_change_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_purchase_order`
--

DROP TABLE IF EXISTS `t_purchase_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_purchase_order` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `supplier` int(10) NOT NULL,
  `number` int(10) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `deliver_date` date DEFAULT NULL,
  `approved_date` date DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL COMMENT 'pending/approve\n',
  `is_view` tinyint(1) DEFAULT NULL,
  `form_name` varchar(50) DEFAULT NULL COMMENT 'purchase order form/grn return form\n',
  `item_value` decimal(10,4) DEFAULT NULL,
  `vat` decimal(10,4) DEFAULT NULL,
  `vat_value` decimal(10,4) DEFAULT NULL,
  `nbt` decimal(10,4) DEFAULT NULL,
  `nbt_value` decimal(10,4) DEFAULT NULL,
  `branch` int(10) NOT NULL,
  `grand_total` decimal(10,4) DEFAULT NULL,
  `return_status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_t_purchasse_order_m_supplier1_idx` (`supplier`),
  CONSTRAINT `fk_t_purchasse_order_m_supplier1` FOREIGN KEY (`supplier`) REFERENCES `m_supplier` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_purchase_order`
--

LOCK TABLES `t_purchase_order` WRITE;
/*!40000 ALTER TABLE `t_purchase_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_purchase_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_purchase_order_detail`
--

DROP TABLE IF EXISTS `t_purchase_order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_purchase_order_detail` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `purchase_order` int(10) NOT NULL,
  `item` int(10) NOT NULL,
  `price` decimal(10,4) DEFAULT NULL,
  `qty` decimal(10,4) DEFAULT NULL,
  `value` decimal(10,4) DEFAULT NULL,
  `discount` decimal(10,4) DEFAULT NULL,
  `discount_value` decimal(10,4) DEFAULT NULL,
  `net_value` decimal(10,4) DEFAULT NULL,
  `stock_qty` decimal(10,4) DEFAULT NULL,
  `order_qty` decimal(10,4) DEFAULT NULL,
  `receive_qty` decimal(10,4) DEFAULT NULL,
  `balance_qty` decimal(10,4) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_t_purchase_order_detail_t_purchasse_order1_idx` (`purchase_order`),
  KEY `fk_t_purchase_order_detail_m_item1_idx` (`item`),
  CONSTRAINT `fk_t_purchase_order_detail_m_item1` FOREIGN KEY (`item`) REFERENCES `m_item` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_purchase_order_detail_t_purchasse_order1` FOREIGN KEY (`purchase_order`) REFERENCES `t_purchase_order` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_purchase_order_detail`
--

LOCK TABLES `t_purchase_order_detail` WRITE;
/*!40000 ALTER TABLE `t_purchase_order_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_purchase_order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_stock_adjustment`
--

DROP TABLE IF EXISTS `t_stock_adjustment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_stock_adjustment` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `enter_date` date NOT NULL,
  `enter_time` time NOT NULL,
  `updated_date` date DEFAULT NULL,
  `updated_time` time DEFAULT NULL,
  `branch` int(11) NOT NULL,
  `check` tinyint(4) DEFAULT '0',
  `ref_no` varchar(50) DEFAULT '0',
  `form_type` varchar(150) DEFAULT '0',
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_stock_adjustment`
--

LOCK TABLES `t_stock_adjustment` WRITE;
/*!40000 ALTER TABLE `t_stock_adjustment` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_stock_adjustment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_stock_adjustment_detail`
--

DROP TABLE IF EXISTS `t_stock_adjustment_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_stock_adjustment_detail` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `stock_adjustment` int(11) NOT NULL DEFAULT '0',
  `item_no` varchar(50) NOT NULL,
  `item_name` varchar(250) NOT NULL,
  `item_unit` varchar(50) DEFAULT NULL,
  `barcode` varchar(150) DEFAULT NULL,
  `cost_price` decimal(20,4) DEFAULT '0.0000',
  `qty` decimal(10,4) NOT NULL DEFAULT '0.0000',
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_stock_adjustment_detail`
--

LOCK TABLES `t_stock_adjustment_detail` WRITE;
/*!40000 ALTER TABLE `t_stock_adjustment_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_stock_adjustment_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_stock_ledger`
--

DROP TABLE IF EXISTS `t_stock_ledger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_stock_ledger` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `item` int(10) NOT NULL,
  `store` int(11) NOT NULL,
  `date` date NOT NULL,
  `in_qty` decimal(10,4) DEFAULT NULL,
  `out_qty` decimal(10,4) DEFAULT NULL,
  `avarage_price_in` decimal(10,4) DEFAULT NULL,
  `avarage_price_out` decimal(10,4) DEFAULT NULL,
  `form_index_no` int(10) NOT NULL,
  `form` varchar(125) NOT NULL,
  `branch` int(10) NOT NULL,
  `type` varchar(50) DEFAULT NULL,
  `group_number` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`index_no`),
  KEY `fk_m_store_has_m_item_m_item1_idx` (`item`),
  KEY `fk_m_store_has_m_item_m_store1_idx` (`store`),
  CONSTRAINT `fk_m_store_has_m_item_m_item1` FOREIGN KEY (`item`) REFERENCES `m_item` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_m_store_has_m_item_m_store1` FOREIGN KEY (`store`) REFERENCES `m_store` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_stock_ledger`
--

LOCK TABLES `t_stock_ledger` WRITE;
/*!40000 ALTER TABLE `t_stock_ledger` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_stock_ledger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_stock_transfer`
--

DROP TABLE IF EXISTS `t_stock_transfer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_stock_transfer` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `in_number` int(10) DEFAULT NULL,
  `out_number` int(10) NOT NULL,
  `in_date` date DEFAULT NULL,
  `out_date` date DEFAULT NULL,
  `from_branch` int(10) NOT NULL,
  `from_store` int(10) NOT NULL,
  `to_branch` int(10) NOT NULL,
  `to_store` int(10) NOT NULL,
  `ref_number` varchar(25) DEFAULT NULL,
  `remarks` varchar(50) DEFAULT NULL,
  `type` varchar(25) NOT NULL COMMENT 'internal/external',
  `status` varchar(25) NOT NULL COMMENT 'approve/pending\n',
  PRIMARY KEY (`index_no`),
  KEY `fk_t_stock_transfer_m_branch1_idx` (`from_branch`),
  KEY `fk_t_stock_transfer_m_branch2_idx` (`to_branch`),
  KEY `fk_t_stock_transfer_m_store1_idx` (`from_store`),
  KEY `fk_t_stock_transfer_m_store2_idx` (`to_store`),
  CONSTRAINT `fk_t_stock_transfer_m_branch1` FOREIGN KEY (`from_branch`) REFERENCES `m_branch` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_stock_transfer_m_branch2` FOREIGN KEY (`to_branch`) REFERENCES `m_branch` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_stock_transfer_m_store1` FOREIGN KEY (`from_store`) REFERENCES `m_store` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_stock_transfer_m_store2` FOREIGN KEY (`to_store`) REFERENCES `m_store` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_stock_transfer`
--

LOCK TABLES `t_stock_transfer` WRITE;
/*!40000 ALTER TABLE `t_stock_transfer` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_stock_transfer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_stock_transfer_item`
--

DROP TABLE IF EXISTS `t_stock_transfer_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_stock_transfer_item` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `item` int(10) NOT NULL,
  `stock_transfer` int(10) NOT NULL,
  `qty` decimal(10,4) NOT NULL,
  `cost` decimal(10,4) NOT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_t_stock_transfer_m_item1_idx` (`item`),
  KEY `fk_t_stock_transfer_item_t_stock_transfer1_idx` (`stock_transfer`),
  CONSTRAINT `fk_t_stock_transfer_item_t_stock_transfer1` FOREIGN KEY (`stock_transfer`) REFERENCES `t_stock_transfer` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_stock_transfer_m_item1` FOREIGN KEY (`item`) REFERENCES `m_item` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_stock_transfer_item`
--

LOCK TABLES `t_stock_transfer_item` WRITE;
/*!40000 ALTER TABLE `t_stock_transfer_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_stock_transfer_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sub_item_check_result`
--

DROP TABLE IF EXISTS `t_sub_item_check_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sub_item_check_result` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `checked` tinyint(1) NOT NULL,
  `sub_item` int(10) NOT NULL,
  `reason` varchar(50) DEFAULT NULL,
  `comfirmation` tinyint(1) DEFAULT NULL,
  `time` varchar(25) DEFAULT NULL,
  `daily_check_list` int(10) NOT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_t_check_result_m_sub_item1_idx` (`sub_item`),
  KEY `fk_t_sub_item_check_result_t_daily_check_list1_idx` (`daily_check_list`),
  CONSTRAINT `fk_t_check_result_m_sub_item1` FOREIGN KEY (`sub_item`) REFERENCES `m_sub_item` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_sub_item_check_result_t_daily_check_list1` FOREIGN KEY (`daily_check_list`) REFERENCES `t_daily_check_list` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sub_item_check_result`
--

LOCK TABLES `t_sub_item_check_result` WRITE;
/*!40000 ALTER TABLE `t_sub_item_check_result` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sub_item_check_result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_supplier_ledger`
--

DROP TABLE IF EXISTS `t_supplier_ledger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_supplier_ledger` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `branch` int(10) NOT NULL,
  `date` date NOT NULL,
  `form_name` varchar(50) NOT NULL COMMENT 'grn form,return form,supplier payment form',
  `grn` int(10) DEFAULT NULL,
  `payment` int(10) DEFAULT NULL,
  `supplier_return` int(10) DEFAULT NULL,
  `supplier` int(10) NOT NULL,
  `credit_amount` decimal(20,4) DEFAULT NULL,
  `debit_amount` decimal(20,4) DEFAULT NULL,
  `ref_number` int(10) DEFAULT NULL COMMENT 'grn,return,payment table indexNo',
  `is_delete` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_t_supplier_ledger_m_supplier1_idx` (`supplier`),
  KEY `fk_t_supplier_ledger_t_grn1_idx` (`grn`),
  KEY `fk_t_supplier_ledger_t_payment1_idx` (`payment`),
  CONSTRAINT `fk_t_supplier_ledger_m_supplier1` FOREIGN KEY (`supplier`) REFERENCES `m_supplier` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_supplier_ledger_t_grn1` FOREIGN KEY (`grn`) REFERENCES `t_grn` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_supplier_ledger_t_payment1` FOREIGN KEY (`payment`) REFERENCES `t_payment` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_supplier_ledger`
--

LOCK TABLES `t_supplier_ledger` WRITE;
/*!40000 ALTER TABLE `t_supplier_ledger` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_supplier_ledger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_type_index_detail`
--

DROP TABLE IF EXISTS `t_type_index_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_type_index_detail` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `master_ref_id` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `account_ref_id` int(11) DEFAULT NULL,
  `account_index` int(11) DEFAULT NULL,
  PRIMARY KEY (`index_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_type_index_detail`
--

LOCK TABLES `t_type_index_detail` WRITE;
/*!40000 ALTER TABLE `t_type_index_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_type_index_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_permission_details`
--

DROP TABLE IF EXISTS `t_user_permission_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user_permission_details` (
  `index_no` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL,
  `form_name` int(11) NOT NULL,
  `view` tinyint(4) NOT NULL DEFAULT '0',
  `add` tinyint(4) NOT NULL DEFAULT '0',
  `update` tinyint(4) NOT NULL DEFAULT '0',
  `delete` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`index_no`),
  KEY `FK_t_user_permission_details_m_user` (`user`),
  KEY `FK_t_user_permission_details_m_form_name` (`form_name`),
  CONSTRAINT `FK_t_user_permission_details_m_form_name` FOREIGN KEY (`form_name`) REFERENCES `m_form_name` (`index_no`),
  CONSTRAINT `FK_t_user_permission_details_m_user` FOREIGN KEY (`user`) REFERENCES `m_user` (`index_no`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_permission_details`
--

LOCK TABLES `t_user_permission_details` WRITE;
/*!40000 ALTER TABLE `t_user_permission_details` DISABLE KEYS */;
INSERT INTO `t_user_permission_details` VALUES (1,1,1,1,1,1,1),(2,1,2,1,1,1,1),(3,1,3,1,1,1,1),(4,1,4,0,0,0,0),(5,1,5,0,0,0,0),(6,2,1,1,1,1,1),(7,2,2,1,1,1,1),(8,2,3,1,1,1,1),(9,2,4,1,1,1,1),(10,2,5,1,1,1,1),(11,1,6,1,1,1,1),(12,2,6,1,1,1,1),(13,1,7,1,1,1,1),(14,2,7,1,1,1,1),(15,1,8,1,1,1,1),(16,2,8,1,1,1,1),(19,1,10,1,1,1,1),(20,2,10,1,1,1,1),(21,2,7,1,1,1,1),(22,1,9,1,1,1,1),(23,2,9,1,1,1,1),(24,1,11,1,1,1,1),(25,2,11,1,1,1,1),(26,1,12,1,1,1,1),(27,2,12,1,1,1,1),(28,1,13,1,1,1,1),(29,2,13,1,1,1,1),(30,1,14,1,1,1,1),(31,2,14,1,1,1,1),(32,1,15,1,1,1,1),(33,2,15,1,1,1,1),(34,1,16,1,1,1,1),(35,2,16,1,1,1,1),(36,1,17,0,0,0,0),(37,2,17,1,1,1,1),(38,1,18,1,1,1,1),(39,2,18,1,0,0,0),(40,1,19,1,1,1,1),(41,2,19,1,1,0,0),(42,1,20,1,1,1,1),(43,2,20,1,1,1,1),(44,1,21,1,1,1,1),(45,2,21,1,1,1,1),(46,1,22,1,1,1,1),(47,2,22,1,1,1,1);
/*!40000 ALTER TABLE `t_user_permission_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_vehicle_assignment`
--

DROP TABLE IF EXISTS `t_vehicle_assignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_vehicle_assignment` (
  `index_no` int(10) NOT NULL AUTO_INCREMENT,
  `job_card` int(10) NOT NULL,
  `bay` int(10) NOT NULL,
  `in_time` datetime NOT NULL,
  `out_time` datetime DEFAULT NULL,
  `branch` int(10) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `time_stop` tinyint(4) DEFAULT '0',
  `time` time DEFAULT '00:00:00',
  `employee_count` int(11) DEFAULT '0',
  `percentage` int(11) DEFAULT NULL,
  PRIMARY KEY (`index_no`),
  KEY `fk_t_vehicle_assignment_t_job_card1_idx` (`job_card`),
  KEY `fk_t_vehicle_assignment_m_bay1_idx` (`bay`),
  CONSTRAINT `fk_t_vehicle_assignment_m_bay1` FOREIGN KEY (`bay`) REFERENCES `m_bay` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_vehicle_assignment_t_job_card1` FOREIGN KEY (`job_card`) REFERENCES `t_job_card` (`index_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_vehicle_assignment`
--

LOCK TABLES `t_vehicle_assignment` WRITE;
/*!40000 ALTER TABLE `t_vehicle_assignment` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_vehicle_assignment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-02 13:35:16
