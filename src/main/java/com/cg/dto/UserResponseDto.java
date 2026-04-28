package com.cg.dto;

public class UserResponseDto {

    private Long userID;
    private String username;
    private String email;

    public UserResponseDto() {}

    public UserResponseDto(Long userID, String username, String email) {
        this.userID = userID;
        this.username = username;
        this.email = email;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
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
}