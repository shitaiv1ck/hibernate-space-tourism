package com.cinema.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Seat {

    @EmbeddedId
    private SeatId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("hallId")
    @JoinColumn(name = "hall_id")
    private Hall hall;

    @Column(nullable = false)
    private Short rowNumber;

    @Column(nullable = false)
    private Short seatType = 1;

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal priceCoefficient = BigDecimal.ONE;

    protected Seat() {}

    public Seat(short seatNumber, Hall hall, short rowNumber, short seatType, BigDecimal priceCoefficient) {
        this.id = new SeatId(seatNumber, hall.getId());
        this.hall = hall;
        this.rowNumber = rowNumber;
        this.seatType = seatType;
        this.priceCoefficient = priceCoefficient;
    }

    public SeatId getId() { return id; }
    public void setId(SeatId id) { this.id = id; }
    public Hall getHall() { return hall; }
    public void setHall(Hall hall) { this.hall = hall; }
    public Short getRowNumber() { return rowNumber; }
    public void setRowNumber(Short rowNumber) { this.rowNumber = rowNumber; }
    public Short getSeatType() { return seatType; }
    public void setSeatType(Short seatType) { this.seatType = seatType; }
    public BigDecimal getPriceCoefficient() { return priceCoefficient; }
    public void setPriceCoefficient(BigDecimal priceCoefficient) { this.priceCoefficient = priceCoefficient; }

    public String getTypeName() {
        return switch (seatType) { case 1 -> "Standard"; case 2 -> "VIP"; default -> "Unknown"; };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seat s)) return false;
        return Objects.equals(id, s.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }

    @Override
    public String toString() {
        return String.format("Seat{%s, row=%d, type=%s, coeff=%s}", id, rowNumber, getTypeName(), priceCoefficient);
    }
}
