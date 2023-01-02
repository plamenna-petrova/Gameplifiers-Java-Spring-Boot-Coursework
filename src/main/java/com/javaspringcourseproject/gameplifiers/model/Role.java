package com.javaspringcourseproject.gameplifiers.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    public Role() {

    }

    public Role(String name) {
        this.name = name;
    }

    @ManyToMany(fetch = FetchType.LAZY,
            cascade =  {
              CascadeType.PERSIST, CascadeType.MERGE
            },
            mappedBy = "roles"
    )
    private Set<User> users = new HashSet<>();

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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
