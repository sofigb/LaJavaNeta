package com.edu.egg.virtual_wallet.service;


import com.edu.egg.virtual_wallet.entity.Employee;
import com.edu.egg.virtual_wallet.exception.InputException;

import com.edu.egg.virtual_wallet.entity.*;

import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.EmployeeRepo;
import com.edu.egg.virtual_wallet.utility.PasswordPolicyEnforcer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

    private final String employee="el empleado ";
    // QUESTION FOR TEAM:
    // Should employees create/delete employees? That should be the role of a Super Admin
    // Employees should only be able to edit their own profiles and customer accounts
    // We could add a Super Amin later, but first, let's focus!
    @Autowired
    private EmployeeRepo employeeRepository;

    @Autowired
    private NameService nameService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ContactService contactService;


    /*
    @Autowired
    private CustomerService customerService; Will allow employee to edit/create customers
    */

    /*
    @Transactional

    public void createEmployee(Employee newEmployee) throws InputException {
=======
    public void createEmployee(Employee newEmployee, Contact contact,Name name,
                               Login login) throws VirtualWalletException {

        try {
            newEmployee.setContactInfo(contactService.createContact(contact));
            newEmployee.setFullName(nameService.createName(name));
            newEmployee.setLoginInfo(loginService.createLogin(login, "EMPLOYEE"));;
            employeeRepository.save(newEmployee);
        } catch (Exception e) {
            throw InputException.NotCreated(employee);
        }
    }

    @Transactional
    public void deactivateEmployee(Integer idEmployee) throws InputException {
        if (employeeRepository.findById(idEmployee).isPresent()) {
            try {
                Employee employee = employeeRepository.findById(idEmployee).get();
                nameService.deactivateName(employee.getFullName().getId());
                loginService.deactivateLogin(employee.getLoginInfo().getId());
                contactService.deactivateContact(employee.getContactInfo().getId());
                employeeRepository.deleteById(idEmployee);
            } catch (Exception e) {
                throw InputException.NotDeleted(employee);
            }
        } else {
            throw InputException.NotFound(employee);
        }
    }
*/
    @Transactional

    public void editEmployee(Employee updatedEmployee, Contact contact, Name name,
                             Login login) throws InputException {

        if (employeeRepository.findById(updatedEmployee.getId()).isPresent()) {
            try {
               // nameService.editName(name);
               // contactService.editContact(contact);
               // loginService.editLogin(login);
                employeeRepository.save(updatedEmployee);
            } catch (Exception e) {
                throw InputException.NotEdited(employee);
            }
        } else {
                throw InputException.NotFound(employee);
        }
    }

    @Transactional
    public Employee returnEmployee(Integer idEmployee) throws InputException {
        if (employeeRepository.findById(idEmployee).isPresent()) {
            try {
                Employee employee = employeeRepository.findById(idEmployee).get();
                employee.setLoginInfo(employee.getLoginInfo());
                employee.setContactInfo(employee.getContactInfo());
                employee.setFullName(employee.getFullName());
                return employee;
            } catch (Exception e) {
                throw InputException.NotReturned(employee);
            }
        } else {
            throw InputException.NotRetrievedData(employee);
        }
    }
}
