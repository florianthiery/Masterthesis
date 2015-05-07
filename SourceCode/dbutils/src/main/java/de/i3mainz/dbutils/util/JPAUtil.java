package de.i3mainz.dbutils.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory emFactory;

    static {
        try {
            //change between local and server
            emFactory = Persistence.createEntityManagerFactory("server");
        } catch (Throwable ex) {
            System.err.println("Cannot create EntityManagerFactory.");
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager createEntityManager() {
        return emFactory.createEntityManager();
    }

    public static void close() {
        emFactory.close();
    }
}
