package com.flygopher.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository
        extends JpaRepository<Account, Long>, QuerydslPredicateExecutor<Account> {

    Optional<Account> findByUsername(String username);

    @Modifying
    @Query(
            value =
                    "update tb_account set balance = balance - :amount where id = :accountId and balance >= :amount",
            nativeQuery = true)
    int updateBalance(@Param("accountId") Long accountId, @Param("amount") Integer amount);

    @Modifying
    @Query(
            value =
                    "update tb_account set balance = balance + :amount where id = :accountId",
            nativeQuery = true)
    int cancelUpdateBalance(@Param("accountId") Long accountId, @Param("amount") Integer amount);
}
