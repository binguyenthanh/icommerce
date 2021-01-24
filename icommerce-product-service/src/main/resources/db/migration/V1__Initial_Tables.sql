CREATE TABLE IF NOT EXISTS `products` (
  `id` bigint(20) PRIMARY KEY AUTO_INCREMENT,
  `name`VARCHAR(255),
  `brand`VARCHAR(255),
  `color`VARCHAR(255),
  `price` DECIMAL(10,2) NOT NULL
);


CREATE TABLE IF NOT EXISTS `price_histories` (
  `id` bigint(20) PRIMARY KEY AUTO_INCREMENT,
  `product_id` bigint(20) NOT NULL,
  `name`VARCHAR(255),
  `brand`VARCHAR(255),
  `color`VARCHAR(255),
  `price` DECIMAL(10,2) NOT NULL
);
