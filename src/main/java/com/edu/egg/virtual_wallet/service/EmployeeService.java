package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Employee;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;

public interface EmployeeService {

    // void createEmployee(Employee newEmployee) throws VirtualWalletException;
    // void deactivateEmployee(Integer idEmployee) throws VirtualWalletException;
    void editEmployee(Employee updatedEmployee) throws VirtualWalletException;
    Employee returnEmployee(Integer idEmployee) throws VirtualWalletException;
}