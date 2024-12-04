package com.example.authservice.dto;

public class AuthResponse {
    private String token;
    private UserDto user;

    // Default constructor
    public AuthResponse() {}

    // Constructor with parameters
    public AuthResponse(String token, UserDto user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    // Static builder method
    public static AuthResponseBuilder builder() {
        return new AuthResponseBuilder();
    }

    // Builder class
    public static class AuthResponseBuilder {
        private String token;
        private UserDto user;

        public AuthResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public AuthResponseBuilder user(UserDto user) {
            this.user = user;
            return this;
        }

        public AuthResponse build() {
            return new AuthResponse(token, user);
        }
    }
} 