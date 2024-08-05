package com.revature.Project0.services;

import com.revature.Project0.models.User;
import com.revature.Project0.repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterSuccess() {
        User user = new User();
        user.setUsername("newUser");
        user.setPassword("newPass");

        when(userRepo.findByUsername("newUser")).thenReturn(null);
        when(userRepo.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.register(user);

        assertNotNull(registeredUser);
        assertEquals("newUser", registeredUser.getUsername());
    }

    @Test
    void testRegisterUsernameAlreadyExists() {
        User user = new User();
        user.setUsername("existingUser");
        user.setPassword("password");

        when(userRepo.findByUsername("existingUser")).thenReturn(user);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.register(user));

        assertEquals("Username already exists", exception.getMessage());
    }

    @Test
    void testLoginSuccess() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPass");

        when(userRepo.findByUsername("testUser")).thenReturn(user);

        User loggedInUser = userService.login("testUser", "testPass");

        assertNotNull(loggedInUser);
        assertEquals("testUser", loggedInUser.getUsername());
    }

    @Test
    void testLoginInvalidPassword() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPass");

        when(userRepo.findByUsername("testUser")).thenReturn(user);

        User loggedInUser = userService.login("testUser", "wrongPass");

        assertNull(loggedInUser);
    }

    @Test
    void testLoginUserNotFound() {
        when(userRepo.findByUsername("nonExistentUser")).thenReturn(null);

        User loggedInUser = userService.login("nonExistentUser", "password");

        assertNull(loggedInUser);
    }
}
