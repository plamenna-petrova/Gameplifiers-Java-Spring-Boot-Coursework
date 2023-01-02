package com.javaspringcourseproject.gameplifiers.service;

public interface SecurityService {
    boolean isAuthenticated();

    void autoLogin(String username, String password);
}
