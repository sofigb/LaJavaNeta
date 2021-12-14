package com.edu.egg.virtual_wallet.model.mapper;

import com.edu.egg.virtual_wallet.model.entity.*;
import com.edu.egg.virtual_wallet.model.dto.EmployeeDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeDTO convertToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new  EmployeeDTO();

        Name name = employee.getUser().getFullName();
        employeeDTO.setFirstName(name.getFirstName());
        employeeDTO.setMiddleName(name.getMiddleName().isEmpty() ? "-" : name.getMiddleName());
        employeeDTO.setLastName(name.getLastName());

        Contact contact = employee.getUser().getContactInfo();
        employeeDTO.setEmail(contact.getEmail());
        employeeDTO.setPhoneNumber(contact.getPhoneNumber());

        Login login = employee.getUser().getLoginDetails();
        employeeDTO.setUsername(login.getUsername());

        return employeeDTO;
    }

    public Employee convertToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        AppUser user = new AppUser();

        Name name = new Name();
        name.setFirstName(employeeDTO.getFirstName());
        name.setMiddleName(employeeDTO.getMiddleName());
        name.setLastName(employeeDTO.getLastName());

        Contact contact = new Contact();
        contact.setEmail(employeeDTO.getEmail());
        contact.setPhoneNumber(employeeDTO.getPhoneNumber());

        Login login = new Login();
        login.setUsername(employeeDTO.getUsername());

        user.setFullName(name);
        user.setContactInfo(contact);
        user.setLoginDetails(login);

        employee.setUser(user);

        return employee;
    }
}
