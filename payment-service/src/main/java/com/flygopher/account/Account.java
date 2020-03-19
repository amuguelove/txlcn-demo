package com.flygopher.account;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "tb_account")
public class Account extends BaseEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "balance")
    private Integer balance;

    @Column(name = "credit")
    private Integer credit;
}
