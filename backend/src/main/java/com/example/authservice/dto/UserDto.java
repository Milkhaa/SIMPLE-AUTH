package com.example.authservice.dto;

public class UserDto {
    private Long id;
    private String username;
    private String name;
    private String email;

    // Default constructor
    public UserDto() {}

    // Constructor with parameters
    public UserDto(Long id, String username, String name, String email) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Static builder method
    public static UserDtoBuilder builder() {
        return new UserDtoBuilder();
    }

    // Builder class
    public static class UserDtoBuilder {
        private Long id;
        private String username;
        private String name;
        private String email;

        public UserDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserDtoBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserDto build() {
            return new UserDto(id, username, name, email);
        }
    }
} 