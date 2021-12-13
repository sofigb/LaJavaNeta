
package com.edu.egg.virtual_wallet.repository;

import com.edu.egg.virtual_wallet.entity.Account;
import com.edu.egg.virtual_wallet.entity.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>  {
    
    @Query(value = "SELECT * FROM Transaction t WHERE  t.sender_account_id=:id", nativeQuery = true)
    List<Transaction> findAllByIdAccount(@Param("id") Long id);
}
