package com.bitstack.tasktracker.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User registration payload")
public class RegisterRequest {

    @Schema(description = "Desired username for the user", example = "johndoe")
    @JsonProperty("username")
    private String username;

    @Schema(description = "Email address of the user", example = "johndoe@example.com")
    @JsonProperty("email")
    private String email;

    @Schema(description = "Password to be used for account creation", example = "MySecureP@ssword123")
    @JsonProperty("password")
    private String password;

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
