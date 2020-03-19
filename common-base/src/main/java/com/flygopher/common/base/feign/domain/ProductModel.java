package com.flygopher.common.base.feign.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductModel {

    private Long id;

    private String name;

    private Integer price;

    private Integer stock;
}
