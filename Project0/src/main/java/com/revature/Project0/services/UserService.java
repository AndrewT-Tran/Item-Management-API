package com.revature.Project0.services;

import com.revature.Project0.models.User;

public interface UserService {
    User register(User user);

    User login(String username, String password);

    User findByUsername(String username);
}
