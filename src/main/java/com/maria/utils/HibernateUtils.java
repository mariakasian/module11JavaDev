package com.maria.utils;

import com.maria.client.Client;
import com.maria.planet.Planet;
import com.maria.ticket.Ticket;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtils {

    private static final HibernateUtils INSTANCE = new HibernateUtils();
    private final SessionFactory sessionFactory;

    private HibernateUtils() {
        this.sessionFactory = new Configuration()
                .addAnnotatedClass(Client.class)
                .addAnnotatedClass(Planet.class)
                .addAnnotatedClass(Ticket.class)
                .buildSessionFactory();

        flywayMigration(PropertyReader.getConnectionUrl());
    }

    public static HibernateUtils getInstance() {
        return INSTANCE;
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public void closeSessionFactory() {
        this.sessionFactory.close();
    }

    /* Flyway */
    private void flywayMigration(String connectionUrl) {
        Flyway flyway = Flyway.configure().dataSource(connectionUrl, null, null).load();
        flyway.migrate();
    }
}
