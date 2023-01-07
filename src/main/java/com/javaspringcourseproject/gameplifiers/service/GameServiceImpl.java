package com.javaspringcourseproject.gameplifiers.service;

import com.javaspringcourseproject.gameplifiers.model.Game;
import com.javaspringcourseproject.gameplifiers.repository.GameRepository;
import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    public List<Game> findAllGames() {
        return gameRepository.findAll();
    }

    public Game findGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid game Id: " + id));
    }

    public void upsertGame(Game game) {
        gameRepository.save(game);
    }

    public void deleteGame(Game game) {
        gameRepository.delete(game);
    }

    public List<Game> searchGamesByCriteria(@Nullable String criterion, @Nullable String searchTerm) {
        List<Game> gamesToSearchByCriteria = new ArrayList<>();

        if (criterion != null) {
            switch (criterion) {
                case "title":
                    gamesToSearchByCriteria = gameRepository
                            .findByTitleContainingIgnoreCase(searchTerm);
                    break;
                case "edition":
                    gamesToSearchByCriteria = gameRepository
                            .findByEditionContainingIgnoreCase(searchTerm);
                    break;
                case "yearOfRelease":
                    gamesToSearchByCriteria = gameRepository
                            .findByYearOfRelease(Integer.parseInt(searchTerm));
                    break;
                case "publisher":
                    gamesToSearchByCriteria = gameRepository
                            .findByPublisherNameContainingIgnoreCase(searchTerm);
            }
        } else {
            gamesToSearchByCriteria = this.findAllGames();
        }

        return gamesToSearchByCriteria;
    }

    public List<Game> findTopFourGames() {
        return gameRepository.findTop4ByOrderByTitleAsc();
    }
}
