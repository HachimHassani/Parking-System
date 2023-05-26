package com.platform.parkingsystem.api.controller;
import com.platform.parkingsystem.api.exceptions.ResourceNotFoundException;
import com.platform.parkingsystem.api.model.ParkingLot;
import com.platform.parkingsystem.api.model.User;
import com.platform.parkingsystem.api.repository.ParkingLotRepository;
import com.platform.parkingsystem.api.repository.UserRepository;
import com.platform.parkingsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    @PostMapping("/favourites/{parkingLotId}")
    public User addParkingLotToFavourites(Authentication authentication, @PathVariable String parkingLotId) {
        String username = authentication.getName();
        Optional<User> userop = userRepository.findUserByEmail(username);
        if (userop.isEmpty()){
            return null;
        }
        User user = userop.get();
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).orElseThrow(() -> new ResourceNotFoundException("Parking lot not found"));
        if (user.getFavourites() == null || user.getFavourites().isEmpty()){
            user.setFavourites(new ArrayList<>(Collections.singletonList(parkingLot)));
        }else{
        user.getFavourites().add(parkingLot);
        }
        return userRepository.save(user);
    }

    @DeleteMapping("/{userId}/favourites/{parkingLotId}")
    public User removeParkingLotFromFavourites(@PathVariable String userId, @PathVariable String parkingLotId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).orElseThrow(() -> new ResourceNotFoundException("Parking lot not found"));

        user.getFavourites().remove(parkingLot);
        return userRepository.save(user);
    }

    @GetMapping("/favourites")
    public List<ParkingLot> getFavourites(Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userop = userRepository.findUserByEmail(username);
        if (userop.isEmpty()){
            return null;
        }
        User user = userop.get();
        return user.getFavourites();
    }
}