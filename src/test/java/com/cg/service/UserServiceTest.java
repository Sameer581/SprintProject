package com.cg.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.dto.UserDto;
import com.cg.dto.UserResponseDto;
import com.cg.entity.User;
//import com.cg.exception.NotAvailableException;
import com.cg.repo.UserRepo;
import com.cg.service.UserService;
import com.cg.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setUsername("rahul");
        user.setEmail("rahul@gmail.com");
        user.setPassword("rahul123");

        userDto = new UserDto();
        userDto.setUsername("rahul");
        userDto.setEmail("rahul@gmail.com");
        userDto.setPassword("rahul123");
    }

    @Test
    void testCreateUser_success() {
        when(userRepo.save(any(User.class))).thenReturn(user);

        UserResponseDto result = userService.createUser(userDto);

        assertEquals("rahul", result.getUsername());
        assertEquals("rahul@gmail.com", result.getEmail());
        verify(userRepo, times(1)).save(any(User.class));
    }

//    // ─── LOGIN ────────────────────────────────────────────────────────────────
//
//    @Test
//    void testLoginUser_success() {
//        when(userRepo.findByEmail("rahul@gmail.com")).thenReturn(Optional.of(user));
//
//        boolean result = userService.loginUser("rahul@gmail.com", "rahul123");
//
//        assertTrue(result);
//    }

//    @Test
//    void testLoginUser_wrongPassword() {
//        when(userRepo.findByEmail("rahul@gmail.com")).thenReturn(Optional.of(user));
//
//        boolean result = userService.loginUser("rahul@gmail.com", "wrongpass");
//
//        assertFalse(result);
//    }
//
//    @Test
//    void testLoginUser_userNotFound() {
//        when(userRepo.findByEmail("wrong@gmail.com")).thenReturn(Optional.empty());
//
//        assertThrows(RuntimeException.class, () -> {
//            userService.loginUser("wrong@gmail.com", "rahul123");
//        });
//    }

    // ─── GET BY ID ────────────────────────────────────────────────────────────

    @Test
    void testGetUserById_success() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDto result = userService.getUserById(1L);

        assertEquals(1L, result.getUserID());
        assertEquals("rahul", result.getUsername());
    }

    @Test
    void testGetUserById_notFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.getUserById(1L);
        });
    }

    // ─── GET ALL ─────────────────────────────────────────────────────────────

    @Test
    void testGetAllUsers_success() {
        User user2 = new User();
        user2.setUserId(2L);
        user2.setUsername("amit");
        user2.setEmail("amit@gmail.com");

        when(userRepo.findAll()).thenReturn(Arrays.asList(user, user2));

        List<UserResponseDto> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("rahul", result.get(0).getUsername());
        assertEquals("amit", result.get(1).getUsername());
    }

    @Test
    void testGetAllUsers_emptyList() {
        when(userRepo.findAll()).thenReturn(Arrays.asList());

        List<UserResponseDto> result = userService.getAllUsers();

        assertEquals(0, result.size());
    }

    // ─── GET BY EMAIL ─────────────────────────────────────────────────────────

    @Test
    void testGetUserByEmail_success() {
        when(userRepo.findByEmail("rahul@gmail.com")).thenReturn(Optional.of(user));

        UserResponseDto result = userService.getUserByEmail("rahul@gmail.com");

        assertEquals("rahul@gmail.com", result.getEmail());
    }

    @Test
    void testGetUserByEmail_notFound() {
        when(userRepo.findByEmail("wrong@gmail.com")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.getUserByEmail("wrong@gmail.com");
        });
    }

    // ─── GET BY USERNAME ──────────────────────────────────────────────────────

    @Test
    void testGetUserByUsername_success() {
        when(userRepo.findByUsername("rahul")).thenReturn(Optional.of(user));

        UserResponseDto result = userService.getUserByUsername("rahul");

        assertEquals("rahul", result.getUsername());
    }

    @Test
    void testGetUserByUsername_notFound() {
        when(userRepo.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.getUserByUsername("unknown");
        });
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────

    @Test
    void testUpdateUser_success() {
        UserDto updatedDto = new UserDto();
        updatedDto.setUsername("rahul_updated");
        updatedDto.setEmail("updated@gmail.com");

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(userRepo.save(any(User.class))).thenReturn(user);

        UserResponseDto result = userService.updateUser(1L, updatedDto);

        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser_notFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.updateUser(1L, userDto);
        });
    }

    // ─── UPDATE USERNAME ──────────────────────────────────────────────────────

    @Test
    void testUpdateUsername_success() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(userRepo.save(any(User.class))).thenReturn(user);

        userService.updateUsername(1L, "rahul_new");

        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUsername_notFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.updateUsername(1L, "rahul_new");
        });
    }

    // ─── UPDATE PASSWORD ──────────────────────────────────────────────────────

    @Test
    void testUpdatePassword_success() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(userRepo.save(any(User.class))).thenReturn(user);

        userService.updatePassword(1L, "newpass999");

        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void testUpdatePassword_notFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.updatePassword(1L, "newpass999");
        });
    }
//
//    // ─── VERIFY PASSWORD ─────────────────────────────────────────────────────
//
//    @Test
//    void testVerifyPassword_correct() {
//        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
//
//        boolean result = userService.verifyPassword(1L, "rahul123");
//
//        assertTrue(result);
//    }
//
//    @Test
//    void testVerifyPassword_incorrect() {
//        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
//
//        boolean result = userService.verifyPassword(1L, "wrongpass");
//
//        assertFalse(result);
//    }

    // ─── EMAIL / USERNAME EXISTS ──────────────────────────────────────────────

    @Test
    void testEmailExists_true() {
        when(userRepo.existsByEmail("rahul@gmail.com")).thenReturn(true);

        assertTrue(userService.emailExists("rahul@gmail.com"));
    }

    @Test
    void testEmailExists_false() {
        when(userRepo.existsByEmail("no@gmail.com")).thenReturn(false);

        assertFalse(userService.emailExists("no@gmail.com"));
    }

    @Test
    void testUsernameExists_true() {
        when(userRepo.existsByUsername("rahul")).thenReturn(true);

        assertTrue(userService.usernameExists("rahul"));
    }

    @Test
    void testUsernameExists_false() {
        when(userRepo.existsByUsername("unknown")).thenReturn(false);

        assertFalse(userService.usernameExists("unknown"));
    }

    // ─── COUNT ────────────────────────────────────────────────────────────────

    @Test
    void testCountUsers() {
        when(userRepo.count()).thenReturn(5L);

        Long result = userService.countUsers();

        assertEquals(5L, result);
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────

    @Test
    void testDeleteUser_success() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepo, times(1)).delete(user);
    }

    @Test
    void testDeleteUser_notFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.deleteUser(1L);
        });
    }

    @Test
    void testDeleteAllUsers() {
        userService.deleteAllUsers();

        verify(userRepo, times(1)).deleteAll();
    }
}