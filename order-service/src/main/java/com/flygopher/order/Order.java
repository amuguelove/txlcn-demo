package com.flygopher.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "tb_order")
public class Order extends BaseEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_unit")
    private Integer productUnit;

    @Column(name = "amount")
    private Integer amount;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "reason")
    private String reason;
}
