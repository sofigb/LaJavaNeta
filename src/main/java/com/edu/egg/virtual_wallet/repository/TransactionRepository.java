
package com.edu.egg.virtual_wallet.repository;

import com.edu.egg.virtual_wallet.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>  {
    
}
