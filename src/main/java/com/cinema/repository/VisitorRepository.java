package com.cinema.repository;

import com.cinema.entity.Visitor;

/**
 * Repository для посетителя, берем прям чистый CRUD из GenericRepository.
 */
public class VisitorRepository extends GenericRepository<Visitor, Integer> {
    public VisitorRepository() { super(Visitor.class); }
}
