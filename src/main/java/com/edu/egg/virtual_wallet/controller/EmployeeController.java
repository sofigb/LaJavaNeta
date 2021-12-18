package com.edu.egg.virtual_wallet.controller;

import com.edu.egg.virtual_wallet.entity.*;
import com.edu.egg.virtual_wallet.exception.InputException;
import com.edu.egg.virtual_wallet.service.CustomerService;
import com.edu.egg.virtual_wallet.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/workDashboard")
@PreAuthorize("isAuthenticated()")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService; // Will allow employee to edit/create customers

    @GetMapping("/myData") // IF THE EMPLOYEE HAS ADMIN ROLE, THIS CONTROLLER DOES NOT WORK
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ModelAndView myData(HttpSession session) throws InputException {
        ModelAndView mav = new ModelAndView("employeeData");
        Integer idEmployee = employeeService.findSessionIdEmployee((Integer) session.getAttribute("id"));
        mav.addObject("employee", employeeService.returnEmployee(idEmployee));
        return mav;
    }

    @GetMapping("/addEmployee")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView addEmployee() {
        ModelAndView mav = new ModelAndView("employeeInfoForm");

        mav.addObject("postPath", "/workDashboard/addEmployee/check");
        mav.addObject("contact", new Contact());
        mav.addObject("name", new Name());
        mav.addObject("username", "");

        return mav;
    }

    @PostMapping("/addEmployee/check")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView checkEmployeeData(@ModelAttribute("contact") Contact  contact, @ModelAttribute("name") Name name,
                                          @RequestParam String username) throws InputException {
        employeeService.createEmployee(contact, name, username);
        return new RedirectView("/workDashboard/addEmployee");
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ModelAndView employeeDashboard(HttpSession session)  {
        ModelAndView mav = new ModelAndView("workDashboard");
        mav.addObject("username", session.getAttribute("username"));
        return mav;
    }

    // ADD SEARCH FOR CUSTOMER USERNAME + EMAIL + DNI

    @GetMapping("/employeeData/{idEmployee}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView employeeProfile(@PathVariable Integer idEmployee) throws InputException {
        ModelAndView mav = new ModelAndView("employeeInfoForm");

        Employee employee = employeeService.returnEmployee(idEmployee);

        mav.addObject("postPath", "/workDashboard/employeeData/edit");
        mav.addObject("id", employee.getId());
        mav.addObject("contact", employee.getContactInfo());
        mav.addObject("name", employee.getFullName());
        mav.addObject("username", employee.getLoginInfo().getUsername());

        return mav;
    }

    @PostMapping("/employeeData/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView editEmployeeProfile(@RequestParam Integer idEmployee, @ModelAttribute("contact") Contact contact,
                                            @ModelAttribute("name") Name name, @RequestParam String username) throws InputException {
        employeeService.editEmployee(idEmployee, contact, name, username);
        return new RedirectView("/workDashboard");
    }

    @GetMapping("/myData/changePassword")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ModelAndView changePassword() {
        ModelAndView mav = new ModelAndView("changePassword");

        mav.addObject("postPath", "/workDashboard/myData/changePassword/check");
        mav.addObject("currentPassword", "");
        mav.addObject("newPassword", "");
        mav.addObject("confirmNewPassword", "");
        mav.addObject("defaultDashboardPath", "/workDashboard");

        return mav;
    }

    @PostMapping("/myData/changePassword/check")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public RedirectView verifyChangedPassword(@RequestParam String currentPassword, @RequestParam String newPassword,
                                              @RequestParam String confirmNewPassword, HttpSession session) throws InputException {

        Integer idEmployee = employeeService.findSessionIdEmployee((Integer) session.getAttribute("id"));
        employeeService.editEmployeePassword(idEmployee, currentPassword, newPassword, confirmNewPassword);
        return new RedirectView("/workDashboard");
    }

    @GetMapping("/registerCustomer")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ModelAndView registerCustomer() {
        ModelAndView mav = new ModelAndView("editCustomerProfile");

        mav.addObject("postPath", "/workDashboard/registerCustomer/check");
        mav.addObject("customer", new Customer());
        mav.addObject("address", new Address());
        mav.addObject("contact", new Contact());
        mav.addObject("name", new Name());
        mav.addObject("username", "");
        mav.addObject("defaultDashboardPath", "/workDashboard");

        return mav;
    }

    @PostMapping("/registerCustomer/check")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public RedirectView saveNewCustomer(@ModelAttribute("customer") Customer customer, @ModelAttribute("address") Address address,
                                        @ModelAttribute("contact") Contact  contact, @ModelAttribute("name") Name name,
                                        @RequestParam String username) throws InputException {

        customerService.createCustomer(customer, address, contact, name, username);
        return new RedirectView("/workDashboard");
    }

    @GetMapping("/updateCustomer/{idCustomer}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ModelAndView updateCustomer(@PathVariable Integer idCustomer) throws InputException {
        ModelAndView mav = new ModelAndView("editCustomerProfile");

        Customer customer = customerService.returnCustomer(idCustomer);

        mav.addObject("postPath", "/workDashboard/updateCustomer/check");
        mav.addObject("customer", customer);
        mav.addObject("address", customer.getAddressInfo());
        mav.addObject("contact", customer.getContactInfo());
        mav.addObject("name", customer.getFullName());
        mav.addObject("username", customer.getLoginInfo().getUsername());

        return mav;
    }

    @PostMapping("/updateCustomer/check")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public RedirectView saveUpdatedCustomer(@RequestParam Integer idCustomer, @ModelAttribute Customer customer,
                                            @ModelAttribute Address address, @ModelAttribute("contact") Contact contact,
                                            @ModelAttribute("name") Name name, @RequestParam String username) throws InputException {

        customerService.editCustomer(customer, idCustomer, address, contact, name, username);
        return new RedirectView("/workDashboard");
    }
}