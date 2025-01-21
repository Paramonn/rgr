package com.tpp.DatabaseProject.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.AccessDeniedException;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class GlobalErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");

        if (statusCode == 404) {
            return "error-404"; // Шаблон для 404
        } else if (statusCode == 403) {
            return "access-denied"; // Шаблон для 403
        } else if (statusCode == 401) {
            return "error-401"; // Шаблон для 401
        }
        return "error"; // Шаблон для інших помилок
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "access-denied"; // Перенаправлення на сторінку доступу заборонено
    }
}
