package com.flygopher.account;

import com.flygopher.common.base.feign.domain.AccountModel;
import com.flygopher.common.base.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{username}")
    public AccountModel getByUsername(@PathVariable("username") String username) {
        return Mapper.map(accountService.getByUsername(username), AccountModel.class);
    }

    @PutMapping("/{username}/{amount}")
    public boolean updateBalance(@PathVariable("username") String username, @PathVariable("amount") Integer amount) {
        return accountService.updateBalance(username, amount);
    }

    @PutMapping("/{username}/{amount}/lcn")
    public boolean updateBalanceByLcn(@PathVariable("username") String username, @PathVariable("amount") Integer amount) {
        return accountService.updateBalance(username, amount);
    }
}
