package com.flygopher.lcn;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import com.flygopher.account.Account;
import com.flygopher.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.flygopher.common.base.exception.NotFoundException.notFoundException;

@Service
@EnableDistributedTransaction
public class LcnAccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public LcnAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getByUsername(String username) {
        return accountRepository
                .findByUsername(username)
                .orElseThrow(notFoundException(String.format("Account[%s] not found", username)));
    }

    @Transactional
    @LcnTransaction
    public boolean updateBalance(String username, Integer amount) {
        Account account = this.getByUsername(username);
        return accountRepository.updateBalance(account.getId(), amount) > 0;
    }
}
