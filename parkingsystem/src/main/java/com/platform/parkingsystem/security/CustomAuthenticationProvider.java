package com.platform.parkingsystem.security;

import com.platform.parkingsystem.api.model.User;
import com.platform.parkingsystem.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Bean
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
//        Optional<User> optionalUser = userRepository.findByEmail(username);
//
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//             if (user == null || !password.equals(user.getPassword())) {
//                throw new BadCredentialsException("Authentication failed for " + username);
//            }
//             List<GrantedAuthority> authorities = new ArrayList<>();
//            for (String role : user.getRoles()) {
//                authorities.add(new SimpleGrantedAuthority(role));
//            }
//
//            return new UsernamePasswordAuthenticationToken(username, password, authorities);}
//             else {
//            throw new BadCredentialsException("Authentication failed for " + username);
//        }
//
//
//    }
//
//    @Bean
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }
}
