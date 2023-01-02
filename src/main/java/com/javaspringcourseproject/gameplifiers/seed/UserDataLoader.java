package com.javaspringcourseproject.gameplifiers.seed;

import com.javaspringcourseproject.gameplifiers.model.Role;
import com.javaspringcourseproject.gameplifiers.model.User;
import com.javaspringcourseproject.gameplifiers.repository.RoleRepository;
import com.javaspringcourseproject.gameplifiers.repository.UserRepository;
import com.javaspringcourseproject.gameplifiers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDataLoader implements CommandLineRunner {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    private void loadUserData() {
        if (roleRepository.count() == 0) {
            List<Role> rolesToSeed = new ArrayList<Role>();
            rolesToSeed.add(new Role("Administrator"));
            rolesToSeed.add(new Role("Editor"));
            rolesToSeed.add(new Role("Contributor"));
            rolesToSeed.add(new Role("NormalUser"));
            for (Role role: rolesToSeed) {
                roleRepository.save(role);
            }
        }

        if (userService.findAllUsers().size() == 0) {
            User firstUserToSeed = new User("John Doe", "John123/");
            User secondUserToSeed = new User("Admin", "Admin123/");
            userService.save(firstUserToSeed);
            userService.saveSuperUser(secondUserToSeed);
        }
    }
}
