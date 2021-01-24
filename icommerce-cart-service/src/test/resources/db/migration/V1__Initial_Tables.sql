CREATE TABLE IF NOT EXISTS `carts` (
  `id` bigint(20) PRIMARY KEY AUTO_INCREMENT,
  `customer_id` bigint(20) NOT NULL,
  `status` INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS `cart_items` (
  `id` bigint(20) PRIMARY KEY AUTO_INCREMENT,
  `cart_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `quantity` bigint(20) NOT NULL,
  CONSTRAINT `fk_cart_items_carts`
    FOREIGN KEY (`cart_id`)
    REFERENCES `carts` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS `orders` (
  `id` bigint(20) PRIMARY KEY AUTO_INCREMENT,
  `cart_id` bigint(20) NOT NULL,
  `customer_id` bigint(20) NOT NULL,
  `status` INT DEFAULT 0,
  `payment_method` INT DEFAULT 0,
  `total_amount` DECIMAL(10,2) NULL,
  `received_amount` DECIMAL(10,2) NULL,
  `change_amount` DECIMAL(10,2) NULL,
  CONSTRAINT `fk_orders_carts`
    FOREIGN KEY (`cart_id`)
    REFERENCES `carts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);
