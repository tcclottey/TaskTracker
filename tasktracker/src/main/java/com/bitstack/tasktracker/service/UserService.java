package com.bitstack.tasktracker.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.bitstack.tasktracker.model.User;
import com.bitstack.tasktracker.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority("ROLE_" + user.getRole()) // Only if role exists in User
        );

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            authorities
        );
    }
    
    public User getCurrentUser(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            String identifier = auth.getName();

            // Try finding by username first
            Optional<User> userOpt = userRepository.findByUsername(identifier);

            if (userOpt.isPresent()) {
                return userOpt.get();
            }

            // Fallback: try finding by email
            return userRepository.findByEmail(identifier)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found with username or email: " + identifier));
        }

        throw new UsernameNotFoundException("No authenticated user found.");
    }

}
