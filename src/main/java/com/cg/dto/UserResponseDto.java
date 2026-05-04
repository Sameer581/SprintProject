package com.cg.dto;

public class UserResponseDto {

    private Long userId;
    private String username;
    private String email;
    private String profilePicture;
   
	public UserResponseDto() {
	}

	public UserResponseDto(Long userId, String username, String email, String profilePicture) {
		super();
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.profilePicture = profilePicture;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
	
	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
}

    