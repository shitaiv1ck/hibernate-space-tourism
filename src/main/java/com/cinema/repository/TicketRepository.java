package com.cinema.repository;

import com.cinema.entity.*;
import com.cinema.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;

import java.util.List;

/**
 * Репозиторий для билетов.
 * Здесь собраны запросы по билетам и покупка билета в транзакции.
 */
public class TicketRepository extends GenericRepository<Ticket, Integer> {

    public TicketRepository() { super(Ticket.class); }

    public List<Ticket> findByVisitorId(int visitorId) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Ticket t JOIN FETCH t.screening JOIN FETCH t.seat " +
                            "WHERE t.visitor.id = :visitorId ORDER BY t.dateTime DESC",
                    Ticket.class).setParameter("visitorId", visitorId).getResultList();
        }
    }

    public List<Ticket> findByScreeningId(int screeningId) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Ticket t JOIN FETCH t.visitor JOIN FETCH t.seat " +
                            "WHERE t.screening.id = :screeningId ORDER BY t.seat.id.seatNumber",
                    Ticket.class).setParameter("screeningId", screeningId).getResultList();
        }
    }

    public boolean existsByScreeningAndSeat(int screeningId, short seatNumber, short hallId) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            long count = em.createQuery(
                    "SELECT COUNT(t) FROM Ticket t " +
                            "WHERE t.screening.id = :sId AND t.seat.id.seatNumber = :seat AND t.seat.id.hallId = :hall",
                    Long.class)
                    .setParameter("sId", screeningId)
                    .setParameter("seat", seatNumber)
                    .setParameter("hall", hallId)
                    .getSingleResult();
            return count > 0;
        }
    }

    /**
     * Покупает билет в транзакции.
     * Место блокируется на время проверки, чтобы два пользователя не купили его одновременно.
     */
    public int purchaseTicket(int visitorId, int screeningId, short seatNumber, short hallId) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Visitor visitor = em.find(Visitor.class, visitorId);
            Screening screening = em.find(Screening.class, screeningId);
            Seat seat = em.find(Seat.class, new SeatId(seatNumber, hallId));

            if (visitor == null || screening == null || seat == null) {
                throw new IllegalArgumentException("Visitor, screening or seat not found");
            }

            em.lock(seat, LockModeType.PESSIMISTIC_WRITE);

            long occupied = em.createQuery(
                    "SELECT COUNT(t) FROM Ticket t " +
                            "WHERE t.screening.id = :sId AND t.seat.id.seatNumber = :seat AND t.seat.id.hallId = :hall",
                    Long.class)
                    .setParameter("sId", screeningId)
                    .setParameter("seat", seatNumber)
                    .setParameter("hall", hallId)
                    .getSingleResult();

            if (occupied > 0) {
                tx.rollback();
                throw new IllegalStateException(
                        String.format("Seat %d in hall %d already taken for screening %d", seatNumber, hallId, screeningId));
            }

            Ticket ticket = new Ticket(visitor, screening, seat);
            em.persist(ticket);
            tx.commit();
            return ticket.getId();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
