package main.java.com.baticuisine.controller;

import main.java.com.baticuisine.model.Client;
import main.java.com.baticuisine.service.ClientService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientController {
    private final ClientService clientService;
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class.getName());

    public ClientController(Connection connection) {
        this.clientService = new ClientService(connection);
    }

    public void addClient(Client client) {
        clientService.addClient(client);
        logger.info("Client added successfully.");
    }

    public void updateClient(Client client, int id) {
        clientService.updateClient(client, id);
        logger.info("Client with ID :" + id + " updated successfully.");
    }

    public void deleteClient(int id) throws SQLException {
        clientService.deleteClient(id);
        logger.info("Client with ID :" + id + " deleted successfully.");
    }

    public Optional<Client> getClientById(int id) throws SQLException {
        Optional<Client> client = clientService.getClient(id);
        client.ifPresentOrElse(
                c -> logger.info("Fetched client with ID :" + id + "."),
                () -> logger.warn("Client with ID :" + id + " does not exist.")
        );
        return client;
    }

    public List<Client> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        logger.info("Fetched all clients.");
        return clients;
    }

    public Optional<Client> rechercherParNom(String nomClient) {
        return clientService.getClientByName(nomClient)
                .map(client -> {
                    logger.info("Fetched client with name :" + nomClient + ".");
                    return client;
                })
                .or(() -> {
                    logger.warn("Client with name :" + nomClient + " does not exist.");
                    return Optional.empty();
                });
    }

}
