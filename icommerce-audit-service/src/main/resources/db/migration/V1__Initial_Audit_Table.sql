CREATE TABLE IF NOT EXISTS `audits` (
  `id` bigint(20) PRIMARY KEY AUTO_INCREMENT,
  `customer_id` bigint(20) NOT NULL,
  `action` varchar(255) NOT NULL,
  `action_value` longtext,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;