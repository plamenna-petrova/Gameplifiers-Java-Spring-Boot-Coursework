package com.javaspringcourseproject.gameplifiers.controller;

import com.javaspringcourseproject.gameplifiers.model.Game;
import com.javaspringcourseproject.gameplifiers.model.Publisher;
import com.javaspringcourseproject.gameplifiers.repository.GameRepository;
import com.javaspringcourseproject.gameplifiers.service.GameService;
import com.javaspringcourseproject.gameplifiers.service.PublisherService;
import com.javaspringcourseproject.gameplifiers.service.SecurityService;
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
import java.util.List;

@Controller
public class GameController {

    @Autowired
    GameService gameService;

    @Autowired
    PublisherService publisherService;

    @RequestMapping("/games")
    public String games(Model model, @Param("criterion") String criterion,
                        @Param("searchTerm") String searchTerm) {
        List<Game> gamesSearchResults = gameService.searchGamesByCriteria(criterion, searchTerm);

        model.addAttribute("games", gamesSearchResults);
        model.addAttribute("criterion", criterion);
        model.addAttribute("searchTerm", searchTerm);

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
        List<Publisher> listOfPublisherForSelectList = publisherService.findAllPublishers();

        model.addAttribute("game", new Game());
        model.addAttribute("listOfPublishers", listOfPublisherForSelectList);

        return "addGame";
    }

    @PostMapping("/add-game")
    public String addGame(@Valid Game game, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "addGame";
        }

        gameService.upsertGame(game);

        List<Game> gamesToLoadAfterCreation = gameService.findAllGames();
        model.addAttribute("games", gamesToLoadAfterCreation);

        return "redirect:/games";
    }

    @GetMapping("/game/edit/{id}")
    public String editGame(@PathVariable("id") Long id, Model model) {
        Game gameToEdit = gameService.findGameById(id);

        model.addAttribute("game", gameToEdit);
        return "editGame";
    }

    @PostMapping("/game/update/{id}")
    public String updateGame(@PathVariable("id") Long id, @Valid Game gameToUpdate,
                                  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            gameToUpdate.setId(id);
            return "editGame";
        }

        gameService.upsertGame(gameToUpdate);

        List<Game> gamesToLoadAfterUpdate = gameService.findAllGames();
        model.addAttribute("game", gamesToLoadAfterUpdate);

        return "redirect:/games";
    }

    @GetMapping("/game/delete/{id}")
    public String gameDeletionDetails(@PathVariable("id") Long id, Model model) {
        Game gameToDelete = gameService.findGameById(id);

        model.addAttribute("game", gameToDelete);

        return "gameDeletionDetails";
    }

    @PostMapping("/game/delete/{id}")
    public String deleteGame(@PathVariable("id") Game gameToConfirmDeletion, Model model) {
        gameService.deleteGame(gameToConfirmDeletion);

        List<Game> gamesToLoadAfterDeletion = gameService.findAllGames();

        model.addAttribute("games", gamesToLoadAfterDeletion);

        return "redirect:/games";
    }
}
