package com.polycoffee.dao;

import com.polycoffee.entity.Drink;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DrinkDAO extends CrudDAO<Drink, Integer> {

    @Override
    protected Class<Drink> getEntityClass() {
        return Drink.class;
    }

    public List<Drink> findAll() {
        EntityManager em = getEntityManager();
        try {
            List<Drink> list = em.createQuery(
                    "SELECT d FROM Drink d", Drink.class).getResultList();

            System.out.println("👉 DAO size = " + list.size());
            return list;
        } finally {
            em.close();
        }
    }

    public List<Drink> findByCategory(Integer categoryId) {
        if (categoryId == null) {
            return findAll();
        }

        EntityManager em = getEntityManager();
        try {
            TypedQuery<Drink> query = em.createQuery(
                    "SELECT d FROM Drink d JOIN FETCH d.category WHERE d.category.id = :cid",
                    Drink.class);
            query.setParameter("cid", categoryId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // ================= SEARCH =================
    public List<Drink> search(String name, Integer categoryId, Boolean active, int page, int size) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT d FROM Drink d WHERE 1=1";

            if (name != null && !name.isEmpty()) {
                jpql += " AND d.name LIKE :name";
            }
            if (categoryId != null) {
                jpql += " AND d.category.id = :cid";
            }
            if (active != null) {
                jpql += " AND d.active = :active";
            }

            TypedQuery<Drink> query = em.createQuery(jpql, Drink.class);

            if (name != null && !name.isEmpty()) {
                query.setParameter("name", "%" + name + "%");
            }
            if (categoryId != null) {
                query.setParameter("cid", categoryId);
            }
            if (active != null) {
                query.setParameter("active", active);
            }

            query.setFirstResult(page * size);
            query.setMaxResults(size);

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // ================= COUNT =================
    public Long count(String name, Integer categoryId, Boolean active) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT COUNT(d) FROM Drink d WHERE 1=1";

            if (name != null && !name.isEmpty()) {
                jpql += " AND d.name LIKE :name";
            }
            if (categoryId != null) {
                jpql += " AND d.category.id = :cid";
            }
            if (active != null) {
                jpql += " AND d.active = :active";
            }

            TypedQuery<Long> query = em.createQuery(jpql, Long.class);

            if (name != null && !name.isEmpty()) {
                query.setParameter("name", "%" + name + "%");
            }
            if (categoryId != null) {
                query.setParameter("cid", categoryId);
            }
            if (active != null) {
                query.setParameter("active", active);
            }

            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}