package com.bitstack.tasktracker.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User registration payload")
public class RegisterRequest {
	
	
	
	@Schema(description = "First name of the user", example = "John")
	@JsonProperty("first_name")
	private String firstName;
	
	@Schema(description = "Last name of the user", example = "Doe")
	@JsonProperty("last_name")
    private String lastName;

    @Schema(description = "Desired username for the user", example = "johndoe")
    @JsonProperty("username")
    private String username;

    @Schema(description = "Email address of the user", example = "johndoe@example.com")
    @JsonProperty("email")
    private String email;

    @Schema(description = "Password to be used for account creation", example = "MySecureP@ssword123")
    @JsonProperty("password")
    private String password;
    
    @Schema(description = "Profile image URL of the user", example = "https://example.com/profile.jpg")
    @JsonProperty("profile_image_url")
    private String profileImageUrl;
    

    

	public RegisterRequest() {
    }

    public RegisterRequest(String firstName, String lastName, String username, String email, String password, String profileImageUrl) {
    	
    	this.firstName = firstName;
    	this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
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

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
}
