package com.cg.service;

import java.util.List;
import com.cg.dto.UserDto;

public interface UserService {

    UserDto createUser(UserDto UserDto);

    UserDto getUserById(Long id);

    List<UserDto> getAllUsers();

    UserDto updateUser(Long id, UserDto UserDto);

    void deleteUser(Long id);
}