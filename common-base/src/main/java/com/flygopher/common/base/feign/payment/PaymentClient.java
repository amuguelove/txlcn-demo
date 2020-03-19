package com.flygopher.common.base.feign.payment;

import com.flygopher.common.base.feign.domain.AccountModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

public interface PaymentClient {

    @GetMapping(path = "/accounts/{username}")
    AccountModel getByUsername(@PathVariable("username") String username);

    @PutMapping(path = "/accounts/{username}/{amount}", consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean updateBalance(
            @PathVariable("username") String username, @PathVariable("amount") Integer amount);

    @PutMapping(path = "/accounts/{username}/{amount}/lcn", consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean updateBalanceByLcn(
            @PathVariable("username") String username, @PathVariable("amount") Integer amount);
}
