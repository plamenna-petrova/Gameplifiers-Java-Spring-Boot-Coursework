package com.javaspringcourseproject.gameplifiers.service;

import com.javaspringcourseproject.gameplifiers.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
