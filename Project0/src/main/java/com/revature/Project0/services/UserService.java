package com.revature.Project0.services;

import com.revature.Project0.models.User;

public interface UserService {

    // Method to register a new user
    User register(User user);

    // Method to log in a user
    User login(String username, String password);
}
