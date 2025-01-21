package com.tpp.DatabaseProject.controllers;

import com.tpp.DatabaseProject.models.Customers;
import com.tpp.DatabaseProject.service.CustomerService;
import org.springframework.security.access.AccessDeniedException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customers";
    }

    @GetMapping("/add")
    public String addCustomerForm(Model model, Authentication authentication) {
        checkAdminAccess(authentication); // Перевірка прав адміністратора
        model.addAttribute("customer", new Customers());
        return "add-customer";
    }

    @PostMapping("/add")
    public String addCustomer(@Valid @ModelAttribute("customer") Customers customer, BindingResult result, Authentication authentication) {
        checkAdminAccess(authentication);
        if (result.hasErrors()) {
            return "add-customer";
        }
        customerService.addCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String editCustomerForm(@PathVariable("id") int id, Model model, Authentication authentication) {
        checkAdminAccess(authentication);
        Customers customer = customerService.findById(id).orElse(null);
        if (customer != null) {
            model.addAttribute("customer", customer);
            return "edit-customer";
        } else {
            return "redirect:/customers";
        }
    }

    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable("id") int id, @Valid @ModelAttribute("customer") Customers customer,
                                  BindingResult result, Authentication authentication) {
        checkAdminAccess(authentication);
        if (result.hasErrors()) {
            return "edit-customer";
        }
        customer.setCustomerId(id);
        customerService.updateCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") int id, Authentication authentication) {
        checkAdminAccess(authentication);
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }

    private void checkAdminAccess(Authentication authentication) {
        if (authentication == null || !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("Access denied");
        }
    }
}
