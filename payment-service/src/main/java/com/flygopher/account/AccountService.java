package com.flygopher.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.flygopher.common.base.exception.NotFoundException.notFoundException;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getByUsername(String username) {
        return accountRepository
                .findByUsername(username)
                .orElseThrow(notFoundException(String.format("Account[%s] not found", username)));
    }

    @Transactional
    public boolean updateBalance(String username, Integer amount) {
        Account account = this.getByUsername(username);
        return accountRepository.updateBalance(account.getId(), amount) > 0;
    }
}
