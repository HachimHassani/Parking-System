package com.platform.parkingsystem.api.controller;
import com.platform.parkingsystem.api.exceptions.ResourceNotFoundException;
import com.platform.parkingsystem.api.model.ParkingLot;
import com.platform.parkingsystem.api.model.User;
import com.platform.parkingsystem.api.repository.ParkingLotRepository;
import com.platform.parkingsystem.api.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

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
    public User addUser( @RequestBody User user) {
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

    @PostMapping("/{userId}/favourites/{parkingLotId}")
    public User addParkingLotToFavourites(@PathVariable String userId, @PathVariable String parkingLotId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).orElseThrow(() -> new ResourceNotFoundException("Parking lot not found"));

        user.getFavourites().add(parkingLot);
        return userRepository.save(user);
    }

    @DeleteMapping("/{userId}/favourites/{parkingLotId}")
    public User removeParkingLotFromFavourites(@PathVariable String userId, @PathVariable String parkingLotId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).orElseThrow(() -> new ResourceNotFoundException("Parking lot not found"));

        user.getFavourites().remove(parkingLot);
        return userRepository.save(user);
    }

    @GetMapping("/{userId}/favourites")
    public List<ParkingLot> getFavourites(@PathVariable String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getFavourites();
    }
}