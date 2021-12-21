package com.edu.egg.virtual_wallet.repository;

import com.edu.egg.virtual_wallet.entity.Customer;
import com.edu.egg.virtual_wallet.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Query("SELECT c FROM Customers c WHERE c.dni = :dni")
    Customer findCustomerByDni(@Param("dni") Long id);

    @Query("SELECT c.id FROM Customers c WHERE c.loginInfo.username = :username")
    Optional<Integer> findCustomerIdByUsername(@Param("username") String username);

    @Query("SELECT c.id FROM Customers c WHERE c.contactInfo.email = :email")
    Optional<Integer> findCustomerIdByEmail(@Param("email") String email);

    @Query("SELECT c.id FROM Customers c WHERE c.dni = :dni")
    Optional<Integer> findCustomerIdByDni(@Param("dni") Long dni);

    boolean existsCustomerByDni(Long dni);

    List<Customer> findAllByOrderByActivationDateDesc();
}