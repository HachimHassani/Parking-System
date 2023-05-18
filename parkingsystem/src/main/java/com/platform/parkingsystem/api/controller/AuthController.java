package com.platform.parkingsystem.api.controller;

import com.platform.parkingsystem.api.exceptions.ResourceNotFoundException;
import com.platform.parkingsystem.api.model.User;
import com.platform.parkingsystem.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public CurrentUser login(@Valid @RequestBody LoginForm form, BindingResult bindingResult,
                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new ResourceNotFoundException("Invalid username or password");
        }

        try {
            request.login(form.getUsername(), form.getPassword());
        } catch (ServletException e) {
            throw new ResourceNotFoundException("Invalid username or password");
        }

        var auth = (Authentication) request.getUserPrincipal();
        var user = (User) auth.getPrincipal();


        return new CurrentUser(user.getId(), user.getFirstName());
    }

    @PostMapping("/logout")
    public LogoutResponse logout() {
        return new LogoutResponse();
    }

    @GetMapping("/current-user")
    public CurrentUser getCurrentUser(@AuthenticationPrincipal User user) {
        return new CurrentUser(user.getId(), user.getFirstName());
    }

    @PostMapping("/register")
    public User addUser( @RequestBody User user) {
        return userService.register(user);
    }


    public record CurrentUser(String id, String nickname) {}
    public record LogoutResponse() {}
}