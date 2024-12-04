package com.example.authservice.service;

import com.example.authservice.dto.AuthRequest;
import com.example.authservice.dto.AuthResponse;
import com.example.authservice.dto.RegisterRequest;
import com.example.authservice.dto.UserDto;
import com.example.authservice.exception.UserAlreadyExistsException;
import com.example.authservice.exception.InvalidCredentialsException;
import com.example.authservice.model.User;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.security.CustomUserDetails;
import com.example.authservice.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, 
                      PasswordEncoder passwordEncoder, 
                      JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse authenticate(AuthRequest request) {
        logger.debug("Attempting to authenticate user: {}", request.getUsername());
        
        // Find user in database
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    logger.error("User not found: {}", request.getUsername());
                    return new InvalidCredentialsException("Invalid username or password");
                });
        
        logger.debug("User found in database: {}", user.getUsername());

        // Verify password
        String rawPassword = request.getPassword();
        String storedHash = user.getPassword();
        
        boolean matches = passwordEncoder.matches(rawPassword, storedHash);
        logger.debug("Password match result: {}", matches);

        if (!matches) {
            logger.error("Password verification failed for user: {}", request.getUsername());
            throw new InvalidCredentialsException("Invalid username or password");
        }
        
        // Generate JWT token
        String token = jwtUtil.generateToken(new CustomUserDetails(user.getUsername()));
        logger.debug("JWT token generated for user: {}", user.getUsername());

        // Create response
        UserDto userDto = mapToUserDto(user);
        AuthResponse response = AuthResponse.builder()
                .token(token)
                .user(userDto)
                .build();
        
        logger.info("Authentication successful for user: {}", user.getUsername());
        return response;
    }

    public AuthResponse register(RegisterRequest request) {
        logger.debug("Attempting to register new user: {}", request.getUsername());
        
        // Check if username already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            logger.error("Username already exists: {}", request.getUsername());
            throw new UserAlreadyExistsException("Username already exists");
        }

        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            logger.error("Email already exists: {}", request.getEmail());
            throw new UserAlreadyExistsException("Email already exists");
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // Save user
        try {
            user = userRepository.save(user);
            logger.debug("User saved to database: {}", user.getUsername());
        } catch (Exception e) {
            logger.error("Error saving user: {}", e.getMessage());
            throw new RuntimeException("Error creating user");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(new CustomUserDetails(user.getUsername()));
        logger.debug("JWT token generated for new user: {}", user.getUsername());

        // Create response
        AuthResponse response = AuthResponse.builder()
                .token(token)
                .user(mapToUserDto(user))
                .build();

        logger.info("Registration successful for user: {}", user.getUsername());
        return response;
    }

    private UserDto mapToUserDto(User user) {
        logger.debug("Mapping User to UserDto for: {}", user.getUsername());
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
} 