package com.revature.Project0.services;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.revature.Project0.models.User;
import com.revature.Project0.repositories.UserRepo;

public class UserServiceImplTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private static final String ENCODED_PASSWORD = "$2a$10$GWF7MmfXC122m.HLWwl.huX9UcZDhoqlqq/uoGDj7WIFvaDO6UIhy";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterSuccess() {
        User user = new User();
        user.setUsername("newUser");
        user.setPassword("newPass");
        user.setRole("USER");

        when(userRepo.findByUsername("newUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("newPass")).thenReturn(ENCODED_PASSWORD);

        // Ensure the user returned by the save method has the encoded password
        User savedUser = new User();
        savedUser.setUsername("newUser");
        savedUser.setPassword(ENCODED_PASSWORD);
        savedUser.setRole("USER");
        when(userRepo.save(any(User.class))).thenReturn(savedUser);

        User registeredUser = userService.register(user);

        assertNotNull(registeredUser);
        assertEquals("newUser", registeredUser.getUsername());
        assertEquals(ENCODED_PASSWORD, registeredUser.getPassword());
        assertEquals("USER", registeredUser.getRole());
    }

    @Test
    void testRegisterUsernameAlreadyExists() {
        User user = new User();
        user.setUsername("existingUser");
        user.setPassword("password");

        when(userRepo.findByUsername("existingUser")).thenReturn(Optional.of(user));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.register(user));

        assertEquals("Username already exists", exception.getMessage());
    }

    @Test
    void testLoginSuccess() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword(ENCODED_PASSWORD);
        user.setRole("USER");

        when(userRepo.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("testPass", ENCODED_PASSWORD)).thenReturn(true);

        User loggedInUser = userService.login("testUser", "testPass");

        assertNotNull(loggedInUser);
        assertEquals("testUser", loggedInUser.getUsername());
        assertEquals("USER", loggedInUser.getRole());
    }

    @Test
    void testLoginInvalidPassword() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword(ENCODED_PASSWORD);
        user.setRole("USER");

        when(userRepo.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPass", ENCODED_PASSWORD)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> userService.login("testUser", "wrongPass"));

        assertEquals("Invalid username or password", exception.getMessage());
    }

    @Test
    void testLoginUserNotFound() {
        when(userRepo.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> userService.login("nonExistentUser", "password"));

        assertEquals("Invalid username or password", exception.getMessage());
    }
}
