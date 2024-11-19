-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: studentu_sistema
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `idAdmin` int NOT NULL AUTO_INCREMENT,
  `Admin_name` varchar(45) NOT NULL,
  `Admin_surrname` varchar(45) NOT NULL,
  PRIMARY KEY (`idAdmin`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'Anton','Kebab'),(2,'Vlad','Rice');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dalykas`
--

DROP TABLE IF EXISTS `dalykas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dalykas` (
  `idDalykas` int NOT NULL AUTO_INCREMENT,
  `Dalyko_pavadinimas` varchar(45) NOT NULL,
  PRIMARY KEY (`idDalykas`),
  UNIQUE KEY `Dalyko_pavadinimas_UNIQUE` (`Dalyko_pavadinimas`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dalykas`
--

LOCK TABLES `dalykas` WRITE;
/*!40000 ALTER TABLE `dalykas` DISABLE KEYS */;
INSERT INTO `dalykas` VALUES (5,'Artificial intelligence'),(4,'English'),(1,'Math'),(2,'OOB'),(3,'Right');
/*!40000 ALTER TABLE `dalykas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dalyko_paskirstymas`
--

DROP TABLE IF EXISTS `dalyko_paskirstymas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dalyko_paskirstymas` (
  `dalykoID` int NOT NULL AUTO_INCREMENT,
  `Dalyko_pavadinimas` varchar(45) NOT NULL,
  `1` int DEFAULT NULL,
  `2` int DEFAULT NULL,
  `3` int DEFAULT NULL,
  `4` int DEFAULT NULL,
  `5` int DEFAULT NULL,
  PRIMARY KEY (`dalykoID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dalyko_paskirstymas`
--

LOCK TABLES `dalyko_paskirstymas` WRITE;
/*!40000 ALTER TABLE `dalyko_paskirstymas` DISABLE KEYS */;
INSERT INTO `dalyko_paskirstymas` VALUES (1,'Math',1,NULL,NULL,4,NULL),(2,'OOB',NULL,NULL,NULL,NULL,NULL),(3,'Right',NULL,NULL,NULL,NULL,NULL),(4,'English',1,NULL,NULL,NULL,NULL),(5,'Artificial intelligence',NULL,NULL,NULL,4,NULL);
/*!40000 ALTER TABLE `dalyko_paskirstymas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dalyko_vertinimas`
--

DROP TABLE IF EXISTS `dalyko_vertinimas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dalyko_vertinimas` (
  `DalykoID` int NOT NULL,
  `StudentoID` int NOT NULL,
  `Ivertinimas` int NOT NULL,
  `Ivertinimas2` int NOT NULL,
  PRIMARY KEY (`DalykoID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dalyko_vertinimas`
--

LOCK TABLES `dalyko_vertinimas` WRITE;
/*!40000 ALTER TABLE `dalyko_vertinimas` DISABLE KEYS */;
INSERT INTO `dalyko_vertinimas` VALUES (5,1,5,0);
/*!40000 ALTER TABLE `dalyko_vertinimas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `destytojai`
--

DROP TABLE IF EXISTS `destytojai`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `destytojai` (
  `destytojoID` int NOT NULL AUTO_INCREMENT,
  `destytojo_vardas` varchar(45) NOT NULL,
  `destytojo_pavarde` varchar(45) NOT NULL,
  PRIMARY KEY (`destytojoID`),
  UNIQUE KEY `destytojoID_UNIQUE` (`destytojoID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `destytojai`
--

LOCK TABLES `destytojai` WRITE;
/*!40000 ALTER TABLE `destytojai` DISABLE KEYS */;
INSERT INTO `destytojai` VALUES (1,'Thomas','Jefferson'),(2,'Bill','Clinton'),(3,'Jimmy','Carter'),(4,'Richard','Nixon'),(5,'Woodrow','Wilson');
/*!40000 ALTER TABLE `destytojai` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dienynas`
--

DROP TABLE IF EXISTS `dienynas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dienynas` (
  `DalykoID` int NOT NULL,
  `Dalyko_pavadinimas` varchar(45) NOT NULL,
  `StudentoID` int NOT NULL,
  `studento_vardas` varchar(45) NOT NULL,
  `studento_pavarde` varchar(45) NOT NULL,
  `grupe` varchar(45) NOT NULL,
  `Ivertinimas` int DEFAULT NULL,
  `Ivertinimas2` int DEFAULT NULL,
  `Ivertinimas3` int DEFAULT NULL,
  `Ivertinimas4` int DEFAULT NULL,
  `Ivertinimas5` int DEFAULT NULL,
  `DienynoID` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`DienynoID`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dienynas`
--

LOCK TABLES `dienynas` WRITE;
/*!40000 ALTER TABLE `dienynas` DISABLE KEYS */;
INSERT INTO `dienynas` VALUES (5,'Artificial intelligence',1,'Ethan ','Walker','Grupe A',5,5,5,6,NULL,1),(5,'Artificial intelligence',3,'Mason ','Carter','Grupe A',NULL,NULL,NULL,NULL,NULL,2),(5,'Artificial intelligence',5,'Noah ','Bennett','Grupe A',NULL,NULL,NULL,NULL,NULL,3),(5,'Artificial intelligence',7,'Lucas ','Reed','Grupe A',NULL,NULL,NULL,NULL,NULL,4),(5,'Artificial intelligence',9,'Oliver ','Grant','Grupe A',NULL,NULL,NULL,NULL,NULL,5),(5,'Artificial intelligence',2,'Lily ','Thompson','Grupe B',NULL,NULL,NULL,NULL,NULL,6),(5,'Artificial intelligence',4,'Sophia ','Brooks','Grupe B',NULL,NULL,NULL,NULL,NULL,7),(5,'Artificial intelligence',6,'Ava','Mitchell','Grupe B',NULL,NULL,NULL,NULL,NULL,8),(5,'Artificial intelligence',8,'Emma ','Hayes','Grupe B',NULL,NULL,NULL,NULL,NULL,9),(5,'Artificial intelligence',10,'Grace ','Sullivan','Grupe B',NULL,NULL,NULL,NULL,NULL,10),(4,'English',1,'Ethan ','Walker','Grupe A',NULL,NULL,NULL,NULL,NULL,11),(4,'English',3,'Mason ','Carter','Grupe A',NULL,NULL,NULL,NULL,NULL,12),(4,'English',5,'Noah ','Bennett','Grupe A',NULL,NULL,NULL,NULL,NULL,13),(4,'English',7,'Lucas ','Reed','Grupe A',NULL,NULL,NULL,NULL,NULL,14),(4,'English',9,'Oliver ','Grant','Grupe A',NULL,NULL,NULL,NULL,NULL,15),(4,'English',2,'Lily ','Thompson','Grupe B',NULL,NULL,NULL,NULL,NULL,16),(4,'English',4,'Sophia ','Brooks','Grupe B',NULL,NULL,NULL,NULL,NULL,17),(4,'English',6,'Ava','Mitchell','Grupe B',NULL,NULL,NULL,NULL,NULL,18),(4,'English',8,'Emma ','Hayes','Grupe B',NULL,NULL,NULL,NULL,NULL,19),(4,'English',10,'Grace ','Sullivan','Grupe B',NULL,NULL,NULL,NULL,NULL,20),(1,'Math',1,'Ethan ','Walker','Grupe A',NULL,NULL,NULL,NULL,NULL,21),(1,'Math',3,'Mason ','Carter','Grupe A',NULL,NULL,NULL,5,NULL,22),(1,'Math',5,'Noah ','Bennett','Grupe A',NULL,NULL,NULL,NULL,NULL,23),(1,'Math',7,'Lucas ','Reed','Grupe A',NULL,NULL,NULL,NULL,NULL,24),(1,'Math',9,'Oliver ','Grant','Grupe A',NULL,NULL,NULL,NULL,NULL,25),(1,'Math',2,'Lily ','Thompson','Grupe B',NULL,NULL,NULL,NULL,NULL,26),(1,'Math',4,'Sophia ','Brooks','Grupe B',NULL,NULL,NULL,NULL,NULL,27),(1,'Math',6,'Ava','Mitchell','Grupe B',NULL,NULL,NULL,NULL,NULL,28),(1,'Math',8,'Emma ','Hayes','Grupe B',NULL,NULL,NULL,NULL,NULL,29),(1,'Math',10,'Grace ','Sullivan','Grupe B',NULL,NULL,NULL,NULL,NULL,30),(2,'OOB',1,'Ethan ','Walker','Grupe A',NULL,NULL,NULL,NULL,NULL,31),(2,'OOB',3,'Mason ','Carter','Grupe A',NULL,NULL,NULL,NULL,NULL,32),(2,'OOB',5,'Noah ','Bennett','Grupe A',NULL,NULL,NULL,NULL,NULL,33),(2,'OOB',7,'Lucas ','Reed','Grupe A',NULL,NULL,NULL,NULL,NULL,34),(2,'OOB',9,'Oliver ','Grant','Grupe A',NULL,NULL,NULL,NULL,NULL,35),(2,'OOB',2,'Lily ','Thompson','Grupe B',NULL,NULL,NULL,NULL,NULL,36),(2,'OOB',4,'Sophia ','Brooks','Grupe B',NULL,NULL,NULL,NULL,NULL,37),(2,'OOB',6,'Ava','Mitchell','Grupe B',NULL,NULL,NULL,NULL,NULL,38),(2,'OOB',8,'Emma ','Hayes','Grupe B',NULL,NULL,NULL,NULL,NULL,39),(2,'OOB',10,'Grace ','Sullivan','Grupe B',NULL,NULL,NULL,NULL,NULL,40),(3,'Right',1,'Ethan ','Walker','Grupe A',NULL,NULL,NULL,NULL,NULL,41),(3,'Right',3,'Mason ','Carter','Grupe A',NULL,NULL,NULL,NULL,NULL,42),(3,'Right',5,'Noah ','Bennett','Grupe A',5,NULL,NULL,NULL,NULL,43),(3,'Right',7,'Lucas ','Reed','Grupe A',NULL,NULL,NULL,NULL,NULL,44),(3,'Right',9,'Oliver ','Grant','Grupe A',NULL,NULL,NULL,NULL,NULL,45),(3,'Right',2,'Lily ','Thompson','Grupe B',NULL,NULL,NULL,NULL,NULL,46),(3,'Right',4,'Sophia ','Brooks','Grupe B',NULL,NULL,NULL,NULL,NULL,47),(3,'Right',6,'Ava','Mitchell','Grupe B',NULL,NULL,NULL,NULL,NULL,48),(3,'Right',8,'Emma ','Hayes','Grupe B',NULL,NULL,NULL,NULL,NULL,49),(3,'Right',10,'Grace ','Sullivan','Grupe B',NULL,NULL,NULL,NULL,NULL,50);
/*!40000 ALTER TABLE `dienynas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupės`
--

DROP TABLE IF EXISTS `grupės`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grupės` (
  `idGrupės` int NOT NULL AUTO_INCREMENT,
  `Grupės_Pavadinimas` varchar(45) NOT NULL,
  PRIMARY KEY (`idGrupės`,`Grupės_Pavadinimas`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupės`
--

LOCK TABLES `grupės` WRITE;
/*!40000 ALTER TABLE `grupės` DISABLE KEYS */;
INSERT INTO `grupės` VALUES (1,'A'),(2,'B');
/*!40000 ALTER TABLE `grupės` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `permissionID` int NOT NULL,
  `permission_name` varchar(45) NOT NULL,
  PRIMARY KEY (`permissionID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'Manage Users'),(2,'Manage Subjects'),(3,'Assign Marks'),(4,'View Marks'),(5,'View Subjects');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permissions`
--

DROP TABLE IF EXISTS `role_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_permissions` (
  `role1ID` int NOT NULL,
  `permissionID` int NOT NULL,
  KEY `roleID_idx` (`role1ID`),
  KEY `permissionID_idx` (`permissionID`),
  CONSTRAINT `permissionID` FOREIGN KEY (`permissionID`) REFERENCES `permission` (`permissionID`),
  CONSTRAINT `role1ID` FOREIGN KEY (`role1ID`) REFERENCES `roles` (`roleID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permissions`
--

LOCK TABLES `role_permissions` WRITE;
/*!40000 ALTER TABLE `role_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `roleID` int NOT NULL,
  `role_name` varchar(45) NOT NULL,
  PRIMARY KEY (`roleID`),
  UNIQUE KEY `roleID_UNIQUE` (`roleID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Admin'),(2,'Teacher'),(3,'Students');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentai`
--

DROP TABLE IF EXISTS `studentai`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studentai` (
  `idStudentai` int NOT NULL AUTO_INCREMENT,
  `Stud_Vardas` varchar(45) NOT NULL,
  `Stud_Pavardė` varchar(45) NOT NULL,
  PRIMARY KEY (`idStudentai`),
  UNIQUE KEY `idStudentai_UNIQUE` (`idStudentai`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentai`
--

LOCK TABLES `studentai` WRITE;
/*!40000 ALTER TABLE `studentai` DISABLE KEYS */;
INSERT INTO `studentai` VALUES (1,'Ethan ','Walker'),(2,'Lily ','Thompson'),(3,'Mason ','Carter'),(4,'Sophia ','Brooks'),(5,'Noah ','Bennett'),(6,'Ava','Mitchell'),(7,'Lucas ','Reed'),(8,'Emma ','Hayes'),(9,'Oliver ','Grant'),(10,'Grace ','Sullivan'),(12,'As','Es');
/*!40000 ALTER TABLE `studentai` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studento_paskirstymas`
--

DROP TABLE IF EXISTS `studento_paskirstymas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studento_paskirstymas` (
  `grupesID` int NOT NULL,
  `studentoID` int NOT NULL,
  PRIMARY KEY (`grupesID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studento_paskirstymas`
--

LOCK TABLES `studento_paskirstymas` WRITE;
/*!40000 ALTER TABLE `studento_paskirstymas` DISABLE KEYS */;
/*!40000 ALTER TABLE `studento_paskirstymas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `userID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `roleID` int NOT NULL,
  PRIMARY KEY (`userID`),
  KEY `roleID_idx` (`roleID`),
  CONSTRAINT `roleID` FOREIGN KEY (`roleID`) REFERENCES `roles` (`roleID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-18 14:04:59
