package com.cg.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cg.dto.SuccessMessageDto;
import com.cg.dto.UserDto;
import com.cg.dto.UserResponseDto;
import com.cg.service.UserService;
import com.cg.web.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private UserResponseDto userResponseDto;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        userResponseDto = new UserResponseDto();
        userResponseDto.setUserId(1L);
        userResponseDto.setUsername("rahul");
        userResponseDto.setEmail("rahul@gmail.com");

        userDto = new UserDto();
        userDto.setUsername("rahul");
        userDto.setEmail("rahul@gmail.com");
        userDto.setPassword("rahul123");
    }

    // ─── POST /users/register ─────────────────────────────────────────────────

    @Test
    void testRegisterUser_success() throws Exception {
        when(userService.createUser(any(UserDto.class))).thenReturn(userResponseDto);

        mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.msg").value("User registered successfully"));
    }

//    // ─── POST /users/login ────────────────────────────────────────────────────
//
//    @Test
//    void testLoginUser_success() throws Exception {
//        when(userService.loginUser("rahul@gmail.com", "rahul123")).thenReturn(true);
//
//        mockMvc.perform(post("/users/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.msg").value("Login successful"));
//    }

//    @Test
//    void testLoginUser_invalidCredentials() throws Exception {
//        when(userService.loginUser("rahul@gmail.com", "rahul123")).thenReturn(false);
//
//        mockMvc.perform(post("/users/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userDto)))
//                .andExpect(status().isUnauthorized())
//                .andExpect(jsonPath("$.msg").value("Invalid email or password"));
//    }

    // ─── POST /users/register/all ─────────────────────────────────────────────

    @Test
    void testRegisterAllUsers_success() throws Exception {
        List<UserDto> dtoList = Arrays.asList(userDto);

        doNothing().when(userService).createAllUsers(any());

        mockMvc.perform(post("/users/register/all")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoList)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.msg").value("All users registered successfully"));
    }

    // ─── POST /users/{id}/verify-password ────────────────────────────────────

//    @Test
//    void testVerifyPassword_correct() throws Exception {
//        when(userService.verifyPassword(eq(1L), any())).thenReturn(true);
//
//        mockMvc.perform(post("/users/1/verify-password")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.msg").value("Password is correct"));
//    }

//    @Test
//    void testVerifyPassword_incorrect() throws Exception {
//        when(userService.verifyPassword(eq(1L), any())).thenReturn(false);
//
//        mockMvc.perform(post("/users/1/verify-password")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userDto)))
//                .andExpect(status().isUnauthorized())
//                .andExpect(jsonPath("$.msg").value("Password is incorrect"));
//    }

    // ─── GET /users/{id} ──────────────────────────────────────────────────────

    @Test
    void testGetUserById_success() throws Exception {
        when(userService.getUserById(1L)).thenReturn(userResponseDto);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID").value(1))
                .andExpect(jsonPath("$.username").value("rahul"))
                .andExpect(jsonPath("$.email").value("rahul@gmail.com"));
    }

    // ─── GET /users/all ───────────────────────────────────────────────────────

    @Test
    void testGetAllUsers_success() throws Exception {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(userResponseDto));

        mockMvc.perform(get("/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].username").value("rahul"));
    }

    // ─── GET /users/search ────────────────────────────────────────────────────

    @Test
    void testSearchUsersByUsername_success() throws Exception {
        when(userService.searchByUsername("rahul")).thenReturn(Arrays.asList(userResponseDto));

        mockMvc.perform(get("/users/search").param("username", "rahul"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("rahul"));
    }

    // ─── GET /users/email ─────────────────────────────────────────────────────

    @Test
    void testGetUserByEmail_success() throws Exception {
        when(userService.getUserByEmail("rahul@gmail.com")).thenReturn(userResponseDto);

        mockMvc.perform(get("/users/email").param("email", "rahul@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("rahul@gmail.com"));
    }

    // ─── GET /users/username/{username} ───────────────────────────────────────

    @Test
    void testGetUserByUsername_success() throws Exception {
        when(userService.getUserByUsername("rahul")).thenReturn(userResponseDto);

        mockMvc.perform(get("/users/username/rahul"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("rahul"));
    }

    // ─── GET /users/count ─────────────────────────────────────────────────────

    @Test
    void testCountUsers_success() throws Exception {
        when(userService.countUsers()).thenReturn(5L);

        mockMvc.perform(get("/users/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    // ─── GET /users/exists/email ──────────────────────────────────────────────

    @Test
    void testEmailExists_true() throws Exception {
        when(userService.emailExists("rahul@gmail.com")).thenReturn(true);

        mockMvc.perform(get("/users/exists/email").param("email", "rahul@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    // ─── GET /users/exists/username ───────────────────────────────────────────

    @Test
    void testUsernameExists_true() throws Exception {
        when(userService.usernameExists("rahul")).thenReturn(true);

        mockMvc.perform(get("/users/exists/username").param("username", "rahul"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    // ─── PUT /users/{id} ──────────────────────────────────────────────────────

    @Test
    void testUpdateUser_success() throws Exception {
        when(userService.updateUser(eq(1L), any(UserDto.class))).thenReturn(userResponseDto);

        mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("rahul"));
    }

    // ─── PATCH /users/{id}/username ───────────────────────────────────────────

    @Test
    void testUpdateUsername_success() throws Exception {
        doNothing().when(userService).updateUsername(1L, "rahul_new");

        mockMvc.perform(patch("/users/1/username").param("newUsername", "rahul_new"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Username updated successfully"));
    }

    // ─── PATCH /users/{id}/email ──────────────────────────────────────────────

    @Test
    void testUpdateEmail_success() throws Exception {
        doNothing().when(userService).updateEmail(1L, "new@gmail.com");

        mockMvc.perform(patch("/users/1/email").param("newEmail", "new@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Email updated successfully"));
    }

    // ─── PATCH /users/{id}/password ───────────────────────────────────────────

    @Test
    void testUpdatePassword_success() throws Exception {
        doNothing().when(userService).updatePassword(1L, "newpass999");

        mockMvc.perform(patch("/users/1/password").param("newPassword", "newpass999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Password updated successfully"));
    }

    // ─── DELETE /users/{id} ───────────────────────────────────────────────────

    @Test
    void testDeleteUser_success() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("User deleted successfully"));
    }

    // ─── DELETE /users/all ────────────────────────────────────────────────────

    @Test
    void testDeleteAllUsers_success() throws Exception {
        doNothing().when(userService).deleteAllUsers();

        mockMvc.perform(delete("/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("All users deleted"));
    }
}