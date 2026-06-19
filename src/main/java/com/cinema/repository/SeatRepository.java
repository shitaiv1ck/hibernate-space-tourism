package com.cinema.repository;

import com.cinema.entity.Seat;
import com.cinema.entity.SeatId;
import com.cinema.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

/**
 * Repository для места (составной ключ SeatId).
 */
public class SeatRepository extends GenericRepository<Seat, SeatId> {

    public SeatRepository() { super(Seat.class); }

    public List<Seat> findByHallId(short hallId) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Seat s WHERE s.hall.id = :hallId ORDER BY s.id.seatNumber",
                    Seat.class).setParameter("hallId", hallId).getResultList();
        }
    }

    /** Сохраняет места пачками: периодически отправляет накопленные INSERT в БД и очищает память Hibernate. */
    public int saveAll(List<Seat> seats) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            for (int i = 0; i < seats.size(); i++) {
                em.persist(seats.get(i));
                if (i > 0 && i % 25 == 0) { em.flush(); em.clear(); }
            }
            tx.commit();
            return seats.size();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
