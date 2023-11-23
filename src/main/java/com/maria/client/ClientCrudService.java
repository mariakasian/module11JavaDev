package com.maria.client;

import com.maria.utils.HibernateUtils;
import com.maria.utils.InvalidIdException;
import com.maria.utils.InvalidNameException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class ClientCrudService {

    public static long create(Client client) throws InvalidNameException {
        clientNameValidation(client.getName());
        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(client);
            transaction.commit();
            return client.getId();
        }
    }

    public static Client getById(long id) throws InvalidIdException {
        clientIdValidation(id);

        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Client client = session.get(Client.class, id);

            transaction.commit();
            return client;
        }
    }

    public static void updateName(long id, String name) throws InvalidIdException, InvalidNameException {
        clientIdValidation(id);
        clientNameValidation(name);
        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            NativeQuery<Client> query = session.createNativeQuery(
                    "UPDATE client SET cname = :clientName WHERE id = :clientId",
                    Client.class
            );
            query.setParameter("clientName", name);
            query.setParameter("clientId", id);

            query.executeUpdate();
            transaction.commit();
        }
    }

    public static void deleteById(long id) throws InvalidIdException {
        clientIdValidation(id);
        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            NativeQuery<Client> query = session.createNativeQuery(
                    "DELETE FROM client WHERE id = :clientId",
                    Client.class
            );
            query.setParameter("clientId", id);

            query.executeUpdate();
            transaction.commit();
        }
    }

    public static List<Client> getAll() {
        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            return session.createNativeQuery("SELECT id, cname FROM client", Client.class).list();
        }
    }

    public static void clientNameValidation(String name) throws InvalidNameException {
        if (name.length() < 3 || name.length() > 200) {
            throw new InvalidNameException("The client`s name length has to be from 3 to 200 characters.");
        }
    }

    public static void clientIdValidation(long id) throws InvalidIdException {
        if (id <= 0) {
            throw new InvalidIdException("Id must be greater than 0.");
        }
    }

    // For testing
    public static void clear() {
        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            NativeQuery<Client> query = session.createNativeQuery(
                    "DELETE FROM client",
                    Client.class
            );

            query.executeUpdate();
            transaction.commit();
        }
    }
}
