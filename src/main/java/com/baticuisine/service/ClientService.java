package main.java.com.baticuisine.service;

import main.java.com.baticuisine.dao.Imp.ClientDaoImpl;
import main.java.com.baticuisine.model.Client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ClientService {
    private final ClientDaoImpl clientDao;
    private final HashMap<String, Client> clientMap;

    public ClientService(Connection connection) {
        clientMap = new HashMap<>();
        this.clientDao = new ClientDaoImpl(connection);
        loadClientsIntoMap();
    }

    private void loadClientsIntoMap() {
        List<Client> clients = clientDao.rechercherTous(); // Fetch all clients from the database
        clients.forEach(client -> clientMap.put(client.getNom().toLowerCase(), client)); // Use lower case for case-insensitive searching
    }

    public void addClient(Client client) {
        clientDao.add(client);
        clientMap.put(client.getNom().toLowerCase(), client); // Update the map after adding
    }

    public void updateClient(Client client, int id) {
        clientDao.update(client, id);
        clientMap.put(client.getNom().toLowerCase(), client); // Update the map after updating
    }

    public void deleteClient(int id) throws SQLException {
        Optional<Client> clientToDelete = Optional.ofNullable(clientDao.rechercherParId(id));
        clientToDelete.ifPresent(client -> {
            clientDao.delete(id);
            clientMap.remove(client.getNom().toLowerCase()); // Remove from the map after deleting
        });
    }

    public Optional<Client> getClient(int id) throws SQLException {
        return Optional.ofNullable(clientDao.rechercherParId(id));
    }

    public List<Client> getAllClients() {
        return clientDao.rechercherTous();
    }

    public Optional<Client> getClientByName(String nomClient) {
        return Optional.ofNullable(clientMap.get(nomClient.toLowerCase())); // Return Optional for better null safety
    }
}
