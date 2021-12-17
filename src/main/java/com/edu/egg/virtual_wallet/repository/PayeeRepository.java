package com.edu.egg.virtual_wallet.repository;

import com.edu.egg.virtual_wallet.entity.Payee;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PayeeRepository extends JpaRepository<Payee, Integer> {

    @Query(value = "SELECT * FROM Payee WHERE payee.active =:active", nativeQuery = true)
    List<Payee> findAllByActive(@Param("active") Boolean active);

    @Modifying
    @Query("UPDATE Payee p SET p.active = true WHERE p.id = :id")
    void active(@Param("id") Integer id);
    
    @Query(value = "SELECT * FROM Payee p WHERE p.active = true AND p.fk_customer=:id", nativeQuery = true)
    List<Payee> findAllByIdCustomer(@Param("id") Integer id);

    @Query(value = "SELECT p.fk_customer FROM Payee p WHERE p.id =:id", nativeQuery = true)
    Integer idCustomer(@Param("id") Integer id);
}
