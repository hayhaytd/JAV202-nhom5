package com.polycoffee.dao;

import java.util.List;

import com.polycoffee.entity.Category;

import jakarta.persistence.EntityManager;

public class CategoryDAO extends CrudDAO<Category, Integer> {

    @Override
    protected Class<Category> getEntityClass() {
        return Category.class;
    }

    public List<Category> findAll() {
    EntityManager em = getEntityManager();
    try {
        return em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
    } finally {
        em.close();
    }
}
}