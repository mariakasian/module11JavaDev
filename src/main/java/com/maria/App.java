package com.maria;

import com.maria.client.Client;
import com.maria.client.ClientCrudService;
import com.maria.planet.Planet;
import com.maria.planet.PlanetCrudService;
import com.maria.utils.InvalidIdException;
import com.maria.utils.InvalidNameException;

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
        System.out.println("=================");
    }
}