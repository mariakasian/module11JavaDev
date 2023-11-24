package com.maria.ticket;

import com.maria.client.Client;
import com.maria.planet.Planet;
import com.maria.utils.HibernateUtils;
import com.maria.utils.InvalidIdException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class TicketCrudService {
    public static long createTicket(Ticket ticket) {
        clientValidation(ticket.getClient());
        fromPlanetValidation(ticket.getFrom());
        toPlanetValidation(ticket.getTo());

        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(ticket);
            transaction.commit();
            return ticket.getId();
        }
    }

    public static Ticket getTicketById(long id) throws InvalidIdException {
        ticketIdValidation(id);
        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Ticket ticket = session.get(Ticket.class, id);
            transaction.commit();
            return ticket;
        }
    }

    public static List<Ticket> getTicketsByClient(Client client){
        clientValidation(client);
        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Ticket> criteriaQuery = criteriaBuilder.createQuery(Ticket.class);
            Root<Ticket> root = criteriaQuery.from(Ticket.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("client"), client));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    public static List<Ticket> getAllTickets() {
        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            return session.createNativeQuery("SELECT id, created_at, client_id, from_planet_id, to_planet_id FROM ticket", Ticket.class).list();
        }
    }

    public static void updateTicket(long id, Client client, Planet from, Planet to) throws InvalidIdException {
        ticketIdValidation(id);
        clientValidation(client);
        fromPlanetValidation(from);
        toPlanetValidation(to);

        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            NativeQuery<Ticket> query = session.createNativeQuery(
                    "UPDATE ticket SET client_id = :clientId, from_planet_id = :fromPlanetId, to_planet_id = :toPlanetId WHERE id = :ticketId",
                    Ticket.class
            );
            query.setParameter("clientId", client.getId());
            query.setParameter("fromPlanetId", from.getId());
            query.setParameter("toPlanetId", to.getId());
            query.setParameter("ticketId", id);
            query.executeUpdate();
            transaction.commit();
        }
    }

    public static void deleteTicketById(long id) throws InvalidIdException {
        ticketIdValidation(id);
        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            NativeQuery<Ticket> query = session.createNativeQuery(
                    "DELETE FROM ticket WHERE id = :ticketId",
                    Ticket.class
            );
            query.setParameter("ticketId", id);
            query.executeUpdate();
            transaction.commit();
        }
    }

    public static void ticketIdValidation(long id) throws InvalidIdException {
        if (id <= 0) {
            throw new InvalidIdException("Id must be greater than 0.");
        }
    }

    public static void clientValidation(Client client) throws NullPointerException {
        if (client == null) {
            throw new NullPointerException("There is no client in this ticket!");
        }
    }

    public static void fromPlanetValidation(Planet from) throws NullPointerException {
        if (from == null) {
            throw new NullPointerException("There is no planet in the “From”-column in this ticket!");
        }
    }

    public static void toPlanetValidation(Planet to) throws NullPointerException {
        if (to == null) {
            throw new NullPointerException("There is no planet in the “To”-column in this ticket!");
        }
    }

    // For testing
    public static void clearTicket() {
        try (Session session = HibernateUtils.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            NativeQuery<Ticket> query = session.createNativeQuery(
                    "DELETE FROM ticket",
                    Ticket.class
            );

            query.executeUpdate();
            transaction.commit();
        }
    }
}
