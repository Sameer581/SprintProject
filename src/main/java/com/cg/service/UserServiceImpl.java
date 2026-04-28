package com.cg.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dto.UserDto;
import com.cg.dto.UserResponseDto;
import com.cg.entity.User;
import com.cg.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepository;

    // CREATE
    @Override
    public UserResponseDto createUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword("default123"); // password set internally, never exposed
        User savedUser = userRepository.save(user);
        return convertToResponseDto(savedUser);
    }

    // GET BY ID
    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return convertToResponseDto(user);
    }

    // GET ALL
    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    // UPDATE
    @Override
    public UserResponseDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        User updatedUser = userRepository.save(user);
        return convertToResponseDto(updatedUser);
    }

    // DELETE
    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }

    // SEARCH BY USERNAME
    @Override
    public List<UserResponseDto> searchByUsername(String username) {
        return userRepository.findByUsernameContaining(username)
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    // GET BY EMAIL
    @Override
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return convertToResponseDto(user);
    }

    // GET BY USERNAME
    @Override
    public UserResponseDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        return convertToResponseDto(user);
    }

    // COUNT ALL USERS
    @Override
    public Long countUsers() {
        return userRepository.count();
    }

    // EMAIL EXISTS
    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    // USERNAME EXISTS
    @Override
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    // UPDATE USERNAME ONLY
    @Override
    public void updateUsername(Long id, String newUsername) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setUsername(newUsername);
        userRepository.save(user);
    }

    // UPDATE EMAIL ONLY
    @Override
    public void updateEmail(Long id, String newEmail) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setEmail(newEmail);
        userRepository.save(user);
    }

    // UPDATE PASSWORD ONLY
    @Override
    public void updatePassword(Long id, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    // DELETE ALL
    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    // ── PRIVATE MAPPER ────────────────────────────────────────────────────────
    // password is NOT included here — so it NEVER appears in any response
    private UserResponseDto convertToResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setUserID(user.getUserID());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }
}