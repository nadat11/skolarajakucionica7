CREATE TABLE `vozilo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `registarskiBroj` varchar(45) NOT NULL,
  `godisteProizvodnje` int NOT NULL,
  `status` tinyint NOT NULL DEFAULT '0',
  `vlasnikId` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `registarskiBroj_UNIQUE` (`registarskiBroj`)
) ENGINE=InnoDB AUTO_INCREMENT=1612 DEFAULT CHARSET=utf8;
