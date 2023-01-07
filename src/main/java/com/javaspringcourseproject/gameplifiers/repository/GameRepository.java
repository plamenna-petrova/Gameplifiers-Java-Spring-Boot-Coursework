package com.javaspringcourseproject.gameplifiers.repository;

import com.javaspringcourseproject.gameplifiers.model.Game;
import com.javaspringcourseproject.gameplifiers.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findById(Long id);

    List<Game> findByTitleContainingIgnoreCase(String title);

    List<Game> findByYearOfRelease(int yearOfRelease);

    List<Game> findByEditionContainingIgnoreCase(String version);

    List<Game> findByPublisherNameContainingIgnoreCase(String publisherName);

    List<Game> findTop4ByOrderByTitleAsc();
}
