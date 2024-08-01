package com.revature.Project0.services;

import com.revature.Project0.models.User;
import com.revature.Project0.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User register(User user) {
        // Check if the username already exists
        if (userRepo.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        // Save user with plain text password
        return userRepo.save(user);
    }

    @Override
    public User login(String username, String password) {
        User user = userRepo.findByUsername(username);
        // Log or print values for debugging
        System.out.println("Attempting login for username: " + username);
        if (user != null) {
            System.out.println("User found: " + user);
            System.out.println("Stored password: " + user.getPassword());
            System.out.println("Provided password: " + password);
            if (user.getPassword().equals(password)) {
                return user;
            }
        } else {
            System.out.println("User not found: " + username);
        }
        return null;
    }
}
