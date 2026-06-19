package com.cinema.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SeatId implements Serializable {

    @Column(name = "seat_number")
    private Short seatNumber;

    @Column(name = "hall_id")
    private Short hallId;

    protected SeatId() {}

    public SeatId(short seatNumber, short hallId) {
        this.seatNumber = seatNumber;
        this.hallId = hallId;
    }

    public Short getSeatNumber() { return seatNumber; }
    public void setSeatNumber(Short seatNumber) { this.seatNumber = seatNumber; }
    public Short getHallId() { return hallId; }
    public void setHallId(Short hallId) { this.hallId = hallId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeatId s)) return false;
        return Objects.equals(seatNumber, s.seatNumber) && Objects.equals(hallId, s.hallId);
    }

    @Override
    public int hashCode() { return Objects.hash(seatNumber, hallId); }

    @Override
    public String toString() { return String.format("SeatId{seat=%d, hall=%d}", seatNumber, hallId); }
}
