package com.maria;

import com.maria.client.Client;
import com.maria.client.ClientCrudService;
import com.maria.planet.Planet;
import com.maria.planet.PlanetCrudService;
import com.maria.ticket.Ticket;
import com.maria.ticket.TicketCrudService;
import com.maria.utils.InvalidIdException;
import com.maria.utils.InvalidNameException;

import java.sql.Timestamp;
import java.util.List;

public class App {
    public static void main(String[] args) throws InvalidNameException, InvalidIdException {
        System.out.println("=================");
        Client newClient = new Client();
        newClient.setName("Charles Chaplin");
        long id = ClientCrudService.create(newClient);
        System.out.println("Created new client with Id = " + id + " and name " + newClient.getName());

        System.out.println("=================");
        List<Client> clients = ClientCrudService.getAll();
        System.out.println("Client`s list:");
        clients.forEach(client -> System.out.println(client.getId() + " " + client.getName()));

        System.out.println("=================");
        ClientCrudService.updateName(1, "New Name");
        System.out.println("After updating client with id = 1: " + ClientCrudService.getById(1).getName());

        System.out.println("=================");
        ClientCrudService.deleteById(1);
        System.out.println("After deleting client with id = 1: " + ClientCrudService.getById(1));

        System.out.println("=========================================");
        Planet newPlanet = new Planet();
        newPlanet.setId("CHR1957");
        newPlanet.setName("Charon");
        PlanetCrudService.create(newPlanet);
        System.out.println("Created new planet: " + newPlanet.getId() + " " + newPlanet.getName());

        System.out.println("=================");
        List<Planet> planets = PlanetCrudService.getAll();
        System.out.println("Plant`s list:");
        planets.forEach(planet -> System.out.println(planet.getId() + " " + planet.getName()));

        System.out.println("=================");
        PlanetCrudService.updateName("GND7780", "New Name");
        System.out.println("After updating planet with id = GND7780: " +
                PlanetCrudService.getById("GND7780").getName());

        System.out.println("=================");
        PlanetCrudService.deleteById("GND7780");
        System.out.println("After deleting client with id = GND7780: " +
                PlanetCrudService.getById("GND7780"));

        System.out.println("=========================================");
        Ticket newTicket = new Ticket();
        String timestampString = "2023-12-25 20:20:00.000";
        Timestamp parcedTimestamp = Timestamp.valueOf(timestampString);
        newTicket.setCreatedAt(parcedTimestamp);
        newTicket.setClient(newClient);
        newTicket.setFrom(PlanetCrudService.getById("MER039"));
        newTicket.setTo(PlanetCrudService.getById("VEN072"));
        long newTicketId = TicketCrudService.createTicket(newTicket);
        System.out.println("Created new ticket with id = " + TicketCrudService.getTicketById(newTicketId).getId()
                + ", client_id = " + TicketCrudService.getTicketById(newTicketId).getClient().getId()
                + ", client name: " + TicketCrudService.getTicketById(newTicketId).getClient().getName()
                + ", from: " + TicketCrudService.getTicketById(newTicketId).getFrom().getName()
                + ", to: " + TicketCrudService.getTicketById(newTicketId).getTo().getName());

        System.out.println("=================");
        System.out.println("Getting tickets by client with id=5 ...");
        List<Ticket> tickets = TicketCrudService.getTicketsByClient(ClientCrudService.getById(5));
        for (Ticket ticket : tickets) {
            System.out.println("Ticket with id = " + ticket.getId()
                    + ", client_id = " + ticket.getClient().getId()
                    + ", client name: " + ticket.getClient().getName()
                    + ", from: " + ticket.getFrom().getName()
                    + ", to: " + ticket.getTo().getName());
        }

        System.out.println("=================");
        System.out.println("Getting all tickets ...");
        List<Ticket> allTickets = TicketCrudService.getAllTickets();
        for (Ticket ticket : allTickets) {
            System.out.println("Ticket with id = " + ticket.getId()
                    + ", client_id = " + ticket.getClient().getId()
                    + ", client name: " + ticket.getClient().getName()
                    + ", from: " + ticket.getFrom().getName()
                    + ", to: " + ticket.getTo().getName());
        }

        System.out.println("=================");
        System.out.println("Update ticket with id=11 ...");
        System.out.println("Before update: ticket with id = 11, client_id = "
                + TicketCrudService.getTicketById(11).getClient().getId()
                + ", client name: " + TicketCrudService.getTicketById(11).getClient().getName()
                + ", from: " + TicketCrudService.getTicketById(11).getFrom().getName()
                + ", to: " + TicketCrudService.getTicketById(11).getTo().getName());
        TicketCrudService.updateTicket(11, ClientCrudService.getById(2), PlanetCrudService.getById("EAR100"), PlanetCrudService.getById("URN1922"));
        System.out.println("After update: ticket with id = 11, client_id = "
                + TicketCrudService.getTicketById(11).getClient().getId()
                + ", client name: " + TicketCrudService.getTicketById(11).getClient().getName()
                + ", from: " + TicketCrudService.getTicketById(11).getFrom().getName()
                + ", to: " + TicketCrudService.getTicketById(11).getTo().getName());

        System.out.println("=================");
        System.out.println("Deleting ticket with id=11 ...");
        TicketCrudService.deleteTicketById(11);
        System.out.println("After deleting ticket with id=11: " + TicketCrudService.getTicketById(11));
        System.out.println("=========================================");
    }
}