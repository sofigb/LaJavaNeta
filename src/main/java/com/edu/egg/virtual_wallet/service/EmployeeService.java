package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Employee;
import com.edu.egg.virtual_wallet.exception.InputException;
import com.edu.egg.virtual_wallet.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
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
    private AppUserService userService;

    @Autowired
    private UserRoleService userRoleService;

    private JavaMailSender sender;
    /*
    @Autowired
    private CustomerService customerService; Will allow employee to edit/create customers
    */

    /*
    @Transactional
    public void createEmployee(Employee newEmployee) throws InputException {
        try {
            newEmployee.getUser().getLoginDetails().setRole(userRoleService.findUserRoleByRoleName("EMPLOYEE"));
            userService.createUser(newEmployee.getUser());
            employeeRepository.save(newEmployee);
            sender.sendEspecialEmail(newEmployee.getUser().getName().getEmail(),// password ,newEmployee.getUser().getName().getFirstName());
        } catch (Exception e) {
            throw InputException.NotCreated(employee);
        }
    }

    @Transactional
    public void deactivateEmployee(Integer idEmployee) throws InputException {
        if (employeeRepository.findById(idEmployee).isPresent()) {
            try {
                userService.deactivateUser(employeeRepository.findById(idEmployee).get().getUser()); // ?
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
    public void editEmployee(Employee updatedEmployee) throws InputException {
        if (employeeRepository.findById(updatedEmployee.getId()).isPresent()) {
            try {
                userService.editUser(updatedEmployee.getUser());
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
                employee.setUser(userService.returnUser(employee.getUser().getId()));
                return employee;
            } catch (Exception e) {
                throw InputException.NotReturned(employee);
            }
        } else {
            throw InputException.NotRetrievedData(employee);
        }
    }
}
