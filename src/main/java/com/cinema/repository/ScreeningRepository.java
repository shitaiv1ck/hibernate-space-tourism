package com.cinema.repository;

import com.cinema.entity.Screening;
import com.cinema.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public class ScreeningRepository extends GenericRepository<Screening, Integer> {

    public ScreeningRepository() { super(Screening.class); }

    public List<Screening> findByDateTimeBetween(OffsetDateTime from, OffsetDateTime to) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Screening s WHERE s.dateTime BETWEEN :from AND :to ORDER BY s.dateTime",
                    Screening.class).setParameter("from", from).setParameter("to", to).getResultList();
        }
    }

    public List<Screening> findByFilmIdWithDetails(int filmId) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Screening s JOIN FETCH s.film JOIN FETCH s.hall WHERE s.film.id = :filmId ORDER BY s.dateTime",
                    Screening.class).setParameter("filmId", filmId).getResultList();
        }
    }

    public Optional<Screening> findByIdWithDetails(int id) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Screening> result = em.createQuery(
                    "FROM Screening s JOIN FETCH s.film JOIN FETCH s.hall WHERE s.id = :id",
                    Screening.class).setParameter("id", id).getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        }
    }
}
