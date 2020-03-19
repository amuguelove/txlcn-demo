package com.flygopher.common.base.feign.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountModel {

    private Long id;

    private String username;

    private Integer balance;

    private Integer credit;

}
