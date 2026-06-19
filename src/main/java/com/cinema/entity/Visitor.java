package com.cinema.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 32)
    private String name;

    @Column(length = 16)
    private String phone;

    @Column(length = 32)
    private String email;

    protected Visitor() {}

    public Visitor(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Visitor v)) return false;
        return Objects.equals(email, v.email);
    }

    @Override
    public int hashCode() { return Objects.hashCode(email); }

    @Override
    public String toString() {
        return String.format("Visitor{id=%d, '%s', phone=%s, email=%s}", id, name, phone, email);
    }
}
