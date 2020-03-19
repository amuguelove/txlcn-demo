
DROP TABLE IF EXISTS `tb_order`;

CREATE TABLE `tb_order` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL COMMENT '下单用户账号',
    `product_name` VARCHAR(50) NOT NULL COMMENT '商品名称',
    `product_unit`  INT(11) NOT NULL COMMENT '商品数量',
    `amount` INT(11) NOT NULL COMMENT '下单金额',
    `status` VARCHAR(20) NOT NULL COMMENT '订单状态',
    `reason` VARCHAR(100) NULL COMMENT '原因',
    `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
);