package com.javaspringcourseproject.gameplifiers.service;

import com.javaspringcourseproject.gameplifiers.model.User;

import java.util.List;

public interface UserService {
    void save(User user);

    List<User> findAllUsers();

    User findByUsername(String username);

    void saveSuperUser(User user);
}
