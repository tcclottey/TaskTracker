package com.bitstack.tasktracker.controller;

import com.bitstack.tasktracker.DTO.AuthRequest;
import com.bitstack.tasktracker.DTO.JwtResponse;
import com.bitstack.tasktracker.DTO.RegisterRequest;
import com.bitstack.tasktracker.DTO.UserResponseDTO;
import com.bitstack.tasktracker.model.User;
import com.bitstack.tasktracker.repository.UserRepository;
import com.bitstack.tasktracker.security.JwtUtil;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.time.LocalDateTime;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername()) ||
            userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Username or email already exists");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setProfileImageUrl(request.getProfileImageUrl()); // Can be null
        user.setCreatedAt(LocalDateTime.now());
        
     // ✅ Check if it's the first user — assign ADMIN
        if (userRepository.count() == 0) {
            user.setRole("ADMIN");
        } else {
            user.setRole("USER");
        }
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthRequest request) throws AuthenticationException {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token = jwtUtil.generateToken(request.getUsername());
        long expiresIn = jwtUtil.getExpiration();  // Ensure this method is implemented

        return ResponseEntity.ok(new JwtResponse(token, expiresIn));
    }
    
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName())
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        UserResponseDTO response = new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getProfileImageUrl()
            );

            return ResponseEntity.ok(response);
        }
}
