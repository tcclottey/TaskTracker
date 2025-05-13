package com.bitstack.tasktracker.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication request payload containing username and password")
public class AuthRequest {

    @Schema(description = "Username of the user", example = "johndoe")
    @JsonProperty("username")
    private String username;

    @Schema(description = "Password of the user", example = "MySecureP@ssword123")
    @JsonProperty("password")
    private String password;

    public AuthRequest() {
    }

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
