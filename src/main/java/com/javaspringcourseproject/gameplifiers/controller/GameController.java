package com.javaspringcourseproject.gameplifiers.controller;

import com.javaspringcourseproject.gameplifiers.model.Game;
import com.javaspringcourseproject.gameplifiers.repository.GameRepository;
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
    SecurityService securityService;

    @Autowired
    GameRepository gameRepository;

    @RequestMapping("/games")
    public String games(Model model, @Param("keyword") String keyword) {
        if (!securityService.isAuthenticated()) {
            return "redirect:/login";
        }

        if (keyword != null) {
            List<Game> searchedGames = gameRepository.findGamesByKeyword(keyword);
            model.addAttribute("games", searchedGames);
        } else {
            List<Game> allGames = gameRepository.findAll();
            model.addAttribute("games", allGames);
        }

        model.addAttribute("keyword", keyword);

        return "games";
    }

    @GetMapping("/game/{id}")
    public String gameDetails(@PathVariable("id") Long id, Model model) {
        if (!securityService.isAuthenticated()) {
            return "redirect:/login";
        }

        Game singleGame = gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid game Id: " + id));

        model.addAttribute("game", singleGame);
        return "gameDetails";
    }

    @GetMapping("/add-game")
    public String addGame(Model model) {
        if (!securityService.isAuthenticated()) {
            return "redirect:/login";
        }

        model.addAttribute("game", new Game());
        return "addGame";
    }

    @PostMapping("/add-game")
    public String addGame(@Valid Game game, BindingResult bindingResult, Model model) {
        if (!securityService.isAuthenticated()) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            return "addGame";
        }

        gameRepository.save(game);

        List<Game> gamesToLoadAfterCreation = gameRepository.findAll();
        model.addAttribute("games", gamesToLoadAfterCreation);

        return "redirect:/games";
    }

    @GetMapping("/game/edit/{id}")
    public String editGame(@PathVariable("id") Long id, Model model) {
        if (!securityService.isAuthenticated()) {
            return "redirect:/login";
        }

        Game gameToEdit = gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid game Id: " + id));

        model.addAttribute("game", gameToEdit);
        return "editGame";
    }

    @PostMapping("/game/update/{id}")
    public String updateGame(@PathVariable("id") Long id, @Valid Game gameToUpdate,
                                  BindingResult bindingResult, Model model) {
        if (!securityService.isAuthenticated()) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            gameToUpdate.setId(id);
            return "editGame";
        }

        gameRepository.save(gameToUpdate);

        List<Game> gamesToLoadAfterUpdate = gameRepository.findAll();
        model.addAttribute("game", gamesToLoadAfterUpdate);

        return "redirect:/games";
    }

    @GetMapping("/game/delete/{id}")
    public String gameDeletionDetails(@PathVariable("id") Long id, Model model) {
        if (!securityService.isAuthenticated()) {
            return "redirect:/login";
        }

        Game gameToDelete = gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid game Id:" + id));

        model.addAttribute("game", gameToDelete);

        return "gameDeletionDetails";
    }

    @PostMapping("/game/delete/{id}")
    public String deleteGame(@PathVariable("id") Game gameToConfirmDeletion, Model model) {
        if (!securityService.isAuthenticated()) {
            return "redirect:/login";
        }

        gameRepository.delete(gameToConfirmDeletion);

        List<Game> gamesToLoadAfterDeletion = gameRepository.findAll();

        model.addAttribute("games", gamesToLoadAfterDeletion);

        return "redirect:/games";
    }
}
