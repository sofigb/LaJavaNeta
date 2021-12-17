package com.edu.egg.virtual_wallet.service;


import com.edu.egg.virtual_wallet.entity.Employee;
import com.edu.egg.virtual_wallet.exception.InputException;

import com.edu.egg.virtual_wallet.entity.*;

import com.edu.egg.virtual_wallet.repository.EmployeeRepo;
import com.edu.egg.virtual_wallet.utility.PasswordPolicyEnforcer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

    private final String employee = "el empleado ";

    @Autowired
    private EmployeeRepo employeeRepository;

    @Autowired
    private NameService nameService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ContactService contactService;

    @Transactional
    public void createEmployee(Employee newEmployee, Contact contact,Name name,
                               Login login) throws InputException {
        try {
            newEmployee.setContactInfo(contactService.createContact(contact));
            newEmployee.setFullName(nameService.createName(name));
            newEmployee.setLoginInfo(loginService.createLogin(login, "EMPLOYEE"));
            newEmployee.setActive(true);
            employeeRepository.save(newEmployee);

            // ADD EMAIL SENDER METHOD
        } catch (Exception e) {
            throw InputException.NotCreated(employee);
        }
    }

    @Transactional
    public void deactivateEmployee(Integer idEmployee) throws InputException {
        if (employeeRepository.findById(idEmployee).isPresent()) {
            try {
                Employee employee = employeeRepository.findById(idEmployee).get();

                nameService.deactivateName(employeeRepository.findNameIdByEmployeeId(idEmployee));
                loginService.deactivateLogin(employeeRepository.findLoginIdByEmployeeId(idEmployee));
                contactService.deactivateContact(employeeRepository.findContactIdByEmployeeId(idEmployee));

                employeeRepository.deleteById(idEmployee);
            } catch (Exception e) {
                throw InputException.NotDeleted(employee);
            }
        } else {
            throw InputException.NotFound(employee);
        }
    }

    @Transactional
    public void editEmployee(Employee updatedEmployee, Integer idEmployee, Contact contact,
                             Name name, String username) throws InputException {

        if (employeeRepository.findById(updatedEmployee.getId()).isPresent()) {
            try {
                nameService.editName(name, employeeRepository.findNameIdByEmployeeId(idEmployee));
                contactService.editContact(contact, employeeRepository.findContactIdByEmployeeId(idEmployee));
                loginService.editUsername(username, employeeRepository.findLoginIdByEmployeeId(idEmployee));

                updatedEmployee.setId(idEmployee);
                employeeRepository.save(updatedEmployee);
            } catch (Exception e) {
                throw InputException.NotEdited(employee);
            }
        } else {
            throw InputException.NotFound(employee);
        }
    }

    @Transactional
    public void editEmployeePassword(Integer idEmployee, String currentPassword, String newPassword, String confirmNewPassword) throws InputException {
        if (employeeRepository.findById(idEmployee).isPresent()) {
            try {
                loginService.editPassword(employeeRepository.findLoginIdByEmployeeId(idEmployee), currentPassword, newPassword, confirmNewPassword);
            } catch (Exception e) {
                throw new InputException(e.getMessage());
            }
        } else {
            throw new InputException("Unable to find Employee");
        }
    }

    @Transactional(readOnly = true)
    public Employee returnEmployee(Integer idEmployee) throws InputException {
        if (employeeRepository.findById(idEmployee).isPresent()) {
            try {
                Employee employee = employeeRepository.findById(idEmployee).get();

                employee.getLoginInfo().setUsername(loginService.returnUsername(employeeRepository.findLoginIdByEmployeeId(idEmployee)));
                employee.setContactInfo(contactService.returnContact(employeeRepository.findContactIdByEmployeeId(idEmployee)));
                employee.setFullName(nameService.returnName(employeeRepository.findNameIdByEmployeeId(idEmployee)));

                return employee;
            } catch (Exception e) {
                throw InputException.NotReturned(employee);
            }
        } else {
            throw InputException.NotRetrievedData(employee);
        }
    }

    @Transactional(readOnly = true)
    public Integer findSessionIdEmployee(Integer idLogin) throws InputException {
        return employeeRepository.findEmployeeIdByLoginId(idLogin)
                .orElseThrow(() -> new InputException("Current Employee session not found"));
    }
}