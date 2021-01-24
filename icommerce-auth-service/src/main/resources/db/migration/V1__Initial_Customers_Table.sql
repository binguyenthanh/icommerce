CREATE TABLE IF NOT EXISTS `customers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `token` longtext,
  INDEX `email_idx` (`email` ASC),
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UK` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
