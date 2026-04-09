package com.polycoffee.dao;

import jakarta.persistence.EntityManager;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.polycoffee.utils.JPAUtil;

public class RevenueDAO {

    // 📅 Theo khoảng ngày (FIX chuẩn)
    public List<Object[]> getRevenueByDate(Date from, Date to) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // 🔥 FIX: lấy hết ngày "to"
            Calendar cal = Calendar.getInstance();
            cal.setTime(to);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            Date endOfDay = cal.getTime();

            String jpql = """
                SELECT b.created_at, SUM(b.total)
                FROM Bill b
                WHERE b.status = 1
                AND b.created_at BETWEEN :from AND :to
                GROUP BY b.created_at
                ORDER BY b.created_at
            """;

            return em.createQuery(jpql, Object[].class)
                    .setParameter("from", from)
                    .setParameter("to", endOfDay)
                    .getResultList();

        } finally {
            em.close(); // 🔥 BẮT BUỘC
        }
    }

    // 📆 Theo tháng
    public List<Object[]> getRevenueByMonth(int year) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = """
                SELECT FUNCTION('MONTH', b.created_at), SUM(b.total)
                FROM Bill b
                WHERE b.status = 1
                AND FUNCTION('YEAR', b.created_at) = :year
                GROUP BY FUNCTION('MONTH', b.created_at)
                ORDER BY FUNCTION('MONTH', b.created_at)
            """;

            return em.createQuery(jpql, Object[].class)
                    .setParameter("year", year)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    // 📊 Theo năm
    public List<Object[]> getRevenueByYear() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = """
                SELECT FUNCTION('YEAR', b.created_at), SUM(b.total)
                FROM Bill b
                WHERE b.status = 1
                GROUP BY FUNCTION('YEAR', b.created_at)
                ORDER BY FUNCTION('YEAR', b.created_at)
            """;

            return em.createQuery(jpql, Object[].class)
                    .getResultList();

        } finally {
            em.close();
        }
    }
}