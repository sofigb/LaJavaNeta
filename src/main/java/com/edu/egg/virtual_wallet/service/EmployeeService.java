package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Employee;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class EmployeeService {


    // QUESTION FOR TEAM:
    // Should employees create/delete employees? That should be the role of a Super Admin
    // Employees should only be able to edit their own profiles and customer accounts
    // We could add a Super Amin later, but first, let's focus!

    @Autowired
    private EmployeeRepo employeeRepository;

    @Autowired

    private AppUserService userService;


    /*
    @Autowired
    private CustomerService customerService; Will allow employee to edit/create customers
    */

    /*
    @Override
    @Transactional
    public void createEmployee(Employee newEmployee) throws VirtualWalletException {
        try {
            userService.createUser(newEmployee.getUser());
            employeeRepository.save(newEmployee);
        } catch (Exception e) {
            throw new VirtualWalletException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deactivateEmployee(Integer idEmployee) throws VirtualWalletException {
        if (employeeRepository.findById(idEmployee).isPresent()) {
            try {
                userService.deactivateUser(employeeRepository.findById(idEmployee).get().getUser()); // ?
                employeeRepository.deleteById(idEmployee);
            } catch (Exception e) {
                throw new VirtualWalletException("Unable to delete Employee");
            }
        } else {
            throw new VirtualWalletException("Unable to find Employee");
        }
    }
    */

    @Transactional
    public void editEmployee(Employee updatedEmployee) throws VirtualWalletException {
        if (employeeRepository.findById(updatedEmployee.getId()).isPresent()) {
            try {
                userService.editUser(updatedEmployee.getUser());
                employeeRepository.save(updatedEmployee);
            } catch (Exception e) {
                throw new VirtualWalletException(e.getMessage());
            }
        } else {
            throw new VirtualWalletException("Unable to find Employee");
        }
    }


    @Transactional
    public Employee returnEmployee(Integer idEmployee) throws VirtualWalletException {
        if (employeeRepository.findById(idEmployee).isPresent()) {
            try {
                Employee employee = employeeRepository.findById(idEmployee).get();
                employee.setUser(userService.returnUser(employee.getUser().getId()));
                return employee;
            } catch (Exception e) {
                throw new VirtualWalletException(e.getMessage());
            }
        } else {
            throw new VirtualWalletException("Unable to retrieve employee data");
        }
    }
}
