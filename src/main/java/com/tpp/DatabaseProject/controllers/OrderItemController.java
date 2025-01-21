package com.tpp.DatabaseProject.controllers;

import com.tpp.DatabaseProject.models.OrderItems;
import com.tpp.DatabaseProject.service.OrderItemService;
import com.tpp.DatabaseProject.service.OrderService;
import com.tpp.DatabaseProject.service.MenuItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/order-items")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MenuItemService menuItemService;

    @GetMapping
    public String listOrderItems(Model model) {
        model.addAttribute("orderItems", orderItemService.getAllOrderItems());
        return "order-items";
    }

    @GetMapping("/add")
    public String addOrderItemForm(Model model, Authentication authentication) {
        checkAdminAccess(authentication);
        model.addAttribute("orderItem", new OrderItems());
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("menuItems", menuItemService.getAllMenuItems());
        return "add-order-item";
    }

    @PostMapping("/add")
    public String addOrderItem(@Valid @ModelAttribute OrderItems orderItem, 
                             BindingResult bindingResult, 
                             Model model, 
                             Authentication authentication) {
        checkAdminAccess(authentication);
        if (bindingResult.hasErrors()) {
            model.addAttribute("orderItem", orderItem);
            model.addAttribute("orders", orderService.getAllOrders());
            model.addAttribute("menuItems", menuItemService.getAllMenuItems());
            return "add-order-item";
        }
        orderItemService.saveOrderItem(orderItem);
        return "redirect:/order-items";
    }

    @GetMapping("/edit/{id}")
    public String editOrderItemForm(@PathVariable("id") Integer id, 
                                  Model model, 
                                  Authentication authentication) {
        checkAdminAccess(authentication);
        Optional<OrderItems> orderItemOpt = orderItemService.findOrderItemById(id);
        if (orderItemOpt.isPresent()) {
            model.addAttribute("orderItem", orderItemOpt.get());
            model.addAttribute("orders", orderService.getAllOrders());
            model.addAttribute("menuItems", menuItemService.getAllMenuItems());
            return "edit-order-item";
        }
        return "redirect:/order-items";
    }

    @PostMapping("/update/{id}")
    public String updateOrderItem(@PathVariable("id") Integer id, 
                                @Valid @ModelAttribute("orderItem") OrderItems orderItem,
                                BindingResult bindingResult, 
                                Model model, 
                                Authentication authentication) {
        checkAdminAccess(authentication);
        if (bindingResult.hasErrors()) {
            model.addAttribute("orders", orderService.getAllOrders());
            model.addAttribute("menuItems", menuItemService.getAllMenuItems());
            return "edit-order-item";
        }
        orderItem.setOrderItemId(id);
        orderItemService.updateOrderItem(orderItem);
        return "redirect:/order-items";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrderItem(@PathVariable("id") Integer id, 
                                Authentication authentication) {
        checkAdminAccess(authentication);
        orderItemService.deleteOrderItemById(id);
        return "redirect:/order-items";
    }

    private void checkAdminAccess(Authentication authentication) {
        if (authentication == null || !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("Access denied");
        }
    }
}