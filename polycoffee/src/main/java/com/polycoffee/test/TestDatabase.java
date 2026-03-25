package com.polycoffee.test;

import com.polycoffee.entity.User;
import com.polycoffee.utils.JPAUtil;

import jakarta.persistence.EntityManager;
import java.util.List;

public class TestDatabase {

    public static void main(String[] args) {

        System.out.println("=== TEST DATABASE ===");

        EntityManager em = JPAUtil.getEntityManager();

        try {
            // Query lấy tất cả user
            List<User> list = em.createQuery("SELECT u FROM User u", User.class)
                                .getResultList();

            System.out.println("Số lượng user: " + list.size());

            // In từng user
            for (User u : list) {
                System.out.println(u);
            }

        } catch (Exception e) {
            System.out.println("❌ Lỗi khi lấy dữ liệu!");
            e.printStackTrace();
        } finally {
            em.close();
        }

        System.out.println("=== END TEST ===");
    }
}