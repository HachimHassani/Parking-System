package com.platform.parkingsystem.api.controller;
import com.platform.parkingsystem.api.model.User;
import com.platform.parkingsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") String id) {
        return userService.getUserById(id);
    }

    // Add new user
    @PostMapping("")
    public User addUser(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    // Update user
    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") String id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // Delete user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
    }
}