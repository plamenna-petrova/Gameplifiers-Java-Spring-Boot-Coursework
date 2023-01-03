package com.javaspringcourseproject.gameplifiers.repository;

import com.javaspringcourseproject.gameplifiers.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    @Query("SELECT p FROM Publisher p WHERE CONCAT(p.name, ' ', p.headquarters) LIKE %?1%")
    List<Publisher> findPublishersByKeyword(String keyword);
}
