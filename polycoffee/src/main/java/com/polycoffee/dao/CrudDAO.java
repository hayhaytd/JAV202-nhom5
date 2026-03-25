package com.polycoffee.dao;

import com.polycoffee.utils.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public abstract class CrudDAO<T, ID> {

    protected abstract Class<T> getEntityClass();

    protected EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public List<T> findAll() {
        EntityManager em = getEntityManager();
        return em.createQuery("FROM " + getEntityClass().getSimpleName(), getEntityClass())
                .getResultList();
    }

    public T findById(ID id) {
        EntityManager em = getEntityManager();
        return em.find(getEntityClass(), id);
    }

    public void create(T entity) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    public void update(T entity) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    public void delete(ID id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        T entity = em.find(getEntityClass(), id);
        if (entity != null) {
            em.remove(entity);
        }
        em.getTransaction().commit();
    }
}