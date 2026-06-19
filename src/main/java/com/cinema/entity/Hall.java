package com.cinema.entity;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(nullable = false, length = 24)
    private String name;

    @Column(nullable = false)
    private Short capacity;

    @Column(nullable = false)
    private Short hallType = 1;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Seat> seats = new ArrayList<>();

    protected Hall() {}

    public Hall(String name, Short capacity, Short hallType) {
        this.name = name;
        this.capacity = capacity;
        this.hallType = hallType;
    }

    public Short getId() { return id; }
    public void setId(Short id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Short getCapacity() { return capacity; }
    public void setCapacity(Short capacity) { this.capacity = capacity; }
    public Short getHallType() { return hallType; }
    public void setHallType(Short hallType) { this.hallType = hallType; }
    public List<Seat> getSeats() { return seats; }

    public String getTypeName() {
        return switch (hallType) {
            case 1 -> "2D"; case 2 -> "3D"; case 3 -> "IMAX"; default -> "Unknown";
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hall h)) return false;
        return Objects.equals(name, h.name);
    }

    @Override
    public int hashCode() { return Objects.hashCode(name); }

    @Override
    public String toString() {
        return String.format("Hall{id=%d, '%s', capacity=%d, type=%s}", id, name, capacity, getTypeName());
    }
}
