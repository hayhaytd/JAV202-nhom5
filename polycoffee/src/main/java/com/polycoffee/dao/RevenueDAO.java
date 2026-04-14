package com.polycoffee.dao;

import jakarta.persistence.EntityManager;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.polycoffee.utils.JPAUtil;

public class RevenueDAO {

    // ================= COMMON =================
    private Date getEndOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    // ================= 1. DASHBOARD =================
    public Object[] getDashboardSummary(Date from, Date to) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = """
                SELECT COUNT(b.id), SUM(b.total), AVG(b.total)
                FROM Bill b
                WHERE b.status = 1
                AND b.created_at BETWEEN :from AND :to
            """;

            return em.createQuery(jpql, Object[].class)
                    .setParameter("from", from)
                    .setParameter("to", getEndOfDay(to))
                    .getSingleResult();

        } finally {
            em.close();
        }
    }

    // ================= 2. DOANH THU THEO NGÀY =================
    public List<Object[]> getRevenueByDate(Date from, Date to) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = """
                SELECT CAST(b.created_at AS date), SUM(b.total)
                FROM Bill b
                WHERE b.status = 1
                AND b.created_at BETWEEN :from AND :to
                GROUP BY CAST(b.created_at AS date)
                ORDER BY CAST(b.created_at AS date)
            """;

            return em.createQuery(jpql, Object[].class)
                    .setParameter("from", from)
                    .setParameter("to", getEndOfDay(to))
                    .getResultList();

        } finally {
            em.close();
        }
    }

    // ================= 3. DOANH THU THEO THÁNG =================
    public List<Object[]> getRevenueByMonth(int year) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = """
                SELECT MONTH(b.created_at), SUM(b.total)
                FROM Bill b
                WHERE b.status = 1
                AND YEAR(b.created_at) = :year
                GROUP BY MONTH(b.created_at)
                ORDER BY MONTH(b.created_at)
            """;

            return em.createQuery(jpql, Object[].class)
                    .setParameter("year", year)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    // ================= 4. DOANH THU THEO NĂM =================
    public List<Object[]> getRevenueByYear() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = """
                SELECT YEAR(b.created_at), SUM(b.total)
                FROM Bill b
                WHERE b.status = 1
                GROUP BY YEAR(b.created_at)
                ORDER BY YEAR(b.created_at)
            """;

            return em.createQuery(jpql, Object[].class)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    // ================= 5. TOP ĐỒ UỐNG =================
    public List<Object[]> getTopDrinks(Date from, Date to) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = """
                SELECT d.name, SUM(bd.quantity), SUM(bd.price * bd.quantity)
                FROM BillDetail bd
                JOIN bd.bill b
                JOIN bd.drink d
                WHERE b.status = 1
                AND b.created_at BETWEEN :from AND :to
                GROUP BY d.name
                ORDER BY SUM(bd.quantity) DESC
            """;

            return em.createQuery(jpql, Object[].class)
                    .setParameter("from", from)
                    .setParameter("to", getEndOfDay(to))
                    .setMaxResults(5)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    // ================= 6. DOANH THU THEO NHÂN VIÊN =================
    public List<Object[]> getRevenueByStaff(Date from, Date to) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = """
                SELECT u.fullname, SUM(b.total)
                FROM Bill b
                JOIN b.user u
                WHERE b.status = 1
                AND b.created_at BETWEEN :from AND :to
                GROUP BY u.fullname
                ORDER BY SUM(b.total) DESC
            """;

            return em.createQuery(jpql, Object[].class)
                    .setParameter("from", from)
                    .setParameter("to", getEndOfDay(to))
                    .getResultList();

        } finally {
            em.close();
        }
    }

    // ================= 7. DOANH THU THEO GIỜ =================
    public List<Object[]> getRevenueByHour(Date from, Date to) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = """
                SELECT HOUR(b.created_at), SUM(b.total)
                FROM Bill b
                WHERE b.status = 1
                AND b.created_at BETWEEN :from AND :to
                GROUP BY HOUR(b.created_at)
                ORDER BY HOUR(b.created_at)
            """;

            return em.createQuery(jpql, Object[].class)
                    .setParameter("from", from)
                    .setParameter("to", getEndOfDay(to))
                    .getResultList();

        } finally {
            em.close();
        }
    }

    // ================= 8. DOANH THU THEO LOẠI =================
    public List<Object[]> getRevenueByCategory(Date from, Date to) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = """
                SELECT c.name, SUM(bd.price * bd.quantity)
                FROM BillDetail bd
                JOIN bd.bill b
                JOIN bd.drink d
                JOIN d.category c
                WHERE b.status = 1
                AND b.created_at BETWEEN :from AND :to
                GROUP BY c.name
                ORDER BY SUM(bd.price * bd.quantity) DESC
            """;

            return em.createQuery(jpql, Object[].class)
                    .setParameter("from", from)
                    .setParameter("to", getEndOfDay(to))
                    .getResultList();

        } finally {
            em.close();
        }
    }
}