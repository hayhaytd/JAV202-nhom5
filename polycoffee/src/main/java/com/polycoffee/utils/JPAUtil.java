package com.polycoffee.utils;

import jakarta.persistence.*;

public class JPAUtil {
    private static EntityManagerFactory factory;
    static {
        factory = Persistence.createEntityManagerFactory("PolyCoffeePU");
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}
