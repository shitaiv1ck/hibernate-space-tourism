package com.cinema.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 24)
    private String name;

    protected Genre() {}

    public Genre(String name) { this.name = name; }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre g)) return false;
        return Objects.equals(name, g.name);
    }

    @Override
    public int hashCode() { return Objects.hashCode(name); }

    @Override
    public String toString() { return String.format("Genre{id=%d, '%s'}", id, name); }
}
