package com.maria;

import com.maria.client.Client;
import com.maria.client.ClientCrudService;
import com.maria.utils.InvalidIdException;
import com.maria.utils.InvalidNameException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.maria.client.ClientCrudService.clientIdValidation;
import static com.maria.client.ClientCrudService.clientNameValidation;
import static org.junit.jupiter.api.Assertions.*;

class ClientCrudServiceTests {
    @Test
    void testClientCreatedAndGetByIdCorrect() throws InvalidIdException, InvalidNameException {
        Client newClient = new Client();
        newClient.setName("Katy Perry");
        long newClientId = ClientCrudService.create(newClient);
        String created = ClientCrudService.getById(newClientId).getName();

        assertEquals("Katy Perry", created);
    }

    @Test
    void testUpdateNameWorksCorrect() throws InvalidNameException, InvalidIdException {
        Client newClient = new Client();
        newClient.setName("Natalie Portman");
        long newClientId = ClientCrudService.create(newClient);

        String newName = "Lady Gaga";
        ClientCrudService.updateName(newClientId, newName);

        String updated = ClientCrudService.getById(newClientId).getName();

        assertEquals(newName, updated);
    }

    @Test
    void testDeleteByIdCorrect() throws InvalidIdException {
        long id = 1;
        assertDoesNotThrow(() -> ClientCrudService.deleteById(id));
        assertNull(ClientCrudService.getById(id));
    }

    @Test
    public void testGetAllCorrect() throws InvalidNameException {
        ClientCrudService.clear();
        List<Client> expectedClients = new ArrayList<>();

        Client client1 = new Client();
        client1.setName("Kit Harington");
        long client1Id = ClientCrudService.create(client1);
        client1.setId(client1Id);
        expectedClients.add(client1);

        Client client2 = new Client();
        client2.setName("Meghan Markle");
        long client2Id = ClientCrudService.create(client2);
        client2.setId(client2Id);
        expectedClients.add(client2);

        List<Client> actualClients = ClientCrudService.getAll();

        assertEquals(expectedClients, actualClients);
    }

    // Id and Name Validation
    @Test
    void testInvalidSmallNameThrowsException() {
        assertThrows(InvalidNameException.class, () ->
                clientNameValidation("v"));
    }

    @Test
    void testInvalidLongNameThrowsException() {
        StringBuilder name = new StringBuilder("A2");
        for (int i = 3; i < 205; i++) name.append(i);
        String finalName = name.toString();
        assertThrows(InvalidNameException.class, () ->
                clientNameValidation(finalName));
    }

    @Test
    void testInvalidNullIdThrowsException() {
        assertThrows(InvalidIdException.class, () ->
                clientIdValidation(0));
    }

    @Test
    void testInvalidNegativeIdThrowsException() {
        assertThrows(InvalidIdException.class, () ->
                clientIdValidation(-10));
    }
}
