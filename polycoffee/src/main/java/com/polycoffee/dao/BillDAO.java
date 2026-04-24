package com.polycoffee.dao;

import com.polycoffee.entity.Bill;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;

public class BillDAO extends CrudDAO<Bill, Integer> {

    @Override
    protected Class<Bill> getEntityClass() {
        return Bill.class;
    }

    // ================= CREATE =================
    public void create(Bill bill) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(bill);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ================= FIND =================
    @Override
    public Bill findById(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bill.class, id);
        } finally {
            em.close();
        }
    }

    public List<Bill> findAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT b FROM Bill b ORDER BY b.created_at DESC",
                    Bill.class).getResultList();
        } finally {
            em.close();
        }
    }

    // ================= DASHBOARD =================

    // 🔥 Doanh thu hôm nay
    public double getTodayRevenue(Date today) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT COALESCE(SUM(b.total), 0) " +
                    "FROM Bill b " +
                    "WHERE FUNCTION('DATE', b.created_at) = FUNCTION('DATE', :today) " +
                    "AND b.status = 1",
                    Double.class)
                    .setParameter("today", today)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    // 🔥 Số đơn hôm nay
    public int countTodayBills(Date today) {
        EntityManager em = getEntityManager();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(b) FROM Bill b " +
                    "WHERE FUNCTION('DATE', b.created_at) = FUNCTION('DATE', :today)",
                    Long.class)
                    .setParameter("today", today)
                    .getSingleResult();
            return count.intValue();
        } finally {
            em.close();
        }
    }

    // 🔥 Đếm theo status (0: chưa thanh toán, 1: đã thanh toán, -1: huỷ)
    public int countByStatus(int status) {
        EntityManager em = getEntityManager();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(b) FROM Bill b WHERE b.status = :st",
                    Long.class)
                    .setParameter("st", status)
                    .getSingleResult();
            return count.intValue();
        } finally {
            em.close();
        }
    }

    // ================= FILTER =================
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

    public List<Bill> findUnpaid() {
        return findByStatus(0);
    }

    public List<Bill> findPaid() {
        return findByStatus(1);
    }

    public List<Bill> findByStatus(int status) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT b FROM Bill b WHERE b.status = :st ORDER BY b.created_at DESC",
                    Bill.class)
                    .setParameter("st", status)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // ================= PAGINATION =================
    public List<Bill> findAllPaged(int page, int size) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT b FROM Bill b ORDER BY b.created_at DESC",
                    Bill.class)
                    .setFirstResult((page - 1) * size)
                    .setMaxResults(size)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // ================= COUNT =================
    public long countAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT COUNT(b) FROM Bill b",
                    Long.class).getSingleResult();
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

    // ================= DELETE =================
    public void delete(Integer id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Bill bill = em.find(Bill.class, id);
            if (bill != null) {
                em.remove(bill);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ================= CODE =================

    // ❌ bản cũ: load hết DB → chậm
    // ✅ bản mới: dùng MAX trực tiếp
    public int getMaxBillNumber() {
        EntityManager em = getEntityManager();
        try {
            String maxCode = em.createQuery(
                    "SELECT MAX(b.code) FROM Bill b",
                    String.class).getSingleResult();

            if (maxCode == null) return 0;

            return Integer.parseInt(maxCode.replaceAll("[^0-9]", ""));
        } finally {
            em.close();
        }
    }

    public String generateBillCode() {
        int next = getMaxBillNumber() + 1;
        return String.format("HD%03d", next);
    }
}