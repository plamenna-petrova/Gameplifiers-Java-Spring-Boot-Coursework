package com.javaspringcourseproject.gameplifiers.service;

import com.javaspringcourseproject.gameplifiers.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAllRoles();

    void saveRole(Role role);

    Role findRoleByName(String roleName);
}
