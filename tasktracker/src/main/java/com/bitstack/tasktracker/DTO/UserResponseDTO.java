package com.bitstack.tasktracker.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User information returned in responses")
public class UserResponseDTO {

    @Schema(description = "Unique identifier of the user", example = "1")
    @JsonProperty("id")
    private Long id;

    @Schema(description = "Username of the user", example = "johndoe")
    @JsonProperty("username")
    private String username;

    @Schema(description = "Email address of the user", example = "johndoe@example.com")
    @JsonProperty("email")
    private String email;

    @Schema(description = "Role assigned to the user", example = "USER")
    @JsonProperty("role")
    private String role;

    // Constructor
    public UserResponseDTO(Long id, String username, String email, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
