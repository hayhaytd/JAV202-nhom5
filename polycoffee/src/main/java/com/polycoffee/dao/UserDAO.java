package com.polycoffee.dao;


import com.polycoffee.entity.User;
import com.polycoffee.utils.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class UserDAO extends CrudDAO<User, Integer> {

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    public User findByEmail(String email) {
        EntityManager em = getEntityManager();
        try {
            System.out.println("👉 Find user by email: " + email);

            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);

            return query.getResultList().stream().findFirst().orElse(null);

        } finally {
            em.close();
        }
    }

    public User Login(String email, String password) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE u.email = :e AND u.password = :p";
            return em.createQuery(jpql, User.class).setParameter("e", email).setParameter("p", password)
                    .getSingleResult();
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
}