package com.edu.egg.virtual_wallet.repository;

import com.edu.egg.virtual_wallet.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Modifying
    @Query("UPDATE Account a SET a.active = true WHERE a.number = :accountNumber")
    void enableByAccountNumber(@Param("accountNumber") Long accountNumber);

    boolean existsByNumber(Long number);

    boolean existsByCvu(String cvu);

    boolean existsByAlias(String alias);
}
