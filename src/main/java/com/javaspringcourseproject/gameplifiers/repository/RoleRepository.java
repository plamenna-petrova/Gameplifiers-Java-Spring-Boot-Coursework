package com.javaspringcourseproject.gameplifiers.repository;

import com.javaspringcourseproject.gameplifiers.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
