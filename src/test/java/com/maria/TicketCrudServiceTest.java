package com.maria;

import com.maria.client.Client;
import com.maria.client.ClientCrudService;
import com.maria.planet.Planet;
import com.maria.planet.PlanetCrudService;
import com.maria.ticket.Ticket;
import com.maria.ticket.TicketCrudService;
import com.maria.utils.InvalidIdException;
import com.maria.utils.InvalidNameException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.maria.ticket.TicketCrudService.*;
import static org.junit.jupiter.api.Assertions.*;

class TicketCrudServiceTest {
    Client newClient;
    Planet fromPlanet;
    Planet toPlanet;
    Ticket newTicket;

    @BeforeEach
    public void beforeEach() throws InvalidNameException, InvalidIdException {
        newClient = new Client();
        newClient.setName("Garfield");
        ClientCrudService.create(newClient);

        fromPlanet = new Planet();
        fromPlanet.setId("VTY6547");
        fromPlanet.setName("Alfa");
        PlanetCrudService.create(fromPlanet);

        toPlanet = new Planet();
        toPlanet.setId("KJF8650");
        toPlanet.setName("Beta");
        PlanetCrudService.create(toPlanet);

        newTicket = new Ticket();
        String timestampString = "2023-11-24 12:35:00.000";
        Timestamp parcedTimestamp = Timestamp.valueOf(timestampString);
        newTicket.setCreatedAt(parcedTimestamp);
        newTicket.setClient(newClient);
        newTicket.setFrom(fromPlanet);
        newTicket.setTo(toPlanet);
    }

    @AfterEach
    public void afterEach() {
        TicketCrudService.clearTicket();
        ClientCrudService.clear();
        PlanetCrudService.clearPlanet();
    }

    @Test
    void testTicketCreatedAndGetByIdCorrect() throws InvalidIdException {
        long newTicketId = TicketCrudService.createTicket(newTicket);
        Ticket createdTicket = TicketCrudService.getTicketById(newTicketId);
        assertEquals(newTicket, createdTicket);
    }

    @Test
    void testGetTicketsByClientCorrect() {
        List<Ticket> expectedTickets = new ArrayList<>();
        expectedTickets.add(newTicket);

        TicketCrudService.createTicket(newTicket);
        List<Ticket> actualTickets = getTicketsByClient(newClient);

        assertEquals(expectedTickets, actualTickets);
    }

    @Test
    void testGetAllTicketsCorrect() {
        List<Ticket> expectedTickets = new ArrayList<>();
        expectedTickets.add(newTicket);

        TicketCrudService.createTicket(newTicket);
        List<Ticket> actualTickets = getAllTickets();

        assertEquals(expectedTickets, actualTickets);
    }

    @Test
    void testUpdateTicketCorrect() throws InvalidNameException, InvalidIdException {
        long ticketId = TicketCrudService.createTicket(newTicket);

        Client client2 = new Client();
        client2.setName("Avatar");
        ClientCrudService.create(client2);

        Planet fromPlanet2 = new Planet();
        fromPlanet2.setId("DTG453");
        fromPlanet2.setName("Gamma");
        PlanetCrudService.create(fromPlanet2);

        Planet toPlanet2 = new Planet();
        toPlanet2.setId("LET974");
        toPlanet2.setName("Omega");
        PlanetCrudService.create(toPlanet2);

        updateTicket(ticketId, client2, fromPlanet2, toPlanet2);

        assertEquals("Avatar", TicketCrudService.getTicketById(ticketId).getClient().getName());
        assertEquals("DTG453", TicketCrudService.getTicketById(ticketId).getFrom().getId());
        assertEquals("LET974", TicketCrudService.getTicketById(ticketId).getTo().getId());
    }

    @Test
    void testDeleteTicketByIdCorrect() throws InvalidIdException {
        long ticketId = TicketCrudService.createTicket(newTicket);

        assertDoesNotThrow(() -> TicketCrudService.deleteTicketById(ticketId));
        assertNull(TicketCrudService.getTicketById(ticketId));
    }

    //Validations
    @Test
    void testInvalidNullTicketIdThrowsException() {
        assertThrows(InvalidIdException.class, () ->
                ticketIdValidation(0));
    }

    @Test
    void testInvalidNegativeTicketIdThrowsException() {
        assertThrows(InvalidIdException.class, () ->
                ticketIdValidation(-10));
    }

    @Test
    void testNullClientTrowsException() {
        assertThrows(NullPointerException.class, () ->
                clientValidation(null));
    }

    @Test
    void testNullFromPlanetTrowsException() {
        assertThrows(NullPointerException.class, () ->
                fromPlanetValidation(null));
    }

    @Test
    void testNullToPlanetTrowsException() {
        assertThrows(NullPointerException.class, () ->
                toPlanetValidation(null));
    }
}