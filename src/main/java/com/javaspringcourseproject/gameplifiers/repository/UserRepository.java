package com.javaspringcourseproject.gameplifiers.repository;

import com.javaspringcourseproject.gameplifiers.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
