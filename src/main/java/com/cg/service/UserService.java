package com.cg.service;

import java.util.List;

import com.cg.dto.UserDto;
import com.cg.dto.UserResponseDto;

public interface UserService {

    UserResponseDto createUser(UserDto userDto);

    UserResponseDto getUserById(Long id);

    List<UserResponseDto> getAllUsers();

    void createAllUsers(List<UserDto> userDtos);

    UserResponseDto updateUser(Long id, UserDto userDto);

    void deleteUser(Long id);

    List<UserResponseDto> searchByUsername(String username);

    UserResponseDto getUserByEmail(String email);

    UserResponseDto getUserByUsername(String username);

    Long countUsers();

    boolean emailExists(String email);

    boolean usernameExists(String username);

    void updateUsername(Long id, String newUsername);

    void updateEmail(Long id, String newEmail);

    void updatePassword(Long id, String newPassword);

    void deleteAllUsers();
}