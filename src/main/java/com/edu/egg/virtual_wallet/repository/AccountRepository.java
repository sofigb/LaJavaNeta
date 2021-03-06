package com.edu.egg.virtual_wallet.repository;

import com.edu.egg.virtual_wallet.entity.Account;
import com.edu.egg.virtual_wallet.entity.Customer;
import java.util.List;

import com.edu.egg.virtual_wallet.enums.CurrencyType;
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

    @Modifying
    @Query("UPDATE Account a SET a.active = true WHERE a.id = :id")
    void active(@Param("id") Long id);
    
    @Query(value = "SELECT * FROM Account a WHERE a.active = true AND a.customer_id_customer=:id", nativeQuery = true)
    List<Account> findAllByIdCustomer(@Param("id") Integer id);

    @Query(value = "SELECT a FROM Account a WHERE a.active = true AND a.customer.id=:id AND a.currency=:currency")
    Account findAllByIdCustomer(@Param("id") Integer id,@Param("currency") CurrencyType currency);

    @Query("SELECT a FROM Account a WHERE a.active = true AND a.number = :number")
    Account findByAccountNumber(@Param("number") Long number);
}
