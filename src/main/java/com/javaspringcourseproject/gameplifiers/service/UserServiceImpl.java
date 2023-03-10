package com.javaspringcourseproject.gameplifiers.service;

import com.javaspringcourseproject.gameplifiers.model.Role;
import com.javaspringcourseproject.gameplifiers.model.User;
import com.javaspringcourseproject.gameplifiers.repository.RoleRepository;
import com.javaspringcourseproject.gameplifiers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role normalUserRole = roleRepository.findRoleByName("NormalUser");
        Role contributorRole = roleRepository.findRoleByName("Contributor");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(normalUserRole);
        userRoles.add(contributorRole);
        user.setRoles(userRoles);
        normalUserRole.getUsers().add(user);
        contributorRole.getUsers().add(user);
        userRepository.save(user);
    }

    @Transactional
    public void saveSuperUser(User superUser) {
        superUser.setPassword(bCryptPasswordEncoder.encode(superUser.getPassword()));
        Role editorRole = roleRepository.findRoleByName("Editor");
        Role administratorRole = roleRepository.findRoleByName("Administrator");
        Set<Role> superUserRoles = new HashSet<>();
        superUserRoles.add(editorRole);
        superUserRoles.add(administratorRole);
        superUser.setRoles(superUserRoles);
        editorRole.getUsers().add(superUser);
        administratorRole.getUsers().add(superUser);
        userRepository.save(superUser);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean checkCurrentUserAdministrativePrivileges() {
        List<Role> currentUserRolesToCheck = this.getCurrentUserRoles();
        List<String> roleNames = currentUserRolesToCheck.stream().map(Role::getName).collect(Collectors.toList());
        return roleNames.contains("Administrator") && roleNames.contains("Editor");
    }

    private List<Role> getCurrentUserRoles() {
        Collection<SimpleGrantedAuthority> simpleGrantedAuthorities =
                (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext()
                        .getAuthentication().getAuthorities();

        List<Role> currentUserRoles = new ArrayList<>();

        for (SimpleGrantedAuthority simpleGrantedAuthority : simpleGrantedAuthorities) {
            String authorityName = simpleGrantedAuthority.getAuthority();
            Role role = roleRepository.findRoleByName(authorityName);
            currentUserRoles.add(role);
        }

        return currentUserRoles;
    }
}
