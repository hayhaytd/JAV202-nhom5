package com.polycoffee.dao;

import java.util.List;

import com.polycoffee.entity.User;
import com.polycoffee.utils.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class UserDAO extends CrudDAO<User, Integer> {

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    // ================= FIND BY EMAIL =================
    public User findByEmail(String email) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);

            return query.getResultList().stream().findFirst().orElse(null);

        } finally {
            em.close();
        }
    }

    // ================= LOGIN =================
    public User Login(String email, String password) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE u.email = :e AND u.password = :p";

            return em.createQuery(jpql, User.class)
                    .setParameter("e", email)
                    .setParameter("p", password)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    // ================= SEARCH + PAGINATION =================
    public List<User> search(String fullname, String email, Boolean active, int page, int size) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE 1=1";

            if (fullname != null && !fullname.isEmpty()) {
                jpql += " AND u.fullname LIKE :fullname";
            }
            if (email != null && !email.isEmpty()) {
                jpql += " AND u.email LIKE :email";
            }
            if (active != null) {
                jpql += " AND u.active = :active";
            }

            jpql += " ORDER BY u.id DESC";

            TypedQuery<User> query = em.createQuery(jpql, User.class);

            if (fullname != null && !fullname.isEmpty()) {
                query.setParameter("fullname", "%" + fullname + "%");
            }
            if (email != null && !email.isEmpty()) {
                query.setParameter("email", "%" + email + "%");
            }
            if (active != null) {
                query.setParameter("active", active);
            }

            // FIX pagination
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);

            return query.getResultList();

        } finally {
            em.close();
        }
    }

    // ================= COUNT =================
    public Long count(String fullname, String email, Boolean active) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT COUNT(u) FROM User u WHERE 1=1";

            if (fullname != null && !fullname.isEmpty()) {
                jpql += " AND u.fullname LIKE :fullname";
            }
            if (email != null && !email.isEmpty()) {
                jpql += " AND u.email LIKE :email";
            }
            if (active != null) {
                jpql += " AND u.active = :active";
            }

            TypedQuery<Long> query = em.createQuery(jpql, Long.class);

            if (fullname != null && !fullname.isEmpty()) {
                query.setParameter("fullname", "%" + fullname + "%");
            }
            if (email != null && !email.isEmpty()) {
                query.setParameter("email", "%" + email + "%");
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
    public int getTotalPage(String fullname, String email, Boolean active, int size) {
        long total = count(fullname, email, active);
        return (int) Math.ceil((double) total / size);
    }
}