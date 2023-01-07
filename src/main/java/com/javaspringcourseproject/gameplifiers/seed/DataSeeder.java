package com.javaspringcourseproject.gameplifiers.seed;

import com.javaspringcourseproject.gameplifiers.model.Game;
import com.javaspringcourseproject.gameplifiers.model.Publisher;
import com.javaspringcourseproject.gameplifiers.model.Role;
import com.javaspringcourseproject.gameplifiers.model.User;
import com.javaspringcourseproject.gameplifiers.repository.PublisherRepository;
import com.javaspringcourseproject.gameplifiers.repository.RoleRepository;
import com.javaspringcourseproject.gameplifiers.repository.UserRepository;
import com.javaspringcourseproject.gameplifiers.service.GameService;
import com.javaspringcourseproject.gameplifiers.service.PublisherService;
import com.javaspringcourseproject.gameplifiers.service.RoleService;
import com.javaspringcourseproject.gameplifiers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Autowired
    PublisherService publisherService;

    @Autowired
    GameService gameService;

    @Override
    public void run(String... args) throws Exception {
        loadRolesAndUsers();
        loadPublishersAndGames();
    }

    private void loadRolesAndUsers() {
        if (roleService.findAllRoles().size() == 0) {
            List<Role> rolesToSeed = new ArrayList<Role>();
            rolesToSeed.add(new Role("Administrator"));
            rolesToSeed.add(new Role("Editor"));
            rolesToSeed.add(new Role("Contributor"));
            rolesToSeed.add(new Role("NormalUser"));
            for (Role role: rolesToSeed) {
                roleService.saveRole(role);
            }
        }

        if (userService.findAllUsers().size() == 0) {
            User firstUserToSeed = new User("John Doe", "John123/");
            User secondUserToSeed = new User("Admin", "Admin123/");
            userService.save(firstUserToSeed);
            userService.saveSuperUser(secondUserToSeed);
        }
    }

    private void loadPublishersAndGames() {
        if (publisherService.findAllPublishers().size() == 0 &&
                gameService.findAllGames().size() == 0) {
            Publisher ubisoftMontreal = new Publisher("Ubisoft Montreal", "Montreal Canada");
            Publisher bethesda = new Publisher("Bethesda Softworks LLC", "Rockville, Maryland");
            Publisher twoKGames = new Publisher("2K Games", "Novato, California");
            List<Publisher> publishersToSeed = new ArrayList<>();
            publishersToSeed.add(ubisoftMontreal);
            publishersToSeed.add(bethesda);
            publishersToSeed.add(twoKGames);
            for (Publisher publisher : publishersToSeed) {
                publisherService.upsertPublisher(publisher);
            }
            Game assassinsCreed = new Game();
            assassinsCreed.setTitle("Assassin's Creed");
            assassinsCreed.setEdition("Director's Cut Edition");
            assassinsCreed.setYearOfRelease(2007);
            assassinsCreed.setImageUrl("https://howlongtobeat.com/games/Assassins_Creed.jpg?width=250");
            assassinsCreed.setMainStoryCompletionTime("15");
            assassinsCreed.setMainStoryPlusSideQuestsCompletionTime("20½");
            assassinsCreed.setCompletionistTime("32½");
            assassinsCreed.setAllStylesCompletionTime("19½");
            assassinsCreed.setAnnotation("Assassin's Creed is the next-gen game developed by Ubisoft Montreal " +
                    "that will redefine the action genre. While other games claim to be " +
                    "next-gen with impressive graphics and physics, " +
                    "Assassin's Creed merges technology, game design, theme, " +
                    "and emotions into a world where you instigate chaos and become a " +
                    "vulnerable, yet powerful, agent of change.The setting is 1191 AD. " +
                    "The Third Crusade is tearing the Holy Land apart. " +
                    "You, Altair, intend to stop the hostilities by suppressing both sides of the conflict." +
                    "You are an Assassin, a warrior shrouded in secrecy and feared for your ruthlessness. " +
                    "Your actions can throw your immediate environment into chaos, and your existence will shape events " +
                    "during this pivotal moment in history."
            );
            assassinsCreed.setPublisher(publishersToSeed.get(0));
            Game fallout3 = new Game();
            fallout3.setTitle("Fallout 3");
            fallout3.setEdition("Game of the Year Edition");
            fallout3.setYearOfRelease(2008);
            fallout3.setImageUrl("https://howlongtobeat.com/games/Fallout_3_cover_art.PNG?width=250");
            fallout3.setMainStoryCompletionTime("22½");
            fallout3.setMainStoryPlusSideQuestsCompletionTime("53");
            fallout3.setCompletionistTime("116");
            fallout3.setAllStylesCompletionTime("47½");
            fallout3.setAnnotation("The third game in the Fallout series, " +
                    "Fallout 3 is a single player action role-playing game (RPG) set in a post-apocalyptic Washington DC. " +
                    "Combining the horrific insanity of the Cold War era theory of mutually assured destruction gone terribly wrong, " +
                    "with the kitschy naivety of American 1950s nuclear propaganda, " +
                    "Fallout 3 will satisfy both players familiar with the popular first " +
                    "two games in its series as well as those coming to the franchise for the first time.");
            fallout3.setPublisher(publishersToSeed.get(1));
            Game borderlands = new Game();
            borderlands.setTitle("Borderlands");
            borderlands.setEdition("Game of the Year Edition");
            borderlands.setYearOfRelease(2009);
            borderlands.setImageUrl("https://howlongtobeat.com/games/Borderlandscover.jpg?width=250");
            borderlands.setMainStoryCompletionTime("22½");
            borderlands.setMainStoryPlusSideQuestsCompletionTime("35");
            borderlands.setCompletionistTime("63");
            borderlands.setAllStylesCompletionTime("32½");
            borderlands.setAnnotation("A sci-fi/action RPG from acclaimed developer Gearbox, " +
                    "Borderlands combines the best in first-person action gaming " +
                    "with elements of a traditional role-playing game (RPG). " +
                    "The excitement of this hybridization is further magnified by the game's groundbreaking content " +
                    "generation system which allows for a near-endless variety in missions, environments, enemies, weapons, " +
                    "item drops and character customization, making the game's single player, " +
                    "multiplayer and online campaigns not to be missed.");
            borderlands.setPublisher(publishersToSeed.get(2));
            List<Game> gamesToSeed = new ArrayList<>();
            gamesToSeed.add(assassinsCreed);
            gamesToSeed.add(fallout3);
            gamesToSeed.add(borderlands);
            for (Game game: gamesToSeed) {
                gameService.upsertGame(game);
            }
        }
    }
}
