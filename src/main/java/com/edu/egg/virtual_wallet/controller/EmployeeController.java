package com.edu.egg.virtual_wallet.controller;

import com.edu.egg.virtual_wallet.entity.*;
import com.edu.egg.virtual_wallet.exception.InputException;
import com.edu.egg.virtual_wallet.service.CustomerService;
import com.edu.egg.virtual_wallet.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService; // Will allow employee to edit/create customers

    // ADMIN
    @GetMapping("/workDashboard/addEmployee")
    public ModelAndView addEmployee() {
        ModelAndView mav = new ModelAndView("employeeInfoForm");

        mav.addObject("postPath", "/workDashboard/addEmployee/check");
        mav.addObject("employee", new Employee());
        mav.addObject("contact", new Contact());
        mav.addObject("name", new Name());
        mav.addObject("login", new Login());

        return mav;
    }

    // ADMIN
    @PostMapping("/workDashboard/addEmployee/check")
    public RedirectView checkEmployeeData(@ModelAttribute("employee") Employee employee, @ModelAttribute("contact") Contact  contact,
                                          @ModelAttribute("name") Name name, @ModelAttribute("login") Login login) throws InputException {
        employeeService.createEmployee(employee, contact, name, login);
        return new RedirectView("/workDashboard/addEmployee");
    }

    @GetMapping("/workDashboard")
    public ModelAndView employeeDashboard(HttpSession session) throws  InputException {
        ModelAndView mav = new ModelAndView("workDashboard");
        Integer idEmployee = employeeService.findSessionIdEmployee((Integer) session.getAttribute("id"));
        mav.addObject("employee", employeeService.returnEmployee(idEmployee));
        return mav;
    }

    // ADMIN
    @GetMapping("/workDashboard/employeeData")
    public ModelAndView employeeProfile(HttpSession session) throws InputException {
        ModelAndView mav = new ModelAndView("employeeInfoForm");

        Integer idEmployee = employeeService.findSessionIdEmployee((Integer) session.getAttribute("id"));
        Employee employee = employeeService.returnEmployee(idEmployee);

        mav.addObject("postPath", "/workDashboard/employeeData/edit");
        mav.addObject("employee", employee);
        mav.addObject("contact", employee.getContactInfo());
        mav.addObject("name", employee.getFullName());
        mav.addObject("username", employee.getLoginInfo().getUsername());

        return mav;
    }

    // ADMIN
    @PostMapping("/workDashboard/employeeData/edit")
    public RedirectView editEmployeeProfile(@ModelAttribute Employee employee, @ModelAttribute("contact") Contact contact,
                                            @ModelAttribute("name") Name name, @RequestParam String username,
                                            HttpSession session) throws InputException {

        Integer idEmployee = employeeService.findSessionIdEmployee((Integer) session.getAttribute("id"));
        employeeService.editEmployee(employee, idEmployee, contact, name, username);
        return new RedirectView("/workDashboard");
    }

    @GetMapping("/workDashboard/changePassword")
    public ModelAndView changePassword() {
        ModelAndView mav = new ModelAndView("changePassword");

        mav.addObject("postPath", "/workDashboard/changePassword/check");
        mav.addObject("currentPassword", "");
        mav.addObject("newPassword", "");
        mav.addObject("confirmNewPassword", "");

        return mav;
    }

    @PostMapping("/workDashboard/changePassword/check")
    public RedirectView verifyChangedPassword(@RequestParam String currentPassword, @RequestParam String newPassword,
                                              @RequestParam String confirmNewPassword, HttpSession session) throws InputException {

        Integer idEmployee = employeeService.findSessionIdEmployee((Integer) session.getAttribute("id"));
        employeeService.editEmployeePassword(idEmployee, currentPassword, newPassword, confirmNewPassword);
        return new RedirectView("/workDashboard");
    }

    @GetMapping("/workDashboard/registerCustomer")
    public ModelAndView registerCustomer() {
        ModelAndView mav = new ModelAndView("editCustomerProfile");

        mav.addObject("customer", new Customer());
        mav.addObject("address", new Address());
        mav.addObject("contact", new Contact());
        mav.addObject("name", new Name());
        mav.addObject("username", "");

        return mav;
    }

    @PostMapping("/workDashboard/registerCustomer/check")
    public RedirectView saveNewCustomer(@ModelAttribute("customer") Customer customer, @ModelAttribute("address") Address address,
                                        @ModelAttribute("contact") Contact  contact, @ModelAttribute("name") Name name,
                                        @RequestParam String username) throws InputException {
        Login login = new Login();
        login.setUsername(username);

        customerService.createCustomer(customer, address, contact, name, login);
        return new RedirectView("/workDashboard");
    }

    /*@GetMapping("/workDashboard/updateCustomer")
    public ModelAndView updateCustomer() {

    }

    @PostMapping("/workDashboard/updateCustomer/check")
    public RedirectView saveUpdatedCustomer() {

    }*/
}