package com.polycoffee.dao;

import com.polycoffee.entity.Bill;
import com.polycoffee.entity.BillDetail;
import com.polycoffee.entity.Drink;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class BillDetailDAO extends CrudDAO<BillDetail, Integer> {

    @Override
    protected Class<BillDetail> getEntityClass() {
        return BillDetail.class;
    }

    // ================= CREATE =================
    public void create(BillDetail bd) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(bd);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ================= UPDATE =================
    public void update(BillDetail bd) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(bd);
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
            BillDetail bd = em.find(BillDetail.class, id);
            if (bd != null) {
                em.remove(bd);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ================= FIND BY ID =================
    @Override
    public BillDetail findById(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BillDetail.class, id);
        } finally {
            em.close();
        }
    }

    // ================= FIND BY BILL =================
    public List<BillDetail> findByBill(Integer billId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<BillDetail> query = em.createQuery(
                    "SELECT bd FROM BillDetail bd " +
                            "WHERE bd.bill.id = :bid",
                    BillDetail.class);
            query.setParameter("bid", billId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // ================= FIND BY BILL + DRINK =================
    public BillDetail findByBillAndDrink(Integer billId, Integer drinkId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<BillDetail> query = em.createQuery(
                    "SELECT bd FROM BillDetail bd " +
                            "WHERE bd.bill.id = :bid AND bd.drink.id = :did",
                    BillDetail.class);
            query.setParameter("bid", billId);
            query.setParameter("did", drinkId);

            List<BillDetail> list = query.getResultList();
            return list.isEmpty() ? null : list.get(0);

        } finally {
            em.close();
        }
    }

    // ================= ADD OR UPDATE (QUAN TRỌNG) =================
    public void addDrinkToBill(Bill bill, Drink drink) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            // check tồn tại
            TypedQuery<BillDetail> query = em.createQuery(
                    "SELECT bd FROM BillDetail bd WHERE bd.bill.id = :bid AND bd.drink.id = :did",
                    BillDetail.class);
            query.setParameter("bid", bill.getId());
            query.setParameter("did", drink.getId());

            List<BillDetail> list = query.getResultList();

            if (!list.isEmpty()) {
                // đã có → tăng số lượng
                BillDetail bd = list.get(0);
                bd.setQuantity(bd.getQuantity() + 1);
                em.merge(bd);
            } else {
                // chưa có → tạo mới
                BillDetail bd = new BillDetail();
                bd.setBill(bill);
                bd.setDrink(drink);
                bd.setQuantity(1);
                bd.setPrice(drink.getPrice()); // lưu giá tại thời điểm mua

                em.persist(bd);
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ================= UPDATE QUANTITY =================
    public void updateQuantity(Integer id, int quantity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            BillDetail bd = em.find(BillDetail.class, id);
            if (bd != null) {
                if (quantity <= 0) {
                    em.remove(bd);
                } else {
                    bd.setQuantity(quantity);
                    em.merge(bd);
                }
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ================= DELETE BY BILL =================
    public void deleteByBill(Integer billId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            em.createQuery("DELETE FROM BillDetail bd WHERE bd.bill.id = :bid")
                    .setParameter("bid", billId)
                    .executeUpdate();

            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // ================= CALCULATE TOTAL =================
    public double getTotalByBill(Integer billId) {
        EntityManager em = getEntityManager();
        try {
            Double total = em.createQuery(
                    "SELECT SUM(bd.quantity * bd.price) FROM BillDetail bd WHERE bd.bill.id = :bid",
                    Double.class)
                    .setParameter("bid", billId)
                    .getSingleResult();

            return total != null ? total : 0.0;

        } finally {
            em.close();
        }
    }
}