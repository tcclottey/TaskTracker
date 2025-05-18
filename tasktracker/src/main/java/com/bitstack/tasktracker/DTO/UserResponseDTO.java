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
    
    
    @Schema(description = "First name of the user", example = "John")
    @JsonProperty("first_name")
    private String firstName;
    
    @Schema(description = "Last name of the user", example = "Doe")
    @JsonProperty("last_name")
    private String lastName;
    
    @Schema(description = "Profile image URL of the user", example = "https://example.com/profile.jpg")
    @JsonProperty("profile_image_url")
    private String profileImageUrl;
  
    
    public UserResponseDTO(Long id, String username, String email, String role, String firstName, String lastName, String profileImageUrl) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.firstName = null;
        this.lastName = null;
        this.profileImageUrl = null;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
}
