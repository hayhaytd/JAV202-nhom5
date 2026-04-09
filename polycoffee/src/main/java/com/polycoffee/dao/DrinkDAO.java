package com.polycoffee.dao;

import com.polycoffee.entity.Drink;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.Query;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DrinkDAO extends CrudDAO<Drink, Integer> {

    @Override
    protected Class<Drink> getEntityClass() {
        return Drink.class;
    }

    // ================= FIND ALL =================
    public List<Drink> findAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT d FROM Drink d", Drink.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // ================= FIND BY ID =================
    public Drink findById(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Drink.class, id);
        } finally {
            em.close();
        }
    }

    // ================= CREATE =================
    public void create(Drink entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ================= UPDATE =================
    public void update(Drink entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ================= DELETE =================
    public void delete(Integer id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            Drink entity = em.find(Drink.class, id);
            if (entity != null) {
                em.remove(entity);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ================= SOFT DELETE (OPTIONAL) =================
    public void deleteSoft(Integer id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            Drink d = em.find(Drink.class, id);
            if (d != null) {
                d.setActive(false);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ================= FIND BY CATEGORY =================
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

    // ================= SEARCH + PAGINATION =================
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

            jpql += " ORDER BY d.id ASC";

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

            // FIX pagination chuẩn
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // ================= COUNT =================
    public long count(String name, Integer categoryId, Boolean active) {
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

    // ================= TOTAL PAGE =================
    public int getTotalPage(String name, Integer categoryId, Boolean active, int size) {
        long total = count(name, categoryId, active);
        return (int) Math.ceil((double) total / size);
    }

    // ================= TOP 5 DRINK (JPA) =================
    public List<Object[]> getTop5Drinks(Date from, Date to) {
        EntityManager em = getEntityManager();

        try {
            String jpql = """
                        SELECT d.id, d.name, SUM(bd.quantity)
                        FROM BillDetail bd
                        JOIN bd.drink d
                        JOIN bd.bill b
                        WHERE b.created_at BETWEEN :from AND :to
                          AND d.active = true
                          AND b.status = 1
                        GROUP BY d.id, d.name
                        ORDER BY SUM(bd.quantity) DESC
                    """;
            ;
            ;
            ;

            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);

            query.setParameter("from", from);
            query.setParameter("to", to);
            query.setMaxResults(5);

            return query.getResultList();

        } finally {
            em.close();
        }
    }
}