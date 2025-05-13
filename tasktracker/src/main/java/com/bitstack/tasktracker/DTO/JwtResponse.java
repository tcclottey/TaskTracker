package com.bitstack.tasktracker.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "JWT authentication response containing token and expiration time")
public class JwtResponse {

    @Schema(description = "JWT token to be used for authenticated requests", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    @JsonProperty("token")
    private String token;

    @Schema(description = "Token expiration time in milliseconds", example = "86400000")
    @JsonProperty("expiresIn")
    private long expiresIn;

    public JwtResponse() {
        // Default constructor
    }

    public JwtResponse(String token, long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
