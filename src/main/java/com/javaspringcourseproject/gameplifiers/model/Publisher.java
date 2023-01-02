package com.javaspringcourseproject.gameplifiers.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The publisher's name is mandatory!")
    @Size(min = 2, max = 50, message = "The name of the games publisher must be between 2 and 50 symbols long!")
    private String name;

    @NotBlank(message = "Please provide headquarters for the publisher!")
    @Size(min = 2, max = 30, message = "The headquarters of the publisher must be between 2 and 50 symbols long!")
    private String headquarters;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    private Set<Game> games = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;

        for (Game game: games) {
            game.setPublisher(this);
        }
    }
}
