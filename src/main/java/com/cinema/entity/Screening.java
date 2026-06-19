package com.cinema.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    @Column(nullable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime dateTime;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;

    protected Screening() {}

    public Screening(Hall hall, Film film, OffsetDateTime dateTime, BigDecimal basePrice) {
        this.hall = hall;
        this.film = film;
        this.dateTime = dateTime;
        this.basePrice = basePrice;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Hall getHall() { return hall; }
    public void setHall(Hall hall) { this.hall = hall; }
    public Film getFilm() { return film; }
    public void setFilm(Film film) { this.film = film; }
    public OffsetDateTime getDateTime() { return dateTime; }
    public void setDateTime(OffsetDateTime dateTime) { this.dateTime = dateTime; }
    public BigDecimal getBasePrice() { return basePrice; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Screening s)) return false;
        return Objects.equals(id, s.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }

    @Override
    public String toString() {
        return String.format("Screening{id=%d, %s, price=%s}", id, dateTime, basePrice);
    }
}
