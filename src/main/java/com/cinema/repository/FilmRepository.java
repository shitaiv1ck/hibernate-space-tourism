package com.cinema.repository;

import com.cinema.entity.Film;
import com.cinema.entity.Genre;
import com.cinema.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

/*
 * Repository для Фильмов
 */
public class FilmRepository extends GenericRepository<Film, Integer> {

    public FilmRepository() { super(Film.class); }

    /* Все фильмы с жанрами за 1 запрос */
    public List<Film> findAllWithGenres() {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "SELECT DISTINCT f FROM Film f LEFT JOIN FETCH f.genres ORDER BY f.id",
                    Film.class).getResultList();
        }
    }

    /* Один фильм с жанрами */
    public Optional<Film> findByIdWithGenres(int id) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Film> result = em.createQuery(
                    "SELECT f FROM Film f LEFT JOIN FETCH f.genres WHERE f.id = :id",
                    Film.class).setParameter("id", id).getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        }
    }

    /* Добавляем жанр через film.getGenres().add(genre), а Hibernate сам генерирует INSERT. */
    public void addGenre(int filmId, int genreId) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Film film = em.find(Film.class, filmId);
            Genre genre = em.find(Genre.class, genreId);
            if (film != null && genre != null) film.addGenre(genre);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /* Удалить жанр */
    public void removeGenre(int filmId, int genreId) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Film film = em.find(Film.class, filmId);
            Genre genre = em.find(Genre.class, genreId);
            if (film != null && genre != null) film.removeGenre(genre);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
