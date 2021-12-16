package com.edu.egg.virtual_wallet.repository;

import com.edu.egg.virtual_wallet.entity.Customer;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    @Query("SELECT c.id FROM Customers c WHERE c.loginInfo.id = :loginId")
    Optional<Integer> findCustomerIdByLoginId(@Param("loginId") Integer id);

    @Query("SELECT c.contactInfo.id FROM Customers c WHERE c.id = :id")
    Integer findContactIdByCustomerId(@Param("id") Integer id);

    @Query("SELECT c.fullName.id FROM Customers c WHERE c.id = :id")
    Integer findNameIdByCustomerId(@Param("id") Integer id);

    @Query("SELECT c.loginInfo.id FROM Customers c WHERE c.id = :id")
    Integer findLoginIdByCustomerId(@Param("id") Integer id);

    @Query("SELECT c.addressInfo.id FROM Customers c WHERE c.id = :id")
    Integer findAddressIdByCustomerId(@Param("id") Integer id);

    @Modifying
    @Query("UPDATE Customers c SET c.dni = :dni, c.dateOfBirth = :dateOfBirth WHERE c.id = :id")
    void updateCustomer(@Param("dni") Long dni, @Param("dateOfBirth")LocalDate dateOfBirth, @Param("id") Integer id);
}