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

    public List<Bill> findByUser(Integer userId) {
        EntityManager em = getEntityManager();
        TypedQuery<Bill> query = em.createQuery(
                "SELECT b FROM Bill b WHERE b.user.id = :uid", Bill.class);
        query.setParameter("uid", userId);
        return query.getResultList();
    }
}