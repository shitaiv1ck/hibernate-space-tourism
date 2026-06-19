package com.cinema.entity;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.*;

@Entity
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 32)
    private String title;

    @Column(nullable = false)
    private LocalTime duration;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "film_genre",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    protected Film() {}

    public Film(String title, LocalTime duration) {
        this.title = title;
        this.duration = duration;
    }

    public void addGenre(Genre genre) { genres.add(genre); }
    public void removeGenre(Genre genre) { genres.remove(genre); }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public LocalTime getDuration() { return duration; }
    public void setDuration(LocalTime duration) { this.duration = duration; }
    public Set<Genre> getGenres() { return genres; }
    public void setGenres(Set<Genre> genres) { this.genres = genres; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film f)) return false;
        return Objects.equals(title, f.title);
    }

    @Override
    public int hashCode() { return Objects.hashCode(title); }

    @Override
    public String toString() { return String.format("Film{id=%d, '%s', %s}", id, title, duration); }
}
