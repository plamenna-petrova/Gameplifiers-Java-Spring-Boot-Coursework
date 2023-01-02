package com.javaspringcourseproject.gameplifiers.seed;

import com.javaspringcourseproject.gameplifiers.model.Publisher;
import com.javaspringcourseproject.gameplifiers.model.Role;
import com.javaspringcourseproject.gameplifiers.model.User;
import com.javaspringcourseproject.gameplifiers.repository.PublisherRepository;
import com.javaspringcourseproject.gameplifiers.repository.RoleRepository;
import com.javaspringcourseproject.gameplifiers.repository.UserRepository;
import com.javaspringcourseproject.gameplifiers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @Autowired
    PublisherRepository publisherRepository;

    @Override
    public void run(String... args) throws Exception {
        loadRolesAndUsers();
        loadPublishers();
    }

    private void loadRolesAndUsers() {
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

    private void loadPublishers() {
        if (publisherRepository.count() == 0) {
            Publisher ubisoftMontreal = new Publisher("Ubisoft Montreal", "Montreal Canada");
            Publisher bethesda = new Publisher("Bethesda Softworks LLC", "Rockville, Maryland");
            Publisher twoKGames = new Publisher("2K Games", "Novato, California");
            List<Publisher> publishersToSeed = new ArrayList<>();
            publishersToSeed.add(ubisoftMontreal);
            publishersToSeed.add(bethesda);
            publishersToSeed.add(twoKGames);
            for (Publisher publisher: publishersToSeed) {
                publisherRepository.save(publisher);
            }
        }
    }
}
