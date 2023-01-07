package com.javaspringcourseproject.gameplifiers.repository;

import com.javaspringcourseproject.gameplifiers.model.Game;
import com.javaspringcourseproject.gameplifiers.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Optional<Publisher> findById(Long id);

    List<Publisher> findByNameContainingIgnoreCase(String name);

    List<Publisher> findByHeadquartersContainingIgnoreCase(String headquarters);

    List<Publisher> findTop4ByOrderByNameAsc();
}
