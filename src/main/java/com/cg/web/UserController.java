package com.cg.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cg.dto.SuccessMessageDto;
import com.cg.dto.UserDto;
import com.cg.dto.UserResponseDto;
import com.cg.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // POST /users/register
    @PostMapping("/register")
    public ResponseEntity<SuccessMessageDto> registerUser(@RequestBody UserDto userDto) {
        UserResponseDto created = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessMessageDto("User registered successfully", created.getUserID()));
    }

    // GET /users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // GET /users/all
    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // GET /users/search?username=john
    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDto>> searchUsersByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.searchByUsername(username));
    }

    // GET /users/email?email=john@gmail.com
    @GetMapping("/email")
    public ResponseEntity<UserResponseDto> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    // GET /users/username/{username}
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    // GET /users/count
    @GetMapping("/count")
    public ResponseEntity<Long> countUsers() {
        return ResponseEntity.ok(userService.countUsers());
    }

    // GET /users/exists/email?email=john@gmail.com
    @GetMapping("/exists/email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        return ResponseEntity.ok(userService.emailExists(email));
    }

    // GET /users/exists/username?username=john
    @GetMapping("/exists/username")
    public ResponseEntity<Boolean> checkUsernameExists(@RequestParam String username) {
        return ResponseEntity.ok(userService.usernameExists(username));
    }

    // PUT /users/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id,
                                                      @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    // PATCH /users/{id}/username
    @PatchMapping("/{id}/username")
    public ResponseEntity<SuccessMessageDto> updateUsername(@PathVariable Long id,
                                                            @RequestParam String newUsername) {
        userService.updateUsername(id, newUsername);
        return ResponseEntity.ok(new SuccessMessageDto("Username updated successfully", id));
    }

    // PATCH /users/{id}/email
    @PatchMapping("/{id}/email")
    public ResponseEntity<SuccessMessageDto> updateEmail(@PathVariable Long id,
                                                         @RequestParam String newEmail) {
        userService.updateEmail(id, newEmail);
        return ResponseEntity.ok(new SuccessMessageDto("Email updated successfully", id));
    }

    // PATCH /users/{id}/password
    @PatchMapping("/{id}/password")
    public ResponseEntity<SuccessMessageDto> updatePassword(@PathVariable Long id,
                                                            @RequestParam String newPassword) {
        userService.updatePassword(id, newPassword);
        return ResponseEntity.ok(new SuccessMessageDto("Password updated successfully", id));
    }

    // DELETE /users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessMessageDto> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new SuccessMessageDto("User deleted successfully", id));
    }

    // DELETE /users/all
    @DeleteMapping("/all")
    public ResponseEntity<SuccessMessageDto> deleteAllUsers() {
        userService.deleteAllUsers();
        return ResponseEntity.ok(new SuccessMessageDto("All users deleted"));
    }
}