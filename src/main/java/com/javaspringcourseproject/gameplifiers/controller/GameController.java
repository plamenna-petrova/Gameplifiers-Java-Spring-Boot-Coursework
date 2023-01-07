package com.javaspringcourseproject.gameplifiers.controller;

import com.javaspringcourseproject.gameplifiers.model.Game;
import com.javaspringcourseproject.gameplifiers.model.Publisher;
import com.javaspringcourseproject.gameplifiers.service.GameService;
import com.javaspringcourseproject.gameplifiers.service.PublisherService;
import com.javaspringcourseproject.gameplifiers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.Year;
import java.util.List;

@Controller
public class GameController {

    @Autowired
    GameService gameService;

    @Autowired
    PublisherService publisherService;

    @Autowired
    UserService userService;

    @RequestMapping("/games")
    public String games(Model model, @Param("criterion") String criterion,
                        @Param("searchTerm") String searchTerm) {
        List<Game> gamesSearchResults = gameService.searchGamesByCriteria(criterion, searchTerm);

        model.addAttribute("games", gamesSearchResults);
        model.addAttribute("criterion", criterion);
        model.addAttribute("searchTerm", searchTerm);

        if (userService.checkCurrentUserAdministrativePrivileges()) {
            model.addAttribute("privileges", "superuser");
        } else {
            model.addAttribute("privileges", "user");
        }

        return "games";
    }

    @GetMapping("/game/{id}")
    public String gameDetails(@PathVariable("id") Long id, Model model) {
        Game singleGame = gameService.findGameById(id);

        model.addAttribute("game", singleGame);

        return "gameDetails";
    }

    @GetMapping("/add-game")
    public String addGame(Model model) {
        if (!userService.checkCurrentUserAdministrativePrivileges()) {
            return "redirect:/welcome";
        }

        List<Publisher> listOfPublisherForCreationSelectList = publisherService.findAllPublishers();

        Game blankGame = new Game();
        blankGame.setYearOfRelease(Year.now().getValue());

        model.addAttribute("game", blankGame);
        model.addAttribute("listOfPublishers", listOfPublisherForCreationSelectList);

        return "addGame";
    }

    @PostMapping("/add-game")
    public String addGame(@Valid Game game, BindingResult bindingResult, Model model) {
        if (!userService.checkCurrentUserAdministrativePrivileges()) {
            return "redirect:/welcome";
        }

        if (bindingResult.hasErrors()) {
            List<Publisher> listOfPublisherForCreationSelectList = publisherService.findAllPublishers();
            model.addAttribute("listOfPublishers", listOfPublisherForCreationSelectList);
            return "addGame";
        }

        gameService.upsertGame(game);

        List<Game> gamesToLoadAfterCreation = gameService.findAllGames();
        model.addAttribute("games", gamesToLoadAfterCreation);

        return "redirect:/games";
    }

    @GetMapping("/game/edit/{id}")
    public String editGame(@PathVariable("id") Long id, Model model) {
        if (!userService.checkCurrentUserAdministrativePrivileges()) {
            return "redirect:/welcome";
        }

        List<Publisher> listOfPublisherForEditSelectList = publisherService.findAllPublishers();

        Game gameToEdit = gameService.findGameById(id);

        model.addAttribute("game", gameToEdit);
        model.addAttribute("listOfPublishers", listOfPublisherForEditSelectList);

        return "editGame";
    }

    @PostMapping("/game/update/{id}")
    public String updateGame(@PathVariable("id") Long id, @Valid Game gameToUpdate,
                                  BindingResult bindingResult, Model model) {
        if (!userService.checkCurrentUserAdministrativePrivileges()) {
            return "redirect:/welcome";
        }

        if (bindingResult.hasErrors()) {
            gameToUpdate.setId(id);
            List<Publisher> listOfPublisherForEditSelectList = publisherService.findAllPublishers();
            model.addAttribute("listOfPublishers", listOfPublisherForEditSelectList);
            return "editGame";
        }

        gameService.upsertGame(gameToUpdate);

        List<Game> gamesToLoadAfterUpdate = gameService.findAllGames();
        model.addAttribute("game", gamesToLoadAfterUpdate);

        return "redirect:/games";
    }

    @GetMapping("/game/delete/{id}")
    public String gameDeletionDetails(@PathVariable("id") Long id, Model model) {
        if (!userService.checkCurrentUserAdministrativePrivileges()) {
            return "redirect:/welcome";
        }

        Game gameToDelete = gameService.findGameById(id);

        model.addAttribute("game", gameToDelete);

        return "gameDeletionDetails";
    }

    @PostMapping("/game/delete/{id}")
    public String deleteGame(@PathVariable("id") Game gameToConfirmDeletion, Model model) {
        if (!userService.checkCurrentUserAdministrativePrivileges()) {
            return "redirect:/welcome";
        }

        gameService.deleteGame(gameToConfirmDeletion);

        List<Game> gamesToLoadAfterDeletion = gameService.findAllGames();

        model.addAttribute("games", gamesToLoadAfterDeletion);

        return "redirect:/games";
    }
}
