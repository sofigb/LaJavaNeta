package com.edu.egg.virtual_wallet.repository;

import com.edu.egg.virtual_wallet.entity.Payee;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PayeeRepository extends JpaRepository<Payee, Integer> {

    @Query(value = "SELECT * FROM Payee WHERE payee.active = active", nativeQuery = true)
    List<Payee> findAllByActive(@Param("active") Boolean active);

    @Modifying
    @Query("UPDATE Payee p SET p.active = true WHERE p.id = :id")
    void active(@Param("id") Integer id);

}
