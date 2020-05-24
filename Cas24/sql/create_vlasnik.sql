CREATE TABLE `vlasnik` (
  `id` int NOT NULL AUTO_INCREMENT,
  `brojVozackeDozvole` varchar(45) NOT NULL,
  `ime` varchar(45) DEFAULT NULL,
  `prezime` varchar(45) DEFAULT NULL,
  `starost` varchar(45) NOT NULL DEFAULT '18',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idvlasnik_UNIQUE` (`id`),
  UNIQUE KEY `brojVozackeDozvole_UNIQUE` (`brojVozackeDozvole`)
) ENGINE=InnoDB AUTO_INCREMENT=1520 DEFAULT CHARSET=utf8;
