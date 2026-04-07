package com.polycoffee.dao;

import com.polycoffee.entity.BillDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class BillDetailDAO extends CrudDAO<BillDetail, Integer> {

    @Override
    protected Class<BillDetail> getEntityClass() {
        return BillDetail.class;
    }

    // ================= FIND BY BILL =================
    public List<BillDetail> findByBill(Integer billId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<BillDetail> query = em.createQuery(
                    "SELECT bd FROM BillDetail bd WHERE bd.bill.id = :bid", BillDetail.class);
            query.setParameter("bid", billId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}