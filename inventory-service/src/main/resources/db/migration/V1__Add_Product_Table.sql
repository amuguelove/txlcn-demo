
DROP TABLE IF EXISTS `tb_product`;

CREATE TABLE `tb_product` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL COMMENT '商品名称',
  `price` INT(11) NOT NULL COMMENT '商品金额',
  `stock` INT(11) NOT NULL COMMENT '商品剩余库存',
  `total_stock` INT(11) NOT NULL COMMENT '商品总库存',
  `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_name` (`name`)
)
