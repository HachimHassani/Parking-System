package com.platform.parkingsystem.api.config;

import com.platform.parkingsystem.api.model.User;
import com.platform.parkingsystem.api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

// UserDetailsServiceImpl class
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findUserByEmail(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Perform necessary authentication steps
            // ...

            return user;
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }
}
