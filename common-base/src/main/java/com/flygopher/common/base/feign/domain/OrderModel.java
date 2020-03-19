package com.flygopher.common.base.feign.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderModel {

    private Long id;

    private String username;

    private String productName;

    private Integer productUnit;

    private Integer amount;

    private String orderStatus;

    private String reason;
}
