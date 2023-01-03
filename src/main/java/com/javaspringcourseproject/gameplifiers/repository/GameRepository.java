package com.javaspringcourseproject.gameplifiers.repository;

import com.javaspringcourseproject.gameplifiers.model.Game;
import com.javaspringcourseproject.gameplifiers.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("SELECT g FROM Game g WHERE CONCAT(g.title, ' ', g.yearOfRelease, '', g.publisher.name) LIKE %?1%")
    List<Game> findGamesByKeyword(String keyword);
}
