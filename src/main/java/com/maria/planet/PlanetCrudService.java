package com.maria.planet;

import com.maria.utils.HibernateUtils;
import com.maria.utils.InvalidIdException;
import com.maria.utils.InvalidNameException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class PlanetCrudService {
    public static void create(Planet planet) throws InvalidIdException, InvalidNameException {
        planetIdValidation(planet.getId());
        planetNameValidation(planet.getName());
        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(planet);
            transaction.commit();
        }
    }

    public static Planet getById(String id) throws InvalidIdException {
        planetIdValidation(id);

        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Planet planet = session.get(Planet.class, id);

            transaction.commit();
            return planet;
        }
    }

    public static void updateName(String id, String name) throws InvalidIdException, InvalidNameException {
        planetIdValidation(id);
        planetNameValidation(name);
        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            NativeQuery<Planet> query = session.createNativeQuery(
                    "UPDATE planet SET pname = :planetName WHERE id = :planetId",
                    Planet.class
            );
            query.setParameter("planetName", name);
            query.setParameter("planetId", id);

            query.executeUpdate();
            transaction.commit();
        }
    }

    public static void deleteById(String id) throws InvalidIdException {
        planetIdValidation(id);
        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            NativeQuery<Planet> query = session.createNativeQuery(
                    "DELETE FROM planet WHERE id = :planetId",
                    Planet.class
            );
            query.setParameter("planetId", id);

            query.executeUpdate();
            transaction.commit();
        }
    }

    public static List<Planet> getAll() {
        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            return session.createNativeQuery("SELECT id, pname FROM planet", Planet.class).list();
        }
    }

    public static void planetNameValidation(String name) throws InvalidNameException {
        if (name.length() < 1 || name.length() > 500) {
            throw new InvalidNameException("The plane name's length has to be from 1 to 500 characters!");
        }
    }

    public static void planetIdValidation(String id) throws InvalidIdException {
        String regex = "^[A-Z0-9]{1,10}$";
        if (!id.matches(regex)) {
            throw new InvalidIdException(
                    "The ID must consist exclusively of uppercase Latin letters and numbers up to 10 characters!");
        }
    }

    // For testing
    public static void clearPlanet() {
        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            NativeQuery<Planet> query = session.createNativeQuery(
                    "DELETE FROM planet",
                    Planet.class
            );

            query.executeUpdate();
            transaction.commit();
        }
    }
}
