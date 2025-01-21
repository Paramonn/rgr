package com.tpp.DatabaseProject.controllers;

import com.tpp.DatabaseProject.models.Orders;
import com.tpp.DatabaseProject.service.CustomerService;
import com.tpp.DatabaseProject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders";
    }

    @GetMapping("/add")
    public String addOrderForm(Model model, 
                             Authentication authentication) {
        checkAdminAccess(authentication);
        model.addAttribute("order", new Orders());
        model.addAttribute("customers", customerService.getAllCustomers());
        return "add-order";
    }

    @PostMapping("/add")
    public String addOrder(@Valid @ModelAttribute("order") Orders order, 
                          BindingResult result, 
                          Model model, 
                          Authentication authentication) {
        checkAdminAccess(authentication);
        if (result.hasErrors()) {
            model.addAttribute("customers", customerService.getAllCustomers());
            return "add-order";
        }
        orderService.saveOrder(order);
        return "redirect:/orders";
    }

    @GetMapping("/edit/{id}")
    public String editOrderForm(@PathVariable("id") Integer id, 
                              Model model, 
                              Authentication authentication) {
        checkAdminAccess(authentication);
        Optional<Orders> orderOpt = orderService.findOrderById(id);
        if (orderOpt.isPresent()) {
            model.addAttribute("order", orderOpt.get());
            model.addAttribute("customers", customerService.getAllCustomers());
            return "edit-order";
        }
        return "redirect:/orders";
    }

    @PostMapping("/update/{id}")
    public String updateOrder(@PathVariable("id") Integer id, 
                            @Valid @ModelAttribute("order") Orders order,
                            BindingResult result, 
                            Model model, 
                            Authentication authentication) {
        checkAdminAccess(authentication);
        if (result.hasErrors()) {
            model.addAttribute("customers", customerService.getAllCustomers());
            return "edit-order";
        }
        orderService.updateOrder(order);
        return "redirect:/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") Integer id, 
                            Authentication authentication) {
        checkAdminAccess(authentication);
        orderService.deleteOrderById(id);
        return "redirect:/orders";
    }

    private void checkAdminAccess(Authentication authentication) {
        if (authentication == null || !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("Access denied");
        }
    }
}