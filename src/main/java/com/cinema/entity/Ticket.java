package com.cinema.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(
        name = "uq_ticket_screening_seat",
        columnNames = {"screening_id", "seat_number", "hall_id"}
))
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitor_id", nullable = false)
    private Visitor visitor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screening_id", nullable = false)
    private Screening screening;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "seat_number", referencedColumnName = "seat_number"),
            @JoinColumn(name = "hall_id", referencedColumnName = "hall_id")
    })
    private Seat seat;

    @Column(nullable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime dateTime;

    protected Ticket() {}

    public Ticket(Visitor visitor, Screening screening, Seat seat) {
        this.visitor = visitor;
        this.screening = screening;
        this.seat = seat;
        this.dateTime = OffsetDateTime.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Visitor getVisitor() { return visitor; }
    public void setVisitor(Visitor visitor) { this.visitor = visitor; }
    public Screening getScreening() { return screening; }
    public void setScreening(Screening screening) { this.screening = screening; }
    public Seat getSeat() { return seat; }
    public void setSeat(Seat seat) { this.seat = seat; }
    public OffsetDateTime getDateTime() { return dateTime; }
    public void setDateTime(OffsetDateTime dateTime) { this.dateTime = dateTime; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket t)) return false;
        return Objects.equals(id, t.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }

    @Override
    public String toString() {
        return String.format("Ticket{id=%d, visitor=%s, screening=%d}",
                id, visitor != null ? visitor.getName() : "?", screening != null ? screening.getId() : 0);
    }
}
