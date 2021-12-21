package com.edu.egg.virtual_wallet.repository;

import com.edu.egg.virtual_wallet.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface EmployeeRepo  extends JpaRepository<Employee, Integer> {

    @Query("SELECT e.id FROM Employees e WHERE e.loginInfo.id = :loginId")
    Optional<Integer> findEmployeeIdByLoginId(@Param("loginId") Integer id);

    @Query("SELECT e.contactInfo.id FROM Employees e WHERE e.id = :id")
    Integer findContactIdByEmployeeId(@Param("id") Integer id);

    @Query("SELECT e.fullName.id FROM Employees e WHERE e.id = :id")
    Integer findNameIdByEmployeeId(@Param("id") Integer id);

    @Query("SELECT e.loginInfo.id FROM Employees e WHERE e.id = :id")
    Integer findLoginIdByEmployeeId(@Param("id") Integer id);

    @Query("SELECT e.id FROM Employees e WHERE e.loginInfo.username = :username")
    Optional<Integer> findEmployeeIdByUsername(@Param("username") String username);

    @Query("SELECT e.id FROM Employees e WHERE e.contactInfo.email = :email")
    Optional<Integer> findEmployeeIdByEmail(@Param("email") String email);

    List<Employee> findAllByOrderByActivationDateDesc();
}