package com.edu.egg.virtual_wallet.repository;

import com.edu.egg.virtual_wallet.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    @Query("SELECT c.id FROM Customers c WHERE c.user.loginDetails.id = :loginId")
    Integer findUserIdByLoginId(@Param("loginId") Integer id);
}