package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Employee;
import com.edu.egg.virtual_wallet.exception.InputException;

import com.edu.egg.virtual_wallet.entity.*;

import com.edu.egg.virtual_wallet.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Autowired
    private EmailSenderService emailService;

    @Transactional(rollbackFor = Exception.class)
    public void createEmployee(Contact contact, Name name, String username) throws InputException {
        try {
            Employee newEmployee = new Employee();

            Login login = new Login();
            login.setUsername(username);

            newEmployee.setContactInfo(contactService.createContact(contact));
            newEmployee.setFullName(nameService.createName(name));
            newEmployee.setLoginInfo(loginService.createLogin(login, "EMPLOYEE"));
            newEmployee.setActive(true);
            employeeRepository.save(newEmployee);

            /*emailService.sendEspecialEmail(
                    newEmployee.getContactInfo().getEmail(),
                    newEmployee.getLoginInfo().getPassword(),
                    newEmployee.getLoginInfo().getUsername());*/
        } catch (Exception e) {
            throw new InputException(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
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

    @Transactional(rollbackFor = Exception.class)
    public void editEmployee(Integer idEmployee, Contact contact, Name name, String username) throws InputException {
        if (employeeRepository.findById(idEmployee).isPresent()) {
            try {
                nameService.editName(name, employeeRepository.findNameIdByEmployeeId(idEmployee));
                contactService.editContact(contact, employeeRepository.findContactIdByEmployeeId(idEmployee));
                loginService.editUsername(username, employeeRepository.findLoginIdByEmployeeId(idEmployee));

                employeeRepository.save(employeeRepository.findById(idEmployee).get());
            } catch (Exception e) {
                throw InputException.NotEdited(employee);
            }
        } else {
            throw InputException.NotFound(employee);
        }
    }

    @Transactional(rollbackFor = Exception.class)
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

    @Transactional(readOnly = true)
    public Integer searchEmployeeByUsernameOrEmail(String username, String email) throws InputException {

        if (employeeRepository.findEmployeeIdByUsername(username).isPresent()) {
            return employeeRepository.findEmployeeIdByUsername(username).get();
        }
        if (employeeRepository.findEmployeeIdByEmail(email).isPresent()) {
            return employeeRepository.findEmployeeIdByEmail(email).get();
        }

        throw new InputException("Employee not found");
    }

    @Transactional(readOnly = true)
    public List<Employee> getEmployeeList() throws InputException {
        try {
            return employeeRepository.findAllByOrderByActivationDateDesc();
        } catch ( Exception e){
            throw new InputException("Unable to retrieve employee list");
        }
    }
}