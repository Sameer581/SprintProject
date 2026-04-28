package com.cg.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dto.UserDto;
import com.cg.entity.User;
import com.cg.repo.UserRepo;
import com.cg.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepository;

    // CREATE
    @Override
    public UserDto createUser(UserDto UserDto) {

        User user = new User();
        user.setUsername(UserDto.getUsername());
        user.setEmail(UserDto.getEmail());
        user.setPassword("default123");

        User savedUser = userRepository.save(user);

        return convertToDTO(savedUser);
    }

    // GET BY ID
    @Override
    public UserDto getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return convertToDTO(user);
    }

    // GET ALL
    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // UPDATE
    @Override
    public UserDto updateUser(Long id, UserDto UserDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(UserDto.getUsername());
        user.setEmail(UserDto.getEmail());

        User updatedUser = userRepository.save(user);

        return convertToDTO(updatedUser);
    }

    // DELETE
    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
    }

    // 🔥 Manual Mapper Method
    private UserDto convertToDTO(User user) {
        UserDto dto = new UserDto();
        dto.setUserID(user.getUserID());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }
}