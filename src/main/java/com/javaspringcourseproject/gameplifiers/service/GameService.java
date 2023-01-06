package com.javaspringcourseproject.gameplifiers.service;

import com.javaspringcourseproject.gameplifiers.model.Game;
import com.sun.istack.Nullable;

import java.util.List;

public interface GameService {
    List<Game> findAllGames();

    Game findGameById(Long id);

    void upsertGame(Game game);

    void deleteGame(Game game);

    List<Game> searchGamesByCriteria(@Nullable String criterion, @Nullable String searchTerm);
}
