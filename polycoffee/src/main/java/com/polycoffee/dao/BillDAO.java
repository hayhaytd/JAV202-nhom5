package com.polycoffee.dao;

import com.polycoffee.entity.Bill;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class BillDAO extends CrudDAO<Bill, Integer> {

    @Override
    protected Class<Bill> getEntityClass() {
        return Bill.class;
    }

    // ================= FIND BY ID =================
    @Override
    public Bill findById(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bill.class, id);
        } finally {
            em.close();
        }
    }

    // ================= FIND BY USER =================
    public List<Bill> findByUser(Integer userId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Bill> query = em.createQuery(
                    "SELECT b FROM Bill b WHERE b.user.id = :uid ORDER BY b.created_at DESC",
                    Bill.class);
            query.setParameter("uid", userId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // ================= FIND ALL PAGED =================
    public List<Bill> findAllPaged(int page, int size) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Bill> query = em.createQuery(
                    "SELECT b FROM Bill b ORDER BY b.created_at DESC", Bill.class);
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // ================= COUNT ALL =================
    public long countAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(b) FROM Bill b", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    // ================= UPDATE =================
    public void update(Bill bill) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(bill);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}